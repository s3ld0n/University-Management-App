package com.foxminded.university.dao.crud_dao_implementations;

import java.sql.*;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.foxminded.university.dao.ConnectionFactory;
import com.foxminded.university.dao.DaoException;
import com.foxminded.university.dao.StudentCrudDao;
import com.foxminded.university.domain.*;

public class StudentDao implements StudentCrudDao {

    private final static String CREATE_QUERY = "INSERT INTO students (first_name, last_name, group_id) "
            + "VALUES(?, ?, (SELECT id FROM groups WHERE name = ?))";
    
    private final static String READ_QUERY = "SELECT students.id, first_name, last_name, groups.name AS group_name "
            + "FROM students "
            + "JOIN groups ON students.group_id = groups.id "
            + "WHERE students.id = ?";
    
    private final static String READ_ALL_QUERY = "SELECT students.id AS id, first_name, last_name, groups.name AS group_name "
            + "FROM students "
            + "JOIN groups ON students.group_id = groups.id";
    
    private final static String UPDATE_QUERY = "UPDATE students "
            + "SET first_name = ?, last_name = ?, "
            + "group_id = (SELECT id FROM groups WHERE name = ?) "
            + "WHERE id = ?";
    
    private final static String DELETE_QUERY = "DELETE FROM students WHERE id = ?";
    
    private final static String READ_ALL_BY_GROUP_ID_QUERY = "SELECT students.id AS id, first_name, last_name, "
            + "groups.name AS group_name "
            + "FROM students "
            + "JOIN groups ON students.group_id = groups.id "
            + "WHERE group_id = ?";
    
    private static Logger log = LogManager.getLogger(StudentDao.class.getName());
    
    public Student create(Student student) {
        
        log.debug("Creating a student: {}", student);
        
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {
            
            log.debug("Prepared statement was created. Setting parameters");
            
            log.trace("Setting first name of the student");
            statement.setString(1, student.getFirstName());
            
            log.trace("Setting last name of the student");
            statement.setString(2, student.getLastName());
            
            log.trace("Setting group of the student");
            statement.setString(3, student.getGroup());

            log.debug("Executing prepared statement");
            
            statement.executeUpdate();

            log.debug("Creating result set");
            
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                log.debug("Result set was created. Setting id from DB to student object to return");
                resultSet.next();
                student.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            log.error("Creation of student has failed.", e);
            throw new DaoException("Creation of student has failed.", e);
        }
        
        log.debug("Student {} was created.", student);
        
        return student;
    }

    public Student findById(int id) {
        
        log.debug("Finding student by id: {}", id);
        
        Student student = null;

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            
            log.debug("Prepared statement was created. Setting parameters");
            
            statement.setInt(1, id);

            log.debug("Creating result set");
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new SQLException("No such student id: " + id);
                }
                
                log.debug("Creating a student object");
                
                student = new Student(id, resultSet.getString("first_name"), resultSet.getString("last_name"),
                        resultSet.getString("group_name"));
            }
        } catch (SQLException e) {
            log.error("Finding student has failed", e);
            throw new DaoException("Finding student has failed", e);
        }

        log.debug("Student was found.");

        return student;
    }

    public Student update(Student student) {
        
        log.debug("Updating student");

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            log.debug("Prepared statement was created. Setting parameters");
            
            log.trace("Setting first name of the student");
            statement.setString(1, student.getFirstName());
            
            log.trace("Setting last name of the student");
            statement.setString(2, student.getLastName());
            
            log.trace("Setting group of the student");
            statement.setString(3, student.getGroup());
            
            log.trace("Setting id of the student");
            statement.setInt(4, student.getId());

            log.debug("Executing prepared statement");
            
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Student update has failed", e);
            throw new DaoException("Student update has failed", e);
        }

        log.debug("Student was updated.");
        
        return student;
    }

    public List<Student> findAll() {

        log.debug("Finding all students");
        
        List<Student> students = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_ALL_QUERY)) {
            
            log.debug("Prepared statement was created. Creating result set");
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    log.debug("Getting student fields from result set");
                    
                    log.trace("Getting id");
                    int id = resultSet.getInt("id");
                    
                    log.trace("Getting first name");
                    String firstName = resultSet.getString("first_name");
                    
                    log.trace("Getting last name");
                    String lastName = resultSet.getString("last_name");
                    
                    log.trace("Getting group name");
                    String group = resultSet.getString("group_name");

                    students.add(new Student(id, firstName, lastName, group));
                    log.debug("Student was created and added to list.");
                }
            }

        } catch (SQLException e) {
            log.error("Finding all students has failed", e);
            throw new DaoException("Finding all students has failed", e);
        }

        log.debug("All students have been found.");
        
        return students;
    }

    public List<Student> findAllByGroupId(int groupId) {
        
        log.debug("Finding all students by group id: {}", groupId);
        
        List<Student> students = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_ALL_BY_GROUP_ID_QUERY)) {

            log.debug("Prepared statement created. Setting parameter");
            
            statement.setInt(1, groupId);

            log.debug("Creating result set");
            
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    do  {
                        log.debug("Getting student fields from result set");
                        
                        log.trace("Getting id");
                        int id = resultSet.getInt("id");
                        
                        log.trace("Getting first name");
                        String firstName = resultSet.getString("first_name");
                        
                        log.trace("Getting last name");
                        String lastName = resultSet.getString("last_name");
                        
                        log.trace("Getting group name");
                        String group = resultSet.getString("group_name");

                        students.add(new Student(id, firstName, lastName, group));
                        log.debug("Student was created and added to list.");
                    } while (resultSet.next());

                } else {
                    throw new SQLException("No such group id: " + groupId + " or the group is empty.");   
                }
            }
        } catch (SQLException e) {
            log.error("Finding students by group id has failed", e);
            throw new DaoException("Finding students by group id has failed", e);
        }

        log.debug("All students by group id have been found.");
        
        return students;
    }

    public void deleteById(int id) {

        log.debug("Deleting student by id: {}", id);
        
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {

            log.debug("Prepared statement created. Setting parameter");
            
            statement.setInt(1, id);
            
            log.debug("Executing statement");
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deletion has failed. No such id: " + id);
            }

        } catch (SQLException e) {
            log.error("Deletion has failed", e);
            throw new DaoException("Deletion has failed", e);
        }
        
        log.debug("Student was successfully deleted.");
    }
}
