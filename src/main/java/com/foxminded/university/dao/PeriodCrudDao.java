package main.java.com.foxminded.university.dao;

import java.util.Set;

import main.java.com.foxminded.university.utils.Period;

public interface PeriodCrudDao {
    
    public Set<Period> findAllByLectureHallId(int hallId);
}
