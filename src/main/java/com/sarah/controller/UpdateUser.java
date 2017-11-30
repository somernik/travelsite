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
import javax.servlet.http.HttpSession;
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
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");
        logger.info(user);
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String userName = req.getParameter("userName");
        String email = req.getParameter("email");

        if (firstName.length() > 0) {
            user.setFirstName(firstName);
        }
        if (lastName.length() > 0) {
            user.setLastName(lastName);
        }
        if (userName.length() > 0) {
            user.setUserName(userName);
        }
        if (email.length() > 0) {
            user.setEmail(email);
        }
        logger.info(req.getParameter("firstName"));
        logger.info(req.getParameter("lastName"));
        logger.info(req.getParameter("userName"));
        logger.info(req.getParameter("email"));
        //user.setUserName(req.getParameter("newUsername"));
        userDao.update(user);

        logger.info("Updated" + user);

        req.setAttribute("user", user); // TODO add user to session
        session.setAttribute("user", user);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/user.jsp");
        dispatcher.forward(req, resp);
    }
}
