package com.foxminded.university.dao.hibernate;

import java.util.List;

import com.foxminded.university.dao.CrudDao;
import com.foxminded.university.domain.hibernate.*;

public interface StudentDao extends CrudDao<Student> {
    
    public List<Student> findAllByGroupId(int groupId);
}
