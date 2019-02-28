package com.foxminded.university.dao.crud_dao_implementations;

import java.sql.*;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.foxminded.university.dao.ConnectionFactory;
import com.foxminded.university.dao.CrudDao;
import com.foxminded.university.dao.DaoException;
import com.foxminded.university.domain.*;

public class GroupDao implements CrudDao<Group> {

    private final static String CREATE_QUERY = "INSERT INTO groups (name) VALUES(?)";
    
    private final static String READ_QUERY = "SELECT id, name FROM groups WHERE id = ?";
    
    private final static String READ_ALL_QUERY = "SELECT id, name FROM groups";
    
    private final static String UPDATE_QUERY = "UPDATE groups SET name = ? WHERE id = ?";
    
    private final static String DELETE_QUERY = "DELETE FROM groups WHERE id = ?";

    private static final Logger log = LogManager.getLogger(GroupDao.class.getName());
    
    public Group create(Group group) {

        log.debug("Creting a group: {}", group);
        
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {
            
            log.debug("Prepared statement was created. Setting group name: {}", group.getName());
            statement.setString(1, group.getName());
            
            log.debug("Executing prepared statement");
            statement.executeUpdate();

            log.debug("Creating result set");
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                
                log.debug("Result set was created. Setting id from DB to group object to return");
                resultSet.next();
                group.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            log.error("Creation of group has failed.", e);
            throw new DaoException("Creation of group has failed.", e);
        }

        log.debug("Group {} was created.", group);
        return group;
    }

    public Group findById(int id) {

        log.debug("Finding group by id: {}", id);

        Group group = null;

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {

            log.debug("Prepared statement was created. Setting id: {}", id);
        
            statement.setInt(1, id);

            log.debug("Creating result set");
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new SQLException("No such group id: " + id);
                }
                
                log.debug("Creating a group object");

                String groupName = resultSet.getString("name");
                group = new Group(id, groupName);
                
                log.debug("Setting students of the group");
                group.setStudents(new StudentDao().findAllByGroupId(id));
            }

        } catch (SQLException e) {
            log.error("Finding group has failed", e);
            throw new DaoException("Finding group has failed", e);
        }

        log.debug("Group {} was successfully found.", group);
        return group;
    }

    public Group update(Group group) {

        log.debug("Updating group with id:{}", group.getId());
        
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            log.debug("Prepared statement was created. Setting parameters");
            
            log.trace("Setting name of the group");
            statement.setString(1, group.getName());
            
            log.trace("Setting id of the group");
            statement.setInt(2, group.getId());
            
            log.debug("Executing prepared statement");
            statement.executeUpdate();

        } catch (SQLException e) {
            log.error("Group update has failed", e);
            throw new DaoException("Group update has failed", e);
        }

        log.debug("Group with id:{} was updated.", group.getId());
        return group;
    }

    public List<Group> findAll() {

        log.debug("Finding all groups");
        
        List<Group> groups = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_ALL_QUERY);
                ResultSet resultSet = statement.executeQuery()) {

            log.debug("Prepared statement was created. Creating result set");
            
            while (resultSet.next()) {
                log.trace("Getting group's id from the result set");
                int id = resultSet.getInt("id");
                
                log.trace("Setting name");
                String name = resultSet.getString("name");

                log.trace("Creating group's object");
                Group group = new Group(id, name);
                
                log.trace("Setting students");
                group.setStudents(new StudentDao().findAllByGroupId(id));
                
                groups.add(group);
                log.trace("Group was found and added to the list");
            }
            
        } catch (SQLException e) {
            log.error("Finding all students has failed", e);
            throw new DaoException("Finding all students has failed", e);
        }

        log.debug("All {} groups have been found.", groups.size());
        return groups;
    }

    public void deleteById(int id) {

        log.debug("Deleting group by id: {}", id);
        
        int affectedRows = 0; 
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {

            log.debug("Prepared statement created. Setting id: {}", id);
            
            statement.setInt(1, id);
            
            log.debug("Executing statement");
            affectedRows = statement.executeUpdate();

        } catch (SQLException e) {
            log.error("Deletion has failed", e);
            throw new DaoException("Deletion has failed", e);
        }
        
        if (affectedRows == 0) {
            log.debug("No group with such id: {} in database.", id);
        } else {
            log.debug("Group was successfully deleted.");            
        }
    }
}
