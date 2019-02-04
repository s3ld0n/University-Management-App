package com.foxminded.university.dao;

import java.sql.*;
import java.util.*;
import com.foxminded.university.domain.*;

public class StudentDao {

    public Student create(Student student) {

        String sql = "INSERT INTO students (first_name, last_name, group_id) "
                + "VALUES(?, ?, (SELECT id FROM groups WHERE name=?));";
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setString(3, student.getGroup());
            
            statement.executeUpdate();
            
            try(ResultSet resultSet = statement.getGeneratedKeys();) {
                resultSet.next();
                student.setId(resultSet.getInt(1));
            }
            
            } catch (SQLException e) {
                e.printStackTrace();
            } 
        
        return student;
    }
    
    public Student findById(int id) {

        Student student = null;

        String sql = "SELECT students.id, first_name, last_name, groups.name AS group_name "
                + "FROM students JOIN groups ON students.group_id = groups.id "
                + "WHERE students.id=?;";

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()){
                resultSet.next();
                student = new Student(id, resultSet.getString("first_name"),
                        resultSet.getString("last_name"), resultSet.getString("group_name"));
            } 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return student;
    }

    public Student update(Student student) {
        
        String sql = "UPDATE students "
                + "SET first_name = ?, last_name = ?, group_id = (SELECT id FROM groups WHERE name=?) "
                + "WHERE id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setString(3, student.getGroup());
            statement.setInt(4, student.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return student;
    }

    public List<Student> findAll() {
        
        List<Student> students = new ArrayList<>();
        
        String sql = "SELECT students.id AS id, first_name, last_name, groups.name AS group_name "
                + "FROM students "
                + "JOIN groups ON students.group_id = groups.id;";

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String group = resultSet.getString("group_name");

                students.add(new Student(id, firstName, lastName, group));
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return students;
    }
    
    public void deleteById(int id) {
        String sql = "DELETE FROM students WHERE id=?";
        
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);){
            
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Deleting failed. No such id: " + id);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } 
    }
}
