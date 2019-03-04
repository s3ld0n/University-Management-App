package com.foxminded.university.domain;

import java.io.Serializable;
import java.util.*;

import com.foxminded.university.utils.Period;

public class LectureHall implements Serializable {
    
    private int id;
    private String name;
    private Set<Period> bookedPeriods;

    public LectureHall() {

    }

    public LectureHall(int id, String name) {
        this.id = id;
        this.name = name;
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

    public Set<Period> getBookedPeriods() {
        return bookedPeriods;
    }

    public void setBookedPeriods(Set<Period> bookedPeriods) {
        this.bookedPeriods = bookedPeriods;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
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
        LectureHall other = (LectureHall) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "LectureHall [id=" + id + ", name=" + name + "]";
    }
}
