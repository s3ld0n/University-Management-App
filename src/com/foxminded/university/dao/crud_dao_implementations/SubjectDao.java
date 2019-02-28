package com.foxminded.university.dao.crud_dao_implementations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.foxminded.university.dao.ConnectionFactory;
import com.foxminded.university.dao.DaoException;
import com.foxminded.university.dao.SubjectCrudDao;
import com.foxminded.university.domain.Subject;

public class SubjectDao implements SubjectCrudDao {
    
    private static final String CREATE_QUERY = "INSERT INTO subjects (name) VALUES(?)";
    
    private static final String READ_QUERY = "SELECT name FROM subjects WHERE id = ?";
    
    private static final String READ_ALL_BY_LECTOR_ID_QUERY = "SELECT subjects.id, subjects.name "
            + "FROM subjects "
            + "JOIN lectors_subjects ON subjects.id = lectors_subjects.subject_id " 
            + "JOIN lectors ON lectors_subjects.lector_id = lectors.id " 
            + "WHERE lectors.id = ?";
    
    private static final String READ_ALL_QUERY = "SELECT id, name FROM subjects";

    private static final String UPDATE_QUERY = "UPDATE subjects SET name = ? WHERE id = ?";

    private final static String DELETE_QUERY = "DELETE FROM subjects WHERE id = ?";

    private static final Logger log = LogManager.getLogger(SubjectDao.class.getName());

    public Subject create(Subject subject) {
        
        log.debug("Creating subject");
        
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS)){
            
            log.debug("Prepared statement was created. Setting id parameter");
            
            statement.setString(1, subject.getName());
            
            log.debug("Executing prepared statement");
            statement.executeUpdate();
            
            try (ResultSet resultSet = statement.getGeneratedKeys()){
                log.debug("Result set was created. Setting id from DB to period object to return");
                resultSet.next();
                subject.setId(resultSet.getInt(1));
            }
            
        } catch (SQLException e) {
            log.error("Creation of subject has failed.", e);
            throw new DaoException("Creation of subject has failed.", e);
        }

        log.debug("Subject {} was created.", subject);
        
        return subject;
    }
    
    public Subject findById(int id) {
        
        log.debug("Finding subject by id: {}", id);
        Subject subject = null;
        
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setInt(1, id);

            log.debug("Prepared statement was created. Creating result set");
            
            try (ResultSet resultSet = statement.executeQuery()) {
                
                if (!resultSet.next()) {
                    throw new SQLException("No such subject's id: " + id);
                }

                log.debug("Creating subject object");
                
                subject = new Subject(id, resultSet.getString("name"));
            }
            
        } catch (SQLException e) {
            log.error("Finding subject has failed", e);
            throw new DaoException("Finding subject has failed", e);
        }

        log.debug("Subject with id: {} was found", id);
        
        return subject;
    }
    
    public Subject update(Subject subject) {

        log.debug("Updating subject with id: {}", subject.getId());
        
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            log.debug("Prepared statement was created. Setting parameters");
            
            log.trace("Setting subject's name");
            statement.setString(1, subject.getName());
            
            log.trace("Setting subject's id");
            statement.setInt(2, subject.getId());
            
            log.debug("Executing prepared statement");
            statement.executeUpdate();

        } catch (SQLException e) {
            log.error("Subject update has failed", e);
            throw new DaoException("Subject update has failed", e);
        }

        log.debug("Subject with id: {} was updated.", subject.getId());

        return subject;
    }
    
    public List<Subject> findAll() {

        log.debug("Finding all subjects");
        
        List<Subject> subjects = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_ALL_QUERY)) {

            log.debug("Prepared statement was created. Creating result set");
            
            try (ResultSet resultSet = statement.executeQuery()) {
                
                while (resultSet.next()) {
                    log.trace("Getting student fields from result set");
                    
                    log.trace("Getting id");
                    int id = resultSet.getInt("id");
                    
                    log.trace("Getting name");
                    String name = resultSet.getString("name");

                    subjects.add(new Subject(id, name));
                    log.trace("Subject was found and added to list.");
                }
            }
        } catch (SQLException e) {
            log.error("Finding all subjects has failed", e);
            throw new DaoException("Finding all subjects has failed", e);
        }

        log.debug("All {} subjects have been found.", subjects.size());

        return subjects;
    }

    public List<Subject> findAllByLectorId(int lectorId) {

        log.debug("Finding all subjects by lector id: {}", lectorId);
        
        List<Subject> subjects = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_ALL_BY_LECTOR_ID_QUERY)) {

            log.debug("Prepared statement created. Setting lector id: {}", lectorId);
            
            statement.setInt(1, lectorId);

            log.debug("Creating result set");
            
            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    log.trace("Getting subject's fields from result set");
                    
                    log.trace("Getting id");
                    int id = resultSet.getInt("id");
                    
                    log.trace("Getting name");
                    String name = resultSet.getString("name");

                    subjects.add(new Subject(id, name));
                    log.trace("Subject was created and added to list.");
                }
            }
        } catch (SQLException e) {
            log.error("Finding subjects by lector id has failed", e);
            throw new DaoException("Finding subjects by lector id has failed", e);
        }

        if (subjects.size() == 0) {
            log.debug("No such lector id: {} or the lector doesn't have any registered subjects.", lectorId);
        } else {
            log.debug("All {} students by lector id: {} have been found.", subjects.size(), lectorId);
        }

        return subjects;
    }
    
    public void deleteById(int id) {
        log.debug("Deleting subject by id: {}", id);

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
            log.debug("No subject with such id: {} in database.", id);
        } else {
            log.debug("Subject with id: {} was successfully deleted.", id);
        }
    }
}
