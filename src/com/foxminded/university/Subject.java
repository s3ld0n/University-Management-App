package com.foxminded.university;

import java.io.Serializable;

public class Subject implements Serializable {
    
    private int id;
    private String name;

    public Subject() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
