package com.foxminded.university.utils;

import java.io.*;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropertyReader {
    
    private static Logger log = LogManager.getLogger(PropertyReader.class.getName());
    
    public static Properties readPropertiesFromFile(String path) {
        Properties properties = new Properties();
        
        log.debug("Reading properties from file");
        
        try (InputStream inputStream = new FileInputStream(new File(path))){
            properties.load(inputStream);
        
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
        log.debug("Properties were successfully read.");
        return properties;
    }
}
