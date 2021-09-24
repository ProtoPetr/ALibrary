package com.epam.command.page;

import com.epam.command.Command;
import com.epam.entity.User;
import com.epam.service.ServiceFactory;
import com.epam.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserManagePageCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        req.getSession().setAttribute("idUser", req.getParameter("idUser"));

        if ("update".equals(req.getParameter("manageAction"))) {

            final long id = Long.parseLong(req.getParameter("idUser"));
            final String name = req.getParameter("nameM");
            final String surname = req.getParameter("surnameM");
            final String login = req.getParameter("loginM");
            final String password = req.getParameter("passwordM");
            final String mail = req.getParameter("mailM");
            final String role = req.getParameter("roleM");
            final String status = req.getParameter("statusM");

            UserService uss = ServiceFactory.getServiceFactory("MySQL").getUserService();

            User user = new User(name, surname, login, password, mail);
            user.setId(id);

            if (role == null) {
                user.setRole("user");
            } else {
                user.setRole(role);
            }

            if (status == null) {
                user.setStatus("active");
            } else {
                user.setStatus(status);
            }

            uss.updateUserData(user);

            req.getSession().setAttribute("change_user_info", "User data was successfully updated");
        }

        return "/pages/admin_page/user_manage.jsp";
    }
}
