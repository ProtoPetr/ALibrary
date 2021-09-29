package com.epam.dao.mysql;

import com.epam.dao.GenreDao;
import com.epam.entity.Genre;
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

public class MysqlGenreDaoTest {
    private static final Logger logger = LogManager.getLogger(MysqlGenreDaoTest.class);
    private final static String JDBC_DRIVER = "org.h2.Driver";
    private final static String DB_URL = "jdbc:h2:file:/home/dev/IdeaProjects/ALibrary/database/testDataBase/testLibrary";
    private final static String URL_CONNECTION = "jdbc:h2:file:/home/dev/IdeaProjects/ALibrary/database/testDataBase/testLibrary;"
            + "user=root;password=12345;";
    private final static String USER = "root";
    private final static String PASS = "12358";

    private static GenreDao gd;

    @BeforeClass
    public static void beforeTest() throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);

        gd = MySqlGenreDao.getInstance();

        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS genre (\n" +
                    "  id INT NOT NULL AUTO_INCREMENT,\n" +
                    "  name VARCHAR(45) NOT NULL,\n" +
                    "  PRIMARY KEY (id));\n";
            statement.executeUpdate(sql);
        }
    }

    @Test
    public void shouldReturnTrueIfGenreIsExistsByName() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            gd.addGenre(connection, "INSERT INTO genre SET name = ?", new Genre("Фантастика"));
            boolean isGenreExists = gd.isExist(connection, "SELECT * FROM genre WHERE name = ?", "Фантастика");
            Assert.assertTrue(isGenreExists);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void shouldReturnAllGenres() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            gd.addGenre(connection, "INSERT INTO genre SET name = ?", new Genre("Фантастика"));
            gd.addGenre(connection, "INSERT INTO genre SET name = ?", new Genre("Информатика"));
            gd.addGenre(connection, "INSERT INTO genre SET name = ?", new Genre("Математика"));
            List<Genre> list = gd.findAll(connection, "SELECT * FROM genre");
            System.out.println(list);
            Assert.assertTrue(list.get(0).getName().equals("Фантастика") &&
                    list.get(1).getName().equals("Информатика") &&
                    list.get(2).getName().equals("Математика"));
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @AfterClass
    public static void afterClass() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement()) {
            String sql = "DROP TABLE genre;\n";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
