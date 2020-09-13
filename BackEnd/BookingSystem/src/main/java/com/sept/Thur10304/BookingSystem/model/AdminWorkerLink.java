package com.sept.Thur10304.BookingSystem.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank; // require non-null fields
import javax.validation.constraints.Size; // require fields of min, max length

import com.fasterxml.jackson.annotation.*;

import ch.qos.logback.core.joran.conditional.ElseAction;

import java.util.Date; // for registration date
import javax.validation.constraints.Pattern; // regex validation
import java.time.ZonedDateTime; // activity timestamp

@Entity
public class AdminWorkerLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminWorkerId;

    private Account adminAccount;

    private Account workerAccount;


    public AdminWorkerLink() {
    }

    public Long getAdminWorkerId() {
        return this.adminWorkerId;
    }

    public void setAdminWorkerId(Long adminWorkerId) {
        this.adminWorkerId = adminWorkerId;
    }

    public Account getAdminAccount() {
        return this.adminAccount;
    }

    public void setAdminAccount(Account adminAccount) {
        this.adminAccount = adminAccount;
    }

    public Account getWorkerAccount() {
        return this.workerAccount;
    }

    public void setWorkerAccount(Account workerAccount) {
        this.workerAccount = workerAccount;
    }

}