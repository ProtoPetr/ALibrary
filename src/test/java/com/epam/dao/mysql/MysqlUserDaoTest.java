package com.epam.dao.mysql;

import com.epam.dao.UserDao;
import com.epam.entity.User;
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

public class MysqlUserDaoTest {
    private static final Logger logger = LogManager.getLogger(MysqlUserDaoTest.class);
    private final static String JDBC_DRIVER = "org.h2.Driver";
    private final static String DB_URL = "jdbc:h2:file:/home/dev/IdeaProjects/ALibrary/database/testDataBase/testLibrary";
    private final static String URL_CONNECTION = "jdbc:h2:file:/home/dev/IdeaProjects/ALibrary/database/testDataBase/testLibrary;"
                                               + "user=root;password=12345;";
    private final static String USER = "root";
    private final static String PASS = "12358";

    private static UserDao ud;

    @BeforeClass
    public static void beforeTest() throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);

        ud = MysqlUserDao.getInstance();

        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS role(\n" +
                    "  id INT NOT NULL,\n" +
                    "  name VARCHAR(45) NOT NULL,\n" +
                    "  PRIMARY KEY (id));\n" +
                    "CREATE TABLE IF NOT EXISTS user (\n" +
                    "  id INT NOT NULL AUTO_INCREMENT,\n" +
                    "  name VARCHAR(45) NOT NULL,\n" +
                    "  surname VARCHAR(45) NOT NULL,\n" +
                    "  login VARCHAR(45) NOT NULL,\n" +
                    "  password VARCHAR(45) NOT NULL,\n" +
                    "  role_id INT NOT NULL DEFAULT 3,\n" +
                    "  mail VARCHAR(45) NOT NULL,\n" +
                    "  status ENUM('active', 'block') NOT NULL DEFAULT 'active',\n" +
                    "  PRIMARY KEY (id),\n" +
                    "  CONSTRAINT role_id\n" +
                    "    FOREIGN KEY (role_id)\n" +
                    "    REFERENCES role (id)\n" +
                    "    ON DELETE RESTRICT\n" +
                    "    ON UPDATE CASCADE);" +
                    "INSERT INTO role (id, name) values (1, 'admin');\n" +
                    "INSERT INTO role (id, name) values (2, 'librarian');\n" +
                    "INSERT INTO role (id, name) values (3, 'user');\n";
            statement.executeUpdate(sql);
        }
    }

    @Test
    public void shouldReturnCreatedUsers() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            ud.create(connection, USER_SQL.ADD_USER.QUERY, new User("Артур", "Пирожков", "Arch", "777", "arch@gmail.com"));
            ud.create(connection, USER_SQL.ADD_USER.QUERY, new User("Elvis", "Presley", "Elvis", "888", "Elvis@gmail.com"));
            List<User> users = ud.findAll(connection, USER_SQL.FIND_ALL_USERS.QUERY);
            Assert.assertTrue(users.get(0).getLogin().equals("Arch") &&
                    users.get(1).getLogin().equals("Elvis"));
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void shouldReturnUserByName() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            ud.create(connection, USER_SQL.ADD_USER.QUERY, new User("Артур", "Пирожков", "Arch", "777", "arch@gmail.com"));
            ud.create(connection, USER_SQL.ADD_USER.QUERY, new User("Elvis", "Presley", "Elvis", "888", "Elvis@gmail.com"));
            List<User> users = ud.findByName(connection, USER_SQL.FIND_USER_BY_NAME.QUERY, "Артур");
            Assert.assertEquals("Arch", users.get(0).getLogin());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void shouldReturnTrueIfUserIsExistsByLoginPassword() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            ud.create(connection, USER_SQL.ADD_USER.QUERY, new User("Артур", "Пирожков", "Arch", "777", "arch@gmail.com"));
            ud.create(connection, USER_SQL.ADD_USER.QUERY, new User("Elvis", "Presley", "Elvis", "888", "Elvis@gmail.com"));
            boolean isUserExists = ud.isExistByLoginPassword(connection, USER_SQL.FIND_USER_BY_LOGIN_PASSWORD.QUERY, "Arch", "777");
            Assert.assertTrue(isUserExists);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void shouldReturnUserById() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            ud.create(connection, USER_SQL.ADD_USER.QUERY, new User("Артур", "Пирожков", "Arch", "777", "arch@gmail.com"));
            ud.create(connection, USER_SQL.ADD_USER.QUERY, new User("Elvis", "Presley", "Elvis", "888", "Elvis@gmail.com"));
            User user = ud.findById(connection, USER_SQL.GET_USER_BY_ID.QUERY, 2);
            Assert.assertEquals("Elvis", user.getLogin());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void shouldUpdateUserById() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            ud.create(connection, USER_SQL.ADD_USER.QUERY, new User("Артур", "Пирожков", "Arch", "777", "arch@gmail.com"));
            ud.create(connection, USER_SQL.ADD_USER.QUERY, new User("Elvis", "Presley", "Elvis", "888", "Elvis@gmail.com"));
            User user = ud.findById(connection, USER_SQL.GET_USER_BY_ID.QUERY, 2);
            user.setStatus("block");
            ud.update(connection, USER_SQL.UPDATE_USER_DATA.QUERY, user);
            user = ud.findById(connection, USER_SQL.GET_USER_BY_ID.QUERY, 2);
            Assert.assertEquals("block", user.getStatus());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void shouldDeleteUserById() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            ud.create(connection, USER_SQL.ADD_USER.QUERY, new User("Артур", "Пирожков", "Arch", "777", "arch@gmail.com"));
            ud.create(connection, USER_SQL.ADD_USER.QUERY, new User("Elvis", "Presley", "Elvis", "888", "Elvis@gmail.com"));
            User user1 = ud.findById(connection, USER_SQL.GET_USER_BY_ID.QUERY, 1);
            ud.delete(connection, USER_SQL.DELETE_USER_BY_ID.QUERY, 1);
            User user2 = ud.findById(connection, USER_SQL.GET_USER_BY_ID.QUERY, 1);
            Assert.assertNotEquals(user1, user2);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void shouldReturnUserByLoginPassword() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            ud.create(connection, USER_SQL.ADD_USER.QUERY, new User("Артур", "Пирожков", "Arch", "777", "arch@gmail.com"));
            ud.create(connection, USER_SQL.ADD_USER.QUERY, new User("Elvis", "Presley", "Elvis", "888", "Elvis@gmail.com"));
            User user = ud.getUserByLoginPassword(connection, USER_SQL.FIND_USER_BY_LOGIN_PASSWORD.QUERY, "Elvis", "888");
            Assert.assertEquals("Elvis", user.getLogin());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @AfterClass
    public static void afterClass() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = connection.createStatement()) {
            String sql = "DROP TABLE user;\n" +
                    "DROP TABLE role;";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    enum USER_SQL {
        UPDATE_USER_DATA("UPDATE user SET name = ?, surname = ?, login = ?, password = ?, role_id = (select id from role where name = ?), "
                + "mail = ?, status = ? WHERE id = ?"),
        ADD_USER("INSERT INTO user SET name = ?, surname = ?, login = ?, password = ?, role_id = (select id from role where name = ?), "
                + "mail = ?, status = ?"),
        DELETE_USER_BY_ID("DELETE FROM user WHERE id = ?"),
        GET_USER_BY_ID("SELECT u.id, u.name, u.surname, u.login, u.password, r.name AS role, u.mail, u.status FROM user u "
                + "INNER JOIN role r ON role_id = r.id "
                + "WHERE u.id = ?"),
        FIND_USER_BY_NAME("SELECT u.id, u.name, u.surname, u.login, u.password, r.name AS role, u.mail, u.status FROM user u "
                + "INNER JOIN role r ON role_id = r.id "
                + "WHERE u.name = ? OR u.surname = ?"),
        FIND_ALL_USERS("SELECT u.id, u.name, u.surname, u.login, u.password, r.name AS role, u.mail, u.status FROM user u "
                + "INNER JOIN role r ON role_id = r.id "
                + "ORDER BY u.id"),
        FIND_ALL_LIBRARY_USERS("SELECT u.id, u.name, u.surname, u.login, u.password, r.name AS role, u.mail, u.status FROM user u "
                + "INNER JOIN role r ON role_id = r.id "
                + "WHERE r.name = 'user' "
                + "ORDER BY u.id"),
        FIND_USER_BY_LOGIN_PASSWORD("SELECT u.id, u.name, u.surname, u.login, u.password, r.name AS role, u.mail, u.status FROM user u "
                + "INNER JOIN role r ON role_id = r.id "
                + "WHERE u.login = ? AND u.password = ?");

        String QUERY;

        USER_SQL(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
