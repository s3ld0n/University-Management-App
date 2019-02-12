package com.foxminded.university.dao;

import java.sql.*;
import java.util.*;
import com.foxminded.university.domain.LectureHall;

public class LectureHallDao {
    
    public static final String CREATE_QUERY = "INSERT INTO lecture_halls (name) VALUES(?);";
    
    public static final String READ_QUERY = "SELECT name "
            + "FROM lecture_halls "
            + "WHERE lecture_halls.id = ?;";
    
    public static final String UPDATE_QUERY = "UPDATE lecture_halls SET name = ? WHERE id = ?;";
    
    public static final String READ_ALL_QUERY = "SELECT id, name FROM lecture_halls;";

    private static final String DELETE_QUERY = "DELETE FROM lecture_halls WHERE id=?;";
    
    public LectureHall create(LectureHall lectureHall) {

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                        Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, lectureHall.getName());
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                lectureHall.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lectureHall;
    }

    public LectureHall findById(int id) {

        LectureHall lectureHall = null;

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new SQLException("No such lecture hall id: " + id);
                }

                lectureHall = new LectureHall(id, resultSet.getString("name"));

                lectureHall.setBookedPeriods(new PeriodDao().findAllByLectureHallId(id));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lectureHall;
    }

    public LectureHall update(LectureHall lectureHall) {

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setString(1, lectureHall.getName());
            statement.setInt(2, lectureHall.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lectureHall;
    }

    public List<LectureHall> findAll() {

        List<LectureHall> lectureHalls = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_ALL_QUERY);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                LectureHall lectureHall = new LectureHall(id, name);
                lectureHall.setBookedPeriods(new PeriodDao().findAllByLectureHallId(id));

                lectureHalls.add(lectureHall);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lectureHalls;
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
