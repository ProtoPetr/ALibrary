package com.epam.command.page;

import com.epam.command.Command;
import com.epam.entity.User;
import com.epam.serviсe.ServiceFactory;
import com.epam.serviсe.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class implements Command interface
 */
public class LibrarianPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        if ("searchUser".equals(req.getParameter("searchUserAction"))) {
            final String userName = req.getParameter("searchedUserName");

            UserService uss = ServiceFactory.getServiceFactory("MySQL").getUserService();

            List<User> list = uss.getUsersBySearch(userName).stream()
                    .filter(u -> u.getRole().equals("user")).collect(Collectors.toList());

            req.getSession().setAttribute("searchedUsersList", list);

            if (!list.isEmpty()) {
                req.getSession().setAttribute("search_user_status", "Was found " + list.size() + " user(s)");
            }
        }
        return "/pages/librarian_page/librarian_menu.jsp";
    }
}
