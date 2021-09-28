package com.epam.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * This class present factory pattern
 * and dispatch commands
 */
public final class CommandFactory {
    private static final Logger logger = LogManager.getLogger(CommandFactory.class);

    private CommandFactory() {
    }

    /**
     * Method return current command depends on request
     */
    public static Command getCommand(HttpServletRequest req) {
        String command = req.getParameter("command");
        Command com;
        if (command != null) {
           try {
               com = CommandEnum.valueOf(command).getCommand();
           } catch (IllegalArgumentException e) {
               com = CommandEnum.ERROR_PAGE.getCommand();
               logger.error(e.getMessage(), e);
           }
        } else {
            com = CommandEnum.ERROR_PAGE.getCommand();
        }
        return com;
    }
}
