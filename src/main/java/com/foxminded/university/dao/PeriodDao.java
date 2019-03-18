package com.foxminded.university.dao;

import java.util.Set;

import com.foxminded.university.utils.Period;

public interface PeriodDao {
    
    public Set<Period> findAllByLectureHallId(int hallId);
}
