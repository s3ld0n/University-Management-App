package com.foxminded.university.utils;

import java.io.*;
import java.util.Properties;

public class PropertyReader {
    
    public static Properties readPropertiesFromFile(String path) {
        Properties properties = new Properties();
        
        try (InputStream inputStream = new FileInputStream(new File(path))){
            properties.load(inputStream);
        
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
        return properties;
    }
}
