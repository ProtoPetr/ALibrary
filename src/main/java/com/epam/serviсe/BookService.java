package com.epam.serviсe;

import com.epam.entity.Book;
import com.epam.supplies.SearchType;

import java.util.List;

/**
 * Interface declares methods for work with current entity
 * and contains specific logic
 */
public interface BookService {

    void createBook(Book book);
    void updateBook(Book book);
    void deleteBook(long id);
    Book getBookById(long id);
    List<Book> getBooksBySearch(String searchStr, SearchType type);
    List<Book> getBooksByLetter(String letter);
    List<Book> getBooksByGenreId(long id);
    List<Book> getAllBooks();
    List<Book> getAllBooksSortedByName();
    List<Book> getAllBooksSortedByAuthor();
    List<Book> getAllBooksSortedByPublisher();
    List<Book> getAllBooksSortedByYearOfPublishing();
}
