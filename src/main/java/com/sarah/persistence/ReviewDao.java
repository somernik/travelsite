package com.sarah.persistence;

import com.sarah.entity.ReviewEntity;
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
public class ReviewDao extends GenericDao {

    private final Logger log = Logger.getLogger(this.getClass());

    /** Get a single reviewEntity for the given id and initialize properties
     *
     * @param id reviewEntity's id
     * @return ReviewEntity
     */
    public ReviewEntity getReviewById(Long id) {

        ReviewEntity review = null;
        Session session = null;

        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            review = (ReviewEntity) session.get(ReviewEntity.class, id);
            Hibernate.initialize(review.getLocation());
            Hibernate.initialize(review.getUser());
        } catch (HibernateException he) {
            log.error("Error getting review with id: " + id, he);

        } finally {
            if (session != null) {
                session.close();
            }
        }

        return review;
    }

    /** Get a single reviewEntity based on a single property and initialize properties
     *
     * @param propertyName The name of the property
     * @param value The value of the property
     * @return List<ReviewEntity> The list of returned entities
     */
    public List<ReviewEntity> findByAndInitializeProperties (String propertyName, Object value) {

        Session session = null;
        List<ReviewEntity> items = new ArrayList<ReviewEntity>();
        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();

            items = session.createCriteria(ReviewEntity.class).add(Restrictions.eq(propertyName, value)).list();
            for (ReviewEntity review: items) {
                Hibernate.initialize(review.getLocation());
                Hibernate.initialize(review.getUser());
            }
        } catch (HibernateException he) {
            log.error("Error initializing items", he);
        } catch (NullPointerException e) {
            log.error("Error initializing item (item does not exist): ", e);

        } finally {
            if (session != null) {
                session.close();
            }
        }
        return items;
    }

}