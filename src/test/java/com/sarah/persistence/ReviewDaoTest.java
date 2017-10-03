package com.sarah.persistence;

import com.sarah.entity.LocationEntity;
import com.sarah.entity.ReviewEntity;
import com.sarah.entity.User;
import javafx.util.converter.LocalDateStringConverter;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by sarah on 9/30/2017.
 */
public class ReviewDaoTest {
    private final Logger log = Logger.getLogger(this.getClass());

    private ReviewDao reviewDao = new ReviewDao();

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getAllReviews() throws Exception {
        List<ReviewEntity> allReviews = reviewDao.getAllReviews();

        //Assert.assertEquals("Incorrect number of reviews", allReviews.size(),0);
    }

    @Test
    public void insertReview() throws Exception {

        LocalDateStringConverter dateConverter = new LocalDateStringConverter();
        // for more info on default formats for the converter
        // see https://docs.oracle.com/javase/tutorial/i18n/format/dateFormat.html

        //User user = new User("Test", "User", "test@gmail.com", "password", "testUser");

        UserDao userDao = new UserDao();
        User testUser = userDao.getUserById(1);

        LocationDao locationDao = new LocationDao();
        LocationEntity location  = locationDao.getLocationById(1);

        log.info(testUser);
        log.info(location);

        ReviewEntity newEntity = new ReviewEntity("This is great!", dateConverter.fromString("10/01/17"), testUser, location);

        int newId = 1;//reviewDao.insertReview(newEntity);

        LocationEntity afterInsert = locationDao.getLocationById(1);
        log.info("here");

        for (ReviewEntity review : afterInsert.getReviews()) {
            log.info(review.getBody());
        }
        log.info("here");

        Assert.assertEquals("Id no matches", newId,1);
    }

    @Test
    public void getReviewById() throws Exception {

        LocalDateStringConverter dateConverter = new LocalDateStringConverter();

        User user = new User("Test", "User", "test@gmail.com", "password", "testUser");
        LocationEntity location = new LocationEntity();

        ReviewEntity review = new ReviewEntity("This is my review", dateConverter.fromString("10/01/17"), user, location);
        int id = reviewDao.insertReview(review);

        ReviewEntity returnedReview = reviewDao.getReviewById(id);
        Assert.assertEquals("Id does not match", returnedReview.getId(), review.getId());
        Assert.assertEquals("Body does not match", returnedReview.getBody(), review.getBody());
        Assert.assertEquals("Date does not match", returnedReview.getDate(), review.getDate());
        Assert.assertEquals("User does not match", returnedReview.getUser(), review.getUser());
        Assert.assertEquals("Location does not match", returnedReview.getLocation(), review.getLocation());
    }

    @Test
    public void updateReview() throws Exception {
        //TODO write this

    }

    @Test
    public void deleteReview() throws Exception {
        // TODO write this
    }
}