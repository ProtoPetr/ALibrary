package com.epam.service;

import com.epam.service.mysql.MySqlServiceFactory;

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
