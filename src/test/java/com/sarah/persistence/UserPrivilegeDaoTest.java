package com.sarah.persistence;

import com.sarah.entity.PrivilegeEntity;
import com.sarah.entity.User;
import com.sarah.entity.UserPrivilegeEntity;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by sarah on 10/5/2017.
 */
public class UserPrivilegeDaoTest {
    private final Logger log = Logger.getLogger(this.getClass());
    private UserPrivilegeDao upDao = new UserPrivilegeDao();

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getAllUserPrivileges() throws Exception {
        List<UserPrivilegeEntity> userPrivileges = upDao.getAllUserPrivileges();
        log.info(userPrivileges);
        log.info(userPrivileges.size());

    }

    @Test
    public void insert() throws Exception {
        User testUser = new User("test", "user", "user@email.com", "test pass", "testuser");
        UserDao userDao = new UserDao();
        userDao.insert(testUser);
        for (UserPrivilegeEntity entity : testUser.getUserPrivileges()) {
            log.info(entity.getPrivilege().getValue());
        }
        PrivilegeEntity privilege = new PrivilegeEntity(1, "Administrator");

        UserPrivilegeEntity userPrivilege = new UserPrivilegeEntity(testUser.getUserName(), testUser, privilege);

        testUser.getUserPrivileges().add(userPrivilege);

        for (UserPrivilegeEntity entity : testUser.getUserPrivileges()) {
            log.info(entity.getPrivilege().getValue());
        }

        userDao.update(testUser);
        //userDao.insert(testUser);

    }

}