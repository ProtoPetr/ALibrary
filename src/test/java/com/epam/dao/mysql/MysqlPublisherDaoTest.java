package com.epam.dao.mysql;

import com.epam.dao.PublisherDao;
import com.epam.entity.Publisher;
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

public class MysqlPublisherDaoTest {
    private static final Logger logger = LogManager.getLogger(MysqlPublisherDaoTest.class);
    private final static String JDBC_DRIVER = "org.h2.Driver";
    private final static String DB_URL = "jdbc:h2:file:/home/dev/IdeaProjects/ALibrary/database/testDataBase/testLibrary";
    private final static String URL_CONNECTION = "jdbc:h2:file:/home/dev/IdeaProjects/ALibrary/database/testDataBase/testLibrary;"
            + "user=root;password=12345;";
    private final static String USER = "root";
    private final static String PASS = "12358";

    private static PublisherDao pd;

    @BeforeClass
    public static void beforeTest() throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);

        pd = MysqlPublisherDao.getInstance();

        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS publisher (\n" +
                    "  id INT NOT NULL AUTO_INCREMENT,\n" +
                    "  name VARCHAR(45) NOT NULL,\n" +
                    "  PRIMARY KEY (id));\n";
            statement.executeUpdate(sql);
        }
    }

    @Test
    public void shouldReturnTrueIfPublisherIsExistsByName() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            pd.addPublisher(connection, "INSERT INTO publisher SET name = ?", new Publisher("Миф"));
            boolean isPublisherExists = pd.isExist(connection, "SELECT * FROM publisher WHERE name = ?", "Миф");
            Assert.assertTrue(isPublisherExists);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @AfterClass
    public static void afterClass() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement()) {
            String sql = "DROP TABLE publisher;\n";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
