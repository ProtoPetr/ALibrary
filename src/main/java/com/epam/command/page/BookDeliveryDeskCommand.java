package com.epam.command.page;

import com.epam.command.Command;
import com.epam.entity.User;
import com.epam.serviсe.DeliveryDeskService;
import com.epam.serviсe.ServiceFactory;
import com.epam.serviсe.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class implements Command interface
 */
public class BookDeliveryDeskCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        DeliveryDeskService deskService = ServiceFactory.getServiceFactory("MySQL").getDeliveryDeskService();
        UserService uss = ServiceFactory.getServiceFactory("MySQL").getUserService();
        String userLogin = (String) req.getSession().getAttribute("login");
        String userPassword = (String) req.getSession().getAttribute("password");
        User user = uss.getUserByLoginPassword(userLogin, userPassword);

        if ("addBook".equals(req.getParameter("deskAction"))) {
            long bookId = Long.parseLong(req.getParameter("bookId"));
            req.getSession().setAttribute("bookId", bookId);
            deskService.addBookToDeliveryDesk(user.getId(), bookId);

            req.getSession().setAttribute("addBookToDeskStatus", "Book was successfully added");
        }

        if ("deleteBook".equals(req.getParameter("deskAction"))) {
            long bookId = Long.parseLong(req.getParameter("bookId"));
            long deliveryDeskId = Long.parseLong(req.getParameter("deskId"));
            System.out.println(bookId);
            System.out.println(deliveryDeskId);
            deskService.deleteBookFromDeliveryDesk(deliveryDeskId, bookId);

            req.getSession().setAttribute("registerInfoAccordion", "");
            req.getSession().setAttribute("deliveryDeskInfoAccordion", "show");

            return "/pages/user_page/user_account.jsp";
        }

        return "/pages/user_page/user_menu.jsp";
    }
}
