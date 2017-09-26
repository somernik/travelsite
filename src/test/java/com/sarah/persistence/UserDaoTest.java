package com.sarah.persistence;

import com.sarah.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by sarah on 9/21/2017.
 */
public class UserDaoTest {
    private UserDao userDao = new UserDao();

    private final Logger log = Logger.getLogger(this.getClass());
    User firstUser = new User("First", "User", 1, "first.user@email.com", "testpassword", "firstUser");
    User secondUser = new User("Test", "Insert", 2, "insert@email.com", "testpassword", "testUser");
    List<User> allUsers = new ArrayList<User>();

    @Before
    public void setUp() throws Exception {
        userDao.insert(firstUser);
        userDao.insert(secondUser);
    }

    @After
    public void tearDown() throws Exception {

        //userDao.delete(firstUser);
        //userDao.delete(secondUser);
        Session session = SessionFactoryProvider.getSessionFactory().openSession();

        // clear entries
        String sql = "DELETE FROM user";
        SQLQuery query = session.createSQLQuery(sql);
        query.executeUpdate();

        // reset ids
        String alterSql = "ALTER TABLE user AUTO_INCREMENT = 1";
        SQLQuery alterQuery = session.createSQLQuery(alterSql);
        alterQuery.executeUpdate();

        //session.getTransaction().commit();

    }

    @Test
    public void getAllUsers() throws Exception {

        allUsers.add(firstUser);
        allUsers.add(secondUser);

        List<User> expectedUsers = userDao.getAllUsers();

        for (int i = 0; i < allUsers.size(); i++) {
            Assert.assertEquals("Ids did not match", expectedUsers.get(i).getUserid(), allUsers.get(i).getUserid());
            Assert.assertEquals("First Name did not match", expectedUsers.get(i).getFirstName(), allUsers.get(i).getFirstName());
            Assert.assertEquals("Last Name did not match", expectedUsers.get(i).getLastName(), allUsers.get(i).getLastName());
            Assert.assertEquals("User name did not match", expectedUsers.get(i).getUserName(), allUsers.get(i).getUserName());
            Assert.assertEquals("Emails did not match", expectedUsers.get(i).getEmail(), allUsers.get(i).getEmail());
            Assert.assertEquals("Passwords  did not match", expectedUsers.get(i).getPassword(), allUsers.get(i).getPassword());

        }

        Assert.assertEquals("Array sizes do not match", expectedUsers.size(), allUsers.size());
    }

    @Test
    public void insert() throws Exception {
        int firstUserId = userDao.insert(firstUser);
        Assert.assertEquals("Ids dont match", firstUserId, firstUser.getUserid());

    }

    @Test
    @Transactional
    public void getUserById() throws Exception {

        User testUser = userDao.getUserById(1);
        //Assert.assertEquals("Users did not match", testUser, firstUser);
        Assert.assertEquals("Ids did not match", testUser.getUserid(), firstUser.getUserid());
        Assert.assertEquals("First Name did not match", testUser.getFirstName(), firstUser.getFirstName());
        Assert.assertEquals("Last Name did not match", testUser.getLastName(), firstUser.getLastName());
        Assert.assertEquals("User name did not match", testUser.getUserName(), firstUser.getUserName());
        Assert.assertEquals("Emails did not match", testUser.getEmail(), firstUser.getEmail());
        Assert.assertEquals("Passwords  did not match", testUser.getPassword(), firstUser.getPassword());

    }

    @Test
    public void getUsersByUsername() throws Exception {
        List<User> expectedUsers = userDao.getUsersByUsername(firstUser.getUserName());
        log.info(userDao.getAllUsers());
        for (int i = 0; i < expectedUsers.size(); i++) {
            Assert.assertEquals("User name did not match", expectedUsers.get(i).getUserName(), firstUser.getUserName());

        }

        Assert.assertEquals("Array sizes do not match", expectedUsers.size(), 1);
    }

    @Test
    public void update() throws Exception {
        secondUser.setEmail("newemail@email.com");
        userDao.update(secondUser);

        User testUser = userDao.getUserById(secondUser.getUserid());
        Assert.assertEquals("Ids did not match", testUser.getUserid(), secondUser.getUserid());
        Assert.assertEquals("First Name did not match", testUser.getFirstName(), secondUser.getFirstName());
        Assert.assertEquals("Last Name did not match", testUser.getLastName(), secondUser.getLastName());
        Assert.assertEquals("User name did not match", testUser.getUserName(), secondUser.getUserName());
        Assert.assertEquals("Emails did not match", testUser.getEmail(), secondUser.getEmail());
        Assert.assertEquals("Passwords  did not match", testUser.getPassword(), secondUser.getPassword());


    }

    @Test
    public void delete() throws Exception {
        userDao.delete(firstUser);

        User testUser = userDao.getUserById(firstUser.getUserid());

        Assert.assertNull("User retrieved is not null", testUser);

    }

}