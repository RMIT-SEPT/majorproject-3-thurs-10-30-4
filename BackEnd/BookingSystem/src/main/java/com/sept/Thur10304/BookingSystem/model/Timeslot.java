package com.sept.Thur10304.BookingSystem.model;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.*;

@Entity
public class Timeslot {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timeslotId;

    private Service_ service;

    //TODO
    // private Long workerId
    
    @NotBlank(message = "Date is required.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @NotBlank(message = "Start time is required.")
    @JsonFormat(pattern = "hh:mm")
    private Date startTime;

    @NotBlank(message = "First name is required.")
    @JsonFormat(pattern = "hh:mm")
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

    public Service_ getService() {
        return this.service;
    }

    public void setService(Service_ service) {
        this.service = service;
    }
}
    
