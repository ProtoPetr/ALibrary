package com.epam.service.mysql;

import com.epam.dao.DaoFactory;
import com.epam.dao.GenreDao;
import com.epam.entity.Genre;
import com.epam.service.GenreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements GenreService interface
 * the interface is implemented according to the needs of the view layer
 */
public class MySqlGenreService implements GenreService {
    private static final Logger logger = LogManager.getLogger(MySqlGenreService.class);
    private static GenreDao genreDao;
    private static DaoFactory daoFactory;

    /**
     * Block initializes objects for working with dao layer
     */
    {
        try {
            daoFactory = DaoFactory.getDaoFactory("MySQL");
            genreDao = daoFactory.getGenreDao();
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * The method find all genres
     */
    @Override
    public List<Genre> findAllGenres() {
        List<Genre> list = new ArrayList<>();
        try (Connection connection = daoFactory.getConnection()) {
            list = genreDao.findAll(connection, "SELECT * FROM genre");
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }
}
