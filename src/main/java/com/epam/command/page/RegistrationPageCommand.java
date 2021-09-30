package com.epam.command.page;

import com.epam.command.Command;
import com.epam.entity.User;
import com.epam.serviсe.ServiceFactory;
import com.epam.serviсe.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class implements Command interface
 */
public class RegistrationPageCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        if ("create".equals(req.getParameter("regAction"))) {
            final String name = req.getParameter("nameR");
            final String surname = req.getParameter("surnameR");
            final String login = req.getParameter("loginR");
            final String password = req.getParameter("passwordR");
            final String mail = req.getParameter("mailR");

            UserService uss = ServiceFactory.getServiceFactory("MySQL").getUserService();

            if (!uss.userIsExist(login, password)) {
                User user = new User(name, surname, login, password, mail);
                uss.createUser(user);
                req.getSession().setAttribute("registrationStatus", "success");
            } else {
                req.getSession().setAttribute("registrationStatus", "failed");
            }
        }

        return "/pages/registration.jsp";
    }
}
