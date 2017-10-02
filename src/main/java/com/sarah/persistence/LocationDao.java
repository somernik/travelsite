package com.sarah.persistence;

import com.sarah.entity.LocationEntity;
import com.sarah.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * Access users in the user table.
 *
 * @author somernik
 */
public class LocationDao {

    private final Logger log = Logger.getLogger(this.getClass());

    /** Return a list of all locations
     *
     * @return All locations
     */
    public List<LocationEntity> getAllLocations() {
        List<LocationEntity> locations = new ArrayList<LocationEntity>();
        Session session = null;
        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            locations = session.createCriteria(LocationEntity.class).list();
        } catch (HibernateException he) {
            log.error("Error getting all locations", he);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return locations;
    }

    /** save new location
     * @param location location to insert
     * @return id of the inserted location
     */
    public int insertLocation(LocationEntity location) {
        int id = 0;
        Transaction transaction = null;
        Session session = null;

        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            id = (Integer) session.save(location);
            transaction.commit();
        } catch (HibernateException he) {
            log.error("Starting roll back: " + location, he);
            if (transaction != null) {
                try {

                    transaction.rollback();
                } catch (HibernateException he2) {
                    log.error("Error rolling back location insert: " + location, he2);
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return id;
    }

    /** Get a single location for the given id
     *
     * @param id location's id
     * @return LocationEnity
     */
    public LocationEntity getLocationById(int id) {

        LocationEntity locationEntity = null;
        Session session = null;

        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            locationEntity = (LocationEntity) session.get(LocationEntity.class, id);

        } catch (HibernateException he) {
            log.error("Error getting location with id: " + id, he);

        } finally {
            if (session != null) {
                session.close();
            }
        }

        return locationEntity;
    }

    /** Update  user
     * @param user user to update
     */
    /*
    public void update(User user) {
        Transaction transaction = null;
        Session session = null;

        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
        } catch(HibernateException he) {
            log.error("Hibernate Exception: ", he);
            if (transaction != null) {

                try {
                    transaction.rollback();
                } catch (HibernateException he2) {
                    log.error("Error rolling back save of user: " + user, he2);
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    */

    /** Delete  user
     * @param user user to update
     */
    /*
    public void delete(User user) {
        Transaction transaction = null;
        Session session = null;

        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        } catch(HibernateException he) {
            log.error("Hibernate Exception: ", he);
            if (transaction != null) {

                try {
                    transaction.rollback();
                } catch (HibernateException he2) {
                    log.error("Error rolling back delete of user: " + user, he2);
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    */

}