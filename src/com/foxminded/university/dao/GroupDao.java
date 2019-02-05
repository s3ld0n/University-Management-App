package com.foxminded.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.foxminded.university.domain.Group;

public class GroupDao {
    
    public Group create(Group group) {
        
        String sql = "INSERT INTO groups (name) VALUES(?);";
        
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
               
               statement.setString(1, group.getName());
               statement.executeUpdate();
               
               try(ResultSet resultSet = statement.getGeneratedKeys()) {
                   resultSet.next();
                   group.setId(resultSet.getInt(1));
               }
               
               } catch (SQLException e) {
                   e.printStackTrace();
               }
        
        return group;
    }
    
    
}
