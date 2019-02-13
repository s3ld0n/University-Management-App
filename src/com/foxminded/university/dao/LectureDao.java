package com.foxminded.university.dao;

import java.sql.*;
import java.util.*;
import com.foxminded.university.domain.*;
import com.foxminded.university.utils.Period;

public class LectureDao implements Dao<Lecture> {
    
    private static final String CREATE_QUERY = "INSERT INTO lectures (subject_id, lector_id, group_id, lecture_hall_id, "
            + "period_id) VALUES (?, ?, ?, ?, ?)";
    
    private static final String READ_QUERY = "SELECT subject_id, group_id, lector_id, lecture_hall_id, period_id "
            + "FROM lectures WHERE id = ?";

    private static final String UPDATE_QUERY = "UPDATE lectures SET period_id = ?, subject_id = ?, lector_id = ?, "
            + "group_id = ?, lecture_hall_id = ? "
            + "WHERE id = ?";

    private static final String READ_ALL_QUERY = "SELECT id, subject_id, group_id, lector_id, lecture_hall_id, period_id "
            + "FROM lectures";

    private static final String DELETE_QUERY = "DELETE FROM lectures WHERE id = ?";
    
    public Lecture create(Lecture lecture) {

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {

            statement.setInt(1, lecture.getSubject().getId());
            statement.setInt(2, lecture.getGroup().getId());
            statement.setInt(3, lecture.getLector().getId());
            statement.setInt(4, lecture.getLectureHall().getId());
            statement.setInt(5, lecture.getPeriod().getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lecture;
    }

    public Lecture findById(int id) {

        Lecture lecture = null;

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (!resultSet.next()) {
                    throw new SQLException("No such lecture id: " + id);
                }

                int groupId = resultSet.getInt("group_id");
                int lectorId = resultSet.getInt("lector_id");
                int lectureHallId = resultSet.getInt("lecture_hall_id");

                PeriodDao periodDao = new PeriodDao();
                SubjectDao subjectDao = new SubjectDao();

                Period period = periodDao.findById(resultSet.getInt("period_id"));
                Subject subject = subjectDao.findById(resultSet.getInt("subject_id"));

                Group group = new GroupDao().findById(groupId);
                group.setStudents(new StudentDao().findAllByGroupId(groupId));

                Lector lector = new LectorDao().findById(lectorId);
                lector.setSubjects(subjectDao.findAllByLectorId(lectorId));

                LectureHall lectureHall = new LectureHallDao().findById(lectureHallId);
                lectureHall.setBookedPeriods(periodDao.findAllByLectureHallId(lectureHallId));

                lecture = new Lecture(id, period, subject, lector, group, lectureHall);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lecture;
    }

    public Lecture update(Lecture lecture) {

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setInt(1, lecture.getPeriod().getId());
            statement.setInt(2, lecture.getSubject().getId());
            statement.setInt(3, lecture.getLector().getId());
            statement.setInt(4, lecture.getGroup().getId());
            statement.setInt(5, lecture.getLectureHall().getId());
            statement.setInt(6, lecture.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lecture;
    }

    public List<Lecture> findAll() {

        List<Lecture> lectures = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement(READ_ALL_QUERY);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                
                PeriodDao periodDao = new PeriodDao(); 
                Period period = periodDao.findById(resultSet.getInt("period_id"));
                
                SubjectDao subjectDao = new SubjectDao();
                Subject subject = subjectDao.findById(resultSet.getInt("subject_id"));

                Lector lector = new LectorDao().findById(resultSet.getInt("lector_id"));
                lector.setSubjects(subjectDao.findAllByLectorId(resultSet.getInt("lector_id")));

                Group group = new GroupDao().findById(resultSet.getInt("group_id"));
                group.setStudents(new StudentDao().findAllByGroupId(resultSet.getInt("group_id")));

                LectureHall lectureHall = new LectureHallDao().findById(resultSet.getInt("lecture_hall_id"));
                lectureHall
                        .setBookedPeriods(periodDao.findAllByLectureHallId(resultSet.getInt("lecture_hall_id")));

                Lecture lecture = new Lecture(id, period, subject, lector, group, lectureHall);
                lectures.add(lecture);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lectures;
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
