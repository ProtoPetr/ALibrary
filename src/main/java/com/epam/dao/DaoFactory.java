package com.epam.dao;

import com.epam.dao.mysql.MysqlDaoFactory;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DaoFactory {

    public static DaoFactory getDaoFactory(String name) {
        switch (name) {
            case "MySQL":
                return MysqlDaoFactory.getInstance();
            default:
                throw new IllegalArgumentException();
        }
    }

    public abstract UserDao getUserDao();

    public abstract BookDao getBookDao();

    public abstract DeliveryDeskDao getDeliveryDeskDao();

    public abstract GenreDao getGenreDao();

    public abstract AuthorDao getAuthorDao();

    public abstract PublisherDao getPublisherDao();

    public abstract Connection getConnection() throws SQLException;
}
