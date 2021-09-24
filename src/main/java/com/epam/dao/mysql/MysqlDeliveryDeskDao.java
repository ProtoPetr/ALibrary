package com.epam.dao.mysql;

import com.epam.dao.DeliveryDeskDao;
import com.epam.dao.GenericDao;
import com.epam.entity.DeliveryDesk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public class MysqlDeliveryDeskDao extends GenericDao<DeliveryDesk> implements DeliveryDeskDao {
    private static final Logger logger = LogManager.getLogger(MysqlDeliveryDeskDao.class);
    private static MysqlDeliveryDeskDao instance;

    private MysqlDeliveryDeskDao() {
    }

    public static synchronized DeliveryDeskDao getInstance() {
        if (instance == null) {
            instance = new MysqlDeliveryDeskDao();
        }
        return instance;
    }

    @Override
    public void update(Connection connection, String sql, long deliveryDeskId, long bookId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql);
            int k = 1;
            ps.setLong(k++, deliveryDeskId);
            ps.setLong(k, bookId);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new SQLException();
        } finally {
            closeStatementsAndResultSet(ps, rs);
        }
    }

    @Override
    public void create(Connection connection, String sql, long deliveryDeskId, long bookId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql);
            int k = 1;
            ps.setLong(k++, deliveryDeskId);
            ps.setLong(k, bookId);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new SQLException();
        } finally {
            closeStatementsAndResultSet(ps, rs);
        }
    }

    @Override
    public long createDeliveryDeskForUser(Connection connection, String sql, long userId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setLong(1, userId);
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next())
                    return rs.getLong(1);
            }

        } finally {
            closeStatementsAndResultSet(ps, rs);
        }
        throw new SQLException("Not added");
    }

    @Override
    public List<DeliveryDesk> findByUserId(Connection connection, String sql, long userId) throws SQLException {
        return findByFields(connection, sql, userId);
    }

    @Override
    public List<DeliveryDesk> findAll(Connection connection, String sql) throws SQLException {
        return findAllItems(connection, sql);
    }

    @Override
    public void delete(Connection connection, String sql, long deliveryDeskId, long bookId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql);
            int k = 1;
            ps.setLong(k++, deliveryDeskId);
            ps.setLong(k, bookId);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new SQLException();
        } finally {
            closeStatementsAndResultSet(ps, rs);
        }
    }

    @Override
    protected DeliveryDesk mapToEntity(ResultSet rs) throws SQLException {
        DeliveryDesk deliveryDesk = new DeliveryDesk();
        deliveryDesk.setId(rs.getLong("id"));
        deliveryDesk.setUserId(rs.getLong("user_id"));
        deliveryDesk.setBookId(rs.getLong("book_id"));
        deliveryDesk.setBookName(rs.getString("book_name"));
        deliveryDesk.setIssueDate(rs.getDate("date_of_issue"));
        deliveryDesk.setReturnDate(rs.getDate("return_date"));
        deliveryDesk.setStatus(rs.getString("status"));
        deliveryDesk.setPenalty(rs.getInt("penalty"));

        return deliveryDesk;
    }

    @Override
    protected void mapFromEntity(PreparedStatement ps, DeliveryDesk deliveryDesk) throws SQLException {
        ps.setLong(1, deliveryDesk.getUserId());
        ps.setLong(2, deliveryDesk.getBookId());
        ps.setString(3, deliveryDesk.getBookName());
        ps.setDate(4, deliveryDesk.getIssueDate());
        ps.setDate(5, deliveryDesk.getReturnDate());
        ps.setString(6, deliveryDesk.getStatus());
        ps.setInt(7, deliveryDesk.getPenalty());
    }
}
