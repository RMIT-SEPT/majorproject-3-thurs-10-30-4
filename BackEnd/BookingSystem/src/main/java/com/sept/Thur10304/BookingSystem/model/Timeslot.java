package com.sept.Thur10304.BookingSystem.model;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.*;

import com.sept.Thur10304.BookingSystem.model.Worker;

import java.math.BigDecimal;

@Entity
public class Timeslot {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timeslotId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "serviceId", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Service_ service;

    @NotNull(message = "Price can not be null")
    @Min(value = 1, message = "Price can not be less than $1")
    @DecimalMax(value = "1000", inclusive = false, message = "Price can not be more than $1000")
    private double price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workerId", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Worker worker;
    
    // @NotBlank(message = "Date is required.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    // @NotBlank(message = "Start time is required.")
    @JsonFormat(pattern = "hh:mm")
    private Date startTime;

    // @NotBlank(message = "End time is required.")
    @JsonFormat(pattern = "hh:mm")
    private Date endTime;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", referencedColumnName = "bookingId")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Booking booking;

    public Long getTimeslotId() {
        return this.timeslotId;
    }

    public void setTimeslotId(Long timeslotId) {
        this.timeslotId = timeslotId;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = BigDecimal.valueOf(price).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public Worker getWorker() {
        return this.worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
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


    public Booking getBooking() {
        return this.booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

}
    
