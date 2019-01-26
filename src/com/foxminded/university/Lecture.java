package com.foxminded.university;

import java.io.Serializable;
import java.util.Date;

public class Lecture implements Serializable {
    private Date date;
    private Subject subject;
    private Lector lector;
    private Group group;
    private LectureHall location;

    public Lecture() {
        super();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Lector getLector() {
        return lector;
    }

    public void setLector(Lector lector) {
        this.lector = lector;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public LectureHall getLocation() {
        return location;
    }

    public void setLocation(LectureHall location) {
        this.location = location;
    }
}
