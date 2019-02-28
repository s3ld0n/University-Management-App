package com.foxminded.university.dao.crud_dao_implementations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.foxminded.university.dao.ConnectionFactory;
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

    public Subject create(Subject subject) {
        
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS)){
            
            statement.setString(1, subject.getName());
            statement.executeUpdate();
            
            try (ResultSet resultSet = statement.getGeneratedKeys()){
                resultSet.next();
                subject.setId(resultSet.getInt(1));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return subject;
    }
    
    public Subject findById(int id) {
        
        Subject subject = null;
        
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new SQLException("No such subject id: " + id);
                }

                subject = new Subject(id, resultSet.getString("name"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return subject;
    }
    
    public Subject update(Subject subject) {

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setString(1, subject.getName());
            statement.setInt(2, subject.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subject;
    }
    
    public List<Subject> findAll() {

        List<Subject> subjects = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_ALL_QUERY);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                subjects.add(new Subject(id, name));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjects;
    }

    public List<Subject> findAllByLectorId(int lectorId) {

        List<Subject> subjects = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_ALL_BY_LECTOR_ID_QUERY)) {

            statement.setInt(1, lectorId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");

                    subjects.add(new Subject(id, name));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjects;
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
