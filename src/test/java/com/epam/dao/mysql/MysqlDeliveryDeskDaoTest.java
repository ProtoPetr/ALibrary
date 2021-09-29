package com.epam.dao.mysql;

import com.epam.dao.*;
import com.epam.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MysqlDeliveryDeskDaoTest {
    private static final Logger logger = LogManager.getLogger(MysqlDeliveryDeskDaoTest.class);
    private final static String JDBC_DRIVER = "org.h2.Driver";
    private final static String DB_URL = "jdbc:h2:file:/home/dev/IdeaProjects/ALibrary/database/testDataBase/testLibrary";
    private final static String URL_CONNECTION = "jdbc:h2:file:/home/dev/IdeaProjects/ALibrary/database/testDataBase/testLibrary;"
            + "user=root;password=12345;";
    private final static String USER = "root";
    private final static String PASS = "12358";

    private static DeliveryDeskDao dd;
    private static BookDao bd;
    private static AuthorDao ad;
    private static GenreDao gd;
    private static PublisherDao pd;

    @BeforeClass
    public static void beforeTest() throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);

        dd = MysqlDeliveryDeskDao.getInstance();
        bd = MysqlBookDao.getInstance();
        ad = MysqlAuthorDao.getInstance();
        pd = MysqlPublisherDao.getInstance();
        gd = MySqlGenreDao.getInstance();

        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS publisher (\n" +
                    " id INT NOT NULL AUTO_INCREMENT,\n" +
                    " name VARCHAR(45) NOT NULL,\n" +
                    " PRIMARY KEY (id));\n" +
                    "CREATE TABLE IF NOT EXISTS author (\n" +
                    " id INT NOT NULL AUTO_INCREMENT,\n" +
                    " name VARCHAR(45) NOT NULL,\n" +
                    " PRIMARY KEY (id));\n" +
                    "CREATE TABLE IF NOT EXISTS genre (\n" +
                    " id INT NOT NULL AUTO_INCREMENT,\n" +
                    " name VARCHAR(45) NOT NULL,\n" +
                    " PRIMARY KEY (id));\n" +
                    "CREATE TABLE IF NOT EXISTS book (\n" +
                    "  id INT NOT NULL AUTO_INCREMENT,\n" +
                    "  name VARCHAR(255) NOT NULL,\n" +
                    "  author_id INT NOT NULL,\n" +
                    "  genre_id INT NOT NULL,\n" +
                    "  publisher_id INT NOT NULL,\n" +
                    "  year_of_publishing INT NOT NULL,\n" +
                    "  count INT NOT NULL,\n" +
                    "  image LONGBLOB NULL,\n" +
                    "  PRIMARY KEY (id),\n" +
                    "  CONSTRAINT author_id\n" +
                    "    FOREIGN KEY (author_id)\n" +
                    "    REFERENCES author (id)\n" +
                    "    ON DELETE RESTRICT\n" +
                    "    ON UPDATE CASCADE,\n" +
                    "  CONSTRAINT genre_id\n" +
                    "    FOREIGN KEY (genre_id)\n" +
                    "    REFERENCES genre (id)\n" +
                    "    ON DELETE RESTRICT\n" +
                    "    ON UPDATE CASCADE,\n" +
                    "  CONSTRAINT publisher_id\n" +
                    "    FOREIGN KEY (publisher_id)\n" +
                    "    REFERENCES publisher (id)\n" +
                    "    ON DELETE RESTRICT\n" +
                    "    ON UPDATE CASCADE);\n" +
                    "CREATE TABLE IF NOT EXISTS delivery_desk (\n" +
                    " id INT NOT NULL AUTO_INCREMENT,\n" +
                    "  user_id INT NOT NULL,\n" +
                    "  PRIMARY KEY (id));\n" +
                    "CREATE TABLE IF NOT EXISTS delivery_desk_has_book (\n" +
                    " delivery_desk_id INT NOT NULL,\n" +
                    "  book_id INT NOT NULL,\n" +
                    "  date_of_issue DATE NULL,\n" +
                    "  return_date DATE NULL,\n" +
                    "  status ENUM('ordered', 'issued', 'returned') NOT NULL DEFAULT 'ordered',\n" +
                    "  penalty INT NULL DEFAULT 0,\n" +
                    "  PRIMARY KEY (delivery_desk_id, book_id),\n" +
                    "  CONSTRAINT delivery_desk_id\n" +
                    "    FOREIGN KEY (delivery_desk_id)\n" +
                    "    REFERENCES delivery_desk (id)\n" +
                    "    ON DELETE CASCADE\n" +
                    "    ON UPDATE CASCADE,\n" +
                    "  CONSTRAINT book_id\n" +
                    "    FOREIGN KEY (book_id)\n" +
                    "    REFERENCES book (id)\n" +
                    "    ON DELETE RESTRICT\n" +
                    "    ON UPDATE CASCADE);\n";
            statement.executeUpdate(sql);
        }
    }

    @Test
    public void shouldReturnAllDeliveryDesks() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            ad.addAuthor(connection, "INSERT INTO author SET name = ?", new Author("Аристотель"));
            ad.addAuthor(connection, "INSERT INTO author SET name = ?", new Author("Платон"));
            gd.addGenre(connection, "INSERT INTO genre SET name = ?", new Genre("Философия"));
            gd.addGenre(connection, "INSERT INTO genre SET name = ?", new Genre("Наука"));
            pd.addPublisher(connection, "INSERT INTO publisher SET name = ?", new Publisher("Наука"));
            bd.create(connection, MysqlBookDaoTest.Book_SQL.ADD_BOOK.QUERY, new Book("Метафизика", "Аристотель", "Наука", "Наука", 1991, 20));
            bd.create(connection, MysqlBookDaoTest.Book_SQL.ADD_BOOK.QUERY, new Book("Апология Сократа", "Платон", "Философия", "Наука", 1988, 20));
            dd.createDeliveryDeskForUser(connection, DELIVERY_DESK_SQL.CREATE_DELIVERY_DESK_FOR_USER.QUERY, 1);
            dd.create(connection, DELIVERY_DESK_SQL.ADD_BOOK_TO_DELIVERY_DESK.QUERY, 1, 1);
            List<DeliveryDesk> list = dd.findAll(connection, DELIVERY_DESK_SQL.GET_ALL_DELIVERY_DESK.QUERY);
            Assert.assertEquals(1, list.get(0).getId());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void shouldReturnAllDeliveryDeskByUserId() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            ad.addAuthor(connection, "INSERT INTO author SET name = ?", new Author("Аристотель"));
            ad.addAuthor(connection, "INSERT INTO author SET name = ?", new Author("Платон"));
            gd.addGenre(connection, "INSERT INTO genre SET name = ?", new Genre("Философия"));
            gd.addGenre(connection, "INSERT INTO genre SET name = ?", new Genre("Наука"));
            pd.addPublisher(connection, "INSERT INTO publisher SET name = ?", new Publisher("Наука"));
            bd.create(connection, MysqlBookDaoTest.Book_SQL.ADD_BOOK.QUERY, new Book("Метафизика", "Аристотель", "Наука", "Наука", 1991, 20));
            bd.create(connection, MysqlBookDaoTest.Book_SQL.ADD_BOOK.QUERY, new Book("Апология Сократа", "Платон", "Философия", "Наука", 1988, 20));
            dd.createDeliveryDeskForUser(connection, DELIVERY_DESK_SQL.CREATE_DELIVERY_DESK_FOR_USER.QUERY, 1);
            dd.create(connection, DELIVERY_DESK_SQL.ADD_BOOK_TO_DELIVERY_DESK.QUERY, 1, 1);
            List<DeliveryDesk> list = dd.findByUserId(connection, DELIVERY_DESK_SQL.GET_ALL_DELIVERY_DESK.QUERY, 1);
            Assert.assertEquals(1, list.get(0).getUserId());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void shouldUpdateDeliveryDesk() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            ad.addAuthor(connection, "INSERT INTO author SET name = ?", new Author("Аристотель"));
            ad.addAuthor(connection, "INSERT INTO author SET name = ?", new Author("Платон"));
            gd.addGenre(connection, "INSERT INTO genre SET name = ?", new Genre("Философия"));
            gd.addGenre(connection, "INSERT INTO genre SET name = ?", new Genre("Наука"));
            pd.addPublisher(connection, "INSERT INTO publisher SET name = ?", new Publisher("Наука"));
            bd.create(connection, MysqlBookDaoTest.Book_SQL.ADD_BOOK.QUERY, new Book("Метафизика", "Аристотель", "Наука", "Наука", 1991, 20));
            bd.create(connection, MysqlBookDaoTest.Book_SQL.ADD_BOOK.QUERY, new Book("Апология Сократа", "Платон", "Философия", "Наука", 1988, 20));
            dd.createDeliveryDeskForUser(connection, DELIVERY_DESK_SQL.CREATE_DELIVERY_DESK_FOR_USER.QUERY, 1);
            dd.create(connection, DELIVERY_DESK_SQL.ADD_BOOK_TO_DELIVERY_DESK.QUERY, 1, 1);
            List<DeliveryDesk> list = dd.findAll(connection, DELIVERY_DESK_SQL.GET_ALL_DELIVERY_DESK.QUERY);
            list.get(0).setPenalty(10);
            list = dd.findAll(connection, DELIVERY_DESK_SQL.GET_ALL_DELIVERY_DESK.QUERY);
            Assert.assertEquals(10, list.get(0).getPenalty());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void shouldDeleteDeliveryDesk() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            ad.addAuthor(connection, "INSERT INTO author SET name = ?", new Author("Аристотель"));
            ad.addAuthor(connection, "INSERT INTO author SET name = ?", new Author("Платон"));
            gd.addGenre(connection, "INSERT INTO genre SET name = ?", new Genre("Философия"));
            gd.addGenre(connection, "INSERT INTO genre SET name = ?", new Genre("Наука"));
            pd.addPublisher(connection, "INSERT INTO publisher SET name = ?", new Publisher("Наука"));
            bd.create(connection, MysqlBookDaoTest.Book_SQL.ADD_BOOK.QUERY, new Book("Метафизика", "Аристотель", "Наука", "Наука", 1991, 20));
            bd.create(connection, MysqlBookDaoTest.Book_SQL.ADD_BOOK.QUERY, new Book("Апология Сократа", "Платон", "Философия", "Наука", 1988, 20));
            dd.createDeliveryDeskForUser(connection, DELIVERY_DESK_SQL.CREATE_DELIVERY_DESK_FOR_USER.QUERY, 1);
            dd.create(connection, DELIVERY_DESK_SQL.ADD_BOOK_TO_DELIVERY_DESK.QUERY, 1, 1);
            List<DeliveryDesk> list1 = dd.findAll(connection, DELIVERY_DESK_SQL.GET_ALL_DELIVERY_DESK.QUERY);
            dd.delete(connection, DELIVERY_DESK_SQL.DELETE_BOOK_FROM_DELIVERY_DESK.QUERY, 1, 1);
            List<DeliveryDesk> list2 = dd.findAll(connection, DELIVERY_DESK_SQL.GET_ALL_DELIVERY_DESK.QUERY);
            Assert.assertNotEquals(list1, list2);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @AfterClass
    public static void afterClass() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement()) {
            String sql = "DROP TABLE delivery_desk_has_book;\n" +
                    "DROP TABLE delivery_desk;\n" +
                    "DROP TABLE book;\n" +
                    "DROP TABLE genre;\n" +
                    "DROP TABLE author;\n" +
                    "DROP TABLE publisher;\n";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

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
