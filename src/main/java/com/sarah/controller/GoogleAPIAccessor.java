package com.sarah.controller;

import com.sarah.persistence.SessionFactoryProvider;
import com.sarah.utility.Validator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to get photo from google api to display on jsp
 * Created by sarah on 11/21/2017.
 */
public class GoogleAPIAccessor {
    private final Logger log = Logger.getLogger(this.getClass());

    /**
     * Uses google API to access photo based on location's placeId (used in google api)
     * @param googleId the location's placeId
     * @return the location's photo URL
     */
    public String getPhotoFromGoogle(String googleId){
        log.info("Id: " + googleId);
        URI baseURI = UriBuilder.fromUri("https://maps.googleapis.com/maps/api/place/details/json?placeid=" + googleId + "&key=AIzaSyA_wVJfh8Ov9cLUZDxSNhOpzw3OEx6y3HE").build();

        Client client = ClientBuilder.newClient();

        WebTarget target = client.target(baseURI);
        //String allQuestions = target.path("JSON/all").request().accept(MediaType.APPLICATION_JSON).get(String.class);

        String response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class);
        log.info(response);
        // place_id
        // rating (for whole location)
        // reviews.author_name, reviews[i].rating, reviews[i].text
        String photoReference = "";

        try {
            JSONObject jsonObj = new JSONObject(response);
            JSONObject result = jsonObj.getJSONObject("result");
            //addLocationsReviews(result);
            JSONArray photos = result.getJSONArray("photos");
            JSONObject firstPhoto = photos.getJSONObject(0);
            photoReference = firstPhoto.getString("photo_reference");
            // width & height
            log.info(photoReference);

        } catch (JSONException jsonException) {
            log.error("JSON Exception: ", jsonException);
        }

        // TODO use url shortener to shorten url with below url, send url to page to appear as photo??

        //return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=5000&photoreference=" + photoReference + "&key=AIzaSyA_wVJfh8Ov9cLUZDxSNhOpzw3OEx6y3HE";
        return photoReference;
    }

    public void addLocationsReviews(JSONObject result) {
        Session session = null;

        // place_id
        // rating (for whole location)
        // reviews.author_name, reviews[i].rating, reviews[i].text
        try {

            session = SessionFactoryProvider.getSessionFactory().openSession();

            String placeId = result.getString("place_id");
            if (result.getJSONArray("reviews").length() > 0) {
                JSONArray reviews = result.getJSONArray("reviews");

                for (int i = 0; i < reviews.length(); i++) {
                    String body = reviews.getJSONObject(i).getString("text");

                    String name = result.getString("name");
                    String cleanBody = StringUtils.remove(body, "'");
                    String rating = reviews.getJSONObject(i).getString("rating");
                    String author = StringUtils.remove(reviews.getJSONObject(i).getString("author_name"), "'");

                    String sql = "INSERT INTO googleReviews (placeName, placeId, body, rating, author) VALUES (\"" + name + "\"," +
                             "'" + placeId + "',\"" + cleanBody + "\",'" + rating + "', \"" + author + "\");";
                    log.info(sql);

                    BufferedWriter writer = new BufferedWriter(new FileWriter("googleReviews.sql", true));
                    writer.append(sql);
                    writer.newLine();

                    writer.close();
                   // SQLQuery query = session.createSQLQuery(sql);
                    //query.executeUpdate();
                }
            }


        } catch (JSONException e) {
            log.error("JSON Exception: ", e);

        } catch (HibernateException he) {
            log.error("Error adding reviews: ", he);

        } catch (FileNotFoundException he) {
            log.error("File not found: ", he);

        }catch (IOException he) {
            log.error("Error writing sql to page: ", he);

        }  finally {
            if (session != null) {
                session.close();
            }
        }
    }

}
