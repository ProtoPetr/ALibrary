package com.epam.command.page;

import com.epam.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class implements Command interface
 */
public class UserAccountPageCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        req.getSession().setAttribute("addBookToDeskStatus", null);

        return "/pages/user_page/user_account.jsp";
    }
}
