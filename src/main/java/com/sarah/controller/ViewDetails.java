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
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;

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
            Map<TaglocationEntity, String> tagLocationMap = new HashMap<>();
            for (TaglocationEntity currentTagLocation: tagLocations) {
                tagLocationMap.put(currentTagLocation, currentTagLocation.getDate().getMonth().name());
            }
            req.setAttribute("tagLocations", tagLocationMap);

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
        // GET recommended Places
        List<String> recommendedPlaces = new ArrayList<>();
        Map<String, String> recommendedLocationsMap = new HashMap<>();
        if (placeId != null && placeId.length() > 0) {
            log.info("here");
            recommendedPlaces = getRecommendedPlaces(name, req);
            if (recommendedPlaces.size() > 0) {
                log.info("here2");
                for (String place: recommendedPlaces) {
                    log.info("here3");
                    List<LocationEntity> recommendedLocations = locationDao.findByProperty(LocationEntity.class, "name", place, MatchMode.ANYWHERE);
                    for (LocationEntity currentLocation: recommendedLocations) {
                        log.info("here4");
                        recommendedLocationsMap.put(currentLocation.getName(), "viewDetails?placeId=" + currentLocation.getGoogleId() + "&placeName=" + currentLocation.getName());
                    }
                }
            }
        }
        log.info(recommendedLocationsMap);
        req.setAttribute("recommendedLocations", recommendedLocationsMap);

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

    public List<String> getRecommendedPlaces(String placeName, HttpServletRequest req) {
        JSONParser parser = new JSONParser();
        List<String> recommendedPlaces = new ArrayList<>();

        ServletContext context = req.getServletContext();
        String path = context.getRealPath("/");
        String contextPath = context.getContextPath();
        log.info(path);
        log.info(contextPath);
        try {
            org.json.simple.JSONObject data = (org.json.simple.JSONObject) parser.parse(new FileReader("/home/ubuntu/data.txt"));
            //org.json.simple.JSONObject data = (org.json.simple.JSONObject) parser.parse(new FileReader("/Users/sarah/amazon/data.txt"));
            org.json.simple.JSONArray recommendations = (org.json.simple.JSONArray) data.get(placeName);
            Iterator<String> iterator = recommendations.iterator();
            int count = 0;
            while (iterator.hasNext()) {
                recommendedPlaces.add(iterator.next());
                count ++;
                if (count == 3) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            log.error("File not found: ", e);
        } catch (ParseException e) {
            log.error("Parsing issue: ", e);
        } catch (NullPointerException e) {
            log.info("No recommendations: " , e);
        } catch (IOException e) {
            log.error("IO e: ", e);
        }

        log.info(recommendedPlaces);
        return recommendedPlaces;
    }


}
