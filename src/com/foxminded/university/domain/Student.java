package com.foxminded.university.domain;

import java.io.Serializable;

public class Student implements Serializable {
    
    private int id;
    private String firstName;
    private String lastName;
    private String groupName;

    public Student() {
        
    }
    
    public Student(int id, String firstName, String lastName, String groupName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupName = groupName;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getGroup() {
        return groupName;
    }

    public void setGroup(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
    }
}
