package com.epam.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericDao<T> {
    private static final Logger logger = LogManager.getLogger(GenericDao.class);

    public List<T> findAllItems(Connection connection, String sql) throws SQLException {
        List<T> list = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToEntity(rs));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new SQLException();
        } finally {
            closeStatementsAndResultSet(ps, rs);
        }
        return list;
    }

    @SafeVarargs
    protected final <V> List<T> findByFields(Connection connection, String sql, @SuppressWarnings("unchecked") V... values)
            throws SQLException {
        List<T> list = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql);
            for (int i = 1; i <= values.length; i++) {
                V value = values[i - 1];
                dispatchType(ps, i, value);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToEntity(rs));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new SQLException();
        } finally {
            closeStatementsAndResultSet(ps, rs);
        }
        return list;
    }

    private <V> void dispatchType(PreparedStatement ps, int i, V value) throws SQLException {
        switch (value.getClass().getSimpleName()) {
            case "Integer":
                ps.setInt(i, (Integer) value);
                break;
            case "Long":
                ps.setLong(i, (Long) value);
                break;
            case "String":
                ps.setString(i, (String) value);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    protected long add(Connection connection, String sql, T item) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            mapFromEntity(ps, item);
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next())
                    return rs.getLong(1);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new SQLException();
        } finally {
            closeStatementsAndResultSet(ps, rs);
        }
        throw new SQLException("Not added");
    }

    protected <V> void updateByField(Connection connection, String sql, T item, int parameterIndex, V value)
            throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql);
            dispatchType(ps, parameterIndex, value);
            mapFromEntity(ps, item);
            if (ps.executeUpdate() == 0)
                throw new SQLException("Not updated");

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            closeStatementsAndResultSet(ps, rs);
        }
    }

    protected <V> void deleteByField(Connection connection, String sql, V value) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql);
            switch (value.getClass().getSimpleName()) {
                case "Integer":
                    ps.setInt(1, (Integer) value);
                    break;
                case "Long":
                    ps.setLong(1, (Long) value);
                    break;
                case "String":
                    ps.setString(1, (String) value);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            if (ps.executeUpdate() == 0)
                throw new SQLException("Not deleted");

        } finally {
            closeStatementsAndResultSet(ps, rs);
        }
    }

    protected void closeStatementsAndResultSet(PreparedStatement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    protected abstract T mapToEntity(ResultSet rs) throws SQLException;

    protected abstract void mapFromEntity(PreparedStatement ps, T obj) throws SQLException;
}
