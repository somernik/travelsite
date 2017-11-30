package com.sarah.controller;

import com.sarah.entity.User;
import com.sarah.persistence.UserDao;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

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

        if (firstName != null && firstName.length() > 0) {
            user.setFirstName(StringEscapeUtils.escapeJava(firstName));
        }
        if (lastName != null && lastName.length() > 0) {
            user.setLastName(StringEscapeUtils.escapeJava(lastName));
        }
        if (userName != null && userName.length() > 0) {
            user.setUserName(StringEscapeUtils.escapeJava(userName));
        }
        if (email != null && email.length() > 0) {
            user.setEmail(StringEscapeUtils.escapeJava(email));
        }

        // TODO get password working
        String newPassword = req.getParameter("passwordInput");
        String confirmPassword = req.getParameter("passwordConfirmInput");
        String oldPassword = req.getParameter("oldPasswordInput");
        logger.info(newPassword);
        logger.info(confirmPassword);
        logger.info(oldPassword);

        if (newPassword != null && confirmPassword != null && oldPassword != null
                && newPassword.length() > 5 && confirmPassword.length() > 5 && oldPassword.length() > 5
                && newPassword.equals(confirmPassword)){
            logger.info(newPassword);
            List<User> users = userDao.findByProperty(User.class, "userName", user.getUserName());
            if (users.size() == 1 && users.get(0).getPassword().equals(oldPassword)) {
                logger.info(oldPassword);
                user.setPassword(StringEscapeUtils.escapeJava(newPassword));
                userDao.update(user);
                req.setAttribute("message", "");
            }else {
                // send error message
                // password doesnt match

                req.setAttribute("message", "Invalid password.");
            }

        } else {
            // Send error message
            // passwords dont match  or not long enough
            req.setAttribute("message", "New password and confirm password must be at least 5 characters and match");
            // TODO appears when updating other items
        }
        logger.info(req.getParameter("firstName"));
        logger.info(req.getParameter("lastName"));
        logger.info(req.getParameter("userName"));
        logger.info(req.getParameter("email"));
        userDao.update(user);

        logger.info("Updated" + user);

        req.setAttribute("user", user);
        session.setAttribute("user", user);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/user.jsp");
        dispatcher.forward(req, resp);
    }
}
