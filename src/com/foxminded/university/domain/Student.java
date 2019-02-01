package com.foxminded.university.domain;

import java.io.Serializable;

import com.foxminded.university.utils.PersonalInfo;

public class Student implements Serializable {
    
    private int id;
    private String firstName;
    private String lastName;
    private String group;

    public Student() {
        
    }
    
    public Student(PersonalInfo personalInfo, String group) {
        this.id = personalInfo.getId();
        this.firstName = personalInfo.getFirstName();
        this.lastName = personalInfo.getLastName();
        this.group = group;
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
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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
        return "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName +  ", group=" + group + "]";
    }
}
