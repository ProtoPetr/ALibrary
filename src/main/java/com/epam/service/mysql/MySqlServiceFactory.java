package com.epam.service.mysql;

import com.epam.service.BookService;
import com.epam.service.DeliveryDeskService;
import com.epam.service.ServiceFactory;
import com.epam.service.UserService;

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
