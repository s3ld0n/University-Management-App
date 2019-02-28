package com.foxminded.university.dao.crud_dao_implementations;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.foxminded.university.dao.ConnectionFactory;
import com.foxminded.university.dao.DaoException;
import com.foxminded.university.dao.PeriodCrudDao;
import com.foxminded.university.utils.Period;

public class PeriodDao implements PeriodCrudDao {
    
    private static final String CREATE_QUERY = "INSERT INTO periods (period_start, period_end) VALUES(?, ?)";

    private static final String READ_QUERY = "SELECT period_start, period_end FROM periods WHERE id = ?";
    
    private static final String READ_ALL_BY_LECTURE_HALL_ID_QUERY = "SELECT id, period_start, period_end "
            + "FROM periods "
            + "JOIN lecture_halls_periods AS lhp ON periods.id = lhp.period_id "
            + "WHERE lhp.lecture_hall_id = ?";
    
    private static final String READ_ALL_QUERY = "SELECT id, period_start, period_end FROM periods";
    
    private static final String DELETE_QUERY = "DELETE FROM periods WHERE id = ?";
    
    private static final String UPDATE_QUERY = "UPDATE periods SET period_start = ?, period_end = ? WHERE id = ?";

    private static final Logger log = LogManager.getLogger(PeriodDao.class.getName());
    
    public Period create(Period period) {

        log.debug("Creating period");
        
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {

            log.debug("Prepared statement was created. Setting period interval");
            
            log.trace("Setting period's start: {}", period.getStart());
            statement.setTimestamp(1, Timestamp.valueOf(period.getStart()));
            
            log.trace("Setting period's end: {}", period.getEnd());
            statement.setTimestamp(2, Timestamp.valueOf(period.getEnd()));
            
            log.debug("Executing prepared statement");
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                log.debug("Result set was created. Setting id from DB to period object to return");
                resultSet.next();
                period.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            log.error("Creation of lecture has failed.", e);
            throw new DaoException("Creation of lecture has failed.", e);
        }

        log.debug("Period {} was created.", period);

        return period;
    }

    public Period findById(int id) {

        log.debug("Finding period by id: {}", id);
        Period period = null;

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setInt(1, id);
            
            log.debug("Prepared statement was created. Creating result set");
            
            try (ResultSet resultSet = statement.executeQuery()) {

                if (!resultSet.next()) {
                    throw new SQLException("No such period id: " + id);
                }
                
                log.debug("Creting period object using period's start, period's end from result set");
                
                period = new Period(id, resultSet.getTimestamp("period_start").toLocalDateTime(),
                        resultSet.getTimestamp("period_end").toLocalDateTime());
            }
        } catch (SQLException e) {
            log.error("Finding period has failed", e);
            throw new DaoException("Finding period has failed", e);
        }

        log.debug("Period with id: {} was found", id);

        return period;
    }

    public Period update(Period period) {

        log.debug("Updating period with id: {}", period.getId());
        
        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            log.debug("Prepared statement was created. Setting parameters");
            
            log.trace("Setting timestamp of period's start");
            statement.setTimestamp(1, Timestamp.valueOf(period.getStart()));
            
            log.trace("Setting timestamp of period's end");
            statement.setTimestamp(2, Timestamp.valueOf(period.getEnd()));
            
            log.trace("Setting id");
            statement.setInt(3, period.getId());
            
            log.debug("Executing prepated statement");
            statement.executeUpdate();

        } catch (SQLException e) {
            log.error("Period update has failed", e);
            throw new DaoException("Period update has failed", e);
        }

        log.debug("Period with id: {} was updated.", period.getId());

        return period;
    }
    
    public Set<Period> findAllByLectureHallId(int hallId) {

        log.debug("Finding all periods by lecture hall id: {}", hallId);
        
        Set<Period> set = new HashSet<Period>();

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_ALL_BY_LECTURE_HALL_ID_QUERY)) {

            log.debug("Prepared statement was created. Setting id");
            
            statement.setInt(1, hallId);

            log.debug("Creating result set");
            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    log.trace("Getting period's fields from current result set's row");
                    
                    log.trace("Getting id");
                    int id = resultSet.getInt("id");
                    
                    log.trace("Getting period's start and making LocalDateTime object of it");
                    LocalDateTime start = resultSet.getTimestamp("period_start").toLocalDateTime();
                    
                    log.trace("Getting period's end and making LocalDateTime object of it");
                    LocalDateTime end = resultSet.getTimestamp("period_end").toLocalDateTime();
                    
                    log.trace("Adding period to the list");
                    set.add(new Period(id, start, end));
                }
            }
        } catch (SQLException e) {
            log.error("Finding all periods of lecture hall has failed", e);
            throw new DaoException("Finding all periods of lecture hall has failed", e);
        }

        log.debug("All {} periods of lecture hall with id: {} have been found.", set.size(), hallId);

        return set;
    }
    
    public List<Period> findAll() {

        log.debug("Finding all periods");
        
        List<Period> periods = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_ALL_QUERY)) {

            log.debug("Prepared statement was created. Creating result set");
            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    log.trace("Getting period's fields from result set");
            
                    log.trace("Getting id");
                    int id = resultSet.getInt("id");
                    
                    log.trace("Getting period's start and making LocalDateTime object of it");
                    LocalDateTime periodStart = resultSet.getTimestamp("period_start").toLocalDateTime();
                    
                    log.trace("Getting period's end and making LocalDateTime object of it");
                    LocalDateTime periodEnd = resultSet.getTimestamp("period_end").toLocalDateTime();

                    periods.add(new Period(id, periodStart, periodEnd));
                    log.trace("Period was found and added to the list");
                }
            }
        } catch (SQLException e) {
            log.error("Finding all periods has failed", e);
            throw new DaoException("Finding all periods has failed", e);
        }

        log.debug("All {} periods have been found.");

        return periods;
    }
    
    public void deleteById(int id) {
        log.debug("Deleting period by id: {}", id);

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
            log.debug("No period with such id: {} in database.", id);
        } else {
            log.debug("Period with id: {} was successfully deleted.", id);
        }
    }
}
