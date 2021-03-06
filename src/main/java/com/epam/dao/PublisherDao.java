package com.epam.dao;

import com.epam.entity.Publisher;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface declares methods for current entity
 */
public interface PublisherDao {
    boolean isExist(Connection connection, String sql, String name) throws SQLException;
    long addPublisher(Connection connection, String sql, Publisher publisher) throws SQLException;
}
