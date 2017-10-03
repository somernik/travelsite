package com.sarah.persistence;

import com.sarah.entity.LocationEntity;
import com.sarah.entity.User;
import com.sarah.utility.DatabaseCleaner;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by sarah on 9/21/2017.
 */
public class UserDaoTest {
    private UserDao userDao = new UserDao();

    private final Logger log = Logger.getLogger(this.getClass());
    private User firstUser = new User("First", "User", 1, "first.user@email.com", "testpassword", "firstUser");
    private User secondUser = new User("Test", "Insert", 2, "insert@email.com", "testpassword", "testUser");
    private List<User> allUsers = new ArrayList<User>();

    @Before
    public void setUp() throws Exception {
        userDao.insert(firstUser);
        userDao.insert(secondUser);

        allUsers.add(firstUser);
        allUsers.add(secondUser);
    }

    @After
    public void tearDown() throws Exception {

        DatabaseCleaner cleaner = new DatabaseCleaner();
        cleaner.run();

    }

    @Test
    public void getAllUsers() throws Exception {


        List<User> testUsers = userDao.getAllUsers();

        for (int i = 0; i < allUsers.size(); i++) {
            Assert.assertEquals("Ids did not match", allUsers.get(i).getUserid(), testUsers.get(i).getUserid());
            Assert.assertEquals("First Name did not match", allUsers.get(i).getFirstName(), testUsers.get(i).getFirstName());
            Assert.assertEquals("Last Name did not match", allUsers.get(i).getLastName(), testUsers.get(i).getLastName());
            Assert.assertEquals("User name did not match", allUsers.get(i).getUserName(), testUsers.get(i).getUserName());
            Assert.assertEquals("Emails did not match", allUsers.get(i).getEmail(), testUsers.get(i).getEmail());
            Assert.assertEquals("Passwords  did not match", allUsers.get(i).getPassword(), testUsers.get(i).getPassword());

        }

        Assert.assertEquals("Array sizes do not match", allUsers.size(), testUsers.size());
    }

    @Test
    public void insert() throws Exception {
        int firstUserId = userDao.insert(firstUser);
        Assert.assertEquals("Ids dont match", firstUser.getUserid(), firstUserId);

    }


    @Test
    public void insertUserWithLocations() throws Exception {

        // Prepare locations
        LocationEntity location1 = new LocationEntity("test", "test id");
        LocationDao locationDao = new LocationDao();
        locationDao.insertLocation(location1);

        Set<LocationEntity> locations = new HashSet<LocationEntity>();
        locations.add(location1);

        // Create user
        User user = new User("Test", "Locations","email@email.com", "password", "username");
        user.setLocations(locations);

        int id = userDao.insert(user);

        Assert.assertEquals("ids dont match", user.getUserid(), id);

        User testUser = userDao.getUserById(id);
        Assert.assertEquals("location amount dont match", user.getLocations().size(), testUser.getLocations().size());

        // TODO better check that locations are valid
    }

    @Test
    @Transactional
    public void getUserById() throws Exception {
        userDao.insert(firstUser);
        User testUser = userDao.getUserById(firstUser.getUserid());

        assertNotNull(testUser);
        Assert.assertEquals("Ids did not match", firstUser.getUserid(), testUser.getUserid());
        Assert.assertEquals("First Name did not match", firstUser.getFirstName(), testUser.getFirstName());
        Assert.assertEquals("Last Name did not match", firstUser.getLastName(), testUser.getLastName());
        Assert.assertEquals("User name did not match", firstUser.getUserName(), testUser.getUserName());
        Assert.assertEquals("Emails did not match", firstUser.getEmail(), testUser.getEmail());
        Assert.assertEquals("Passwords  did not match", firstUser.getPassword(), testUser.getPassword());

    }


    @Test
    public void update() throws Exception {
        secondUser.setEmail("newemail@email.com");
        userDao.update(secondUser);

        User testUser = userDao.getUserById(secondUser.getUserid());
        Assert.assertEquals("Ids did not match", secondUser.getUserid(), testUser.getUserid());
        Assert.assertEquals("First Name did not match", secondUser.getFirstName(), testUser.getFirstName());
        Assert.assertEquals("Last Name did not match", secondUser.getLastName(), testUser.getLastName());
        Assert.assertEquals("User name did not match", secondUser.getUserName(), testUser.getUserName());
        Assert.assertEquals("Emails did not match", secondUser.getEmail(), testUser.getEmail());
        Assert.assertEquals("Passwords  did not match", secondUser.getPassword(), testUser.getPassword());


    }

    @Test
    public void delete() throws Exception {
        userDao.delete(firstUser);

        //User testUser = userDao.getUserById(firstUser.getUserid());

        List<User> users = userDao.getAllUsers();

        //Assert.assertNull("User retrieved is not null", testUser);
        Assert.assertEquals("Incorrect number of users in database", allUsers.size() - 1, users.size());

    }

}