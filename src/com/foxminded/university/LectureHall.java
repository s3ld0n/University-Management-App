package com.foxminded.university;

import java.io.Serializable;
import java.util.*;

public class LectureHall implements Serializable {
    
    private int id;
    private String name;
    private Set<Date> bookedDates;

    public LectureHall(int id, String name, Set<Date> bookedDates) {
        this.id = id;
        this.name = name;
        this.bookedDates = bookedDates;
    }

    public LectureHall() {

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bookedDates == null) ? 0 : bookedDates.hashCode());
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        LectureHall other = (LectureHall) obj;
        if (bookedDates == null) {
            if (other.bookedDates != null)
                return false;
        } else if (!bookedDates.equals(other.bookedDates))
            return false;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "LectureHall [id=" + id + ", name=" + name + ", bookedDates=" + bookedDates + "]";
    }
}
