package com.foxminded.university.dao;

import java.util.List;

public interface CrudDao<T> {
    
    T create(T t);
    
    T findById(int id);
    
    T update(T t);
    
    List<T> findAll();
    
    void deleteById(int id);
}
