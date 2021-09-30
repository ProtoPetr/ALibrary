package com.epam.serviсe.mysql;

import com.epam.serviсe.BookService;
import com.epam.serviсe.DeliveryDeskService;
import com.epam.serviсe.ServiceFactory;
import com.epam.serviсe.UserService;

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
