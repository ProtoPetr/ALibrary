package com.epam.filter;

import com.epam.entity.User;
import com.epam.service.ServiceFactory;
import com.epam.service.UserService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This filter is used to define the user's role
 * and grant access levels
 */
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse res = (HttpServletResponse) servletResponse;

         String login = req.getParameter("login");
         String password = req.getParameter("password");

        UserService uss = ServiceFactory.getServiceFactory("MySQL").getUserService();

        final HttpSession session = req.getSession();

        //Logged user
        if (session.getAttribute("login") != null &&
                session.getAttribute("password") != null) {

            final User.ROLE role = (User.ROLE) session.getAttribute("role");

            moveToMenu(req, res, role);

        } else if (uss.userIsExist(login, password)) {

            final User.ROLE role = uss.getRoleByLoginPassword(login, password);

            req.getSession().setAttribute("password", password);
            req.getSession().setAttribute("login", login);
            req.getSession().setAttribute("role", role);

            moveToMenu(req, res, role);

        } else {
            moveToMenu(req, res, User.ROLE.UNKNOWN);
        }
    }

    /**
     * Move user to menu
     * If access 'admin' move to admin menu
     * If access 'user' move to user menu
     * If access 'librarian' move to librarian menu
     */
    private void moveToMenu(final HttpServletRequest req,
                            final HttpServletResponse res,
                            final User.ROLE role) throws ServletException, IOException {
        if (role.equals(User.ROLE.ADMIN)) {
            req.getRequestDispatcher("/controller?command=ADMIN_PAGE").forward(req, res);
        } else if (role.equals(User.ROLE.USER)) {
            req.getRequestDispatcher("/controller?command=USER_PAGE").forward(req, res);
        } else if (role.equals(User.ROLE.LIBRARIAN)) {
            req.getRequestDispatcher("/controller?command=LIBRARIAN_PAGE").forward(req, res);
        } else if (role.equals(User.ROLE.UNKNOWN)) {
            req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, res);
        } else {
            req.getRequestDispatcher("/controller?command=INDEX_PAGE").forward(req, res);
        }
    }
}
