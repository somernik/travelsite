package com.sarah.persistence;

import com.sarah.entity.ReviewEntity;
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
public class ReviewDao extends GenericDao {

    private final Logger log = Logger.getLogger(this.getClass());

    /** Get a single reviewEntity for the given id
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

}