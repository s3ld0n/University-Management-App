package com.foxminded.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.foxminded.university.domain.Subject;

public class SubjectDao {
    
    private static final String CREATE_QUERY = "INSERT INTO subjects (name) VALUES(?);";
    
    public Subject create(Subject subject) {
        
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS)
                        ){
            statement.setString(1, subject.getName());
            
            try (ResultSet resultSet = statement.executeQuery()){
                resultSet.next();
                subject.setId(resultSet.getInt(1));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return subject;
    }
}
