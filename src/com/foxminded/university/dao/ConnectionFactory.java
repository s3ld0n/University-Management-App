package com.foxminded.university.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.foxminded.university.utils.PropertyReader;

public class ConnectionFactory {

    private static Properties properties = PropertyReader.readPropertiesFromFile("resources/config.properties");
    private static Logger log = LogManager.getLogger(ConnectionFactory.class.getName());
    
    public static Connection getConnection() {
        Connection connection = null;
        
        log.info("Creating a new connection");

        try {
            connection = DriverManager.getConnection(properties.getProperty("url") , properties.getProperty("user"),
                    properties.getProperty("password"));
        } catch (SQLException e) {
            log.error("Connection has not been created." , e);
            throw new DaoException("Connection has not been created." , e);
        }
        
        log.info("Connection was successfully created.");
        
        return connection;
    }
}
