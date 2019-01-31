package com.foxminded.university.dao;

import java.util.Properties;
import com.foxminded.university.utils.PropertyReader;

public abstract class ConnectorDao {

    String url;
    String user;
    String password;
    
    public ConnectorDao() {
        Properties properties = PropertyReader.getPropertiesFromFile("/config.properties");
        this.url = properties.getProperty("url");
        this.user = properties.getProperty("user");
        this.password = properties.getProperty("password");
    }
}
