package com.epam.command.page;

import com.epam.command.Command;
import com.epam.entity.Book;
import com.epam.entity.User;
import com.epam.service.BookService;
import com.epam.service.ServiceFactory;
import com.epam.service.UserService;
import com.epam.supplies.SearchType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AdminPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        if ("searchUser".equals(req.getParameter("searchUserAction"))) {
            final String userName = req.getParameter("userName");

            UserService uss = ServiceFactory.getServiceFactory("MySQL").getUserService();

            List<User> list = uss.getUsersBySearch(userName);

            req.getSession().setAttribute("searchedUsersList", list);
            req.getSession().setAttribute("accordionUser", "show");
            req.getSession().setAttribute("accordionBook", "");
            req.getSession().setAttribute("search_user_status", "Was found " + list.size() + " user(s)");
        }

        if ("searchBook".equals(req.getParameter("searchBookAction"))) {
            final String bookName = req.getParameter("bookName");
            System.out.println(bookName);
            BookService bss = ServiceFactory.getServiceFactory("MySQL").getBookService();

            List<Book> list = bss.getBooksBySearch(bookName, SearchType.NAME);

            req.getSession().setAttribute("searchedBooksList", list);
            req.getSession().setAttribute("accordionUser", "");
            req.getSession().setAttribute("accordionBook", "show");
            req.getSession().setAttribute("search_book_status", "Was found " + list.size() + " book(s)");
        }

        if ("delete".equals(req.getParameter("deleteAction"))) {
            long id = Long.parseLong(req.getParameter("idBook"));

            BookService bss = ServiceFactory.getServiceFactory("MySQL").getBookService();
            bss.deleteBook(id);

            req.getSession().setAttribute("accordionUser", "");
            req.getSession().setAttribute("accordionBook", "show");
            req.getSession().setAttribute("delete_book_status", "Book was successfully deleted");
        }

        return "/pages/admin_page/admin_menu.jsp";
    }
}
