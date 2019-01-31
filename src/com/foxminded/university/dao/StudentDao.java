package com.foxminded.university.dao;

import java.sql.*;
import java.util.*;
import com.foxminded.university.domain.*;

public class StudentDao extends ConnectorDao {
    
    public void addStudent(Student student) {
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        String addStudent = "INSERT INTO students (id, first_name, last_name, group_id) "
                + "VALUES(?, ?, ?, (SELECT id FROM groups WHERE name=?));";
        
        try {
            connection = DriverManager.getConnection(url, user,password);
            
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
    
    public Student getStudentById(int id) {
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Student student = null;
        
        String getStudent = "SELECT students.id, first_name, last_name, name AS group_name "
                + "FROM students JOIN groups ON students.group_id = groups.id "
                + "WHERE students.id=?;";
        
        try {
            connection = DriverManager.getConnection(url, user,password);            
            statement = connection.prepareStatement(getStudent);
            statement.setInt(1, id);
            
            resultSet = statement.executeQuery();
            resultSet.next();

            String firstName = resultSet.getString("first_name"); 
            String lastName = resultSet.getString("last_name");
            String group = resultSet.getString("group_name");
            
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
            connection = DriverManager.getConnection(url, user,password);            
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
    
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String studentsIds = getAllStudentsIds();
        List<String> buffer = Arrays.asList(studentsIds.split(","));
        
        for (String idAsString : buffer) {
            int id = Integer.parseInt(idAsString);
            students.add(getStudentById(id));
        }
        
        return students;
    }
    
    private String getAllStudentsIds() {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String rowOfIds = "";
        
        String getAllStudents = "SELECT string_agg(CAST(students.id AS VARCHAR(50)), ',') "
                + "AS all_students_ids from students;";

        try {
            connection = DriverManager.getConnection(url, user,password);            
            statement = connection.prepareStatement(getAllStudents);
            resultSet = statement.executeQuery();
            resultSet.next();
            rowOfIds = resultSet.getString("all_students_ids");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
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

        return rowOfIds;
    }
}
