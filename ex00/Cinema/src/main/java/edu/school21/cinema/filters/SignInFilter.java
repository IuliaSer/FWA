package edu.school21.cinema.filters;

import edu.school21.cinema.models.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({"/signIn", "/signUp", "/profile"})
public class SignInFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if(user != null && (requestURI.contains("signIn") || requestURI.contains("signUp"))) {
            response.sendRedirect(request.getContextPath() + "/profile");
            return;
        }
        else if (user == null && requestURI.contains("profile")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        chain.doFilter(req, res);
    }
}