package com.epam.dao;

import com.epam.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    List<User> findByName(Connection connection, String sql, String name) throws SQLException;

    List<User> findAll(Connection connection, String sql) throws SQLException;

    User findById(Connection connection, String sql, long userId) throws SQLException;

    void create(Connection connection, String sql, User user) throws SQLException;

    void update(Connection connection, String sql, User user) throws SQLException;

    void delete(Connection connection, String sql, long userId) throws SQLException;

    User getUserByLoginPassword(Connection con, String sql, String login, String password) throws SQLException;

    boolean isExistByLoginPassword(Connection connection, String sql, String login, String password) throws SQLException;
}
