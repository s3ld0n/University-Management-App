package com.foxminded.university;

import java.util.*;

public class LectureHall {
    private int id;
    private String name;
    private Set<Date> bookedDates;
  
    public void bookDate(Date date) {
        bookedDates.add(date);
    }
    
    public void cancelDate(Date date) {
        bookedDates.remove(date);
    }

    public LectureHall(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<Date> getBookedDates() {
        return bookedDates;
    }
    
    public int getId() {
        return id;
    }
}
