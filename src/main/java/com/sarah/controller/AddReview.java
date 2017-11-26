package com.sarah.controller;

import com.sarah.entity.*;
import com.sarah.persistence.LocationDao;
import com.sarah.persistence.ReviewDao;
import com.sarah.persistence.TagDao;
import com.sarah.persistence.TagLocationDao;
import com.sarah.utility.DateFormatter;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.MatchMode;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.swing.text.html.HTML;
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

    private DateFormatter formatter = new DateFormatter();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        LocationDao locationDao = new LocationDao();

        log.info(session.getAttribute("user"));
        // Get user from session
        User currentUser = (User) session.getAttribute("user");

        // TODO validate input
        // if no user -> exit
        if (currentUser == null) {
            log.info("no user");

            RequestDispatcher dispatcher = req.getRequestDispatcher("user.jsp");
            dispatcher.forward(req, resp);
        }

        // somehow no location properties -> exit
        if (req.getParameter("placeId") == null || req.getParameter("placeName") == null) {
            log.info("null properties");

            RequestDispatcher dispatcher = req.getRequestDispatcher("explore");
            dispatcher.forward(req, resp);
        }

        log.info(req.getParameter("placeId"));
        log.info(req.getParameter("placeName"));
        log.info(req.getParameter("review"));
        log.info(req.getParameter("rating"));
        log.info(req.getParameter("goodTags"));
        log.info(req.getParameter("badTags"));

        // check if place exists based on placeid
        // if not add review
        List<LocationEntity> locations = locationDao.findByProperty(LocationEntity.class, "googleId", req.getParameter("placeId"), MatchMode.ANYWHERE);
        LocationEntity location = new LocationEntity();
        Long locationId = new Long(0);

        log.info(locationId);

        // TODO NEW METHOD get location
        if (locations.size() == 1) {
            location = locations.get(0);
            locationId = location.getId();
        } else if (locations.size() == 0 || locations == null) {
            String escapedName = StringEscapeUtils.escapeJava(req.getParameter("placeName"));
            location = new LocationEntity(escapedName, req.getParameter("placeId"));
            locationId = locationDao.save(location);
            location.setId(locationId);
        } else {
            // TODO error with location -> exit
            // TODO figure out how to add error message and save search
            RequestDispatcher dispatcher = req.getRequestDispatcher("explore");
            dispatcher.forward(req, resp);
        }

        //log.info(req.getParameter("date"));
        LocalDate date = LocalDate.now();
        if (req.getParameter("date").length() > 0) {
            date = formatter.convertStringToLocalDate((String) req.getParameter("date"));
        }

        // Process tags
        if (req.getParameter("badTags").length() > 0) {
            processTagInput(req.getParameter("badTags"), location, false);
        }

        if (req.getParameter("goodTags").length() > 0) {
            processTagInput(req.getParameter("goodTags"), location, true);
        }

        // add review (which is optional)
        if (req.getParameter("review").length() > 0 && locationId > 0 && req.getParameter("date").length() > 0) {
            ReviewEntity review = new ReviewEntity(req.getParameter("review"), date, currentUser, location, Integer.parseInt(req.getParameter("review")));
            log.info(review.toString());
            ReviewDao reviewDao = new ReviewDao();
            Long id = reviewDao.save(review);
        }

        req.setAttribute("locationId", locationId);

        RequestDispatcher dispatcher = req.getRequestDispatcher("viewDetails");
        dispatcher.forward(req, resp);
    }

    private void processTagInput(String tagString, LocationEntity location, boolean positive) {
        TagDao tagDao = new TagDao();
        String[] tags = tagString.split("\\;");

        for (String tag : tags) {
            // TODO sanitize inputs

            log.info("line 126 process tag input" + tag);
            List<TagEntity> returnedTags = tagDao.findByProperty(TagEntity.class, "name", tag, MatchMode.ANYWHERE);
            log.info("returned tags: " + returnedTags);
            log.info("returned tags length: " + returnedTags.size());
            processTag(location, tag, returnedTags, positive);

        }
    }

    private void processTag(LocationEntity location, String tag, List<TagEntity> returnedTags, boolean positive) {
        TagDao tagDao = new TagDao();

        TagLocationDao tagLocationDao = new TagLocationDao();

        if (returnedTags.size() == 0) {
            // tag doesn't exist so add it
            TagEntity tagObj = new TagEntity(tag);
            Long id = tagDao.save(tagObj);
            tagObj.setId(id);
            TaglocationEntity taglocation;

            log.info("line 144: process tag " + tagObj);
            // add taglocation
            if (positive) {

                taglocation = new TaglocationEntity(1, 0, location, tagObj);
            } else {
                taglocation = new TaglocationEntity(0, 1, location, tagObj);
            }
            tagLocationDao.save(taglocation);

            log.info(taglocation);

        } else {
            // update taglocation
            tagLocationDao = new TagLocationDao();

            TaglocationEntity taglocation = tagLocationDao.getByLocationAndTag(location, returnedTags.get(0));

            // update ranks
            if (positive) {
                taglocation.setRank(taglocation.getRank() + 1);
            } else {
                taglocation.setNegativeRank(taglocation.getNegativeRank() + 1);
            }

            tagLocationDao.update(taglocation);

            log.info(taglocation);

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("post method");
    }

}
