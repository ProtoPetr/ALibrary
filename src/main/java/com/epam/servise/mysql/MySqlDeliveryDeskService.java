package com.epam.servise.mysql;

import com.epam.dao.DaoFactory;
import com.epam.dao.DeliveryDeskDao;
import com.epam.entity.Book;
import com.epam.entity.DeliveryDesk;
import com.epam.servise.BookService;
import com.epam.servise.DeliveryDeskService;
import com.epam.servise.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class implements DeliveryDeskService interface
 * the interface is implemented according to the needs of the view layer
 */
public class MySqlDeliveryDeskService implements DeliveryDeskService {
    private static final Logger logger = LogManager.getLogger(MySqlDeliveryDeskService.class);
    private static BookService bss;
    private static DeliveryDeskDao deliveryDeskDao;
    private static DaoFactory daoFactory;

    /**
     * Block initializes objects for working with dao layer
     */
    {
        try {
            bss = ServiceFactory.getServiceFactory("MySQL").getBookService();
            daoFactory = DaoFactory.getDaoFactory("MySQL");
            deliveryDeskDao = daoFactory.getDeliveryDeskDao();
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * The method find DeliveryDesk by user id
     * if there are lease violations, a penalty is set
     */
    @Override
    public List<DeliveryDesk> getUserDeliveryDesk(long userId) {
        Connection connection = null;
        List<DeliveryDesk> list = new ArrayList<>();
        try {
            connection = daoFactory.getConnection();
            connection.setAutoCommit(false);
            list = deliveryDeskDao.findByUserId(connection, DELIVERY_DESK_SQL.GET_DELIVERY_DESK_BY_USER_ID.QUERY, userId)
            .stream().peek(dd -> {
               if (dd.getReturnDate() != null && "issued".equals(dd.getStatus()) && new Date(new Date().getTime() - 24*60*60*1000).after(dd.getReturnDate())) {
                   dd.setPenalty(10);
                   setPenalty(dd.getId(), dd.getBookId());
               } else {
                   dd.setPenalty(0);
               }
            }).collect(Collectors.toList());
            connection.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error(e.getMessage(), e);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return list;
    }

    /**
     * The method add new order in DeliveryDesk
     * according user and book id
     */
    @Override
    public void addBookToDeliveryDesk(long userId, long bookId) {
        Connection connection = null;
        long deliveryDeskId;
        try {
            connection = daoFactory.getConnection();
            connection.setAutoCommit(false);
            if (deliveryDeskDao.findByUserId(connection, DELIVERY_DESK_SQL.GET_DELIVERY_DESK_BY_USER_ID.QUERY, userId).isEmpty()) {
                deliveryDeskId = deliveryDeskDao.createDeliveryDeskForUser(connection, DELIVERY_DESK_SQL.CREATE_DELIVERY_DESK_FOR_USER.QUERY, userId);
            } else {
                deliveryDeskId = deliveryDeskDao.findByUserId(connection, DELIVERY_DESK_SQL.GET_DELIVERY_DESK_BY_USER_ID.QUERY, userId).get(0).getId();
            }
            deliveryDeskDao.create(connection, DELIVERY_DESK_SQL.ADD_BOOK_TO_DELIVERY_DESK_BY_ID.QUERY, deliveryDeskId, bookId);
            connection.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error(e.getMessage(), e);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * The method updates the status of a current order in delivery desk
     * status = `issued`
     * return day - issue day = 1
     * decrement book count
     */
    @Override
    public void issueBookFromDeliveryDeskOnDay(long deliveryDeskId, long bookId) {
        Connection connection = null;
        try  {
            connection = daoFactory.getConnection();
            connection.setAutoCommit(false);
            deliveryDeskDao.update(connection, DELIVERY_DESK_SQL.ISSUE_BOOK_ON_DAY.QUERY, deliveryDeskId, bookId);
            Book book = bss.getBookById(bookId);
            book.setCount(book.getCount() - 1);
            bss.updateBook(book);
            connection.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error(e.getMessage(), e);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * The method updates the status of a current order in delivery desk
     * status = `issued`
     * return day - issue day = 30
     * decrement book count
     */
    @Override
    public void issueBookFromDeliveryDeskOnMonth(long deliveryDeskId, long bookId) {
        Connection connection = null;
        try {
            connection = daoFactory.getConnection();
            connection.setAutoCommit(false);
            deliveryDeskDao.update(connection, DELIVERY_DESK_SQL.ISSUE_BOOK_ON_MONTH.QUERY, deliveryDeskId, bookId);
            Book book = bss.getBookById(bookId);
            book.setCount(book.getCount() - 1);
            bss.updateBook(book);
            connection.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error(e.getMessage(), e);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * The method updates the status of a current order in delivery desk
     * status = `returned`
     * increment book count
     */
    @Override
    public void returnBookToDeliveryDesk(long deliveryDeskId, long bookId) {
        Connection connection = null;
        try {
            connection = daoFactory.getConnection();
            connection.setAutoCommit(false);
            deliveryDeskDao.update(connection, DELIVERY_DESK_SQL.GET_BOOK.QUERY, deliveryDeskId, bookId);
            Book book = bss.getBookById(bookId);
            book.setCount(book.getCount() + 1);
            bss.updateBook(book);
            connection.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error(e.getMessage(), e);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * The method set a penalty of a current order in delivery desk
     */
    @Override
    public void setPenalty(long deliveryDeskId, long bookId) {
        try (Connection connection = daoFactory.getConnection()) {
            deliveryDeskDao.update(connection, DELIVERY_DESK_SQL.SET_PENALTY.QUERY, deliveryDeskId, bookId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * The method delete current order in delivery desk
     * according delivery desk and book id
     */
    @Override
    public void deleteBookFromDeliveryDesk(long deliveryDeskId, long bookId) {
        try (Connection connection = daoFactory.getConnection()) {
            deliveryDeskDao.delete(connection, DELIVERY_DESK_SQL.DELETE_BOOK_FROM_DELIVERY_DESK.QUERY, deliveryDeskId, bookId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * The method find all delivery desks
     */
    @Override
    public List<DeliveryDesk> getAllDeliveryDesk() {
        List<DeliveryDesk> list = new ArrayList<>();
        try (Connection connection = daoFactory.getConnection())
        {
            list = deliveryDeskDao.findAll(connection, DELIVERY_DESK_SQL.GET_ALL_DELIVERY_DESK.QUERY);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * SQL queries for DeliveryDesk entity
     */
    enum DELIVERY_DESK_SQL {
        ADD_BOOK_TO_DELIVERY_DESK_BY_ID("INSERT INTO delivery_desk_has_book (delivery_desk_id, book_id) VALUES (?, ?)"),
        IS_USER_ID_EXIST_IN_DELIVERY_DESK("SELECT * FROM delivery_desk WHERE user_id = ?"),
        ADD_BOOK_TO_DELIVERY_DESK("INSERT INTO delivery_desk_has_book (delivery_desk_id, book_id) VALUES (?, ?)"),
        ISSUE_BOOK_ON_DAY("UPDATE delivery_desk_has_book SET status = 'issued', date_of_issue = now(), return_date = now() WHERE delivery_desk_id = ? AND book_id = ?"),
        ISSUE_BOOK_ON_MONTH("UPDATE delivery_desk_has_book SET status = 'issued', date_of_issue = now(), return_date = DATE_ADD(now(), INTERVAL 30 DAY) WHERE delivery_desk_id = ? AND book_id = ?"),
        SET_PENALTY("UPDATE delivery_desk_has_book SET penalty = 10 WHERE delivery_desk_id = ? AND book_id = ?"),
        OFF_PENALTY("UPDATE delivery_desk_has_book SET penalty = 0 WHERE delivery_desk_id = ? AND book_id = ?"),
        GET_BOOK("UPDATE delivery_desk_has_book SET status = 'returned' WHERE delivery_desk_id = ? AND book_id = ?"),
        CREATE_DELIVERY_DESK_FOR_USER("INSERT INTO delivery_desk (user_id) VALUES (?)"),
        DELETE_BOOK_FROM_DELIVERY_DESK("DELETE FROM delivery_desk_has_book WHERE delivery_desk_id = ? AND book_id = ?"),
        GET_DELIVERY_DESK_BY_USER_ID("SELECT dd.id, dd.user_id, dhb.book_id AS book_id, b.name AS book_name, dhb.date_of_issue AS date_of_issue, "
                + "dhb.return_date AS return_date, dhb.status AS status, dhb.penalty AS penalty FROM delivery_desk dd "
                + "INNER JOIN delivery_desk_has_book dhb ON id = dhb.delivery_desk_id "
                + "INNER JOIN book b ON dhb.book_id = b.id "
                + "WHERE dd.user_id = ?"),
        GET_ALL_DELIVERY_DESK("SELECT dd.id, dd.user_id, dhb.book_id AS book_id, b.name AS book_name, dhb.date_of_issue AS date_of_issue, "
                + "dhb.return_date AS return_date, dhb.status AS status, dhb.penalty AS penalty FROM delivery_desk dd "
                + "INNER JOIN delivery_desk_has_book dhb ON id = dhb.delivery_desk_id "
                + "INNER JOIN book b ON dhb.book_id = b.id");

        String QUERY;

        DELIVERY_DESK_SQL(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
