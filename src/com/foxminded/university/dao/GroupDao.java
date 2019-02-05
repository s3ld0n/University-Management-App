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

    private List<Student> findStudents(int groupId) {
        
        List<Student> students = new ArrayList<>();
        
        String sql = "SELECT students.id AS id, first_name, last_name, groups.name AS group_name, group_id "
                + "FROM students "
                + "JOIN groups ON students.group_id = groups.id "
                + "WHERE group_id =?;";

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, groupId);
            
            try (ResultSet resultSet = statement.executeQuery()){
            
                while (resultSet.next()) {
                    int studentId = resultSet.getInt("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String group = resultSet.getString("group_name");
    
                    students.add(new Student(studentId, firstName, lastName, group));
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return students;
    }
    
    public Group update(Group group) {
        
        String sql = "UPDATE groups "
                + "SET name = ? "
                + "WHERE id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, group.getName());
            statement.setInt(2, group.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return group;
    }

    public List<Group> findAll() {
        
        List<Group> groups = new ArrayList<>();
        
        String sql = "SELECT id, name FROM groups;";
        
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                groups.add(new Group(id, name));
            }
            
        } catch (SQLException e) {
           e.printStackTrace();
        }
        
        return groups;
    }
}
