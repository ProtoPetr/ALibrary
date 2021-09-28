package com.epam.entity;

import java.io.Serializable;

/**
 *This class defines the book entity
 * and provides an interface for working with it
 */
public class Book implements Serializable {
    private long id;
    private String name;
    private String author;
    private String genre;
    private String publisher;
    private int yearOfPublishing;
    private byte[] image;
    private int count;

    public Book() {
    }

    public Book(String name, String author, String genre, String publisher, int yearOfPublishing, int count) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.yearOfPublishing = yearOfPublishing;
        this.count = count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYearOfPublishing() {
        return yearOfPublishing;
    }

    public void setYearOfPublishing(int yearOfPublishing) {
        this.yearOfPublishing = yearOfPublishing;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", author=" + author +
                ", genre=" + genre +
                ", publisher=" + publisher +
                ", yearOfPublishing=" + yearOfPublishing +
                '}';
    }
}
