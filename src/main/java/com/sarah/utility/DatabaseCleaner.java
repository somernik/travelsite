package com.sarah.utility;

import com.sarah.persistence.SessionFactoryProvider;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * Created by sarah on 10/3/2017.
 */
public class DatabaseCleaner {
    public void run() {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();

        // clear entries
        String sql = "DELETE FROM userlocation";
        SQLQuery query = session.createSQLQuery(sql);
        query.executeUpdate();

        sql = "DELETE FROM review";
        query = session.createSQLQuery(sql);
        query.executeUpdate();

        sql = "DELETE FROM user";
        query = session.createSQLQuery(sql);
        query.executeUpdate();

        sql = "DELETE FROM location";
        query = session.createSQLQuery(sql);
        query.executeUpdate();

        // reset ids
        String alterSql = "ALTER TABLE userlocation AUTO_INCREMENT = 1";
        SQLQuery alterQuery = session.createSQLQuery(alterSql);
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
    }
}
