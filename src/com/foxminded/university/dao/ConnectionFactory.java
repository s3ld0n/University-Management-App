package com.foxminded.university.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import com.foxminded.university.utils.PropertyReader;

public class ConnectionFactory {

    private static Properties properties = PropertyReader.readPropertiesFromFile("resources/config.properties");
    
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(properties.getProperty("url") , properties.getProperty("user"),
                    properties.getProperty("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return connection;
    }
}
