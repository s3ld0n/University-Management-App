package com.foxminded.university;

import java.util.*;

public class LectureHall {
    private int id;
    private String name;
    private Set<Date> bookedDates;
  
    public LectureHall(int id, String name, Set<Date> bookedDates) {
        this.id = id;
        this.name = name;
        this.bookedDates = bookedDates;
    }
    
    public void bookDate(Date date) {
        bookedDates.add(date);
    }
    
    public void cancelDate(Date date) {
        bookedDates.remove(date);
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
