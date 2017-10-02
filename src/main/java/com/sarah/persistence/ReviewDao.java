package com.sarah.persistence;

import com.sarah.entity.ReviewEntity;
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
public class ReviewDao {

    private final Logger log = Logger.getLogger(this.getClass());

    /** Return a list of all reviews
     *
     * @return All reviews
     */
    public List<ReviewEntity> getAllReviews() {
        List<ReviewEntity> reviews = new ArrayList<ReviewEntity>();
        Session session = null;
        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            reviews = session.createCriteria(ReviewEntity.class).list();
        } catch (HibernateException he) {
            log.error("Error getting all reviews", he);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return reviews;
    }

    /** save new review
     * @param review review to insert
     * @return id of the inserted review
     */
    public int insertReview(ReviewEntity review) {
        int id = 0;
        Transaction transaction = null;
        Session session = null;

        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            id = (Integer) session.save(review);
            transaction.commit();
        } catch (HibernateException he) {
            log.error("Starting roll back: " + review, he);
            if (transaction != null) {
                try {

                    transaction.rollback();
                } catch (HibernateException he2) {
                    log.error("Error rolling back review insert: " + review, he2);
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return id;
    }

    /** Get a single reviewEntity for the given id
     *
     * @param id reviewEntity's id
     * @return ReviewEntity
     */
    public ReviewEntity getReviewById(int id) {

        ReviewEntity review = null;
        Session session = null;

        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            review = (ReviewEntity) session.get(ReviewEntity.class, id);

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