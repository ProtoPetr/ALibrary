package com.epam.command.page;

import com.epam.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class implements Command interface
 */
public class RegistrationPageCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return "/pages/registration.jsp";
    }
}
