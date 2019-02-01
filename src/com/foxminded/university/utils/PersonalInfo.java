package com.foxminded.university.utils;

public class PersonalInfo {
    
    private int id;
    private String firstName;
    private String lastName;
    
    public PersonalInfo(int id, String firstName, String lastName) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
