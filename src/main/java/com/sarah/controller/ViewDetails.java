package com.sarah.controller;

import com.sarah.entity.LocationEntity;
import com.sarah.entity.ReviewEntity;
import com.sarah.entity.TagEntity;
import com.sarah.entity.TaglocationEntity;
import com.sarah.persistence.LocationDao;
import com.sarah.persistence.ReviewDao;
import com.sarah.persistence.TagDao;
import com.sarah.persistence.TagLocationDao;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringEscapeUtils;

import org.hibernate.criterion.MatchMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Prepares the details of a location for the jsp
 * Created by sarah on 10/29/2017.
 */
@WebServlet(
        urlPatterns = {"/viewDetails"}
)
public class ViewDetails extends HttpServlet {
    private final Logger log = Logger.getLogger(this.getClass());
    private GoogleAPIAccessor googleAPIAccessor = new GoogleAPIAccessor();

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String placeId = req.getParameter("placeId");
        String name = req.getParameter("placeName");
        log.info("place: " + placeId);
        log.info("name: " + name);
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
            req.setAttribute("tagLocations", tagLocations);

            // add location
            req.setAttribute("location", locations.get(0));

        } else if (locations.size() < 1 && placeId != null && name != null) {
            // Location isn't in Database --> add it
            log.info("name: " + name);
            String escapedName = StringEscapeUtils.escapeJava(name);
            log.info("escaped Name: " + escapedName);
            LocationEntity location = new LocationEntity(escapedName, placeId);
            String photoReference = googleAPIAccessor.getPhotoFromGoogle(req.getParameter("placeId"));
            location.setPhotoReference(photoReference);
            Long id = locationDao.save(location);
            location.setId(id);

            req.setAttribute("location", location);
        } else {

            // TODO throw error? there shouldnt be more than 1 location with a single id
        }
        // google images, later - images
        /*
        if (placeId != null && placeId.length() > 0) {
            String photoUrl = googleAPIAccessor.getPhotoFromGoogle(req.getParameter("placeId"));

            req.setAttribute("photoUrl", photoUrl); // --> will now be put together in front end
        }
        */

        //log.info(session.getAttribute("user"));

        TagDao tagDao = new TagDao();
        List<TagEntity> tags = tagDao.findAll(TagEntity.class);

        req.setAttribute("tags", tags);

        req.setAttribute("referrer", "viewDetails");
        if (req.getParameter("message") != null){

            req.setAttribute("message", req.getParameter("message"));
        }

        log.info("in viewdetail");

        //Map<String, String> weatherInfo = getWeather(name);

        //req.setAttribute("weatherInfo", weatherInfo);

        RequestDispatcher dispatcher = req.getRequestDispatcher("detail.jsp");
        dispatcher.forward(req, resp);
    }

    public Map<String, String> getWeather(String name) {
        URI baseURI = UriBuilder.fromUri("http://api.openweathermap.org/data/2.5/weather?q=" + name + "&appid=86c55161a064dac93b325d6b174b685e").build();

        Client client = ClientBuilder.newClient();

        WebTarget target = client.target(baseURI);
        //String allQuestions = target.path("JSON/all").request().accept(MediaType.APPLICATION_JSON).get(String.class);

        String response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class);

        Map<String, String> weatherStuff = new HashMap<>();

        try {
            JSONObject jsonObj = new JSONObject(response);
            JSONObject main = jsonObj.getJSONObject("main");
            String temp = main.getString("temp");
            //addLocationsReviews(result);
            JSONArray weather = jsonObj.getJSONArray("weather");
            String mainWeather = weather.getJSONObject(0).getString("main");
            String description = weather.getJSONObject(0).getString("description");
            weatherStuff.put("Temperature", temp);
            weatherStuff.put("Weather", mainWeather);
            weatherStuff.put("Description", description);


        } catch (JSONException jsonException) {
            log.error("JSON Exception: ", jsonException);
        }

        return weatherStuff;
    }


}
