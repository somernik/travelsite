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
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Adds a review
 * Created by sarah on 10/5/2017.
 */
@WebServlet(
        urlPatterns = {"/addReview"}
)
public class AddReview extends HttpServlet {
    private final Logger log = Logger.getLogger(this.getClass());
    private GoogleAPIAccessor googleAPIAccessor = new GoogleAPIAccessor();
    private DateFormatter formatter = new DateFormatter();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        LocationDao locationDao = new LocationDao();

        log.info(session.getAttribute("user"));
        // Get user from session
        User currentUser = (User) session.getAttribute("user");
        String referrer = req.getParameter("referrer");

        // TODO validate input
        // if no user -> exit
        if (currentUser == null) {
            log.info("no user");

            RequestDispatcher dispatcher = req.getRequestDispatcher("user.jsp");
            dispatcher.forward(req, resp);
        }

        // somehow no location properties -> exit
        if (req.getParameter("placeId") == null || req.getParameter("placeName") == null
                || req.getParameter("placeId").length() < 1 || req.getParameter("placeName").length() < 1) {
            log.info("null or empty properties");

            RequestDispatcher dispatcher = req.getRequestDispatcher(referrer);
            dispatcher.forward(req, resp);
        } else {

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
            Long locationId = 0L;

            log.info(locationId);

            // TODO NEW METHOD get location
            if (locations.size() == 1) {
                location = locations.get(0);
                locationId = location.getId();
            } else if (locations.size() == 0 || locations == null) {
                String escapedName = StringEscapeUtils.escapeJava(req.getParameter("placeName"));
                location = new LocationEntity(escapedName, req.getParameter("placeId"));
                log.info("escaped Name: " + escapedName);
                String photoReference = googleAPIAccessor.getPhotoFromGoogle(req.getParameter("placeId"));
                location.setPhotoReference(photoReference);
                locationId = locationDao.save(location);
                location.setId(locationId);

                req.setAttribute("location", location);
            } else {
                // TODO error with location -> exit
                // TODO figure out how to add error message and save search
                //RequestDispatcher dispatcher = req.getRequestDispatcher("explore");
                //dispatcher.forward(req, resp);
            }

            //log.info(req.getParameter("date"));
            LocalDate date = LocalDate.now();
            if (req.getParameter("date").length() > 0) {
                date = formatter.convertStringToLocalDate(req.getParameter("date"));
            }

            // Process tags
            if (req.getParameter("badTags").length() > 0) {
                processTagInput(currentUser, req.getParameter("badTags"), location, false, date);
            }

            if (req.getParameter("goodTags").length() > 0) {
                processTagInput(currentUser, req.getParameter("goodTags"), location, true, date);
            }

            // add review (which is optional) --  review, stars, date and location needed
            // TODO require stars/review/date
            // TODO check body length (255)
            if (req.getParameter("review").length() > 0 && locationId > 0 && req.getParameter("date").length() > 0 && req.getParameter("rating").length() > 0) {
                ReviewEntity review = new ReviewEntity(req.getParameter("review"), date, currentUser, location, Integer.parseInt(req.getParameter("rating")));
                log.info(review.toString());
                ReviewDao reviewDao = new ReviewDao();
                Long id = reviewDao.save(review);
            } else {
                // TODO send error
                // dont have review/star/date
            }

            // update session with users reviews
            ReviewDao reviewDao = new ReviewDao();
            List<ReviewEntity> reviews = reviewDao.findByAndInitializeProperties("user", currentUser);
            session.setAttribute("userReviews", reviews);

            log.info(referrer);
            resp.sendRedirect(referrer + "?placeId=" + req.getParameter("placeId") + "&placeName=" + req.getParameter("placeName"));
        }
    }

    /**
     * Processes the tag string sent from the form
     * @param tagString the string with tags separated by ';'
     * @param location the current location entity
     * @param positive 'good' vs 'bad' tags
     */
    private void processTagInput(User user, String tagString, LocationEntity location, boolean positive, LocalDate date) {
        TagDao tagDao = new TagDao();
        String[] tags = tagString.split("\\;");

        for (String tag : tags) {
            // TODO sanitize inputs

            log.info("line 126 process tag input" + tag);
            List<TagEntity> returnedTags = tagDao.findByProperty(TagEntity.class, "name", tag, MatchMode.ANYWHERE);
            log.info("returned tags: " + returnedTags);
            log.info("returned tags length: " + returnedTags.size());
            processTag(user, location, tag, returnedTags, positive, date);

        }
    }

    /**
     * Processes each tag, adding new ones to the database and saving them to a specific location
     * @param location the Location entity
     * @param tag the single tag name
     * @param returnedTags any tags that match the current tag
     * @param positive 'good' vs 'bad' tags
     */
    private void processTag(User user, LocationEntity location, String tag, List<TagEntity> returnedTags, boolean positive, LocalDate date) {
        TagDao tagDao = new TagDao();

        TagLocationDao tagLocationDao = new TagLocationDao();

        //if (returnedTags.size() == 0) {
            // tag doesn't exist so add it
            TagEntity tagObj = new TagEntity(tag);
            Long id = tagDao.save(tagObj);
            tagObj.setId(id);
            TaglocationEntity taglocation;

            log.info("line 144: process tag " + tagObj);
            // add taglocation each 'vote'
            if (positive) {

                taglocation = new TaglocationEntity(user, location, tagObj, true, date);

            } else {
                taglocation = new TaglocationEntity(user, location, tagObj, false, date);
            }
            tagLocationDao.save(taglocation);

            log.info(taglocation);

        /*} else {
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
        */
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("post method");
    }

}
