package com.epam.command.page;

import com.epam.command.Command;
import com.epam.servise.DeliveryDeskService;
import com.epam.servise.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class implements Command interface
 */
public class CurrentDeliveryDeskCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        DeliveryDeskService dds = ServiceFactory.getServiceFactory("MySQL").getDeliveryDeskService();

        if ("issue".equals(req.getParameter("manageAction"))) {
            long deskId = Long.parseLong(req.getParameter("deskId"));
            long bookId = Long.parseLong(req.getParameter("bookId"));
            if ("1d".equals(req.getParameter("term"))) {
                dds.issueBookFromDeliveryDeskOnDay(deskId, bookId);
            }
            if ("30d".equals(req.getParameter("term"))) {
                dds.issueBookFromDeliveryDeskOnMonth(deskId, bookId);
            }
        }

        if ("get".equals(req.getParameter("manageAction"))) {
            long deskId = Long.parseLong(req.getParameter("deskId"));
            long bookId = Long.parseLong(req.getParameter("bookId"));
            dds.returnBookToDeliveryDesk(deskId, bookId);
        }

        return "/pages/librarian_page/current_delivery_desk.jsp";
    }
}
