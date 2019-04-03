package com.foxminded.university.dao.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.foxminded.university.dao.DaoException;

public class HibenateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {

        try {
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
            return configuration.buildSessionFactory();
        
        } catch ( HibernateException e) {
            throw new DaoException("Creation of SessionFactory has failed", e);
        }
    }

    public static SessionFactory getSessionfactory() {
        return sessionFactory;
    }
}
