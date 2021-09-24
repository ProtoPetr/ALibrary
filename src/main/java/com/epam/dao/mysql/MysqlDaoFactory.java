package com.epam.dao.mysql;

import com.epam.dao.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MysqlDaoFactory extends DaoFactory {
    private static final Logger logger = LogManager.getLogger(MysqlDaoFactory.class);
    private static MysqlDaoFactory instance;
    private final DataSource ds;

    private MysqlDaoFactory() {
        Context initContext;
        Context envContext;
        try {
            initContext = new InitialContext();
            envContext = (Context)initContext.lookup("java:/comp/env");
            ds = (DataSource)envContext.lookup("jdbc/librdb");
        } catch (NamingException e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException("Can not init MysqlDaoFactory, e");
        }
    }

    public static synchronized MysqlDaoFactory getInstance() {
        if (instance == null) {
            instance = new MysqlDaoFactory();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    @Override
    public UserDao getUserDao() {
        return MysqlUserDao.getInstance();
    }

    @Override
    public BookDao getBookDao() {
        return MysqlBookDao.getInstance();
    }

    @Override
    public DeliveryDeskDao getDeliveryDeskDao() {
        return MysqlDeliveryDeskDao.getInstance();
    }

    @Override
    public GenreDao getGenreDao() {
        return MySqlGenreDao.getInstance();
    }

    @Override
    public AuthorDao getAuthorDao() {
        return MysqlAuthorDao.getInstance();
    }

    @Override
    public PublisherDao getPublisherDao() {
        return MysqlPublisherDao.getInstance();
    }
}
