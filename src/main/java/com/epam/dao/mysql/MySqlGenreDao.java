package com.epam.dao.mysql;

import com.epam.dao.GenericDao;
import com.epam.dao.GenreDao;
import com.epam.entity.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MySqlGenreDao extends GenericDao<Genre> implements GenreDao {
    private static MySqlGenreDao instance;

    private MySqlGenreDao() {
    }

    public static synchronized GenreDao getInstance() {
        if (instance == null) {
            instance = new MySqlGenreDao();
        }
        return instance;
    }

    @Override
    public List<Genre> findAll(Connection connection, String sql) throws SQLException {
        return findAllItems(connection, sql);
    }

    @Override
    public boolean isExist(Connection connection, String sql, String name) throws SQLException {
        if (name == null) {
            return false;
        }
        return !findByFields(connection, sql, name).isEmpty();
    }

    @Override
    public long addGenre(Connection connection, String sql, Genre genre) throws SQLException {
        long id = add(connection, sql, genre);
        genre.setId(id);
        return id;
    }

    @Override
    protected Genre mapToEntity(ResultSet rs) throws SQLException {
        Genre genre = new Genre();
        genre.setId(rs.getLong("id"));
        genre.setName(rs.getString("name"));
        return genre;
    }

    @Override
    protected void mapFromEntity(PreparedStatement ps, Genre genre) throws SQLException {
        ps.setString(1, genre.getName());
    }
}
