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
 * A simple servlet to welcome the user.
 * @author somernik
 */

@WebServlet(
        urlPatterns = {"/addUser"}
)

public class AddUser extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // TODO validate password check
        UserDao userDao = new UserDao();
        User newUser = new User(req.getParameter("first_name"), req.getParameter("last_name"), req.getParameter("email"), req.getParameter("password"), req.getParameter("username")); // pass in params
        userDao.insert(newUser);
        logger.info(newUser);


        req.setAttribute("user", newUser); // TODO add user to session

        RequestDispatcher dispatcher = req.getRequestDispatcher("/user.jsp");
        dispatcher.forward(req, resp);
    }

}