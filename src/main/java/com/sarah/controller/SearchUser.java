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
 * Searches users based on input values.
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
            User user = searchUserById(req);
            users.add(user);
        } else {
            UserDao dao = new UserDao();
            users = dao.findAll(User.class);
        }

        logger.info(users);
        req.setAttribute("users", users);
        RequestDispatcher dispatcher = req.getRequestDispatcher("admin/admin.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * Searches users by specific id
     * @param req the request
     * @return User
     */
    private User searchUserById(HttpServletRequest req) {

        User user;
        UserDao userDao = new UserDao();

        user = userDao.getUserById(Long.parseLong(req.getParameter("searchValue"))); // TODO check inputs

        logger.debug(user);
        return user;
    }

}