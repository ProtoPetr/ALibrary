package com.epam.command.page;

import com.epam.command.Command;
import com.epam.entity.Book;
import com.epam.service.BookService;
import com.epam.service.ServiceFactory;
import com.epam.supplies.SearchType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        BookService bss = ServiceFactory.getServiceFactory("MySQL").getBookService();
        List<Book> list = new ArrayList<>();

        if (req.getParameter("genre_id") != null) {
            long genreId = Long.parseLong(req.getParameter("genre_id"));
            list = bss.getBooksByGenreId(genreId);
            req.getSession().setAttribute("selectedGenreId", genreId);
        } else if (req.getParameter("letter") != null) {
            String letter = req.getParameter("letter");
            list = bss.getBooksByLetter(letter);
            req.getSession().setAttribute("searchLetter", "letter");
        } else if (req.getParameter("search_string") != null) {
            String searchString = req.getParameter("search_string");
            System.out.println(searchString);
            req.getSession().setAttribute("search_string", searchString);
            SearchType type = SearchType.NAME;
            if (req.getParameter("search_option").equals("2")) {
                type = SearchType.AUTHOR;
            }
            if (searchString != null && !searchString.trim().equals("")) {
                list = bss.getBooksBySearch(searchString, type);
            }
            System.out.println(list);
        } else {
            list = bss.getAllBooks();
        }

        req.getSession().setAttribute("currentBookList", list);

        List<Book> currentList = (List<Book>) req.getSession().getAttribute("currentBookList");

        System.out.println(currentList);

        if ("sortByName".equals(req.getParameter("sortAction"))) {
            req.getSession().setAttribute("currentBookList", currentList.stream()
                    .sorted(Comparator.comparing(Book::getName))
                    .collect(Collectors.toList()));
        }

        if ("sortByAuthor".equals(req.getParameter("sortAction"))) {
            req.getSession().setAttribute("currentBookList", currentList.stream()
                    .sorted(Comparator.comparing(Book::getAuthor))
                    .collect(Collectors.toList()));
        }

        if ("sortByPublisher".equals(req.getParameter("sortAction"))) {
            req.getSession().setAttribute("currentBookList", currentList.stream()
                    .sorted(Comparator.comparing(Book::getPublisher))
                    .collect(Collectors.toList()));
        }

        if ("sortByYear".equals(req.getParameter("sortAction"))) {
            req.getSession().setAttribute("currentBookList", currentList.stream()
                    .sorted(Comparator.comparing(Book::getYearOfPublishing))
                    .collect(Collectors.toList()));
        }

        return "/pages/user_page/user_menu.jsp";
    }
}
