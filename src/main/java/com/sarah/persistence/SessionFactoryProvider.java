package com.sarah.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * This file provides a SessionFactory for use with DAOS using Hibernate
 */
public class SessionFactoryProvider {

    private static SessionFactory sessionFactory;

    /**
     * Creates session factory to be used throughout application
     */
    private static void createSessionFactory() {

        Configuration configuration = new Configuration();
        configuration.configure();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    /**
     * Returns the session factory
     * @return The session factory
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            createSessionFactory();
        }
        return sessionFactory;

    }
}