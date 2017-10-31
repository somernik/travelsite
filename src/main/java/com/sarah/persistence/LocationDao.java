package com.sarah.persistence;

import com.sarah.entity.LocationEntity;
import com.sarah.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * Access users in the user table.
 *
 * @author somernik
 */
public class LocationDao extends GenericDao {

    private final Logger log = Logger.getLogger(this.getClass());

    /** Get a single location for the given id
     *
     * @param id location's id
     * @return LocationEnity
     */
    public LocationEntity getLocationByIdWithReview(Long id) {

        LocationEntity locationEntity = null;
        Session session = null;

        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            locationEntity = (LocationEntity) session.get(LocationEntity.class, id);
            Hibernate.initialize(locationEntity.getReviews());
            Hibernate.initialize(locationEntity.getUsers());
        } catch (HibernateException he) {
            log.error("Error getting location with id: " + id, he);

        } finally {
            if (session != null) {
                session.close();
            }
        }

        return locationEntity;
    }

}