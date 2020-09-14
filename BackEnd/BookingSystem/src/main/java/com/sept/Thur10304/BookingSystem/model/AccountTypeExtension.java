package com.sept.Thur10304.BookingSystem.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank; // require non-null fields
import javax.validation.constraints.Size; // require fields of min, max length

import com.fasterxml.jackson.annotation.*;

import ch.qos.logback.core.joran.conditional.ElseAction;

import java.util.Date; // for registration date
import javax.validation.constraints.Pattern; // regex validation
import java.time.ZonedDateTime; // activity timestamp

import com.sept.Thur10304.BookingSystem.model.enums.AccountType;

@MappedSuperclass
public abstract class AccountTypeExtension {

    @Id
    private Long hostId;

    @OneToOne()
    @MapsId
    @JoinColumn(name = "host_id")
    private Account hostAccount;

    protected AccountType accountType;

    public String getAccountTypeName(){
        return this.accountType.toString().toLowerCase();
    }

    public AccountType getAccountType(){
        return this.accountType;
    }
    
    public void setAccountType(AccountType accountType){
        this.accountType = accountType;
    }


    public Account getHostAccount() {
        return this.hostAccount;
    }

    public void setHostAccount(Account hostAccount) {
        this.hostAccount = hostAccount;
    }
    
}