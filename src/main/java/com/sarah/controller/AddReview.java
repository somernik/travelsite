package com.sarah.controller;

import com.sarah.entity.LocationEntity;
import com.sarah.entity.ReviewEntity;
import com.sarah.entity.User;
import com.sarah.persistence.LocationDao;
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
 * Created by sarah on 10/5/2017.
 */
@WebServlet(
        urlPatterns = {"/addReview"}
)
public class AddReview extends HttpServlet {
    private final Logger log = Logger.getLogger(this.getClass());

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        LocationDao locationDao = new LocationDao();
        // TODO validate input
        // TODO get user?
        log.info(req.getParameter("placeId"));
        log.info(req.getParameter("placeName"));
        LocationEntity location = new LocationEntity(req.getParameter("placeName"), req.getParameter("placeId"));
        location = locationDao.update(location);
        log.info(location.getId());
        log.info(session.getAttribute("user"));

        log.info(req.getParameter("date"));
        log.info(req.getParameter("review"));
        log.info(req.getParameter("rating"));

        log.info(req.getParameter("goodTags"));
        log.info(req.getParameter("badTags"));

        String[] badTags = req.getParameter("badTags").split("\\;");
        for (String tag: badTags) {
            // TODO process each tag
            log.info(tag);
        }
        // TODO if there is a review body (which is optional)
        // TODO convert string date to date
        //ReviewEntity review = new ReviewEntity(req.getParameter("review"), LocalDate date, User user, locations.get(0));

        RequestDispatcher dispatcher = req.getRequestDispatcher("detail.jsp");
        dispatcher.forward(req, resp);
    }
}
