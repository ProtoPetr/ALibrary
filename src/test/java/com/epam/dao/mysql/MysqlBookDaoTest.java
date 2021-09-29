package com.epam.dao.mysql;

import com.epam.dao.AuthorDao;
import com.epam.dao.BookDao;
import com.epam.dao.GenreDao;
import com.epam.dao.PublisherDao;
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

public class MysqlBookDaoTest {
    private static final Logger logger = LogManager.getLogger(MysqlBookDaoTest.class);
    private final static String JDBC_DRIVER = "org.h2.Driver";
    private final static String DB_URL = "jdbc:h2:file:/home/dev/IdeaProjects/ALibrary/database/testDataBase/testLibrary";
    private final static String URL_CONNECTION = "jdbc:h2:file:/home/dev/IdeaProjects/ALibrary/database/testDataBase/testLibrary;"
            + "user=root;password=12345;";
    private final static String USER = "root";
    private final static String PASS = "12358";

    private static BookDao bd;
    private static AuthorDao ad;
    private static GenreDao gd;
    private static PublisherDao pd;

    @BeforeClass
    public static void beforeTest() throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);

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
                    "    ON UPDATE CASCADE)";
            statement.executeUpdate(sql);
        }
    }

    @Test
    public void shouldReturnCreatedBooks() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            ad.addAuthor(connection, "INSERT INTO author SET name = ?", new Author("Аристотель"));
            ad.addAuthor(connection, "INSERT INTO author SET name = ?", new Author("Платон"));
            gd.addGenre(connection, "INSERT INTO genre SET name = ?", new Genre("Философия"));
            gd.addGenre(connection, "INSERT INTO genre SET name = ?", new Genre("Наука"));
            pd.addPublisher(connection, "INSERT INTO publisher SET name = ?", new Publisher("Наука"));
            bd.create(connection, Book_SQL.ADD_BOOK.QUERY, new Book("Метафизика", "Аристотель", "Наука", "Наука", 1991, 20));
            bd.create(connection, Book_SQL.ADD_BOOK.QUERY, new Book("Апология Сократа", "Платон", "Философия", "Наука", 1988, 20));
            List<Book> books = bd.findAll(connection, Book_SQL.FIND_ALL_BOOKS.QUERY);
            Assert.assertTrue(books.get(0).getName().equals("Метафизика") &&
                    books.get(1).getName().equals("Апология Сократа"));
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void shouldReturnBooksByName() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            ad.addAuthor(connection, "INSERT INTO author SET name = ?", new Author("Аристотель"));
            ad.addAuthor(connection, "INSERT INTO author SET name = ?", new Author("Платон"));
            gd.addGenre(connection, "INSERT INTO genre SET name = ?", new Genre("Философия"));
            gd.addGenre(connection, "INSERT INTO genre SET name = ?", new Genre("Наука"));
            pd.addPublisher(connection, "INSERT INTO publisher SET name = ?", new Publisher("Наука"));
            bd.create(connection, Book_SQL.ADD_BOOK.QUERY, new Book("Метафизика", "Аристотель", "Наука", "Наука", 1991, 20));
            bd.create(connection, Book_SQL.ADD_BOOK.QUERY, new Book("Апология Сократа", "Платон", "Философия", "Наука", 1988, 20));
            List<Book> books = bd.findByName(connection, Book_SQL.FIND_BOOK_BY_NAME.QUERY, "Метафизика");
            Assert.assertEquals("Метафизика", books.get(0).getName());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void shouldReturnBookById() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            ad.addAuthor(connection, "INSERT INTO author SET name = ?", new Author("Аристотель"));
            ad.addAuthor(connection, "INSERT INTO author SET name = ?", new Author("Платон"));
            gd.addGenre(connection, "INSERT INTO genre SET name = ?", new Genre("Философия"));
            gd.addGenre(connection, "INSERT INTO genre SET name = ?", new Genre("Наука"));
            pd.addPublisher(connection, "INSERT INTO publisher SET name = ?", new Publisher("Наука"));
            bd.create(connection, Book_SQL.ADD_BOOK.QUERY, new Book("Метафизика", "Аристотель", "Наука", "Наука", 1991, 20));
            bd.create(connection, Book_SQL.ADD_BOOK.QUERY, new Book("Апология Сократа", "Платон", "Философия", "Наука", 1988, 20));
            Book book = bd.findById(connection, Book_SQL.GET_BOOK_BY_ID.QUERY, 1);
            Assert.assertEquals("Метафизика", book.getName());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void shouldReturnBookByGenreId() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            ad.addAuthor(connection, "INSERT INTO author SET name = ?", new Author("Аристотель"));
            ad.addAuthor(connection, "INSERT INTO author SET name = ?", new Author("Платон"));
            gd.addGenre(connection, "INSERT INTO genre SET name = ?", new Genre("Философия"));
            gd.addGenre(connection, "INSERT INTO genre SET name = ?", new Genre("Наука"));
            pd.addPublisher(connection, "INSERT INTO publisher SET name = ?", new Publisher("Наука"));
            bd.create(connection, Book_SQL.ADD_BOOK.QUERY, new Book("Метафизика", "Аристотель", "Наука", "Наука", 1991, 20));
            bd.create(connection, Book_SQL.ADD_BOOK.QUERY, new Book("Апология Сократа", "Платон", "Философия", "Наука", 1988, 20));
            List<Book> books = bd.getBooksByGenreId(connection, Book_SQL.GET_BOOKS_BY_GENRE_ID.QUERY, 2);
            Assert.assertEquals("Метафизика", books.get(0).getName());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void shouldUpdateBook() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            ad.addAuthor(connection, "INSERT INTO author SET name = ?", new Author("Аристотель"));
            ad.addAuthor(connection, "INSERT INTO author SET name = ?", new Author("Платон"));
            gd.addGenre(connection, "INSERT INTO genre SET name = ?", new Genre("Философия"));
            gd.addGenre(connection, "INSERT INTO genre SET name = ?", new Genre("Наука"));
            pd.addPublisher(connection, "INSERT INTO publisher SET name = ?", new Publisher("Наука"));
            bd.create(connection, Book_SQL.ADD_BOOK.QUERY, new Book("Метафизика", "Аристотель", "Наука", "Наука", 1991, 20));
            bd.create(connection, Book_SQL.ADD_BOOK.QUERY, new Book("Апология Сократа", "Платон", "Философия", "Наука", 1988, 20));
            Book book = bd.findById(connection, Book_SQL.GET_BOOK_BY_ID.QUERY, 1);
            book.setCount(10);
            bd.update(connection, Book_SQL.UPDATE_BOOK.QUERY, book);
            bd.findById(connection, Book_SQL.GET_BOOK_BY_ID.QUERY, 1);
            Assert.assertEquals(10, book.getCount());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void shouldDeleteBookById() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            ad.addAuthor(connection, "INSERT INTO author SET name = ?", new Author("Аристотель"));
            ad.addAuthor(connection, "INSERT INTO author SET name = ?", new Author("Платон"));
            gd.addGenre(connection, "INSERT INTO genre SET name = ?", new Genre("Философия"));
            gd.addGenre(connection, "INSERT INTO genre SET name = ?", new Genre("Наука"));
            pd.addPublisher(connection, "INSERT INTO publisher SET name = ?", new Publisher("Наука"));
            bd.create(connection, Book_SQL.ADD_BOOK.QUERY, new Book("Метафизика", "Аристотель", "Наука", "Наука", 1991, 20));
            bd.create(connection, Book_SQL.ADD_BOOK.QUERY, new Book("Апология Сократа", "Платон", "Философия", "Наука", 1988, 20));
            Book book1 = bd.findById(connection, Book_SQL.GET_BOOK_BY_ID.QUERY, 1);
            bd.delete(connection, Book_SQL.DELETE_BOOK_BY_ID.QUERY, 1);
            Book book2 = bd.findById(connection, Book_SQL.GET_BOOK_BY_ID.QUERY, 1);
            Assert.assertNotEquals(book1, book2);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @AfterClass
    public static void afterClass() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement()) {
            String sql = "DROP TABLE book;\n" +
                    "DROP TABLE author;\n" +
                    "DROP TABLE genre;\n" +
                    "DROP TABLE publisher;\n";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

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
