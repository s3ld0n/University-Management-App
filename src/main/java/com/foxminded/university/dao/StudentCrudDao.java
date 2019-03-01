package main.java.com.foxminded.university.dao;

import java.util.List;

import main.java.com.foxminded.university.domain.Student;

public interface StudentCrudDao extends CrudDao<Student> {
    
    public List<Student> findAllByGroupId(int groupId);
}
