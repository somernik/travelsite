package com.sarah.controller;

import com.sarah.entity.User;
import com.sarah.persistence.UserDao;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple servlet to welcome the user.
 * @author somernik
 */

@WebServlet(
        urlPatterns = {"/searchUser"}
)

public class SearchUser extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // old way List<User> users = searchUsers(req);
        //req.setAttribute("users", users);
        //logger.info(users);

        //List<User> users = searchUsers(req);
        //req.setAttribute("users", users);

        List<User> users = new ArrayList<User>();
        if (req.getParameter("searchValue") != null) {
            if (req.getParameter("searchType").equals("id")) {
                User user = searchUserById(req);
                users.add(user);

            }

            String type;
            String operator;

            if (req.getParameter("searchType").equals("f_name")) {
                type = "first name";
            } else if (req.getParameter("searchType").equals("l_name")) {
                type = "last name";
            } else {
                type = req.getParameter("searchType");
            }

            if (req.getParameter("searchOperator").equals("LIKE")) {
                operator = "contains";
            } else {
                operator = req.getParameter("searchOperator");
            }

            req.setAttribute("type", type);
            req.setAttribute("value", req.getParameter("searchValue"));
            req.setAttribute("operator", operator);
        }
        logger.info(users);
        req.setAttribute("users", users);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/results.jsp");
        dispatcher.forward(req, resp);
    }

    private User searchUserById(HttpServletRequest req) {

        User user;
        UserDao userDao = new UserDao();

        user = userDao.getUserById(Integer.parseInt(req.getParameter("searchValue"))); // TODO check inputs

        logger.debug(user);
        return user;
    }

}