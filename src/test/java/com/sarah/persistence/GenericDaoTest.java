package com.sarah.persistence;

import com.sarah.entity.LocationEntity;
import com.sarah.entity.ReviewEntity;
import com.sarah.entity.User;
import com.sarah.utility.DatabaseCleaner;
import javafx.util.converter.LocalDateStringConverter;
import org.apache.log4j.Logger;
import org.hibernate.criterion.MatchMode;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by sarah on 10/31/2017.
 */
public class GenericDaoTest {
    private UserDao userDao = new UserDao();
    @Inject
    private GenericDao dao;

    private final Logger log = Logger.getLogger(this.getClass());
    private User firstUser = new User("First", "User", new Long(1), "first.user@email.com", "testpassword", "firstUser");
    private User secondUser = new User("Test", "Insert", new Long(2), "insert@email.com", "testpassword", "testUser");

    private List<User> allUsers = new ArrayList<User>();

    @Before
    public void setUp() throws Exception {
        dao = new GenericDao();

        dao.save(firstUser);
        dao.save(secondUser);

        userDao.addAdmin(firstUser);

        allUsers.add(firstUser);
        allUsers.add(secondUser);
    }

// Todo test for exceptions

    @After
    public void tearDown() throws Exception {

        DatabaseCleaner cleaner = new DatabaseCleaner();
        cleaner.run();

    }

    @Test
    public void findAllTest() throws Exception {

        List<User> testUsers = dao.findAll(User.class);
        log.info(testUsers);


        for (int i = 0; i < allUsers.size(); i++) {

            Assert.assertTrue("User did not match", testUsers.get(i).equals(allUsers.get(i)));

        }

        Assert.assertEquals("Array sizes do not match", allUsers.size(), testUsers.size());
    }

    @Test
    public void findTest() throws Exception {

        dao.save(firstUser);
        User testUser = dao.find(User.class, firstUser.getId());

        assertNotNull(testUser);

        Assert.assertTrue("User did not match", testUser.equals(firstUser));
    }

    @Test
    public void updateTest() throws Exception {
        secondUser.setEmail("newemail@email.com");
        dao.update(secondUser);

        User testUser = dao.find(User.class, secondUser.getId());

        Assert.assertTrue("User did not match", testUser.equals(secondUser));
    }


    @Test
    public void deleteTest() throws Exception {
        dao.delete(User.class, firstUser.getId());

        List<User> users = dao.findAll(User.class);

        //Assert.assertNull("User retrieved is not null", testUser);
        Assert.assertEquals("Incorrect number of users in database", allUsers.size() - 1, users.size());

    }

    @Test
    public void insertTest() throws Exception {
        Long firstUserId = dao.save(firstUser);
        Assert.assertEquals("Ids dont match", firstUser.getId(), firstUserId);

    }

    @Test
    public void findByTextTest() throws Exception {
        List<User> usersWithMatchingUsername = dao.findByProperty(User.class, "userName", firstUser.getUserName(), MatchMode.ANYWHERE);
        Assert.assertEquals("Usernames dont match", usersWithMatchingUsername.get(0).getUserName(), firstUser.getUserName());

    }

    @Test
    public void findByObjectTest() throws Exception {
        LocalDateStringConverter dateConverter = new LocalDateStringConverter();
        LocationEntity location = new LocationEntity("Madison", "123");

        ReviewEntity review1 = new ReviewEntity("Awesome!", dateConverter.fromString("10/01/17"), firstUser, location);
        ReviewEntity review2 = new ReviewEntity("Meh", dateConverter.fromString("10/01/17"), secondUser, location);

        dao.save(location);
        dao.save(review1);
        dao.save(review2);

        List<ReviewEntity> reviews = dao.findByProperty(ReviewEntity.class, "user", firstUser);

        log.info(reviews.size());

        Assert.assertEquals("Incorrect # of reviews", 1, reviews.size());
    }
}
