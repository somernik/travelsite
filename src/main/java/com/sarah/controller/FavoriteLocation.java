package com.sarah.controller;

import com.sarah.entity.LocationEntity;
import com.sarah.entity.User;
import com.sarah.persistence.LocationDao;
import com.sarah.persistence.UserDao;
import org.apache.log4j.Logger;
import org.hibernate.criterion.MatchMode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Allows a user to save a location.
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

        req.setAttribute("message", "");
        logger.info(currentUser);
        // if no user -> exit
        if (currentUser == null) {
            logger.info("no user");
            // send error message
            resp.sendRedirect("viewDetails?placeId=" + req.getParameter("placeId")
                    + "&placeName=" + req.getParameter("placeName")
                    + "&message=" + "You must be logged in to add a review");
        } else {

            String googleId = req.getParameter("placeId");
            if (googleId.equals(null)) {
                // no location passed
                // TODO exit/error
                req.setAttribute("message", "There was an error saving the location");
            }
            LocationDao locationDao = new LocationDao();
            List<LocationEntity> locations = locationDao.findByProperty(LocationEntity.class, "googleId", googleId, MatchMode.EXACT);
            logger.info("locations: " + locations);

            if (locations.size() == 1) {

                UserDao userDao = new UserDao();
                currentUser = userDao.addSavedLocation(currentUser, locations.get(0));
                req.setAttribute("message", "Location saved!");
                //update session variable to update on users page
                session.setAttribute("user", currentUser);

            } else {
                // TODO send error?
                //add location to db
                // need name
                // and escape string
                // break out check if exists and add to its own class or something
                // used here add review and view details

            }

            req.setAttribute("placeId", googleId);

            // Update user and images
            GoogleAPIAccessor googleAPIAccessor = new GoogleAPIAccessor();
            Map<Long, String> locationImageURLs = new HashMap<Long, String>();

            for (LocationEntity location : currentUser.getLocations()) {
                String imageURL = googleAPIAccessor.getPhotoFromGoogle(location.getGoogleId());
                locationImageURLs.put(location.getId(), imageURL);
            }

            session.setAttribute("imageUrls", locationImageURLs);
            session.setAttribute("user", currentUser);

            // TODO add abitility to favorite from search page as well?
            resp.sendRedirect("viewDetails");
        }
    }

}
