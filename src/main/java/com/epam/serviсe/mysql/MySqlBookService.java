package com.epam.serviсe.mysql;

import com.epam.dao.*;
import com.epam.entity.Author;
import com.epam.entity.Book;
import com.epam.entity.Genre;
import com.epam.entity.Publisher;
import com.epam.serviсe.BookService;
import com.epam.supplies.SearchType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements BookService interface
 * the interface is implemented according to the needs of the view layer
 */
public class MySqlBookService implements BookService {
    private static final Logger logger = LogManager.getLogger(MySqlBookService.class);
    private static AuthorDao authorDao;
    private static PublisherDao publisherDao;
    private static GenreDao genreDao;
    private static BookDao bookDao;
    private static DaoFactory daoFactory;

    /**
     * Block initializes objects for working with dao layer
     */
    {
        try {
            daoFactory = DaoFactory.getDaoFactory("MySQL");
            bookDao = daoFactory.getBookDao();
            authorDao = daoFactory.getAuthorDao();
            publisherDao = daoFactory.getPublisherDao();
            genreDao = daoFactory.getGenreDao();
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * The method add new book in database
     * if book author is not exist in database - in author table add new author
     * if book genre is not exist in database - in genre table add new genre
     * if book publisher is not exist in database - in publisher table add new publisher
     */
    @Override
    public void createBook(Book book) {
        Connection connection = null;
        try {
            connection = daoFactory.getConnection();
            connection.setAutoCommit(false);
            if (!authorDao.isExist(connection, Book_SQL.GET_AUTHOR_BY_NAME.QUERY, book.getAuthor())) {
                authorDao.addAuthor(connection, Book_SQL.ADD_AUTHOR.QUERY, new Author(book.getAuthor()));
            }
            if (!genreDao.isExist(connection, Book_SQL.GET_GENRE_BY_NAME.QUERY, book.getGenre())) {
                genreDao.addGenre(connection, Book_SQL.ADD_GENRE.QUERY, new Genre(book.getGenre()));
            }
            if (!publisherDao.isExist(connection, Book_SQL.GET_PUBLISHER_BY_NAME.QUERY, book.getPublisher())) {
                publisherDao.addPublisher(connection, Book_SQL.ADD_PUBLISHER.QUERY, new Publisher(book.getPublisher()));
            }
            bookDao.create(connection, Book_SQL.ADD_BOOK.QUERY, book);
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
     * The method updates the fields of a current book
     * if book author is not exist in database - in author table add new author
     * if book genre is not exist in database - in genre table add new genre
     * if book publisher is not exist in database - in publisher table add new publisher
     */
    @Override
    public void updateBook(Book book) {
        Connection connection = null;
        try {
            connection = daoFactory.getConnection();
            connection.setAutoCommit(false);
            if (!authorDao.isExist(connection, Book_SQL.GET_AUTHOR_BY_NAME.QUERY, book.getAuthor())) {
                authorDao.addAuthor(connection, Book_SQL.ADD_AUTHOR.QUERY, new Author(book.getAuthor()));
            }
            if (!genreDao.isExist(connection, Book_SQL.GET_GENRE_BY_NAME.QUERY, book.getGenre())) {
                genreDao.addGenre(connection, Book_SQL.ADD_GENRE.QUERY, new Genre(book.getGenre()));
            }
            if (!publisherDao.isExist(connection, Book_SQL.GET_PUBLISHER_BY_NAME.QUERY, book.getPublisher())) {
                publisherDao.addPublisher(connection, Book_SQL.ADD_PUBLISHER.QUERY, new Publisher(book.getPublisher()));
            }
            bookDao.update(connection, Book_SQL.UPDATE_BOOK.QUERY, book);
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
     * The method delete current book by id
     */
    @Override
    public void deleteBook(long id) {
        try (Connection connection = daoFactory.getConnection()) {
            bookDao.delete(connection, Book_SQL.DELETE_BOOK_BY_ID.QUERY, id);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * The method find book by id
     */
    @Override
    public Book getBookById(long id) {
        Book book = new Book();
        if (id == 0) {
            return book;
        }

        try (Connection connection = daoFactory.getConnection()) {
            book = bookDao.findById(connection, Book_SQL.GET_BOOK_BY_ID.QUERY, id);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return book;
    }

    /**
     * The method find book by fields:
     * name of author
     * name of book
     */
    @Override
    public List<Book> getBooksBySearch(String searchStr, SearchType type) {
        List<Book> list = new ArrayList<>();
        if (type == SearchType.AUTHOR) {
            try (Connection connection = daoFactory.getConnection()) {
                list = bookDao.findByName(connection, Book_SQL.FIND_BOOK_BY_AUTHOR.QUERY,
                        "%" + searchStr.toLowerCase() + "%");
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (type == SearchType.NAME) {
            try (Connection connection = daoFactory.getConnection()) {
                list = bookDao.findByName(connection, Book_SQL.FIND_BOOK_BY_NAME.QUERY,
                        "%" + searchStr.toLowerCase() + "%");
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return list;
    }

    /**
     * The method find book by first letter
     */
    @Override
    public List<Book> getBooksByLetter(String letter) {
        List<Book> list = new ArrayList<>();
        try (Connection connection = daoFactory.getConnection()) {
            list = bookDao.findByName(connection, Book_SQL.GET_BOOKS_BY_LETTER.QUERY, letter);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * The method find all books by genre id
     */
    @Override
    public List<Book> getBooksByGenreId(long id) {
        List<Book> list = new ArrayList<>();
        try (Connection connection = daoFactory.getConnection()) {
            list = bookDao.getBooksByGenreId(connection, Book_SQL.GET_BOOKS_BY_GENRE_ID.QUERY, id);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * The method find all books
     */
    @Override
    public List<Book> getAllBooks() {
        List<Book> list = new ArrayList<>();
        try (Connection connection = daoFactory.getConnection()) {
            list = bookDao.findAll(connection, Book_SQL.FIND_ALL_BOOKS.QUERY);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * The method find all books by genre id
     * oder by name of book
     */
    @Override
    public List<Book> getAllBooksSortedByName() {
        List<Book> list = new ArrayList<>();
        try (Connection connection = daoFactory.getConnection()) {
            list = bookDao.findAll(connection, Book_SQL.FIND_ALL_BOOKS_ORDER_BY_NAME.QUERY);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * The method find all books by genre id
     * oder by author of book
     */
    @Override
    public List<Book> getAllBooksSortedByAuthor() {
        List<Book> list = new ArrayList<>();
        try (Connection connection = daoFactory.getConnection()) {
            list = bookDao.findAll(connection, Book_SQL.FIND_ALL_BOOKS_ORDER_BY_AUTHOR.QUERY);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * The method find all books by genre id
     * oder by publisher of book
     */
    @Override
    public List<Book> getAllBooksSortedByPublisher() {
        List<Book> list = new ArrayList<>();
        try (Connection connection = daoFactory.getConnection()) {
            list = bookDao.findAll(connection, Book_SQL.FIND_ALL_BOOKS_ORDER_BY_PUBLISHER.QUERY);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * The method find all books by genre id
     * oder by publish year of book
     */
    @Override
    public List<Book> getAllBooksSortedByYearOfPublishing() {
        List<Book> list = new ArrayList<>();
        try (Connection connection = daoFactory.getConnection()) {
            list = bookDao.findAll(connection, Book_SQL.FIND_ALL_BOOKS_ORDER_BY_YEAR_OF_PUBLISHING.QUERY);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * SQL queries for Book entity
     */
    enum Book_SQL {
        UPDATE_BOOK("UPDATE book SET name = ?, author_id = (SELECT id FROM author WHERE name = ?), genre_id = (SELECT id FROM genre WHERE name = ?), "
                + "publisher_id = (SELECT id FROM publisher WHERE name = ?), year_of_publishing = ?, count = ? "
                + "WHERE id = ?"),
        ADD_BOOK("INSERT INTO book SET name = ?, author_id = (SELECT id FROM author WHERE name = ?), genre_id = (SELECT id FROM genre WHERE name = ?), "
                + "publisher_id = (SELECT id FROM publisher WHERE name = ?), year_of_publishing = ?, count = ?"),
        DELETE_BOOK_BY_ID("DELETE FROM book WHERE id = ?"),
        GET_AUTHOR_BY_NAME("SELECT * FROM author WHERE name = ?"),
        ADD_AUTHOR("INSERT INTO author SET name = ?"),
        GET_GENRE_BY_NAME("SELECT * FROM genre WHERE name = ?"),
        ADD_GENRE("INSERT INTO genre SET name = ?"),
        GET_PUBLISHER_BY_NAME("SELECT * FROM publisher WHERE name = ?"),
        ADD_PUBLISHER("INSERT INTO publisher SET name = ?"),
        GET_BOOK_BY_ID("SELECT b.id, b.name, b.year_of_publishing, b.count, p.name AS publisher, a.name AS author, g.name AS genre, b.image FROM book b "
                + "INNER JOIN author a ON author_id = a.id "
                + "INNER JOIN genre g ON genre_id = g.id "
                + "INNER JOIN publisher p ON publisher_id = p.id "
                + "WHERE b.id = ? ORDER BY b.name"),
        GET_BOOKS_BY_GENRE_ID("SELECT b.id, b.name, b.year_of_publishing, b.count, p.name AS publisher, a.name AS author, g.name AS genre, b.image FROM book b "
                + "INNER JOIN author a ON author_id = a.id "
                + "INNER JOIN genre g ON genre_id = g.id "
                + "INNER JOIN publisher p ON publisher_id = p.id "
                + "WHERE genre_id = ? ORDER BY b.name"),
        GET_BOOKS_BY_LETTER("SELECT b.id, b.name, b.year_of_publishing, b.count, p.name AS publisher, a.name AS author, g.name AS genre, b.image FROM book b "
                + "INNER JOIN author a ON author_id = a.id "
                + "INNER JOIN genre g ON genre_id = g.id "
                + "INNER JOIN publisher p ON publisher_id = p.id "
                + "WHERE substr(b.name,1,1) = ? ORDER BY b.name"),
        FIND_BOOK_BY_NAME("SELECT b.id, b.name, b.year_of_publishing, b.count, p.name AS publisher, a.name AS author, g.name AS genre, b.image FROM book b "
                + "INNER JOIN author a ON author_id = a.id "
                + "INNER JOIN genre g ON genre_id = g.id "
                + "INNER JOIN publisher p ON publisher_id = p.id "
                + "WHERE lower(b.name) like ? ORDER BY b.name"),
        FIND_BOOK_BY_AUTHOR("SELECT b.id, b.name, b.year_of_publishing, b.count, p.name AS publisher, a.name AS author, g.name AS genre, b.image FROM book b "
                + "INNER JOIN author a ON author_id = a.id "
                + "INNER JOIN genre g ON genre_id = g.id "
                + "INNER JOIN publisher p ON publisher_id = p.id "
                + "WHERE lower(a.name) like ? ORDER BY b.name"),
        FIND_ALL_BOOKS("SELECT b.id, b.name, b.year_of_publishing, b.count, p.name AS publisher, a.name AS author, g.name AS genre, b.image FROM book b "
                + "INNER JOIN author a ON author_id = a.id "
                + "INNER JOIN genre g ON genre_id = g.id "
                + "INNER JOIN publisher p ON publisher_id = p.id ORDER BY b.id"),
        FIND_ALL_BOOKS_ORDER_BY_NAME("SELECT b.id, b.name, b.year_of_publishing, b.count, p.name AS publisher, a.name AS author, g.name AS genre, b.image FROM book b "
                + "INNER JOIN author a ON author_id = a.id "
                + "INNER JOIN genre g ON genre_id = g.id "
                + "INNER JOIN publisher p ON publisher_id = p.id ORDER BY b.name"),
        FIND_ALL_BOOKS_ORDER_BY_AUTHOR("SELECT b.id, b.name, b.year_of_publishing, b.count, p.name AS publisher, a.name AS author, g.name AS genre, b.image FROM book b "
                + "INNER JOIN author a ON author_id = a.id "
                + "INNER JOIN genre g ON genre_id = g.id "
                + "INNER JOIN publisher p ON publisher_id = p.id ORDER BY author"),
        FIND_ALL_BOOKS_ORDER_BY_PUBLISHER("SELECT b.id, b.name, b.year_of_publishing, b.count, p.name AS publisher, a.name AS author, g.name AS genre, b.image FROM book b "
                + "INNER JOIN author a ON author_id = a.id "
                + "INNER JOIN genre g ON genre_id = g.id "
                + "INNER JOIN publisher p ON publisher_id = p.id ORDER BY publisher"),
        FIND_ALL_BOOKS_ORDER_BY_YEAR_OF_PUBLISHING("SELECT b.id, b.name, b.year_of_publishing, b.count, p.name AS publisher, a.name AS author, g.name AS genre, b.image FROM book b "
                + "INNER JOIN author a ON author_id = a.id "
                + "INNER JOIN genre g ON genre_id = g.id "
                + "INNER JOIN publisher p ON publisher_id = p.id ORDER BY b.year_of_publishing");

        String QUERY;

        Book_SQL(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
