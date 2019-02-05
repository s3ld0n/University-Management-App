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
                
                String groupName = resultSet.getString("name");
                group = new Group(id, groupName);
                group.setStudents(new StudentDao().findAllByGroupId(id));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return group;
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
        StudentDao studentDao = new StudentDao();

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_ALL_QUERY);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                Group group = new Group(id, name);
                group.setStudents(studentDao.findAllByGroupId(id));
                groups.add(group);
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
