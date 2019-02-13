package com.foxminded.university.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import com.foxminded.university.utils.Period;

public class PeriodDao implements Dao<Period> {
    
    private static final String CREATE_QUERY = "INSERT INTO periods (period_start, period_end) VALUES(?, ?)";

    private static final String READ_QUERY = "SELECT period_start, period_end FROM periods WHERE id = ?";
    
    private static final String READ_ALL_BY_LECTURE_HALL_ID_QUERY = "SELECT id, period_start, period_end "
            + "FROM periods "
            + "JOIN lecture_halls_periods AS lhp ON periods.id = lhp.period_id "
            + "WHERE lhp.lecture_hall_id = ?";
    
    private static final String READ_ALL_QUERY = "SELECT id, period_start, period_end FROM periods";
    
    private static final String DELETE_QUERY = "DELETE FROM periods WHERE id = ?";
    
    private static final String UPDATE_QUERY = "UPDATE periods SET period_start = ?, period_end = ? WHERE id = ?";
    
    public Period create(Period period) {

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {

            statement.setTimestamp(1, Timestamp.valueOf(period.getStart()));
            statement.setTimestamp(2, Timestamp.valueOf(period.getEnd()));
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                period.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return period;
    }

    public Period findById(int id) {

        Period period = null;

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (!resultSet.next()) {
                    throw new SQLException("No such period id: " + id);
                }

                period = new Period(id, resultSet.getTimestamp("period_start").toLocalDateTime(),
                        resultSet.getTimestamp("period_end").toLocalDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return period;
    }

    public Period update(Period period) {

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setTimestamp(1, Timestamp.valueOf(period.getStart()));
            statement.setTimestamp(2, Timestamp.valueOf(period.getEnd()));
            statement.setInt(3, period.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return period;
    }
    
    public Set<Period> findAllByLectureHallId(int hallId) {

        Set<Period> set = new HashSet<Period>();

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_ALL_BY_LECTURE_HALL_ID_QUERY)) {

            statement.setInt(1, hallId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    LocalDateTime start = resultSet.getTimestamp("period_start").toLocalDateTime();
                    LocalDateTime end = resultSet.getTimestamp("period_end").toLocalDateTime();
                    set.add(new Period(id, start, end));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return set;
    }
    
    public List<Period> findAll() {

        List<Period> periods = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_ALL_QUERY);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                LocalDateTime periodStart = resultSet.getTimestamp("period_start").toLocalDateTime();
                LocalDateTime periodEnd = resultSet.getTimestamp("period_end").toLocalDateTime();

                periods.add(new Period(id, periodStart, periodEnd));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return periods;
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
