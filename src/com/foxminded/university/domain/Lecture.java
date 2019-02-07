package com.foxminded.university.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Lecture implements Serializable {
    
    private int id;
    private LocalDateTime date;
    private Subject subject;
    private Lector lector;
    private Group group;
    private LectureHall location;

    public Lecture() {

    }

    public Lecture(int id, LocalDateTime date, Subject subject, Lector lector, Group group, LectureHall location) {
        this.id = id;
        this.date = date;
        this.subject = subject;
        this.lector = lector;
        this.group = group;
        this.location = location;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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
        Lecture other = (Lecture) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Lecture [id=" + id + ", date=" + date + ", subject=" + subject + ", lector=" + lector + ", group="
                + group + "]";
    }
}
