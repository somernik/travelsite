package com.sarah.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.NotFoundException;

import com.sarah.entity.BaseEntity;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * A generic DAO implementation using Hibernate's Session directly, not JPA.
 *
 * <p>The primary noticeable difference is the use of "hibernate criteria" instead of "JPA criteria". 
 *
 * @author Rodrigo Uchôa (http://rodrigouchoa.wordpress.com)
 *
 */
@Stateless
public class GenericDao {
    private final Logger log = Logger.getLogger(this.getClass());

    /**
     * Saves an entity.
     *
     * @param entity
     * @return newly created id for the entity.
     */
    @SuppressWarnings("unchecked")
    public <T extends BaseEntity<PK>, PK extends Serializable> PK save(T entity) {
        Long id = new Long(0);
        Transaction transaction = null;
        Session session = null;

        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            id = (Long) session.save(entity);
            transaction.commit();
        } catch (HibernateException he) {
            log.error("Starting roll back: " + entity, he);
            if (transaction != null) {
                try {

                    transaction.rollback();
                } catch (HibernateException he2) {
                    log.error("Error rolling back insert: " + entity, he2);
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return (PK) id;
    }

    /**
     * Marges objects with the same identifier within a session into a newly created object.
     *
     * @param entity
     * @return a newly created instance merged.
     */
    @SuppressWarnings("unchecked")
    public <T extends BaseEntity<PK>, PK extends Serializable> T update(T entity) {

        Transaction transaction = null;
        Session session = null;

        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(entity);
            transaction.commit();
        } catch(HibernateException he) {
            log.error("Hibernate Exception: ", he);
            if (transaction != null) {

                try {
                    transaction.rollback();
                } catch (HibernateException he2) {
                    log.error("Error rolling back save of item: " + entity, he2);
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return entity;
    }

    /**
     * Deletes tne entity.
     *
     * @param clazz
     * @param id
     * @throws NotFoundException if the id does not exist.
     */
    public <T extends BaseEntity<PK>, PK extends Serializable> void delete(Class<T> clazz, PK id) {
        T entity = find(clazz, id);

        Transaction transaction = null;
        Session session = null;

        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch(HibernateException he) {
            log.error("Hibernate Exception: ", he);
            if (transaction != null) {

                try {
                    transaction.rollback();
                } catch (HibernateException he2) {
                    log.error("Error rolling back delete of item: " + entity, he2);
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * Find an entity by its identifier.
     *
     * @param clazz
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends BaseEntity<?>> T find(Class<T> clazz, Serializable id) {
        T item = null;
        Session session = null;

        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            item = (T) session.get(clazz, id);
        } catch (HibernateException he) {
            log.error("Error getting user with id: " + id, he);

        } catch (NullPointerException e) {
            log.error("Error getting user with id (user does not exist): " + id, e);

        } finally {
            if (session != null) {
                session.close();
            }
        }

        return item;
    }

    /**
     * Finds entities by one of its properties.
     *
     *
     *
     * @param clazz the entity class.
     * @param propertyName the property name.
     * @param value the value by which to find.
     * @return
     */
    /*
    @SuppressWarnings("unchecked")

    public <T extends BaseEntity<?>> List<T> findByProperty(Class<T> clazz, String propertyName, Object value) {
        return getSession().createCriteria(clazz).add(Restrictions.eq(propertyName, value)).list();
    }
    */

    /**
     * Finds entities by a String property specifying a MatchMode. This search
     * is case insensitive.
     *
     * @param clazz the entity class.
     * @param propertyName the property name.
     * @param value the value to check against.
     * @param matchMode the match mode: EXACT, START, END, ANYWHERE.
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends BaseEntity<?>> List<T> findByProperty(Class<T> clazz, String propertyName, String value, MatchMode matchMode){
        Session session = null;
        List<T> items = new ArrayList<T>();
        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            if (matchMode != null){
                items =  session.createCriteria(clazz).add(Restrictions.ilike(propertyName, value, matchMode)).list();
            }else{
                items =  session.createCriteria(clazz).add(Restrictions.ilike(propertyName, value, MatchMode.EXACT)).list();
            }
        } catch (HibernateException he) {
            log.error("Error getting all users", he);
        } catch (NullPointerException e) {
            log.error("Error getting user with id (user does not exist): ", e);

        } finally {
            if (session != null) {
                session.close();
            }
        }
        return items;
    }

    /**
     * Finds all objects of an entity class.
     *
     * @param clazz the entity class.
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends BaseEntity<?>> List<T> findAll(Class<T> clazz) {

        List<T> items = new ArrayList<T>();
        Session session = null;
        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            items = session.createCriteria(clazz).list();

        } catch (HibernateException he) {
            log.error("Error getting all users", he);
        } catch (NullPointerException e) {
            log.error("Error getting user with id (user does not exist): ", e);

        } finally {
            if (session != null) {
                session.close();
            }
        }
        return items;
    }

}