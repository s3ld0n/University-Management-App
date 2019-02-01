package com.foxminded.university.dao;

import java.sql.*;
import java.util.*;
import com.foxminded.university.domain.*;

public class StudentDao extends ConnectorDao {

    public void addStudent(Student student) throws DaoException {

        Connection connection = null;
        PreparedStatement statement = null;

        String addStudent = "INSERT INTO students (id, first_name, last_name, group_id) "
                + "VALUES(?, ?, ?, (SELECT id FROM groups WHERE name=?));";

        try {
            connection = DriverManager.getConnection(url, user, password);

            statement = connection.prepareStatement(addStudent);

            statement.setInt(1, student.getId());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            statement.setString(4, student.getGroup());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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

    public Student getStudentById(int id) throws DaoException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Student student = null;

        String getStudent = "SELECT students.id, first_name, last_name, name AS group_name "
                + "FROM students JOIN groups ON students.group_id = groups.id "
                    + "WHERE students.id=?;";

        try {
            connection = DriverManager.getConnection(url, user, password);
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

        return student;
    }

    public void updateStudent(int id, String firstName, String lastName, String group) throws DaoException {

        Connection connection = null;
        PreparedStatement statement = null;

        String updateStudent = "UPDATE students "
                + "SET first_name = ?, last_name = ?, group_id = (SELECT id FROM groups WHERE name=?) "
                + "WHERE id = ?";

        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.prepareStatement(updateStudent);

            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, group);
            statement.setInt(4, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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

    public List<Student> getAllStudents() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Student> students = new ArrayList<>();

        String getStudent = "SELECT students.id AS id, first_name, last_name, name AS group_name " + "FROM students "
                + "JOIN groups ON students.group_id = groups.id;";

        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.prepareStatement(getStudent);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String group = resultSet.getString("group_name");

                students.add(new Student(id, firstName, lastName, group));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
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

        return students;
    }
}
