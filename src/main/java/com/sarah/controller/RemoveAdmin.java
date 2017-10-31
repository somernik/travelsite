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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by sarah on 10/5/2017.
 */
@WebServlet(
        urlPatterns = {"/removeAdmin"}
)
public class RemoveAdmin extends HttpServlet{
    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        List<User> adminUsers = (List<User>) session.getAttribute("adminUsers");
        // TODO check logged in

        UserDao userDao = new UserDao();

        User updateUser = userDao.getUserById(Long.parseLong(req.getParameter("removeId")));
        logger.info("User before: " + updateUser.getUserPrivileges().size());
        //adminUsers.remove(updateUser);
        userDao.removeAdmin(updateUser);
        logger.info("User after remove admin: " + updateUser.getUserPrivileges().size());


        ///userDao.update(updateUser);
        //adminUsers.add(updateUser);
        //logger.info("Retrieving user from database: " + userDao.getUserById((Long) updateUser.getId()).getUserPrivileges().size());
        adminUsers = userDao.getAdminUsers();

        session.setAttribute("adminUsers", adminUsers);

        RequestDispatcher dispatcher = req.getRequestDispatcher("admin/admin.jsp");
        dispatcher.forward(req, resp);
    }
}
