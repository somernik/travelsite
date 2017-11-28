package com.sarah.controller;

import com.sarah.entity.LocationEntity;
import com.sarah.entity.ReviewEntity;
import com.sarah.entity.TaglocationEntity;
import com.sarah.persistence.LocationDao;
import com.sarah.persistence.ReviewDao;
import com.sarah.persistence.TagLocationDao;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringEscapeUtils;

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
 * Created by sarah on 10/29/2017.
 */
@WebServlet(
        urlPatterns = {"/viewDetails"}
)
public class ViewDetails extends HttpServlet {
    private final Logger log = Logger.getLogger(this.getClass());
    private LocationPhoto locationPhoto = new LocationPhoto();

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String placeId = req.getParameter("placeId");
        String name = req.getParameter("placeName");
        log.info("place: " + placeId);
        // TODO handle no place Id

        // TODO Get locations info for detail page
        // Get reviews
        LocationDao locationDao = new LocationDao();
        List<LocationEntity> locations = locationDao.findByProperty(LocationEntity.class, "googleId" , placeId, MatchMode.EXACT);
        log.info("locations: " + locations);

        if (locations != null && locations.size() == 1) {
            log.info("location: " + locations.get(0));
            // add review
            ReviewDao reviewDao = new ReviewDao();
            List<ReviewEntity> reviews = reviewDao.findByAndInitializeProperties("location", locations.get(0));

            req.setAttribute("reviews", reviews);

            // add tags
            TagLocationDao tagLocationDao = new TagLocationDao();
            List<TaglocationEntity> tagLocations = tagLocationDao.findByAndInitializeTag("location", locations.get(0));
            req.setAttribute("tags", tagLocations);

            // add location
            req.setAttribute("location", locations.get(0));


        } else if (locations.size() < 1 && placeId != null && name != null) {
            // Location isn't in Database --> add it
            String escapedName = StringEscapeUtils.escapeJava(name);
            LocationEntity location = new LocationEntity(escapedName, placeId);
            Long id = locationDao.save(location);
            location.setId(id);

            req.setAttribute("location", location);
        } else {

            // TODO throw error? there shouldnt be more than 1 location with a single id
        }
        // google images, later - images
        if (placeId != null && placeId.length() > 0) {
            String photoUrl = locationPhoto.getPhotoFromGoogle(req.getParameter("placeId"));

            req.setAttribute("photoUrl", photoUrl);
        }

        //log.info(session.getAttribute("user"));

        req.setAttribute("referrer", "detail.jsp");

        RequestDispatcher dispatcher = req.getRequestDispatcher("detail.jsp");
        dispatcher.forward(req, resp);
    }


}
