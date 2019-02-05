package com.foxminded.university.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.foxminded.university.domain.Group;
import com.foxminded.university.domain.Student;

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
    
    public Group findById(int id) {

        Group group = null;

        String sql = "SELECT id, name FROM groups WHERE id=?";

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)){
            
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()){
                resultSet.next();
                group = new Group(id, resultSet.getString("name"));
                List<Student> students = findStudents(id);
                group.setStudents(students);
            } 
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return group;
    }

    private List<Student> findStudents(int id) {
        
        List<Student> students = new ArrayList<>();
        
        String sql = "SELECT students.id AS id, first_name, last_name, groups.name AS group_name "
                + "FROM students "
                + "JOIN groups ON students.group_id = groups.id "
                + "WHERE group_id =?;";

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()){
            
                while (resultSet.next()) {
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String group = resultSet.getString("group_name");
    
                    students.add(new Student(id, firstName, lastName, group));
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
