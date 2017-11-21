package com.sarah.controller;

import com.sarah.entity.LocationEntity;
import com.sarah.entity.TagEntity;
import com.sarah.entity.User;
import com.sarah.persistence.LocationDao;
import com.sarah.persistence.TagDao;
import com.sarah.persistence.UserDao;
import org.apache.log4j.Logger;
import org.hibernate.criterion.MatchMode;

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
 * Created by sarah on 11/21/2017.
 */

@WebServlet(
        urlPatterns = {"/favorite"}
)
public class FavoriteLocation extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO nth (nice to have) front end api request to avoid reloading page instead of this controller

        HttpSession session = req.getSession();
        logger.info(session.getAttribute("user"));
        // Get user from session
        User currentUser = (User) session.getAttribute("user");

        // if no user -> exit
        if (currentUser == null) {
            logger.info("no user");

            RequestDispatcher dispatcher = req.getRequestDispatcher("user.jsp");
            dispatcher.forward(req, resp);
        }

        String googleId = req.getParameter("placeId");
        if (googleId.equals(null)) {
            // no location passed
            // TODO exit/error
        }
        LocationDao locationDao = new LocationDao();
        List<LocationEntity> locations = locationDao.findByProperty(LocationEntity.class, "googleId" , googleId, MatchMode.EXACT);
        logger.info("locations: " + locations);

        if (locations.size() == 1) {

            UserDao userDao = new UserDao();
            userDao.addSavedLocation(currentUser,locations.get(0));

        } else {
            // TODO send error?
        }

        req.setAttribute("placeId", googleId);

        // TODO add abitility to favorite from search page as well
        RequestDispatcher dispatcher = req.getRequestDispatcher("viewDetails");
        dispatcher.forward(req, resp);
    }

}
