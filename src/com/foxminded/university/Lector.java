package com.foxminded.university;

import java.util.List;

public class Lector {
    private int id;
    private String firstName;
    private String lastName;
    private List<Subject> subjects;

    public Lector(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }
    
    public void removeSubject(Subject subject) {
        subjects.remove(subject);
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public int getId() {
        return id;
    }
}
