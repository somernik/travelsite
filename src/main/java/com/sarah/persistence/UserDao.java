package com.sarah.persistence;

import com.sarah.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Access users in the user table.
 *
 * @author somernik
 */
public class UserDao {

    private final Logger log = Logger.getLogger(this.getClass());

    /** Return a list of all users
     *
     * @return All users
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        Session session = null;
        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            users = session.createCriteria(User.class).list();
        } catch (HibernateException he) {
            log.error("Error getting all users", he);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return users;
    }

    /** save new user
     * @param user user to insert
     * @return id of the inserted user
     */
    public int insert(User user) {
        int id = 0;
        Transaction transaction = null;
        Session session = null;

        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            id = (Integer) session.save(user);
            log.info(id + " transaction: " + transaction);
            transaction.commit();
        } catch (HibernateException he) {
            log.error("Starting roll back: " + user, he);
            if (transaction != null) {
                try {

                    transaction.rollback();
                } catch (HibernateException he2) {
                    log.error("Error rolling back user insert: " + user, he2);
                }
            }
        } finally {
            log.info("Finally insert");
            if (session != null) {
                session.close();
            }
        }

        return id;
    }
}