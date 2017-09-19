package com.sarah.controller;

import com.sarah.entity.User;
import com.sarah.persistence.UserDao;
import com.sarah.persistence.UserDaoOld;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        List<User> users = searchUsers(req);
        req.setAttribute("users", users);

        UserDao userDao = new UserDao();

        List<User> hibernateUsers = userDao.getAllUsers();
        logger.info(hibernateUsers);
        logger.info(users);

        if (req.getParameter("searchValue") != null) {
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

        RequestDispatcher dispatcher = req.getRequestDispatcher("/results.jsp");
        dispatcher.forward(req, resp);
    }

    private List<User> searchUsers(HttpServletRequest req) {
        List<User> users;
        UserDaoOld userData = new UserDaoOld();



        if (req.getParameter("searchValue") != null) {
            users = userData.getSpecificUsers(req.getParameter("searchType"), req.getParameter("searchValue"), req.getParameter("searchOperator"));
            //User user = userData.getSingleUser(req.getParameter("searchType"), req.getParameter("searchValue"), req.getParameter("searchOperator"));
            //System.out.println(user.toString());
            logger.info("Some message you want logged");
        } else {

        }
        users = userData.getAllUsers();
        logger.debug(users);
        return users;
    }

}