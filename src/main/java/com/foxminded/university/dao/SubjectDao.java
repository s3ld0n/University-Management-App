package com.foxminded.university.dao;

import java.util.List;

import com.foxminded.university.domain.Subject;

public interface SubjectDao extends CrudDao<Subject> {
    
    public List<Subject> findAllByLectorId(int lectorId);
}
