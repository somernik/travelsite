package com.sarah.persistence;

import com.sarah.entity.LocationEntity;
import com.sarah.entity.User;
import com.sarah.entity.UserPrivilegeEntity;
import com.sarah.utility.DatabaseCleaner;
import org.apache.log4j.Logger;
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


        List<User> testUsers = userDao.getAllUsersWithPrivileges();

        for (int i = 0; i < allUsers.size(); i++) {

            Assert.assertTrue("User did not match", testUsers.get(i).equals(allUsers.get(i)));

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
        Assert.assertEquals("location amounts dont match", user.getLocations().size(), testUser.getLocations().size());

        // TODO better check that locations are valid
    }

    @Test
    @Transactional
    public void getUserById() throws Exception {
        userDao.insert(firstUser);
        User testUser = userDao.getUserById(firstUser.getUserid());

        assertNotNull(testUser);

        Assert.assertTrue("User did not match", testUser.equals(firstUser));

    }


    @Test
    public void update() throws Exception {
        secondUser.setEmail("newemail@email.com");
        userDao.update(secondUser);

        User testUser = userDao.getUserById(secondUser.getUserid());

        Assert.assertTrue("User did not match", testUser.equals(secondUser));
    }

    @Test
    public void delete() throws Exception {
        userDao.delete(firstUser);

        //User testUser = userDao.getUserById(firstUser.getUserid());

        List<User> users = userDao.getAllUsersWithPrivileges();

        //Assert.assertNull("User retrieved is not null", testUser);
        Assert.assertEquals("Incorrect number of users in database", allUsers.size() - 1, users.size());

    }

    @Test
    public void addAdmin() {
        userDao.addAdmin(secondUser);

        User returnedUser = userDao.getUserById(secondUser.getUserid());

        log.info(returnedUser.getUserPrivileges());
        Assert.assertEquals("Incorrect # of privileges", returnedUser.getUserPrivileges().size(), 2);

        List<String> returnedValues = new ArrayList<String>();
        List<String> actualValues = new ArrayList<String>();
        actualValues.add("administrator");
        actualValues.add("contributor");

        for (UserPrivilegeEntity entity : returnedUser.getUserPrivileges()) {
            log.info(entity.getPrivilege().getValue());
            returnedValues.add(entity.getPrivilege().getValue());
        }
        // TODO
        //Assert.assertEquals("First priv doesn't match", returnedValues.get(0), actualValues.get(0));
        //Assert.assertEquals("Second priv doesn't match", returnedValues.get(1), actualValues.get(1));
    }
/*
    @Test(expected = HibernateException.class)
    public void InsertExceptionTest() throws Exception {
        User user = new User();
        userDao.insert(user);
    }*/

}