package com.epam.dao.mysql;

import com.epam.dao.GenericDao;
import com.epam.dao.PublisherDao;
import com.epam.entity.Publisher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlPublisherDao extends GenericDao<Publisher> implements PublisherDao {
    private static MysqlPublisherDao instance;

    private MysqlPublisherDao() {
    }

    public static synchronized PublisherDao getInstance() {
        if (instance == null) {
            instance = new MysqlPublisherDao();
        }
        return instance;
    }

    @Override
    public boolean isExist(Connection connection, String sql, String name) throws SQLException {
        if (name == null) {
            return false;
        }
        return !findByFields(connection, sql, name).isEmpty();
    }

    @Override
    public long addPublisher(Connection connection, String sql, Publisher publisher) throws SQLException {
        long id = add(connection, sql, publisher);
        publisher.setId(id);
        return id;
    }

    @Override
    protected Publisher mapToEntity(ResultSet rs) throws SQLException {
        Publisher publisher = new Publisher();
        publisher.setId(rs.getLong("id"));
        publisher.setName(rs.getString("name"));
        return publisher;
    }

    @Override
    protected void mapFromEntity(PreparedStatement ps, Publisher publisher) throws SQLException {
        ps.setString(1, publisher.getName());
    }
}
