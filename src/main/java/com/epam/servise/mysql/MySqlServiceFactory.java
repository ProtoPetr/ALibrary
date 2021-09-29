package com.epam.servise.mysql;

import com.epam.servise.BookService;
import com.epam.servise.DeliveryDeskService;
import com.epam.servise.ServiceFactory;
import com.epam.servise.UserService;

/**
 * This class inherits from ServiceFactory
 * Contains implementation of based class that return implementation of service interfaces
 * for service a specific entity
 */
public class MySqlServiceFactory extends ServiceFactory {
    private static MySqlServiceFactory instance;

    private MySqlServiceFactory() {
    }

    public static synchronized MySqlServiceFactory getInstance() {
        if (instance == null) {
            instance = new MySqlServiceFactory();
        }
        return instance;
    }

    @Override
    public UserService getUserService() {
        return new  MySqlUserService();
    }

    @Override
    public BookService getBookService() {
        return new  MySqlBookService();
    }

    @Override
    public DeliveryDeskService getDeliveryDeskService() {
        return new MySqlDeliveryDeskService();
    }
}
