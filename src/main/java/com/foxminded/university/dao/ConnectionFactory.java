package com.foxminded.university.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class ConnectionFactory {

    private static final Logger log = LogManager.getLogger(ConnectionFactory.class.getName());
    private static DataSource dataSource;
    
    public static Connection getConnection() {
        Connection connection = null;
        
        log.debug("Creating a new connection");

        try (ClassPathXmlApplicationContext context = 
                new ClassPathXmlApplicationContext("applicationContext.xml")) {
            
            dataSource = context.getBean("dataSource", DriverManagerDataSource.class);
            connection = dataSource.getConnection();
            
        } catch (SQLException e) {
            log.error("Connection has not been created." , e);
            throw new DaoException("Connection has not been created." , e);
        }
        
        log.debug("Connection was successfully created.");
        
        return connection;
    }
}
