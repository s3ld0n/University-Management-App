package com.foxminded.university.dao.hibernate;

import com.foxminded.university.dao.CrudDao;
import com.foxminded.university.domain.hibernate.*;

public interface GroupDao extends CrudDao<Group> {
    Group findByName(String name);
}
