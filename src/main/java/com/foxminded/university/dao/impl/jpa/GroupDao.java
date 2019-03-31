package com.foxminded.university.dao.impl.jpa;

import com.foxminded.university.dao.CrudDao;
import com.foxminded.university.domain.alt_impl.*;

public interface GroupDao extends CrudDao<Group> {
    Group findByName(String name);
}
