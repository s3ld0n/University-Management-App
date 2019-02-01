package com.foxminded.university.utils;

import java.io.*;
import java.util.Properties;

public class PropertyReader {
    
    public static Properties readPropertiesFromFile(String path) {
        InputStream inputStream = null;
        Properties properties = new Properties();
        
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            inputStream = classLoader.getClass().getResourceAsStream(path);
            properties.load(inputStream);
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }
}
