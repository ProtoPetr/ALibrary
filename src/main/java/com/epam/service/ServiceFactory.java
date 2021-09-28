package com.epam.service;

import com.epam.service.mysql.MySqlServiceFactory;

/**
 * This abstract class present factory pattern
 * Contains a declaration of abstract methods that return implementation of service interfaces
 * for working with a specific entity
 * that have been invoked from view and command
 */
public abstract class ServiceFactory {
    public static ServiceFactory getServiceFactory(String db) {
        switch (db) {
            case "MySQL":
                return MySqlServiceFactory.getInstance();
            default:
                throw new IllegalArgumentException();
        }
    }

    public abstract UserService getUserService();

    public abstract BookService getBookService();

    public abstract DeliveryDeskService getDeliveryDeskService();
}
