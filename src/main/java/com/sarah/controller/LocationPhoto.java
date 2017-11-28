package com.sarah.controller;

import com.sarah.entity.LocationEntity;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.net.UnknownHostException;

/**
 * Created by sarah on 11/21/2017.
 */
public class LocationPhoto {
    private final Logger log = Logger.getLogger(this.getClass());

    public String getPhotoFromGoogle(String googleId){
        log.info("Id: " + googleId);
        URI baseURI = UriBuilder.fromUri("https://maps.googleapis.com/maps/api/place/details/json?placeid=" + googleId + "&key=AIzaSyA_wVJfh8Ov9cLUZDxSNhOpzw3OEx6y3HE").build();

        Client client = ClientBuilder.newClient();

        WebTarget target = client.target(baseURI);
        //String allQuestions = target.path("JSON/all").request().accept(MediaType.APPLICATION_JSON).get(String.class);

        String response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class);
        log.info(response);
        String photoReference = "";

        try {
            JSONObject jsonObj = new JSONObject(response);
            JSONObject result = jsonObj.getJSONObject("result");
            JSONArray photos = result.getJSONArray("photos");
            JSONObject firstPhoto = photos.getJSONObject(0);
            photoReference = firstPhoto.getString("photo_reference");
            // width & height
            log.info(photoReference);

        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }

        // TODO use url shortener to shorten url with below url, send url to page to appear as photo??

        return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=5000&photoreference=" + photoReference + "&key=AIzaSyA_wVJfh8Ov9cLUZDxSNhOpzw3OEx6y3HE";
    }

}
