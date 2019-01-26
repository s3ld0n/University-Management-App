package com.foxminded.university;

import java.io.Serializable;
import java.util.*;

public class LectureHall implements Serializable {
    private int id;
    private String name;
    private Set<Date> bookedDates;

    public LectureHall() {
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

    public Set<Date> getBookedDates() {
        return bookedDates;
    }

    public void setBookedDates(Set<Date> bookedDates) {
        this.bookedDates = bookedDates;
    }

}
