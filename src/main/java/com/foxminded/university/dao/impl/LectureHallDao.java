package com.foxminded.university.dao.impl;

import java.sql.*;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.foxminded.university.dao.ConnectionFactory;
import com.foxminded.university.dao.CrudDao;
import com.foxminded.university.dao.DaoException;
import com.foxminded.university.domain.LectureHall;

public class LectureHallDao implements CrudDao<LectureHall> {
    
    public static final String CREATE_QUERY = "INSERT INTO lecture_halls (name) VALUES(?)";
    
    public static final String READ_QUERY = "SELECT name FROM lecture_halls WHERE lecture_halls.id = ?";
    
    public static final String UPDATE_QUERY = "UPDATE lecture_halls SET name = ? WHERE id = ?";
    
    public static final String READ_ALL_QUERY = "SELECT id, name FROM lecture_halls ORDER BY id";

    private static final String DELETE_QUERY = "DELETE FROM lecture_halls WHERE id = ?";

    private static final Logger log = LogManager.getLogger(LectureHallDao.class.getName());
    
    public LectureHall create(LectureHall lectureHall) {

        log.debug("Creating lecture hall");

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {

            log.debug("Prepared statement was created.");

            log.trace("Setting lecture hall's name: {}", lectureHall.getName());
            statement.setString(1, lectureHall.getName());

            log.debug("Executing prepared statement");
            statement.executeUpdate();

            log.debug("Creating result set");
            try (ResultSet resultSet = statement.getGeneratedKeys()) {

                log.debug("Result set was created. Setting id from DB to lecture object to return");
                resultSet.next();
                lectureHall.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            log.error("Creation of lecture hall has failed.", e);
            throw new DaoException("Creation of lecture hall has failed.", e);
        }

        log.debug("Lecture hall {} was created.", lectureHall);

        return lectureHall;
    }

    public LectureHall findById(int id) {

        log.debug("Finding lecture hall by id: {}", id);

        LectureHall lectureHall = null;

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {

            log.debug("Prepared statement was created. Setting id");
            statement.setInt(1, id);

            log.debug("Creating result set");
            try (ResultSet resultSet = statement.executeQuery()) {

                if (!resultSet.next()) {
                    throw new SQLException("No such lecture hall id: " + id);
                }

                log.debug("Getting name from result set and creating lecture hall object");
                lectureHall = new LectureHall(id, resultSet.getString("name"));

                log.debug("Setting booked periods to lecture hall");
                lectureHall.setBookedPeriods(new PeriodDao().findAllByLectureHallId(id));
            }

        } catch (SQLException e) {
            log.error("Finding lecture hall has failed", e);
            throw new DaoException("Finding lecture hall has failed", e);
        }

        log.debug("Lecture hall with id: {} was found", id);

        return lectureHall;
    }

    public LectureHall update(LectureHall lectureHall) {

        log.debug("Updating lecture hall with id: {}", lectureHall.getId());

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            log.debug("Prepared statement was created. Setting name and id");

            log.trace("Setting lecture hall's name: {}", lectureHall.getName());
            statement.setString(1, lectureHall.getName());

            log.trace("Setting lecture hall's id: {}", lectureHall.getId());
            statement.setInt(2, lectureHall.getId());

            log.debug("Executing prepated statement");
            statement.executeUpdate();

        } catch (SQLException e) {
            log.error("Lecture hall update has failed", e);
            throw new DaoException("Lecture hall update has failed", e);
        }

        log.debug("Lecture hall with id: {} was updated.", lectureHall.getId());

        return lectureHall;
    }

    public List<LectureHall> findAll() {

        log.trace("Finding all lecture halls");
        List<LectureHall> lectureHalls = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_ALL_QUERY)) {

            log.debug("Prepared statement was created. Creating result set");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    log.trace("Getting lecture hall's id and name from result set");

                    log.trace("Getting id");
                    int id = resultSet.getInt("id");

                    log.trace("Getting name");
                    String name = resultSet.getString("name");

                    log.trace("Creating lecture hall object");
                    LectureHall lectureHall = new LectureHall(id, name);

                    log.trace("Setting booked periods");
                    lectureHall.setBookedPeriods(new PeriodDao().findAllByLectureHallId(id));

                    lectureHalls.add(lectureHall);
                    log.trace("Lecture hall was found and added to the list");
                }
            }
        } catch (SQLException e) {
            log.error("Finding all lecture halls has failed", e);
            throw new DaoException("Finding all lecture halls has failed", e);
        }

        log.debug("All {} lecture halls have been found.", lectureHalls.size());

        return lectureHalls;
    }

    public void deleteById(int id) {
        log.debug("Deleting lecture hall by id: {}", id);

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
            log.debug("No lecture hall with such id: {} in database.", id);
        } else {
            log.debug("Lecture hall with id: {} was successfully deleted.", id);
        }
    }
}
