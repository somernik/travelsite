package com.sarah.persistence;

import com.sarah.entity.LocationEntity;
import com.sarah.entity.ReviewEntity;
import com.sarah.entity.User;
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
        User user = new User("Test", "User", "test@gmail.com", "password", "testUser");
        LocationEntity location = new LocationEntity();

        ReviewEntity newEntity = new ReviewEntity("This is great!", LocalDateTime.now(), user, location);

        int newId = reviewDao.insertReview(newEntity);

        //Assert.assertEquals("Id no matches", newId,1);
    }

}