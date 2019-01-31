package com.foxminded.university.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    
    public static Properties getPropertiesFromFile(String path) {
        InputStream inputStream = null;
        Properties properties = new Properties();
        
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            inputStream = classLoader.getClass().getResourceAsStream(path);
            properties.load(inputStream);
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
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
