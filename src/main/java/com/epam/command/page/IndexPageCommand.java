package com.epam.command.page;

import com.epam.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class IndexPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        final HttpSession session = req.getSession();

        session.removeAttribute("password");
        session.removeAttribute("login");
        session.removeAttribute("role");

        return "/WEB-INF/index.jsp";
    }
}
