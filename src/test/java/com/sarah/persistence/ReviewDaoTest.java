package com.sarah.persistence;

import com.sarah.entity.LocationEntity;
import com.sarah.entity.ReviewEntity;
import com.sarah.entity.User;
import com.sarah.utility.DatabaseCleaner;
import javafx.util.converter.LocalDateStringConverter;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;


/**
 * Created by sarah on 9/30/2017.
 */
public class ReviewDaoTest {
    private final Logger log = Logger.getLogger(this.getClass());
    private LocalDateStringConverter dateConverter = new LocalDateStringConverter();
    // for more info on default formats for the converter
    // see https://docs.oracle.com/javase/tutorial/i18n/format/dateFormat.html

    private ReviewDao reviewDao = new ReviewDao();

    private ReviewEntity firstReview = new ReviewEntity();
    private ReviewEntity secondReview = new ReviewEntity();
    User testUser = new User("testy", "mctest", "tester@test.com", "testpassword", "tester");
    LocationEntity location = new LocationEntity("test", "test id");

    private List<ReviewEntity> allReviews = new ArrayList<ReviewEntity>();

    @Before
    public void setUp() throws Exception {
        // Prepare user
        UserDao userDao = new UserDao();
        userDao.save(testUser);

        // Prepare location
        LocationDao locationDao = new LocationDao();
        locationDao.save(location);

        firstReview = new ReviewEntity(new Long(1), "This is great!", dateConverter.fromString("10/01/17"), testUser, location, 5);
        secondReview = new ReviewEntity(new Long(2), "This is not great :(", dateConverter.fromString("10/02/17"), testUser, location, 2);

        reviewDao.save(firstReview);
        reviewDao.save(secondReview);

        allReviews.add(firstReview);
        allReviews.add(secondReview);
    }

    @After
    public void tearDown() throws Exception {

        DatabaseCleaner cleaner = new DatabaseCleaner();
        cleaner.run();

    }

    @Test
    public void getAllReviews() throws Exception {
        List<ReviewEntity> testReviews = reviewDao.findAll(ReviewEntity.class);

        Assert.assertEquals("Incorrect number of reviews", testReviews.size(), allReviews.size());

        for (int i = 0; i < testReviews.size(); i ++) {
            Assert.assertEquals("Body is incorrect", testReviews.get(i).getBody(), allReviews.get(i).getBody());
            Assert.assertEquals("Date is incorrect", testReviews.get(i).getDate(), allReviews.get(i).getDate());
        }

    }

    @Test
    public void insertReview() throws Exception {

        ReviewEntity newReview = new ReviewEntity(new Long(3),"This is meh", dateConverter.fromString("10/01/17"), testUser, location, 3);

        Long newId = reviewDao.save(newReview);

        Assert.assertEquals("Id no matches", newReview.getId(), newId);
    }

    @Test
    public void getReviewById() throws Exception {

        ReviewEntity returnedReview = reviewDao.find(ReviewEntity.class, secondReview.getId());
        Assert.assertEquals("Id does not match", secondReview.getId(), returnedReview.getId());
        Assert.assertEquals("Body does not match", secondReview.getBody(), returnedReview.getBody());
        Assert.assertEquals("Date does not match", secondReview.getDate(), returnedReview.getDate());
    }

    @Test
    public void updateReview() throws Exception {
        secondReview.setBody("This was awesome");
        reviewDao.update(secondReview);

        ReviewEntity returnedReview = reviewDao.find(ReviewEntity.class, secondReview.getId());
        Assert.assertEquals("Id does not match", secondReview.getId(), returnedReview.getId());
        Assert.assertEquals("Body does not match", secondReview.getBody(), returnedReview.getBody());
        Assert.assertEquals("Date does not match", secondReview.getDate(), returnedReview.getDate());

    }

    @Test
    public void deleteReview() throws Exception {
        reviewDao.delete(ReviewEntity.class, firstReview.getId());

        List<ReviewEntity> reviews = reviewDao.findAll(ReviewEntity.class);

        Assert.assertEquals("Incorrect number of reviews in database", allReviews.size() - 1, reviews.size());

    }

    @Test
    public void getReviewWithInfo () throws Exception {
        ReviewEntity returnedReview = reviewDao.getReviewById(secondReview.getId());
        Assert.assertEquals("Id does not match", secondReview.getId(), returnedReview.getId());
        Assert.assertEquals("Body does not match", secondReview.getBody(), returnedReview.getBody());
        Assert.assertEquals("Date does not match", secondReview.getDate(), returnedReview.getDate());
        Assert.assertEquals("User does not match", secondReview.getUser().toString(), returnedReview.getUser().toString());
        Assert.assertEquals("Location does not match", secondReview.getLocation().toString(), returnedReview.getLocation().toString());

    }

    // TODO test intialize function
    @Test
    public void getLocationImage() throws Exception {
        URI baseURI = UriBuilder.fromUri("https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJ_xkgOm1TBogRmEFIurX8DE4&key=AIzaSyA_wVJfh8Ov9cLUZDxSNhOpzw3OEx6y3HE").build();

        Client client = ClientBuilder.newClient();

        WebTarget target = client.target(baseURI);
        //String allQuestions = target.path("JSON/all").request().accept(MediaType.APPLICATION_JSON).get(String.class);

        String response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class);
        //log.info(response);
        String photoReference = "";

        try {
            //JSONArray jsonArray = new JSONArray(response);
            //log.info(jsonArray);
            JSONObject jsonObj = new JSONObject(response);
            JSONObject result = jsonObj.getJSONObject("result");
            JSONArray photos = result.getJSONArray("photos");
            JSONObject firstPhoto = photos.getJSONObject(0);
            photoReference = firstPhoto.getString("photo_reference");

            log.info(photoReference);

            //questionsArrayList = parseJSON(jsonArray);
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }

        URI baseURI2 = UriBuilder.fromUri("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + photoReference + "&key=AIzaSyA_wVJfh8Ov9cLUZDxSNhOpzw3OEx6y3HE").build();

        Client client2 = ClientBuilder.newClient();

        WebTarget target2 = client2.target(baseURI2);

        String response2 = target2.request().accept(MediaType.APPLICATION_JSON).get(String.class);
        //log.info(response2);



        //https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=CnRtAAAATLZNl354RwP_9UKbQ_5Psy40texXePv4oAlgP4qNEkdIrkyse7rPXYGd9D_Uj1rVsQdWT4oRz4QrYAJNpFX7rzqqMlZw2h2E2y5IKMUZ7ouD_SlcHxYq1yL4KbKUv3qtWgTK0A6QbGh87GB3sscrHRIQiG2RrmU_jF4tENr9wGS_YxoUSSDrYjWmrNfeEHSGSc3FyhNLlBU&key=YOUR_API_KEY

    }
}