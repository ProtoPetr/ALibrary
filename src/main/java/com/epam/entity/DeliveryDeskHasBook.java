package com.epam.entity;

import java.io.Serializable;

public class DeliveryDeskHasBook implements Serializable {
    private long deliveryDeskId;
    private long bookId;
    private int count;

    public DeliveryDeskHasBook() {
    }

    public DeliveryDeskHasBook(long deliveryDeskId, long bookId) {
        this.deliveryDeskId = deliveryDeskId;
        this.bookId = bookId;
    }

    public long getDeliveryDeskId() {
        return deliveryDeskId;
    }

    public void setDeliveryDeskId(long deliveryDeskId) {
        this.deliveryDeskId = deliveryDeskId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "DeliveryDeskHasBook{" +
                "deliveryDeskId=" + deliveryDeskId +
                ", bookId=" + bookId +
                ", count=" + count +
                '}';
    }
}
