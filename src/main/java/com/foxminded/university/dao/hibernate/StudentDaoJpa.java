package com.foxminded.university.dao.hibernate;

import java.util.*;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.foxminded.university.domain.hibernate.*;

public class StudentDaoJpa implements StudentDao {
    
    public Student create(Student student) {

        Session session = JpaSessionCreator.getSession(Student.class, Group.class);

        session.beginTransaction();
        session.save(student);
        session.getTransaction().commit();

        return student;
    }

    public Student findById(int id) {

        Session session = JpaSessionCreator.getSession(Student.class, Group.class);
        
        
        session.beginTransaction();
        Student student = session.get(Student.class, id);
        session.getTransaction().commit();
        
        return student;
    }

    public Student update(Student updatedStudent) {

        Session session = JpaSessionCreator.getSession(Student.class, Group.class);

        session.beginTransaction();
        Student currentStudent = session.get(Student.class, updatedStudent.getId());
        
        currentStudent.setFirstName(updatedStudent.getFirstName());
        currentStudent.setLastName(updatedStudent.getLastName());
        currentStudent.setGroup(updatedStudent.getGroup());
        
        session.getTransaction().commit();
        
        return updatedStudent;
    }

    public List<Student> findAll() {

        Session session = JpaSessionCreator.getSession(Student.class, Group.class);

        session.beginTransaction();
        
        @SuppressWarnings("unchecked")
        List<Student> students = session.createQuery("from Student").getResultList();
        students.sort((a, b) -> (a.getId() - b.getId()));
        
        session.getTransaction().commit();

        return students;
    }

    public List<Student> findAllByGroupId(int groupId) {

        Session session = JpaSessionCreator.getSession(Student.class, Group.class);
        
        session.beginTransaction();
        
        @SuppressWarnings("unchecked")
        Query<Student> query = session.createSQLQuery("SELECT students.id AS id, first_name, last_name, "
            + "group_id "
            + "FROM students "
            + "JOIN groups ON students.group_id = groups.id "
            + "WHERE group_id = :groupId").addEntity(Student.class).setParameter("groupId", groupId);
        
        List<Student> students =  query.getResultList();
        
        session.getTransaction().commit();

        return students;
    }

    public void deleteById(int id) {
        Session session = JpaSessionCreator.getSession(Student.class, Group.class);

        session.beginTransaction();
        Student student = session.get(Student.class, id);
        session.delete(student);
        session.getTransaction().commit();
    }
}





