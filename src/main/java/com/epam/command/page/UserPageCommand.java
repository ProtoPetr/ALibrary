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

        if (req.getSession().getAttribute("currentBookList") == null) {
            req.getSession().setAttribute("currentBookList", list);
        }

        List<Book> currentList = (List<Book>) req.getSession().getAttribute("currentBookList");

        String sortAction = req.getParameter("sortAction");
        req.getSession().setAttribute("sortAction", sortAction);

        if ("sortByName".equals(req.getSession().getAttribute("sortAction"))) {
            req.getSession().setAttribute("currentBookList", currentList.stream()
                    .sorted(Comparator.comparing(Book::getName))
                    .collect(Collectors.toList()));
        }

        if ("sortByAuthor".equals(req.getSession().getAttribute("sortAction"))) {
            req.getSession().setAttribute("currentBookList", currentList.stream()
                    .sorted(Comparator.comparing(Book::getAuthor))
                    .collect(Collectors.toList()));
        }

        if ("sortByPublisher".equals(req.getSession().getAttribute("sortAction"))) {
            req.getSession().setAttribute("currentBookList", currentList.stream()
                    .sorted(Comparator.comparing(Book::getPublisher))
                    .collect(Collectors.toList()));
        }

        if ("sortByYear".equals(req.getSession().getAttribute("sortAction"))) {
            req.getSession().setAttribute("currentBookList", currentList.stream()
                    .sorted(Comparator.comparing(Book::getYearOfPublishing))
                    .collect(Collectors.toList()));
        }

        req.getSession().setAttribute("pagination", false);

        if ("true".equals(req.getParameter("pagination"))) {
            req.getSession().setAttribute("pagination", true);
            int booksPerPage = 4;

            if (req.getParameter("booksPerPage") != null) {
                booksPerPage = Integer.parseInt(req.getParameter("booksPerPage"));
                req.getSession().setAttribute("pageItemsCount", booksPerPage);
            }

            int allBooksCount = ((List<Book>) req.getSession().getAttribute("currentBookList")).size();

            int totalPages;
            if (allBooksCount > 0) {
                totalPages = allBooksCount % booksPerPage == 0 ? allBooksCount / booksPerPage : allBooksCount / booksPerPage + 1;
            } else {
                totalPages = 0;
            }

            req.getSession().setAttribute("totalPages", totalPages);

            int currentPage = 1;
            if (req.getParameter("currentPage") != null) {
                currentPage = Integer.parseInt(req.getParameter("currentPage"));
            }
            System.out.println(currentPage);
            req.getSession().setAttribute("currentPage", currentPage);

            int finalBooksPerPage = booksPerPage;
            int finalCurrentPage = currentPage;
            List<Book> pageBookList = ((List<Book>) req.getSession().getAttribute("currentBookList")).stream()
                    .filter(book -> currentList.indexOf(book) >= (finalCurrentPage - 1) * finalBooksPerPage &&
                            currentList.indexOf(book) < finalCurrentPage * finalBooksPerPage).collect(Collectors.toList());

            req.getSession().setAttribute("pageBookList", pageBookList);
        }

        return "/pages/user_page/user_menu.jsp";
    }
}
