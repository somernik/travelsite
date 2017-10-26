package com.sarah.utility;

import com.sarah.entity.User;
import com.sarah.persistence.UserDao;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by sarah on 10/22/2017.
 */
@WebFilter("/*")
public class UserFilter implements Filter {

    public void init(FilterConfig config) {

    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String remoteUser = request.getRemoteUser();

        if (remoteUser != null) {
            HttpSession session = request.getSession();

            if (session.getAttribute("user") == null) {
                UserDao userDao = new UserDao();
                User user = userDao.getUserByUsername(remoteUser);

                session.setAttribute("user", user);
            }
        }

        chain.doFilter(req, res);
    }

    public void destroy() {
        // If you have assigned any expensive resources as field of
        // this Filter class, then you could clean/close them here.
    }
}