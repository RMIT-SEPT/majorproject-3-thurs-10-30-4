package com.sept.Thur10304.BookingSystem.model;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Timeslot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timeslotId;

    //TODO
    // private Long serviceId

    //TODO
    // private Long workerId
    
    private Date date;

    private Date startTime;

    private Date endTime;

    public Long getTimeslotId() {
        return this.timeslotId;
    }

    public void setTimeslotId(Long timeslotId) {
        this.timeslotId = timeslotId;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}