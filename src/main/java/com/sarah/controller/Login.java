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
import java.util.List;

/**
 * Created by sarah on 10/22/2017.
 */

@WebServlet(
        urlPatterns = {"/login"}
)
public class Login extends HttpServlet {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        resp.sendRedirect("user.jsp");

    }
}
