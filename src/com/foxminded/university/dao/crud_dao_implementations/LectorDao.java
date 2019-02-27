package com.foxminded.university.dao.crud_dao_implementations;

import java.sql.*;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.foxminded.university.dao.ConnectionFactory;
import com.foxminded.university.dao.DaoException;
import com.foxminded.university.dao.LectorCrudDao;
import com.foxminded.university.domain.*;

public class LectorDao implements LectorCrudDao {
    
    private static final String CREATE_QUERY = "INSERT INTO lectors (first_name, last_name) VALUES (?, ?)";
    
    private static final String READ_QUERY = "SELECT id, first_name, last_name FROM lectors WHERE id = ?";
    
    private static final String ADD_SUBJECT_BY_ID_QUERY = "INSERT INTO lectors_subjects (lector_id, subject_id) "
            + "VALUES(?, ?)";

    private static final String REMOVE_SUBJECT_BY_ID_QUERY = "DELETE FROM lectors_subjects "
            + "WHERE lector_id = ? AND subject_id = ?";
    
    private static final String READ_ALL_QUERY = "SELECT id, first_name, last_name FROM lectors";
    
    private static final String UPDATE_QUERY = "UPDATE lectors SET first_name = ?, last_name = ? WHERE id = ?";
    
    private static final String DELETE_QUERY = "DELETE FROM lectors WHERE id = ?";

    private static final Logger log = LogManager.getLogger(LectorDao.class.getName());
    
    public Lector create(Lector lector) {

        log.debug("Creating lector {} {}", lector.getFirstName(), lector.getLastName());
        
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            
            log.debug("Prepared statement was created. Setting parameters");
            
            log.trace("Setting first name");
            statement.setString(1, lector.getFirstName());
            
            log.trace("Setting last name");
            statement.setString(2, lector.getLastName());

            log.debug("Executing prepared statement");
            statement.executeUpdate();

            log.debug("Creating result set.");
            
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                log.debug("Result set was created. Setting id from DB to lector object to return");
                resultSet.next();
                lector.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            log.error("Creation of student has failed.", e);
            throw new DaoException("Creation of student has failed.", e);
        }

        log.debug("Lector {} was created.", lector);
        
        return lector; 
    }
    
    public Lector findById(int id) {

        log.debug("Finding lector by id:{}", id);
        Lector lector = null;

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            
            log.debug("Prepared statement was created. Setting id: {}", id);
            statement.setInt(1, id);
            
            log.debug("Creating result set");
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new SQLException("No such lector id: " + id);
                }
                
                log.debug("Creating lector object");
                
                lector = new Lector(id, resultSet.getString("first_name"), resultSet.getString("last_name"));
                
                log.debug("Setting subjects");
                lector.setSubjects(new SubjectDao().findAllByLectorId(id));
            }
        } catch (SQLException e) {
            log.error("Finding lector has failed", e);
            throw new DaoException("Finding lector has failed", e);
        }

        log.debug("Lector {} {} was found by id:{}.", lector.getFirstName(), lector.getLastName(), lector.getId());
        return lector;
    }

    public Lector update(Lector lector) {
        
        log.debug("Updating lector with id:{}", lector.getId());
        
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            log.debug("Prepared statement was created. Setting parameters");
            
            log.trace("Setting first name");
            statement.setString(1, lector.getFirstName());
            
            log.trace("Setting last name");
            statement.setString(2, lector.getLastName());
            
            log.trace("Setting id");
            statement.setInt(3, lector.getId());
            
            log.debug("Executing prepared statement");
            statement.executeUpdate();

        } catch (SQLException e) {
            log.error("Lector update has failed", e);
            throw new DaoException("Lector update has failed", e);
        }

        log.debug("Lector with id:{} was updated.", lector.getId());

        return lector;
    }
    
    public List<Lector> findAll() {

        log.debug("Finding all lectors");
        List<Lector> lectors = new ArrayList<>();
        
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_ALL_QUERY)) {

            log.debug("Prepared statement was created. Creating result set");
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    log.trace("Getting student fields from result set");
                    
                    log.trace("Getting id");
                    int id = resultSet.getInt("id");
                    
                    log.trace("Getting first name");
                    String firstName = resultSet.getString("first_name");
                    
                    log.trace("Getting last name");
                    String lastName = resultSet.getString("last_name");
                    
                    log.trace("Creating lector object");
                    Lector lector = new Lector(id, firstName, lastName);
                    
                    log.trace("Setting subjects");
                    lector.setSubjects(new SubjectDao().findAllByLectorId(id));
                    
                    log.trace("Adding lector object to list");
                    lectors.add(lector);
                }
            }
        } catch (SQLException e) {
            log.error("Finding all lectors has failed", e);
            throw new DaoException("Finding all lectors has failed", e);
        }

        log.debug("All {} lectors have been found.", lectors.size());

        return lectors;
    }
    
    public void addSubjectById(Lector lector, int subjectId) {
        
        log.debug("Adding subject by id:{} to lector {} {}", subjectId, lector.getFirstName(), lector.getLastName());
        
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(ADD_SUBJECT_BY_ID_QUERY)) {
            
                log.debug("Prepared statement was created. Setting parameters");
            
                log.trace("Setting lector id");
                statement.setInt(1, lector.getId());
                
                log.trace("Setting subject's id");
                statement.setInt(2, subjectId);
                
                log.trace("Executing prepared statement");
                statement.executeUpdate();
            
        } catch (SQLException e) {
            log.error("Adding subject to lector has failed", e);
            throw new DaoException("Adding subject to lector has failed", e);
        }
    }
    
    public void removeSubjectById(Lector lector, int subjectId) {
        
        log.debug("Removing subject by id:{} from lector {} {}", subjectId, lector.getFirstName(), lector.getLastName());
        
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(REMOVE_SUBJECT_BY_ID_QUERY)) {
            
            log.debug("Prepared statement was created. Setting parameters");
            
            log.trace("Setting lector id");
            statement.setInt(1, lector.getId());
            
            log.trace("Setting subject's id");
            statement.setInt(2, subjectId);
            
            log.trace("Executing prepared statement");
            statement.executeUpdate();
        
        } catch (SQLException e) {
            log.error("Removing subject from lector has failed", e);
            throw new DaoException("Removing subject from lector has failed", e);
        }
    }
    
    public void deleteById(int id) {

        log.debug("Deleting lector by id: {}", id);
        
        int affectedRows = 0;
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {

            log.debug("Prepared statement was created. Setting id");
            
            statement.setInt(1, id);

            log.debug("Executing prepared statement");
            affectedRows = statement.executeUpdate();

        } catch (SQLException e) {
            log.error("Deletion has failed", e);
            throw new DaoException("Deletion has failed", e);
        }
        
        if (affectedRows == 0) {
            log.debug("No such id: {} in database.", id);
        } else {
            log.debug("Lector was successfully deleted.");            
        }
    }
}
