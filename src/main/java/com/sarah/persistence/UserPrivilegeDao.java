package com.sarah.persistence;

import com.sarah.entity.UserPrivilegeEntity;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Access users in the user table.
 *
 * @author somernik
 */
public class UserPrivilegeDao {

    private final Logger log = Logger.getLogger(this.getClass());

    /** Return a list of all locations
     *
     * @return All locations
     */
    public List<UserPrivilegeEntity> getAllUserPrivileges() {
        List<UserPrivilegeEntity> userPrivileges = new ArrayList<UserPrivilegeEntity>();
        Session session = null;
        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            userPrivileges = session.createCriteria(UserPrivilegeEntity.class).list();
        } catch (HibernateException he) {
            log.error("Error getting all user privileges", he);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return userPrivileges;
    }

    /** save new location
     * @param userPrivilege userPrivilege to insert
     * @return id of the inserted location
     */
    public int insert(UserPrivilegeEntity userPrivilege) {
        int id = 0;
        Transaction transaction = null;
        Session session = null;

        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            id = (Integer) session.save(userPrivilege);
            transaction.commit();
        } catch (HibernateException he) {
            log.error("Starting roll back: " + userPrivilege, he);
            if (transaction != null) {
                try {

                    transaction.rollback();
                } catch (HibernateException he2) {
                    log.error("Error rolling back user privilege insert: " + userPrivilege, he2);
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return id;
    }


}