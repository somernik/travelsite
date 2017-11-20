package com.sarah.controller;

import com.sarah.entity.LocationEntity;
import com.sarah.entity.ReviewEntity;
import com.sarah.entity.TaglocationEntity;
import com.sarah.persistence.LocationDao;
import com.sarah.persistence.ReviewDao;
import com.sarah.persistence.TagLocationDao;
import org.apache.log4j.Logger;
import org.hibernate.criterion.MatchMode;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * Created by sarah on 10/29/2017.
 */
@WebServlet(
        urlPatterns = {"/viewDetails"}
)
public class ViewDetails extends HttpServlet {
    private final Logger log = Logger.getLogger(this.getClass());

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String placeId = req.getParameter("placeId");
        log.info(placeId);
        // TODO handle no place Id

        // TODO Get locations info for detail page
        // Get reviews
        LocationDao locationDao = new LocationDao();
        List<LocationEntity> locations = locationDao.findByProperty(LocationEntity.class, "googleId" , "ChIJ_xkgOm1TBogRmEFIurX8DE4", MatchMode.EXACT);
        if (locations.size() == 1) {
            log.info(locations.get(0));
            ReviewDao reviewDao = new ReviewDao();
            List<ReviewEntity> reviews = reviewDao.findByAndInitializeProperties("location", locations.get(0));

            req.setAttribute("reviews", reviews);

            TagLocationDao tagLocationDao = new TagLocationDao();
            List<TaglocationEntity> tagLocations = tagLocationDao.findByAndInitializeTag("location", locations.get(0));
            req.setAttribute("tags", tagLocations);

            req.setAttribute("location", locations.get(0));

            getPhotoFromGoogle(locations.get(0));

        } else {

            // TODO throw error? there shouldnt be more than 1 location with a single id
        }
        // tags, reviews, google images,
        // later - images


        log.info(session.getAttribute("user"));
        RequestDispatcher dispatcher = req.getRequestDispatcher("detail.jsp");
        dispatcher.forward(req, resp);
    }

    private void getPhotoFromGoogle(LocationEntity location) {
        URI baseURI = UriBuilder.fromUri("https://maps.googleapis.com/maps/api/place/details/json?placeid=" + location.getGoogleId() + "&key=AIzaSyA_wVJfh8Ov9cLUZDxSNhOpzw3OEx6y3HE").build();

        Client client = ClientBuilder.newClient();

        WebTarget target = client.target(baseURI);

        String allQuestions = target.path("JSON/all").request().accept(MediaType.APPLICATION_JSON).get(String.class);
        log.info(allQuestions);
        /*
        try {
            JSONArray jsonArray = new JSONArray(allQuestions);
            questionsArrayList = parseJSON(jsonArray);
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        */
    }

}
