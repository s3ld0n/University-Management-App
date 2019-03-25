package com.foxminded.university.utils;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ContextReceiver {
    
    private static final Logger log = LogManager.getLogger(ContextReceiver.class.getName());
    
    @SuppressWarnings("resource")
    public static DataSource receiveDataSourceFromContext(String contextFile) {
        ApplicationContext context = null;
        DataSource dataSource = null;
        
        try {
            context = new ClassPathXmlApplicationContext(contextFile);
            dataSource = context.getBean("dataSource", DataSource.class);
            
        } catch (BeanCreationException e) {
            log.error("DataSource failed to be created", e);
            throw new BeanCreationException("DataSource failed to be created", e);
        } 
        
        return dataSource;
    }
}
