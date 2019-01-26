package com.foxminded.university.utils;

import java.io.Serializable;
import java.util.Date;

public class Period implements Serializable {
    
    private Date start;
    private Date finish;

    public Period() {
        super();
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
}
