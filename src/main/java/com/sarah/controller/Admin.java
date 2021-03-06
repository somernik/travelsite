package com.sarah.controller;

import com.sarah.entity.*;
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
import java.util.List;

/**
 * Generates list of admin users
 * Created by sarah on 10/5/2017.
 */
@WebServlet(
        urlPatterns = {"/admin"}
)
public class Admin extends HttpServlet{
    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        UserDao userDao = new UserDao();
        List<User> adminUsers = userDao.getAdminUsers();

        session.setAttribute("adminUsers", adminUsers);
        logger.info("admin users: " + adminUsers);

        RequestDispatcher dispatcher = req.getRequestDispatcher("admin/admin.jsp");
        dispatcher.forward(req, resp);
    }


}
