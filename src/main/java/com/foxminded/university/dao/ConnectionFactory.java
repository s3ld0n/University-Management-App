package com.foxminded.university.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionFactory {

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
    private static final Logger log = LogManager.getLogger(ConnectionFactory.class.getName());
    
    public static Connection getConnection() {
        Connection connection = null;
        
        log.debug("Creating a new connection");

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(resourceBundle.getString("url") , resourceBundle.getString("user"),
                    resourceBundle.getString("password"));
        } catch (SQLException | ClassNotFoundException e) {
            log.error("Connection has not been created." , e);
            throw new DaoException("Connection has not been created." , e);
        }
        
        log.debug("Connection was successfully created.");
        
        return connection;
    }
}
