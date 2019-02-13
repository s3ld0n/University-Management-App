package com.foxminded.university.dao;

import java.sql.*;
import java.util.*;
import com.foxminded.university.domain.*;

public class LectorDao {
    
    private static final String CREATE_QUERY = "INSERT INTO lectors (first_name, last_name) VALUES (?, ?)";
    
    private static final String READ_QUERY = "SELECT id, first_name, last_name FROM lectors WHERE id = ?";
    
    private static final String APPOINT_TO_SUBJECT_BY_ID_QUERY = "INSERT INTO lectors_subjects (lector_id, subject_id) "
            + "VALUES(?, ?)";

    private static final String DISCARD_SUBJECT_BY_ID_QUERY = "DELETE FROM lectors_subjects WHERE lector_id = ? "
            + "AND subject_id = ?";
    
    private static final String READ_ALL_QUERY = "SELECT id, first_name, last_name FROM lectors";
    
    private static final String UPDATE_QUERY = "UPDATE lectors SET first_name = ?, last_name = ? WHERE id = ?";
    
    private static final String DELETE_QUERY = "DELETE FROM lectors WHERE id = ?";
    
    public Lector create(Lector lector) {

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, lector.getFirstName());
            statement.setString(2, lector.getLastName());

            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                lector.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lector; 
    }
    
    public Lector findById(int id) {

        Lector lector = null;

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new SQLException("No such lector id: " + id);
                }
                
                lector = new Lector(id, resultSet.getString("first_name"), resultSet.getString("last_name"));
                lector.setSubjects(new SubjectDao().findAllByLectorId(id));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lector;
    }

    public Lector update(Lector lector) {
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setString(1, lector.getFirstName());
            statement.setString(2, lector.getLastName());
            statement.setInt(3, lector.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lector;
    }
    
    public List<Lector> findAll() {

        List<Lector> lectors = new ArrayList<>();
        
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_ALL_QUERY);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                
                Lector lector = new Lector(id, firstName, lastName);
                lector.setSubjects(new SubjectDao().findAllByLectorId(id));
                
                lectors.add(lector);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lectors;
    }
    
    public void appointToSubjectById(Lector lector, int subjectId) {
        
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(APPOINT_TO_SUBJECT_BY_ID_QUERY)) {
            
                statement.setInt(1, lector.getId());
                statement.setInt(2, subjectId);
                statement.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void discardSubjectById(Lector lector, int subjectId) {
        
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(DISCARD_SUBJECT_BY_ID_QUERY)) {
            
            statement.setInt(1, lector.getId());
            statement.setInt(2, subjectId);
            statement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteById(int id) {

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {

            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting failed. No such id: " + id);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
