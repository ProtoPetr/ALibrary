package com.epam.command.procedure;

import com.epam.command.Command;
import com.epam.entity.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * This class implements Command interface
 */
public class ShowImageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ShowImageCommand.class);

    /**
     * Method send response with current picture
     * depends on request parameter `index`
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String condition = "procedure";

        resp.setContentType("image/jpeg");
        try (OutputStream out = resp.getOutputStream()) {
            int index = Integer.parseInt(req.getParameter("index"));

            ArrayList<Book> list = (ArrayList<Book>) req.getSession(false).getAttribute("currentBookList");
            Book book = list.get(index);
            resp.setContentLength(book.getImage().length);
            out.write(book.getImage());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return condition;
    }
}
