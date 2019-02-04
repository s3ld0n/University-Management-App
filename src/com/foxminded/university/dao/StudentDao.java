package com.foxminded.university.dao;

import java.sql.*;
import java.util.*;
import com.foxminded.university.domain.*;
import com.foxminded.university.utils.PersonalInfo;

public class StudentDao {

    public Student create(Student student) {

        String sql = "INSERT INTO students (id, first_name, last_name, group_id) "
                + "VALUES(DEFAULT, ?, ?, (SELECT id FROM groups WHERE name=?)) RETURNING id;";
        
        ResultSet resultSet = null;
        int idOfCreatedStudent = 0;
        
        String firstName = student.getFirstName();
        String lastName = student.getLastName();
        String group = student.getGroup();
        
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){

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
        
        String sql = "SELECT students.id, first_name, last_name, groups.name AS group_name "
                + "FROM students JOIN groups ON students.group_id = groups.id "
                + "WHERE students.id=?;";

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)){

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
        
        String sql = "SELECT students.id AS id, first_name, last_name, group.name AS group_name "
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

                students.add(new Student(new PersonalInfo(id, firstName, lastName), group));
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return students;
    }
}
