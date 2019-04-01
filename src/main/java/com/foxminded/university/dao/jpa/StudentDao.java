package com.foxminded.university.dao.jpa;

import java.util.List;

import com.foxminded.university.dao.CrudDao;
import com.foxminded.university.domain.alt_impl.*;

public interface StudentDao extends CrudDao<Student> {
    
    public List<Student> findAllByGroupId(int groupId);
}
