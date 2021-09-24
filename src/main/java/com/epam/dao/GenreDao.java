package com.epam.dao;

import com.epam.entity.Genre;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GenreDao {
    List<Genre> findAll(Connection connection, String sql) throws SQLException;
    boolean isExist(Connection connection, String sql, String name) throws SQLException;
    long addGenre(Connection connection, String sql, Genre genre) throws SQLException;
}
