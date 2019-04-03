package com.foxminded.university.dao.impl;

import java.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.domain.Group;

public class GroupDaoImpl implements GroupDao {
    
    private SessionFactory sessionFactory = HibenateUtil.getSessionfactory();
    
    public Group create(Group group) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(group);
        session.getTransaction().commit();
        
        return group;
    }

    public Group findById(int id) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Group group = session.get(Group.class, id);
        session.getTransaction().commit();

        return group;
    }

    public Group findByName(String name) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Group group = (Group) session.createQuery("FROM Group g WHERE g.name = :groupName")
        .setParameter("groupName", name).getResultList().get(0);
        
        session.getTransaction().commit();
        return group;
    }
    
    public Group update(Group updatedGroup) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Group currentGroup = session.get(Group.class, updatedGroup.getId());
        
        currentGroup.setName(updatedGroup.getName());
        
        session.getTransaction().commit();

        return currentGroup;
    }

    public List<Group> findAll() {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        @SuppressWarnings("unchecked")
        List<Group> groups = session.createQuery("from Group").getResultList();
        groups.sort((a, b) -> (a.getId() - b.getId()));
        session.getTransaction().commit();
        
        return groups;
    }
    
    public void deleteById(int id) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        Group group = session.get(Group.class, id);
        session.delete(group);
        session.getTransaction().commit();
    }
}
