package com.sept.Thur10304.BookingSystem.model;

import com.fasterxml.jackson.annotation.*;

public enum AccountType{
    CUSTOMER,
    WORKER,
    ADMIN
}

public abstract class AccountTypeExtension {

    @Id
    private Long Id;

    protected AccountType accountType;

    @OneToOne
    @MapsId
    protected Account hostAccount;

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