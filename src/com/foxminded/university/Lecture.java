package com.foxminded.university;

import java.io.Serializable;
import java.util.Date;

public class Lecture implements Serializable {
    
    private Date date;
    private Subject subject;
    private Lector lector;
    private Group group;
    private LectureHall location;

    public Lecture(Date date, Subject subject, Lector lector, Group group, LectureHall location) {
        this.date = date;
        this.subject = subject;
        this.lector = lector;
        this.group = group;
        this.location = location;
    }

    public Lecture() {

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((group == null) ? 0 : group.hashCode());
        result = prime * result + ((lector == null) ? 0 : lector.hashCode());
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + ((subject == null) ? 0 : subject.hashCode());
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
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (group == null) {
            if (other.group != null)
                return false;
        } else if (!group.equals(other.group))
            return false;
        if (lector == null) {
            if (other.lector != null)
                return false;
        } else if (!lector.equals(other.lector))
            return false;
        if (location == null) {
            if (other.location != null)
                return false;
        } else if (!location.equals(other.location))
            return false;
        if (subject == null) {
            if (other.subject != null)
                return false;
        } else if (!subject.equals(other.subject))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Lecture [date=" + date + ", subject=" + subject + ", lector=" + lector + ", group=" + group
                + ", location=" + location + "]";
    }
}
