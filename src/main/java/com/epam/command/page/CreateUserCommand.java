package com.epam.command.page;

import com.epam.command.Command;
import com.epam.entity.User;
import com.epam.servise.ServiceFactory;
import com.epam.servise.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class implements Command interface
 */
public class CreateUserCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        final String name = req.getParameter("nameR");
        final String surname = req.getParameter("surnameR");
        final String login = req.getParameter("loginR");
        final String password = req.getParameter("passwordR");
        final String mail = req.getParameter("mailR");

        UserService uss = ServiceFactory.getServiceFactory("MySQL").getUserService();

        if (!uss.userIsExist(login, password)) {
            User user = new User(name, surname, login, password, mail);
            uss.createUser(user);
            System.out.println(user);
            req.getSession().setAttribute("registrationStatus", "success");
        } else {
            req.getSession().setAttribute("registrationStatus", "failed");
        }

        return "/pages/registration.jsp";
    }
}
