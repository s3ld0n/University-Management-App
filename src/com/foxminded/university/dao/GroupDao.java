package com.foxminded.university.dao;

import java.sql.*;
import java.util.*;
import com.foxminded.university.domain.*;

public class GroupDao {
    
    private final static String CREATE_QUERY = "INSERT INTO groups (name) VALUES(?);";
    private final static String READ_QUERY = "SELECT id, name FROM groups WHERE id=?";
    private final static String READ_ALL_QUERY = "SELECT id, name FROM groups;";
    private final static String UPDATE_QUERY = "UPDATE groups SET name = ? WHERE id = ?";
    private final static String DELETE_QUERY = "DELETE FROM groups WHERE id=?";
    
    public Group create(Group group) {

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, group.getName());
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
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

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
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

        String sql = "SELECT students.id AS id, first_name, last_name, groups.name AS group_name "
                + "FROM students "
                + "JOIN groups ON students.group_id = groups.id "
                + "WHERE group_id =?;";

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, groupId);

            try (ResultSet resultSet = statement.executeQuery()) {

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

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

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

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_ALL_QUERY);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                groups.add(new Group(id, name));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groups;
    }

    public void deleteById(int id) {

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {

            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting failed. No such id: " + id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
