package com.foxminded.university.utils;

import java.io.Serializable;
import java.util.Date;

public class Period implements Serializable {

    private int id;
    private Date start;
    private Date end;

    public Period() {

    }

    public Period(Date start, Date end) {
        this.start = start;
        this.end = end;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
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
        Period other = (Period) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Period [id=" + id + ", start=" + start + ", end=" + end + "]";
    }
}
