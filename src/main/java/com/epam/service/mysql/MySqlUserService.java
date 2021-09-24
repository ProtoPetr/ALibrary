package com.epam.service.mysql;

import com.epam.dao.DaoFactory;
import com.epam.dao.UserDao;
import com.epam.entity.User;
import com.epam.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserService implements UserService {
    private static final Logger logger = LogManager.getLogger(MySqlUserService.class);
    private static UserDao userDao;
    private static DaoFactory daoFactory;

    {
        try {
            daoFactory = DaoFactory.getDaoFactory("MySQL");
            userDao = daoFactory.getUserDao();
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void updateUserData(User user) {
        try (Connection connection = daoFactory.getConnection()) {
            userDao.update(connection, USER_SQL.UPDATE_USER_DATA.QUERY, user);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public User findUserById(long id) {
        User user = new User();
        try (Connection connection = daoFactory.getConnection()) {
            user = userDao.findById(connection, USER_SQL.GET_USER_BY_ID.QUERY, id);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return user;
    }

    @Override
    public List<User> getUsersBySearch(String searchStr) {
        List<User> list = new ArrayList<>();
        try (Connection connection = daoFactory.getConnection()) {
            list = userDao.findByName(connection, USER_SQL.FIND_USER_BY_NAME.QUERY,
                    "%" + searchStr.toLowerCase() + "%");
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public void createUser(User user) {
        try (Connection connection = daoFactory.getConnection()) {
            userDao.create(connection, USER_SQL.ADD_USER.QUERY, user);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean userIsExist(String login, String password) {
        boolean result = false;
        try (Connection connection = daoFactory.getConnection()) {
            result = userDao.isExistByLoginPassword(connection,
                    USER_SQL.FIND_USER_BY_LOGIN_PASSWORD.QUERY, login, password);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> list = new ArrayList<>();
        try (Connection connection = daoFactory.getConnection()) {
            list = userDao.findAll(connection, USER_SQL.FIND_ALL_USERS.QUERY);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public List<User> findAllLibraryUsers() {
        List<User> list = new ArrayList<>();
        try (Connection connection = daoFactory.getConnection()) {
            list = userDao.findAll(connection, USER_SQL.FIND_ALL_LIBRARY_USERS.QUERY);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }

    @Override
    public User getUserByLoginPassword(String login, String password) {
        User user = new User();
        try (Connection connection = daoFactory.getConnection()) {
            user = userDao.getUserByLoginPassword(connection, USER_SQL.FIND_USER_BY_LOGIN_PASSWORD.QUERY, login, password);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return user;
    }

    @Override
    public User.ROLE getRoleByLoginPassword(String login, String password) {
        User.ROLE role = User.ROLE.UNKNOWN;
        try (Connection connection = daoFactory.getConnection()) {
            User user = userDao.getUserByLoginPassword(connection, USER_SQL.FIND_USER_BY_LOGIN_PASSWORD.QUERY, login, password);
            role = User.ROLE.valueOf(user.getRole().toUpperCase());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return role;
    }


    /**
     * SQL queries for User entity.
     */
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
                + "WHERE lower(u.name) LIKE ? OR lower(u.surname) LIKE ?"),
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
