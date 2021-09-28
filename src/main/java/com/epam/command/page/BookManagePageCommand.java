package com.epam.command.page;

import com.epam.command.Command;
import com.epam.entity.Book;
import com.epam.service.BookService;
import com.epam.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class implements Command interface
 */
public class BookManagePageCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        BookService bss = ServiceFactory.getServiceFactory("MySQL").getBookService();
        req.getSession().setAttribute("idBook", req.getParameter("idBook"));

        if ("update".equals(req.getParameter("manageAction"))) {

            final long id = Long.parseLong(req.getParameter("idBook"));
            Book book = mapFromReqToBook(req);
            book.setId(id);
            bss.updateBook(book);

            req.getSession().setAttribute("manage_book_info", "Book data was successfully updated");
        }

        if ("create".equals(req.getParameter("manageAction"))) {
            Book book = mapFromReqToBook(req);
            bss.createBook(book);

            req.getSession().setAttribute("manage_book_info", "Book was successfully added");
        }

        return "/pages/admin_page/book_manage.jsp";
    }

    private Book mapFromReqToBook(HttpServletRequest req) {
        final String name = req.getParameter("nameM");
        final String author = req.getParameter("authorM");
        final String genre = req.getParameter("genreM");
        final String publisher = req.getParameter("publisherM");
        final int yearOfPublishing = Integer.parseInt(req.getParameter("yearOfPublishingM"));
        final int count = Integer.parseInt(req.getParameter("countM"));

        return new Book(name, author, genre, publisher, yearOfPublishing, count);
    }
}
