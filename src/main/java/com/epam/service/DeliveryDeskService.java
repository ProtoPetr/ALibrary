package com.epam.service;

import com.epam.entity.DeliveryDesk;

import java.util.List;

/**
 * Interface declares methods for work with current entity
 * and contains specific logic
 */
public interface DeliveryDeskService {
    List<DeliveryDesk> getUserDeliveryDesk(long userId);
    void addBookToDeliveryDesk(long userId, long bookId);
    void issueBookFromDeliveryDeskOnDay(long deliveryDeskId, long bookId);
    void issueBookFromDeliveryDeskOnMonth(long deliveryDeskId, long bookId);
    void returnBookToDeliveryDesk(long deliveryDeskId, long bookId);
    void setPenalty(long deliveryDeskId, long bookId);
    void deleteBookFromDeliveryDesk(long userId, long bookId);
    List<DeliveryDesk> getAllDeliveryDesk();
}
