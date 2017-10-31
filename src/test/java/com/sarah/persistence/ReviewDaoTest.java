package com.sarah.persistence;

import com.sarah.entity.LocationEntity;
import com.sarah.entity.ReviewEntity;
import com.sarah.entity.User;
import com.sarah.utility.DatabaseCleaner;
import javafx.util.converter.LocalDateStringConverter;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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
        GenericDao dao = new GenericDao();
        dao.save(testUser);

        // Prepare location
        LocationDao locationDao = new LocationDao();
        locationDao.insertLocation(location);

        firstReview = new ReviewEntity(1, "This is great!", dateConverter.fromString("10/01/17"), testUser, location);
        secondReview = new ReviewEntity(2, "This is not great :(", dateConverter.fromString("10/02/17"), testUser, location);

        reviewDao.insertReview(firstReview);
        reviewDao.insertReview(secondReview);

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
        List<ReviewEntity> testReviews = reviewDao.getAllReviews();

        Assert.assertEquals("Incorrect number of reviews", testReviews.size(), allReviews.size());

        for (int i = 0; i < testReviews.size(); i ++) {
            Assert.assertEquals("Body is incorrect", testReviews.get(i).getBody(), allReviews.get(i).getBody());
            Assert.assertEquals("Date is incorrect", testReviews.get(i).getDate(), allReviews.get(i).getDate());
        }

    }

    @Test
    public void insertReview() throws Exception {

        ReviewEntity newReview = new ReviewEntity(3,"This is meh", dateConverter.fromString("10/01/17"), testUser, location);

        int newId = reviewDao.insertReview(newReview);

        Assert.assertEquals("Id no matches", newReview.getId(),newId);
    }

    @Test
    public void getReviewById() throws Exception {

        ReviewEntity returnedReview = reviewDao.getReviewById(secondReview.getId());
        Assert.assertEquals("Id does not match", secondReview.getId(), returnedReview.getId());
        Assert.assertEquals("Body does not match", secondReview.getBody(), returnedReview.getBody());
        Assert.assertEquals("Date does not match", secondReview.getDate(), returnedReview.getDate());
        Assert.assertEquals("User does not match", secondReview.getUser().getUserName(), returnedReview.getUser().getUserName());
        Assert.assertEquals("Location does not match", secondReview.getLocation().getGoogleId(), returnedReview.getLocation().getGoogleId());
    }

    @Test
    public void updateReview() throws Exception {
        secondReview.setBody("This was awesome");
        reviewDao.updateReview(secondReview);

        ReviewEntity returnedReview = reviewDao.getReviewById(secondReview.getId());
        Assert.assertEquals("Id does not match", secondReview.getId(), returnedReview.getId());
        Assert.assertEquals("Body does not match", secondReview.getBody(), returnedReview.getBody());
        Assert.assertEquals("Date does not match", secondReview.getDate(), returnedReview.getDate());
        Assert.assertEquals("User does not match", secondReview.getUser().getUserName(), returnedReview.getUser().getUserName());
        Assert.assertEquals("Location does not match", secondReview.getLocation().getGoogleId(), returnedReview.getLocation().getGoogleId());

    }

    @Test
    public void deleteReview() throws Exception {
        reviewDao.deleteReview(firstReview);

        List<ReviewEntity> reviews = reviewDao.getAllReviews();

        Assert.assertEquals("Incorrect number of reviews in database", allReviews.size() - 1, reviews.size());

    }
}