package com.sept.Thur10304.BookingSystem.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank; // require non-null fields
import javax.validation.constraints.Size; // require fields of min, max length

import com.fasterxml.jackson.annotation.*;

import ch.qos.logback.core.joran.conditional.ElseAction;

import java.util.Date; // for registration date
import java.util.List;

import javax.validation.constraints.Pattern; // regex validation
import java.time.ZonedDateTime; // activity timestamp

import com.sept.Thur10304.BookingSystem.model.enums.AccountType;

@Entity
public class Admin extends AccountTypeExtension {

    @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Worker> workers;
/*
    // TODO when admins are being implemented
    @OneToOne(mappedBy = "admin")
    private Service_ service;
*/
    public List<Worker> getWorkers() {
        return this.workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    @PrePersist
    protected void onCreate()
    {
        getAccount().setAccountType(AccountType.ADMIN);
    }
}