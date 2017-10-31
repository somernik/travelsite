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

/**
 * Updates User
 * Created by sarah on 9/21/2017.
 */

@WebServlet(
        urlPatterns = {"/updateUser"}
)
public class UpdateUser extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO validate password check
        // TODO validate all user input
        UserDao userDao = new UserDao();
        User updateUser = userDao.getUserById(Long.parseLong(req.getParameter("id"))); // TODO get from session
        logger.info("Current: " + updateUser);

        //User updateUser = new User(req.getParameter("first_name"), req.getParameter("last_name"), req.getParameter("email"), req.getParameter("password"), req.getParameter("username")); // pass in params
        updateUser.setUserName(req.getParameter("newUsername"));
        logger.info("To Update: " + updateUser);
        userDao.update(updateUser);

        logger.info("Updated" + updateUser);

        req.setAttribute("user", updateUser); // TODO add user to session

        RequestDispatcher dispatcher = req.getRequestDispatcher("/user.jsp");
        dispatcher.forward(req, resp);
    }
}
