package com.epam.dao.mysql;

import com.epam.dao.AuthorDao;
import com.epam.entity.Author;
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

public class MysqlAuthorDaoTest {
    private static final Logger logger = LogManager.getLogger(MysqlAuthorDaoTest.class);
    private final static String JDBC_DRIVER = "org.h2.Driver";
    private final static String DB_URL = "jdbc:h2:file:/home/dev/IdeaProjects/ALibrary/database/testDataBase/testLibrary";
    private final static String URL_CONNECTION = "jdbc:h2:file:/home/dev/IdeaProjects/ALibrary/database/testDataBase/testLibrary;"
            + "user=root;password=12345;";
    private final static String USER = "root";
    private final static String PASS = "12358";

    private static AuthorDao ad;

    @BeforeClass
    public static void beforeTest() throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);

        ad = MysqlAuthorDao.getInstance();

        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS author (\n" +
                    "  id INT NOT NULL AUTO_INCREMENT,\n" +
                    "  name VARCHAR(45) NOT NULL,\n" +
                    "  PRIMARY KEY (id));\n";
            statement.executeUpdate(sql);
        }
    }

    @Test
    public void shouldReturnTrueIfAuthorIsExistsByName() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            ad.addAuthor(connection, "INSERT INTO author SET name = ?", new Author("Платон"));
            boolean isAuthorExists = ad.isExist(connection, "SELECT * FROM author WHERE name = ?", "Платон");
            Assert.assertTrue(isAuthorExists);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @AfterClass
    public static void afterClass() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement()) {
            String sql = "DROP TABLE author;\n";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
