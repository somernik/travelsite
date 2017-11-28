package com.sarah.controller;

import com.sarah.entity.User;
import com.sarah.persistence.GenericDao;
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
 * Deletes User
 * Created by sarah on 9/21/2017.
 */

@WebServlet(
        urlPatterns = {"/deleteUser"}
)
public class DeleteUser extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO validate password check
        // TODO validate all user input
        UserDao userDao = new UserDao();
        GenericDao dao = new GenericDao();
        User userToDelete = userDao.getUserById(Long.parseLong(req.getParameter("id")));
        logger.info("Current: " + userToDelete);

        dao.delete(User.class, userToDelete.getId()); // TODO get from session
        logger.info("Deleted: " + userToDelete);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/index.jsp");
        dispatcher.forward(req, resp);
    }
}
