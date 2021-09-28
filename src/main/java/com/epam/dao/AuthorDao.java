package com.epam.dao;

import com.epam.entity.Author;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface declares crud methods for current entity
 */
public interface AuthorDao {
    boolean isExist(Connection connection, String sql, String name) throws SQLException;
    long addAuthor(Connection connection, String sql, Author author) throws SQLException;
}
