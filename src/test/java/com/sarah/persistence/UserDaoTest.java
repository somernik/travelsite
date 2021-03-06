package com.sarah.persistence;

import com.sarah.entity.LocationEntity;
import com.sarah.entity.User;
import com.sarah.utility.DatabaseCleaner;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.script.*;
import javax.transaction.Transactional;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
/**
 * Created by sarah on 9/21/2017.
 */
public class UserDaoTest {
    private UserDao userDao = new UserDao();

    private final Logger log = Logger.getLogger(this.getClass());
    private User firstUser = new User("First", "User", new Long(1), "first.user@email.com", "testpassword", "firstUser");
    private User secondUser = new User("Test", "Insert", new Long(2), "insert@email.com", "testpassword", "testUser");

    private List<User> allUsers = new ArrayList<User>();

    @Before
    public void setUp() throws Exception {

        userDao.save(firstUser);
        userDao.save(secondUser);

        userDao.addAdmin(firstUser);

        allUsers.add(firstUser);
        allUsers.add(secondUser);
    }

    @After
    public void tearDown() throws Exception {

        DatabaseCleaner cleaner = new DatabaseCleaner();
        cleaner.run();

    }


    @Test
    public void insertUserWithLocations() throws Exception {

        // Prepare locations
        LocationEntity location1 = new LocationEntity("test", "test id");
        LocationDao locationDao = new LocationDao();
        locationDao.save(location1);

        Set<LocationEntity> locations = new HashSet<LocationEntity>();
        locations.add(location1);

        // Create user
        User user = new User("Test", "Locations","email@email.com", "password", "username");
        user.setLocations(locations);

        Long id = userDao.save(user);

        Assert.assertEquals("ids dont match", user.getId(), id);

        User testUser = userDao.getUserById(id);
        Assert.assertEquals("location amounts dont match", user.getLocations().size(), testUser.getLocations().size());

        // TODO better check that locations are valid
    }

    @Test
    @Transactional
    public void getUserByIdWithLocationsAndPrivilegesTest() throws Exception {
        userDao.save(firstUser);
        User testUser = userDao.getUserById(firstUser.getId());

        assertNotNull(testUser);

        Assert.assertTrue("User did not match", testUser.equals(firstUser));

    }


    @Test
    public void addAdmin() {
        userDao.addAdmin(secondUser);

        User returnedUser = userDao.getUserById(secondUser.getId());

        log.info(returnedUser.getUserPrivileges());
        Assert.assertEquals("Incorrect # of privileges", returnedUser.getUserPrivileges().size(), 2);

    }

    @Test
    public void getUserByUsername() throws Exception {
        User testUser = userDao.getUserByUsernameWithPrivilege(secondUser.getUserName());

        //log.info(testUser);
        //log.info(secondUser);

        Assert.assertTrue("Users do not match", secondUser.equals(testUser));
    }

    @Test
    public void removeAdmin() throws Exception {
        //log.info(firstUser.getUserPrivileges().size());
        userDao.removeAdmin(firstUser);
        User returnedUser = userDao.getUserById(firstUser.getId());

        //log.info("Returned from DB: " + returnedUser.getUserPrivileges().size());
        Assert.assertEquals("Incorrect # of privileges", returnedUser.getUserPrivileges().size(), 1);

    }

    @Test
    public void getAdminUsersTest() throws Exception {

        List<User> adminUsers = userDao.getAdminUsers();
        log.info(adminUsers);
        Assert.assertEquals("Incorrect # of users with admin", 1, adminUsers.size());

    }

    @Test
    public void addSavedLocationTest() throws Exception {
        LocationEntity locationEntity = new LocationEntity("Madison", "123");
        log.info(firstUser.getLocations());
        log.info(locationEntity);
        firstUser = userDao.addSavedLocation(firstUser, locationEntity);
        log.info(firstUser.getLocations());

        Assert.assertEquals("Location not saved correctly", 1, firstUser.getLocations().size());
    }

    @Test
    public void removeSavedLocationTest() throws Exception {
        LocationEntity locationEntity = new LocationEntity("Madison", "1234");

        firstUser = userDao.addSavedLocation(firstUser, locationEntity);
        for (LocationEntity location: firstUser.getLocations()) {
            firstUser = userDao.removeSavedLocation(firstUser, location);
        }

        Assert.assertEquals("Location not removed correctly", 0, firstUser.getLocations().size());

    }

}