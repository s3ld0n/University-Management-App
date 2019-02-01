package com.foxminded.university.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import com.foxminded.university.utils.PropertyReader;

public class ConnectorDao {

    private String url;
    private String user;
    private String password;
    
    public ConnectorDao() {
        Properties properties = PropertyReader.readPropertiesFromFile("/config.properties");
        this.url = properties.getProperty("url");
        this.user = properties.getProperty("user");
        this.password = properties.getProperty("password");
    }
    
    public Connection receiveConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return connection;
    }
}
