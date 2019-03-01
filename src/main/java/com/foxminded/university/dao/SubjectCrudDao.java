package main.java.com.foxminded.university.dao;

import java.util.List;

import main.java.com.foxminded.university.domain.Subject;

public interface SubjectCrudDao extends CrudDao<Subject> {
    
    public List<Subject> findAllByLectorId(int lectorId);
}
