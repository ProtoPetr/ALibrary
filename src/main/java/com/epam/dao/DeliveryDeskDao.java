package com.epam.dao;

import com.epam.entity.DeliveryDesk;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Interface declares crud methods for current entity
 */
public interface DeliveryDeskDao {
    void update(Connection connection, String sql, long userId, long bookId) throws SQLException;
    void create(Connection connection, String sql, long bookId, long deliveryDeskId) throws SQLException;
    long createDeliveryDeskForUser(Connection connection, String sql, long userId) throws SQLException;
    List<DeliveryDesk> findByUserId(Connection connection, String sql, long userId) throws SQLException;
    List<DeliveryDesk> findAll(Connection connection, String sql) throws SQLException;
    void delete(Connection connection, String sql, long deliveryDeskId, long bookId) throws SQLException;
}
