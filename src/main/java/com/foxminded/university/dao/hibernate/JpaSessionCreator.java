package com.foxminded.university.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class JpaSessionCreator {

    private static Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
    
    static Session getSession(Class<?> ... classes) {
        for (Class<?> klass : classes) {
            configuration.addAnnotatedClass(klass);
        }
        
        return configuration.buildSessionFactory().getCurrentSession();
    }
}
