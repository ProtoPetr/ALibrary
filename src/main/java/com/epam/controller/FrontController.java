package com.epam.controller;

import com.epam.command.Command;
import com.epam.command.CommandFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = handleRequest(req, resp);
        if (!"procedure".equals(forward)) {
            req.getRequestDispatcher(forward).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String redirect = handleRequest(req, resp);
        if (!"procedure".equals(redirect)) {
            resp.sendRedirect(redirect);
        }
    }

    private String handleRequest(HttpServletRequest req, HttpServletResponse resp) {
        Command command = CommandFactory.getCommand(req);
        return command.execute(req, resp);
    }
}
