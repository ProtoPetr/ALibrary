package com.epam.dao.mysql;

import com.epam.dao.AuthorDao;
import com.epam.dao.GenericDao;
import com.epam.entity.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlAuthorDao extends GenericDao<Author> implements AuthorDao {
    private static MysqlAuthorDao instance;

    private MysqlAuthorDao() {
    }

    public static synchronized AuthorDao getInstance() {
        if (instance == null) {
            instance = new MysqlAuthorDao();
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
    public long addAuthor(Connection connection, String sql, Author author) throws SQLException {
        long id = add(connection, sql, author);
        author.setId(id);
        return id;
    }

    @Override
    protected Author mapToEntity(ResultSet rs) throws SQLException {
        Author author = new Author();
        author.setId(rs.getLong("id"));
        author.setName(rs.getString("name"));
        return author;
    }


    @Override
    protected void mapFromEntity(PreparedStatement ps, Author author) throws SQLException {
        ps.setString(1, author.getName());
    }
}
