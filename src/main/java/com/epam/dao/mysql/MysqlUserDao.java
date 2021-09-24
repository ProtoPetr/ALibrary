package com.epam.dao.mysql;

import com.epam.dao.GenericDao;
import com.epam.dao.UserDao;
import com.epam.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MysqlUserDao extends GenericDao<User> implements UserDao {
    private static MysqlUserDao instance;

    private MysqlUserDao() {
    }

    public static synchronized UserDao getInstance() {
        if (instance == null) {
            instance = new MysqlUserDao();
        }
        return instance;
    }

    @Override
    public List<User> findByName(Connection connection, String sql, String name) throws SQLException {
        List<User> list = findByFields(connection, sql, name, name);
        if (list.isEmpty())
            throw new SQLException();
        return list;
    }

    @Override
    public boolean isExistByLoginPassword(Connection connection, String sql, String login, String password) throws SQLException {
        if (login == null || password == null) {
            return false;
        }
        return !findByFields(connection, sql, login, password).isEmpty();
    }

    @Override
    public List<User> findAll(Connection connection, String sql) throws SQLException {
        return findAllItems(connection, sql);
    }

    @Override
    public User findById(Connection connection, String sql, long userId) throws SQLException {
        List<User> list = findByFields(connection, sql, userId);
        if (list.isEmpty())
            throw new SQLException();
        return list.get(0);
    }

    @Override
    public void create(Connection connection, String sql, User user) throws SQLException {
        long id = add(connection, sql, user);
        user.setId(id);
    }

    @Override
    public void update(Connection connection, String sql, User user) throws SQLException {
        updateByField(connection, sql, user, 8, user.getId());
    }

    @Override
    public void delete(Connection connection, String sql, long userId) throws SQLException {
        deleteByField(connection, sql, userId);
    }

    @Override
    public User getUserByLoginPassword(Connection connection, String sql, String login, String password) throws SQLException {
        List<User> list = findByFields(connection, sql, login, password);
        if (list.isEmpty())
            throw new SQLException();
        return list.get(0);
    }

    @Override
    protected User mapToEntity(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setMail(rs.getString("mail"));
        user.setRole(rs.getString("role"));
        user.setStatus(rs.getString("status"));

        return user;
    }

    @Override
    protected void mapFromEntity(PreparedStatement ps, User user) throws SQLException {
        ps.setString(1, user.getName());
        ps.setString(2, user.getSurname());
        ps.setString(3, user.getLogin());
        ps.setString(4, user.getPassword());
        ps.setString(5, user.getRole());
        ps.setString(6, user.getMail());
        ps.setString(7, user.getStatus());
    }
}
