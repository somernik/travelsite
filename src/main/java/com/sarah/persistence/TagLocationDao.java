package com.sarah.persistence;

import com.sarah.entity.LocationEntity;
import com.sarah.entity.TagEntity;
import com.sarah.entity.TaglocationEntity;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * Access taglocations in the taglocation table.
 *
 * @author somernik
 */
public class TagLocationDao extends GenericDao {

    private final Logger log = Logger.getLogger(this.getClass());

    /**
     * Gets taglocations based on tag and location
     * @param location Location
     * @param tag Tag
     * @return The taglocationentity
     */
    public TaglocationEntity getByLocationAndTag(LocationEntity location, TagEntity tag){
        Session session = null;
        List<TaglocationEntity> items = new ArrayList<TaglocationEntity>();
        TaglocationEntity entity = new TaglocationEntity();

        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(TaglocationEntity.class);
            criteria.add(Restrictions.eq("location", location));
            criteria.add(Restrictions.eq("tag", tag));
            items =  criteria.list();

        } catch (HibernateException he) {
            log.error("Error getting taglocation", he);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        if (items.size()==1) {
            entity = items.get(0);
        }

        return entity;
    }

    /**
     * Find taglocation based on a single property and initialize properties
     * @param propertyName The property name
     * @param value the property value
     * @return A list of tag locations
     */
    public List<TaglocationEntity> findByAndInitializeTag (String propertyName, Object value) {

        Session session = null;
        List<TaglocationEntity> items = new ArrayList<TaglocationEntity>();
        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();

            items = session.createCriteria(TaglocationEntity.class).add(Restrictions.eq(propertyName, value)).list();
            for (TaglocationEntity review: items) {
                Hibernate.initialize(review.getTag());
            }
        } catch (HibernateException he) {
            log.error("Error initializing items", he);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return items;
    }
}