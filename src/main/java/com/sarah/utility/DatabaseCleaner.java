package com.sarah.utility;

import com.sarah.persistence.SessionFactoryProvider;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * Created by sarah on 10/3/2017.
 */
public class DatabaseCleaner {
    private final Logger log = Logger.getLogger(this.getClass());

    /**
     * Removes all entries and re-sets id value for each table.
     * Used for cleaning test database.
     */
    public void run() {
        Session session = null;
        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();

            // clear entries
            String sql = "DELETE FROM userlocation";
            SQLQuery query = session.createSQLQuery(sql);
            query.executeUpdate();

            sql = "DELETE FROM userprivilege";
            query = session.createSQLQuery(sql);
            query.executeUpdate();

            sql = "DELETE FROM review";
            query = session.createSQLQuery(sql);
            query.executeUpdate();

            sql = "DELETE FROM user";
            query = session.createSQLQuery(sql);
            query.executeUpdate();

            sql = "DELETE FROM taglocationuser";
            query = session.createSQLQuery(sql);
            query.executeUpdate();

            sql = "DELETE FROM taglocation";
            query = session.createSQLQuery(sql);
            query.executeUpdate();

            sql = "DELETE FROM location";
            query = session.createSQLQuery(sql);
            query.executeUpdate();

            sql = "DELETE FROM tag";
            query = session.createSQLQuery(sql);
            query.executeUpdate();

            // reset ids
            String alterSql = "ALTER TABLE userlocation AUTO_INCREMENT = 1";
            SQLQuery alterQuery = session.createSQLQuery(alterSql);
            alterQuery.executeUpdate();

            alterSql = "ALTER TABLE userprivilege AUTO_INCREMENT = 1";
            alterQuery = session.createSQLQuery(alterSql);
            alterQuery.executeUpdate();

            alterSql = "ALTER TABLE review AUTO_INCREMENT = 1";
            alterQuery = session.createSQLQuery(alterSql);
            alterQuery.executeUpdate();

            alterSql = "ALTER TABLE user AUTO_INCREMENT = 1";
            alterQuery = session.createSQLQuery(alterSql);
            alterQuery.executeUpdate();

            alterSql = "ALTER TABLE location AUTO_INCREMENT = 1";
            alterQuery = session.createSQLQuery(alterSql);
            alterQuery.executeUpdate();

            alterSql = "ALTER TABLE taglocationuser AUTO_INCREMENT = 1";
            alterQuery = session.createSQLQuery(alterSql);
            alterQuery.executeUpdate();

            alterSql = "ALTER TABLE taglocation AUTO_INCREMENT = 1";
            alterQuery = session.createSQLQuery(alterSql);
            alterQuery.executeUpdate();

            alterSql = "ALTER TABLE tag AUTO_INCREMENT = 1";
            alterQuery = session.createSQLQuery(alterSql);
            alterQuery.executeUpdate();

        } catch (HibernateException he) {
            log.error("Error cleaning database", he);

        } finally {
            if (session != null) {
                session.close();
            }
        }

    }
}
