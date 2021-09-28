package com.epam.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface declares method for work with current command
 */
public interface Command {

    String execute(HttpServletRequest req, HttpServletResponse resp);

}
