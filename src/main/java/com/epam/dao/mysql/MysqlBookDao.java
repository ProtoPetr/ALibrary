package com.epam.dao.mysql;

import com.epam.dao.BookDao;
import com.epam.dao.GenericDao;
import com.epam.entity.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * This class inherits from GenericDao<T> and types it by Book
 * implements BookDao interface
 */
public class MysqlBookDao extends GenericDao<Book> implements BookDao {
    private static MysqlBookDao instance;

    private MysqlBookDao() {
    }

    public static synchronized BookDao getInstance() {
        if (instance == null) {
            instance = new MysqlBookDao();
        }
        return instance;
    }

    @Override
    public List<Book> findByName(Connection connection, String sql, String name) throws SQLException {
        List<Book> list = findByFields(connection, sql, name);
        if (list.isEmpty())
            throw new SQLException();
        return list;
    }

    @Override
    public List<Book> findAll(Connection connection, String sql) throws SQLException {
        return findAllItems(connection, sql);
    }

    @Override
    public Book findById(Connection connection, String sql, long id) throws SQLException {
        List<Book> list = findByFields(connection, sql, id);
        if (list.isEmpty())
            throw new SQLException();
        return list.get(0);
    }

    @Override
    public List<Book> getBooksByGenreId(Connection connection, String sql, long id) throws SQLException {
        List<Book> list = findByFields(connection, sql, id);
        if (list.isEmpty())
            throw new SQLException();
        return list;
    }

    @Override
    public void create(Connection connection, String sql, Book book) throws SQLException {
        long id = add(connection, sql, book);
        book.setId(id);
    }

    @Override
    public void update(Connection connection, String sql, Book book) throws SQLException {
        updateByField(connection, sql, book, 7, book.getId());
    }

    @Override
    public void delete(Connection connection, String sql, long Id) throws SQLException {
        deleteByField(connection, sql, Id);
    }

    /**
     * The method forms the entity based on resultSet
     */
    @Override
    protected Book mapToEntity(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setName(rs.getString("name"));
        book.setAuthor(rs.getString("author"));
        book.setGenre(rs.getString("genre"));
        book.setPublisher(rs.getString("publisher"));
        book.setYearOfPublishing(rs.getInt("year_of_publishing"));
        book.setCount(rs.getInt("count"));
        book.setImage(rs.getBytes("image"));
        return book;
    }

    /**
     * The method forms the resultSet based on entity
     */
    @Override
    protected void mapFromEntity(PreparedStatement ps, Book book) throws SQLException {
        ps.setString(1, book.getName());
        ps.setString(2, book.getAuthor());
        ps.setString(3, book.getGenre());
        ps.setString(4, book.getPublisher());
        ps.setInt(5, book.getYearOfPublishing());
        ps.setInt(6, book.getCount());
//        ps.setBytes(7, book.getImage());
    }
}
