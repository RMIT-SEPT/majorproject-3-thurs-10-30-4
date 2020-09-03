package com.sept.Thur10304.BookingSystem.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import com.fasterxml.jackson.annotation.*;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @OneToOne(mappedBy = "booking")
    private Timeslot timeslot;

    // TODO change to customer
    // @OneToOne(targetEntity = Account.class, cascade = CascadeType.ALL)
    // private Account customer;

    @JsonFormat(pattern = "yyyy-MM-dd|hh:mm")
    private Date dateCreated;

    public Long getBookingId() {
        return this.bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    @PrePersist
    protected void onCreate()
    {
        this.dateCreated = new Date();
    }
}