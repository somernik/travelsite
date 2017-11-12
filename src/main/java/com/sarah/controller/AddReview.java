package com.sarah.controller;

import com.sarah.entity.LocationEntity;
import com.sarah.entity.ReviewEntity;
import com.sarah.entity.User;
import com.sarah.persistence.LocationDao;
import com.sarah.persistence.ReviewDao;
import org.apache.log4j.Logger;
import org.hibernate.criterion.MatchMode;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * Created by sarah on 10/5/2017.
 */
@WebServlet(
        urlPatterns = {"/addReview"}
)
public class AddReview extends HttpServlet {
    private final Logger log = Logger.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        LocationDao locationDao = new LocationDao();

        log.info(session.getAttribute("user"));
        // Get user from session
        User currentUser = (User) session.getAttribute("user");

        // TODO validate input
        // TODO if no user -> exit
        if (currentUser == null) {
            log.info("no user");

            RequestDispatcher dispatcher = req.getRequestDispatcher("user.jsp");
            dispatcher.forward(req, resp);
        }
        if (req.getParameter("placeId") == null || req.getParameter("placeName") == null) {
            log.info(" null properties");

            RequestDispatcher dispatcher = req.getRequestDispatcher("explore.jsp");
            dispatcher.forward(req, resp);
        }

        log.info(req.getParameter("placeId"));
        log.info(req.getParameter("placeName"));

        // check if place exists based on placeid
        // if not add
        // add review
        List<LocationEntity> locations = locationDao.findByProperty(LocationEntity.class, "googleId", req.getParameter("placeId"), MatchMode.ANYWHERE);
        LocationEntity location = new LocationEntity();
        Long locationId = new Long(0);
        // TODO NEW METHOD get location
        if (locations.size() == 1) {
            location = locations.get(0);
            locationId = location.getId();
        } else if (locations.size() == 0 || locations == null) {
            location = new LocationEntity(req.getParameter("placeName"), req.getParameter("placeId"));
            locationId = locationDao.save(location);
        } else {
            // TODO error with location -> exit
        }

        log.info(locationId);

        log.info(req.getParameter("date"));

        // TODO new METHOD convert string date to date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy");
        formatter = formatter.withLocale(Locale.US);  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
        LocalDate date = LocalDate.parse((String) req.getParameter("date"), formatter);
        log.info(date);

        log.info(req.getParameter("review"));
        log.info(req.getParameter("rating"));

        log.info(req.getParameter("goodTags"));
        log.info(req.getParameter("badTags"));

        // TODO new METHOD
        if (req.getParameter("badTags").length() > 0) {
            String[] badTags = req.getParameter("badTags").split("\\;");
            for (String tag : badTags) {
                // TODO add each tag
                log.info(tag);
            }
        }

        if (req.getParameter("goodTags").length() > 0) {
            String[] goodTags = req.getParameter("goodTags").split("\\;");
            for (String tag : goodTags) {
                // TODO add each tag
                log.info(tag);
            }
        }


        // add review (which is optional)
        if (req.getParameter("review").length() > 0 && locationId > 0) {
            ReviewEntity review = new ReviewEntity(req.getParameter("review"), date, currentUser, location);
            log.info(review.toString());
            ReviewDao reviewDao = new ReviewDao();
            Long id = reviewDao.save(review);
        }


        // TODO Get locations info for detail page
        // tags, reviews, google images,
        // later - images


        RequestDispatcher dispatcher = req.getRequestDispatcher("detail.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("post method");
    }

}
