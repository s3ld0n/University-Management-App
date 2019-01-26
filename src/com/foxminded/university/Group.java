package com.foxminded.university;

import java.util.List;

public class Group {
    private int id;
    private String name;
    private List<Student> students;

    public Group(int id, String name, List<Student> students) {
        super();
        this.id = id;
        this.name = name;
        this.students = students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }
    
    public void removeStudent(Student student) {
        students.remove(student);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
}
