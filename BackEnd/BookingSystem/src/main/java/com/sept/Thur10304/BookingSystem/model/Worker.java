package com.sept.Thur10304.BookingSystem.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank; // require non-null fields
import javax.validation.constraints.Size; // require fields of min, max length

import com.fasterxml.jackson.annotation.*;

import ch.qos.logback.core.joran.conditional.ElseAction;

import java.util.Date; // for registration date
import java.util.Set;

import javax.validation.constraints.Pattern; // regex validation
import java.time.ZonedDateTime; // activity timestamp

import com.sept.Thur10304.BookingSystem.model.enums.AccountType;

@Entity
public class Worker extends AccountTypeExtension {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "adminId", nullable = false)
    private Admin admin;

    @JsonIgnore
    @OneToMany(mappedBy = "worker", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Timeslot> assignedTimeslots;


    public Admin getAdmin() {
        return this.admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Set<Timeslot> getAssignedTimeslots() {
        return this.assignedTimeslots;
    }

    public void setAssignedTimeslots(Set<Timeslot> assignedTimeslots) {
        this.assignedTimeslots = assignedTimeslots;
    }

    @PrePersist
    protected void onCreate()
    {
        getAccount().setAccountType(AccountType.WORKER);
    }
}