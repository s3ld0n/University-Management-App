package com.foxminded.university.dao.impl;

import java.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.foxminded.university.dao.StudentDao;
import com.foxminded.university.domain.*;

public class StudentDaoImpl implements StudentDao {

    private SessionFactory sessionFactory = HibenateUtil.getSessionfactory();
    
    public Student create(Student student) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.save(student);
        session.getTransaction().commit();

        return student;
    }

    public Student findById(int id) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Student student = session.get(Student.class, id);
        session.getTransaction().commit();
        
        return student;
    }

    public Student update(Student updatedStudent) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Student currentStudent = session.get(Student.class, updatedStudent.getId());
        
        currentStudent.setFirstName(updatedStudent.getFirstName());
        currentStudent.setLastName(updatedStudent.getLastName());
        currentStudent.setGroup(updatedStudent.getGroup());
        
        session.getTransaction().commit();
        
        return updatedStudent;
    }

    public List<Student> findAll() {
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        @SuppressWarnings("unchecked")
        List<Student> students = session.createQuery("from Student").getResultList();
        students.sort((a, b) -> (a.getId() - b.getId()));
        
        session.getTransaction().commit();

        return students;
    }

    public List<Student> findAllByGroupId(int groupId) {
        
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        
        @SuppressWarnings("unchecked")
        Query<Student> query = session.createSQLQuery("SELECT students.id, first_name, last_name, "
            + "group_id "
            + "FROM students "
            + "JOIN groups ON students.group_id = groups.id "
            + "WHERE group_id = :groupId").addEntity(Student.class).setParameter("groupId", groupId);
        
        List<Student> students =  query.getResultList();
        
        session.getTransaction().commit();

        return students;
    }

    public void deleteById(int id) {

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Student student = session.get(Student.class, id);
        session.delete(student);
        session.getTransaction().commit();
    }
}
