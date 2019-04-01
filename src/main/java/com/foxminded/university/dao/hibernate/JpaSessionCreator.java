package com.foxminded.university.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class JpaSessionCreator {

    private static Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
    
    static Session getSession() {
        return configuration.buildSessionFactory().getCurrentSession();
    }
}
