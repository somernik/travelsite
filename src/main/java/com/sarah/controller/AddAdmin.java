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
 * Created by sarah on 10/5/2017.
 */
@WebServlet(
    urlPatterns = {"/addAdmin"}
)
public class AddAdmin extends HttpServlet{
    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // TODO validate all user input
        UserDao userDao = new UserDao();
        /*
        User updateUser = userDao.getUserById(Integer.parseInt(req.getParameter("id"))); // TODO get from session

        userDao.update(updateUser);

        req.setAttribute("user", updateUser); // TODO add user to adminUsers in session
*/
        RequestDispatcher dispatcher = req.getRequestDispatcher("admin/admin.jsp");
        dispatcher.forward(req, resp);
    }
}
