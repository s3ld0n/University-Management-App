package com.foxminded.university.utils;

import java.io.Serializable;
import java.util.Date;

public class Period implements Serializable {

    private int id;
    private Date start;
    private Date finish;

    public Period() {

    }

    public Period(Date start, Date finish) {
        this.start = start;
        this.finish = finish;
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

    public Date getFinish() {
        return finish;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
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
        return "Period [id=" + id + ", start=" + start + ", finish=" + finish + "]";
    }
}
