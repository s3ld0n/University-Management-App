package com.foxminded.university.dao;

import com.foxminded.university.domain.Lector;

public interface LectorDao extends CrudDao<Lector> {
    
    public void addSubjectById(Lector lector, int subjectId);
    
    public void removeSubjectById(Lector lector, int subjectId);
}
