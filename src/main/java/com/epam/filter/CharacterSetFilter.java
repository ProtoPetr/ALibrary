package com.epam.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * This filter sets the default encoding for request and response
 */
public class CharacterSetFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain next) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        next.doFilter(request, response);
    }
}
