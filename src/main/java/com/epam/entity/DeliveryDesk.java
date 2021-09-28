package com.epam.entity;

import java.io.Serializable;
import java.sql.Date;

/**
 *This class defines the deliveryDesk entity
 * and provides an interface for working with it
 */
public class DeliveryDesk implements Serializable {
    private long id;
    private long userId;
    private long bookId;
    private String bookName;
    private Date issueDate = null;
    private Date returnDate = null;
    private String status = "ordered";
    private int penalty = 0;



    public DeliveryDesk() {
    }

    public DeliveryDesk(String bookName) {
        this.bookName = bookName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "DeliveryDesk{" +
                "id=" + id +
                ", userId=" + userId +
                ", book_id=" + bookId +
                ", bookName='" + bookName +
                ", issueDate=" + issueDate +
                ", returnDate=" + returnDate +
                ", status='" + status +
                ", penalty=" + penalty +
                '}';
    }
}
