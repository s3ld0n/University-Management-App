package com.foxminded.university.dao;

import java.sql.*;
import java.util.Properties;

import com.foxminded.university.domain.*;
import com.foxminded.university.utils.PropertyReader;

public class StudentDao {
    
    private Properties properties = PropertyReader.getPropertiesFromFile("/config.properties");
    
    public void addStudent(Student student) {
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        String addStudent = "INSERT INTO students (id, first_name, last_name, group_id) "
                + "VALUES(?, ?, ?, (SELECT id FROM groups WHERE name=?));";
        
        try {
            connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"),
                    properties.getProperty("password"));
            
            statement = connection.prepareStatement(addStudent);

            statement.setInt(1, student.getId());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            statement.setString(4, student.getGroup());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public Student getStudentByID(int id) {
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Student student = null;
        
        String getStudent = "SELECT students.id, first_name, last_name, name "
                + "FROM students JOIN groups ON students.group_id = groups.id "
                + "WHERE students.id=?;";
        
        try {
            connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"),
                    properties.getProperty("password"));
            
            statement = connection.prepareStatement(getStudent);
            statement.setInt(1, id);
            
            resultSet = statement.executeQuery();
            resultSet.next();

            String firstName = resultSet.getString("first_name"); 
            String lastName = resultSet.getString("last_name");
            String group = resultSet.getString("name");
            
            student = new Student(id, firstName, lastName, group);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return student;
    }
    
    public void updateStudent(int id, String firstName, String lastName, String group) {
        
        Connection connection = null;
        PreparedStatement statement = null;

        String updateStudent = "UPDATE students "
                + "SET first_name = ?, last_name = ?, group_id = (SELECT id FROM groups WHERE name=?) "
                + "WHERE id = ?";
   
        try {
            connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"),
                    properties.getProperty("password"));
            
            statement = connection.prepareStatement(updateStudent);

            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, group);
            statement.setInt(4, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
