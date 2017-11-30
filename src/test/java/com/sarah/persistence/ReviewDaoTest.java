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

        firstReview = new ReviewEntity(1L, "This is great!", dateConverter.fromString("10/01/17"), testUser, location, 5);
        secondReview = new ReviewEntity(2L, "This is not great :(", dateConverter.fromString("10/02/17"), testUser, location, 2);

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

        ReviewEntity newReview = new ReviewEntity(3L,"This is meh", dateConverter.fromString("10/01/17"), testUser, location, 3);

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
    public void getReviewWithInfoTest() throws Exception {
        ReviewEntity returnedReview = reviewDao.getReviewById(secondReview.getId());
        Assert.assertEquals("Id does not match", secondReview.getId(), returnedReview.getId());
        Assert.assertEquals("Body does not match", secondReview.getBody(), returnedReview.getBody());
        Assert.assertEquals("Date does not match", secondReview.getDate(), returnedReview.getDate());
        Assert.assertEquals("User does not match", secondReview.getUser().toString(), returnedReview.getUser().toString());
        Assert.assertEquals("Location does not match", secondReview.getLocation().toString(), returnedReview.getLocation().toString());

    }

    @Test
    public void findByAndInitializePropertiesTest() throws Exception {
        List<ReviewEntity> reviews = reviewDao.findByAndInitializeProperties("body", secondReview.getBody());

        for (ReviewEntity review : reviews) {
            Assert.assertEquals("Review items not match", secondReview.getBody(), review.getBody());
        }
    }

}