package com.foxminded.university.dao;

import java.sql.*;
import java.util.*;
import com.foxminded.university.domain.*;
import com.foxminded.university.utils.PersonalInfo;

public class StudentDao {

    private ConnectionFactory connector = new ConnectionFactory(); 
    
    public Student create(Student student) {

        String addStudent = "INSERT INTO students (id, first_name, last_name, group_id) "
                + "VALUES(DEFAULT, ?, ?, (SELECT id FROM groups WHERE name=?)) RETURNING id;";
        
        ResultSet resultSet = null;
        int idOfCreatedStudent = 0;
        
        String firstName = student.getFirstName();
        String lastName = student.getLastName();
        String group = student.getGroup();
        
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(addStudent)){

            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, group);
            
            resultSet = statement.executeQuery();
            resultSet.next();
            
            idOfCreatedStudent = resultSet.getInt(1);
            
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        
        return new Student(new PersonalInfo(idOfCreatedStudent, firstName, lastName), group);
    }
    
    public Student findById(int id) {

        Student student = null;
        ResultSet resultSet = null;
        
        String getStudent = "SELECT students.id, first_name, last_name, name AS group_name "
                + "FROM students JOIN groups ON students.group_id = groups.id "
                + "WHERE students.id=?;";

        try (Connection connection = connector.getConnection();
                PreparedStatement statement = connection.prepareStatement(getStudent)){

            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            resultSet.next();

            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String group = resultSet.getString("group_name");

            student = new Student(new PersonalInfo(id, firstName, lastName), group);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return student;
    }

    public Student update(Student student) {
        
        String updateStudent = "UPDATE students "
                + "SET first_name = ?, last_name = ?, group_id = (SELECT id FROM groups WHERE name=?) "
                + "WHERE id = ?";

        try (Connection connection = connector.getConnection();
                PreparedStatement statement = connection.prepareStatement(updateStudent)){

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
        
        String getStudent = "SELECT students.id AS id, first_name, last_name, name AS group_name " + "FROM students "
                + "JOIN groups ON students.group_id = groups.id;";

        try (Connection connection = connector.getConnection();
                PreparedStatement statement = connection.prepareStatement(getStudent);
                ResultSet resultSet = statement.executeQuery();) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String group = resultSet.getString("group_name");

                students.add(new Student(new PersonalInfo(id, firstName, lastName), group));
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return students;
    }
}
