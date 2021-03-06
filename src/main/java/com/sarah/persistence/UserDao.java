package com.sarah.persistence;

import com.sarah.entity.LocationEntity;
import com.sarah.entity.PrivilegeEntity;
import com.sarah.entity.User;
import com.sarah.entity.UserPrivilegeEntity;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Access users in the user table.
 *
 * @author somernik
 */
public class UserDao extends GenericDao {

    private final Logger log = Logger.getLogger(this.getClass());

    /** Get a single user for the given id and intialize properties
     *
     * @param id user's id
     * @return User
     */
    public User getUserById(Long id) {

        User user = null;
        Session session = null;

        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            user = (User) session.get(User.class, id);
            Hibernate.initialize(user.getLocations());
            Hibernate.initialize(user.getUserPrivileges());
        } catch (HibernateException he) {
            log.error("Error getting user with id: " + id, he);

        } catch (NullPointerException e) {
            log.error("Error getting user with id (user does not exist): " + id, e);

        } finally {
            if (session != null) {
                session.close();
            }
        }

        return user;
    }

    /**
     * Add a location to user's list of locations
     * @param user User
     * @param location Location
     * @return The updated user
     */
    public User addSavedLocation(User user, LocationEntity location) {

        LocationDao locationDao = new LocationDao();
        LocationEntity updatedLocation = locationDao.update(location); // In case location wasnt previously saved

        user = this.getUserById(user.getId());
        Set<LocationEntity> currentLocations = user.getLocations();
        currentLocations.add(updatedLocation);

        // Create user
        user.setLocations(currentLocations);

        user = this.update(user);

        // Get locations & privileges
        user = this.getUserById(user.getId());

        return user;

    }

    /**
     * Remove a location from a user's list of locations
     * @param user User
     * @param location Location
     * @return The updated User
     */
    public User removeSavedLocation(User user, LocationEntity location) {
        User userWithLocations = this.getUserById(user.getId());

        Session session = null;
        Transaction transaction = null;
        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // delete admin privelege for current user
            String sql = "DELETE FROM userlocation WHERE User_id='" + userWithLocations.getId() + "' AND Location_id='" + location.getId() + "'";
            SQLQuery query = session.createSQLQuery(sql);
            query.executeUpdate();
            transaction.commit();

        } catch(HibernateException he) {
            log.error("Hibernate Exception: ", he);

        } finally {
            if (session != null) {
                session.close();
            }
        }

        return this.getUserById(userWithLocations.getId());
    }

    /** Get a single user for the given id and initialize properties
     *
     * @param username user's username
     * @return User
     */
    public User getUserByUsernameWithPrivilege(String username) {

        User user = new User();
        Session session = null;

        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            Criteria c2 = session.createCriteria(User.class);
            c2.add(Restrictions.ilike("userName", username, MatchMode.END));
            c2.setMaxResults(1);
            user = (User) c2.uniqueResult();
            Hibernate.initialize(user.getUserPrivileges());
            Hibernate.initialize(user.getLocations());
            Hibernate.initialize(user.getReviews());

        } catch (HibernateException he) {
            log.error("Error getting user with username: " + username, he);

        }  catch (NullPointerException e) {
            log.error("Error getting user with id (user does not exist): ", e);

        } finally {
            if (session != null) {
                session.close();
            }
        }

        return user;
    }

    /**
     * Adding admin privilege to a user
     * @param user User
     */
    public void addAdmin(User user) {

        PrivilegeEntity privilege = new PrivilegeEntity(1, "Administrator");

        UserPrivilegeEntity userPrivilege = new UserPrivilegeEntity(user.getUserName(), user, privilege);

        user.getUserPrivileges().add(userPrivilege);

        this.update(user);
    }

    /**
     * Removes admin privilege from a user
     * @param user User
     */
    public void removeAdmin(User user) {

        Session session = null;
        Transaction transaction = null;
        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // delete admin privelege for current user
            String sql = "DELETE FROM userprivilege WHERE user_name='" + user.getUserName() + "' AND Privilege_Id=1";
            SQLQuery query = session.createSQLQuery(sql);
            query.executeUpdate();
            transaction.commit();

        } catch(HibernateException he) {
            log.error("Hibernate Exception: ", he);

        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Returns list of all admin users
     * @return List of admin users
     */
    public List<User> getAdminUsers() {
        List<User> adminUsers = new ArrayList<User>();
        List<Long> ids = new ArrayList<Long>();

        Session session = null;
        Transaction transaction = null;

        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // delete admin privilege for current user
            String sql = "SELECT user.id as userid FROM user JOIN userprivilege ON user.id = userprivilege.User_id WHERE userprivilege.Privilege_id = 1";
            SQLQuery query = session.createSQLQuery(sql);
            ids = query.list();

        } catch (HibernateException he) {
            log.error("Error getting admin users", he);

        } finally {
            if (session != null) {
                session.close();
            }
        }

        for (int i = 0; i < ids.size(); i++) {
            Long id = Long.parseLong(String.valueOf(ids.get(i)));
            User user = getUserById(id);
            adminUsers.add(user);
        }

        return adminUsers;
    }

}