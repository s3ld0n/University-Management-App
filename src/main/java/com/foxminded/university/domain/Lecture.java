package main.java.com.foxminded.university.domain;

import java.io.Serializable;

import main.java.com.foxminded.university.utils.Period;

public class Lecture implements Serializable {

    private int id;
    private Period period;
    private Subject subject;
    private Lector lector;
    private Group group;
    private LectureHall lectureHall;

    public Lecture() {

    }

    public Lecture(int id, Period period, Subject subject, Lector lector, Group group, LectureHall lectureHall) {
        this.id = id;
        this.period = period;
        this.subject = subject;
        this.lector = lector;
        this.group = group;
        this.lectureHall = lectureHall;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
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

    public LectureHall getLectureHall() {
        return lectureHall;
    }

    public void setLectureHall(LectureHall lectureHall) {
        this.lectureHall = lectureHall;
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
        return "Lecture [id=" + id + ", period=" + period + ", subject=" + subject + ", lector=" + lector + ", group="
                + group + "]";
    }
}
