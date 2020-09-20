package com.sept.Thur10304.BookingSystem.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank; // require non-null fields
import javax.validation.constraints.Size; // require fields of min, max length

import com.fasterxml.jackson.annotation.*;

import ch.qos.logback.core.joran.conditional.ElseAction;

import java.util.Date; // for registration date
import javax.validation.constraints.Pattern; // regex validation
import java.time.ZonedDateTime; // activity timestamp

import com.sept.Thur10304.BookingSystem.model.AccountTypeExtension;

import com.sept.Thur10304.BookingSystem.model.enums.AccountType;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min=1, max=20, message = "First name must be between 1-20 characters.")
    @NotBlank(message = "First name is required.")
    private String firstName;
    @Size(min=1, max=20, message = "Last name must be between 1-20 characters.")
    @NotBlank(message = "Last name is required.")
    private String lastName;
    @Size(min=6, max=20, message = "Password must be between 6-20 characters.")
    @NotBlank(message = "Password is required.")
    private String password;
    @NotBlank(message = "Email is required.")
    @Size(min=3, max=320, message = "Email must be between 3-320 characters.")
    // basic email regex but should be fine for project scope
    @Pattern(regexp="^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$", message="Email format is not valid")
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateCreated;
    // Timestamp of last activity (to make login expire and require new login)
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm")
    private ZonedDateTime activityTimestamp;
    // Login authentication code (normally should use encryption but should be fine for scope of project)
    // We will just generate a unique authtoken which doesn't change so that we don't have to worry
    // about multiple devices being logged into the same account.
    private String userToken;

    // type : customer, employee/worker, admin (hardcoded in datbase)
    // "customer", "employee", "admin"
    // private String type;

    // Not sure if this is needed
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @Transient private AccountTypeExtension accountTypeExtension;

    private AccountType accountType;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String _firstName) {
        this.firstName = _firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String _lastName) {
        this.lastName = _lastName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail()
    {
        return this.email;
    }

    public void setEmail(String email)
    {
        this.email=email;
    }


    public Date getDateCreated()
    {
        return this.dateCreated;
    }
    public void setDateCreated(Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }
  
    public String generateToken()
    {
        // valid token characters
        String tokenChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz"; 
  
        // create StringBuffer size of AlphaNumericString 
        StringBuilder sb = new StringBuilder(16); 
  
        for (int i = 0; i < 16; i++)
        { 
            // generate a random number between 
            // 0 to AlphaNumericString variable length 
            int index = (int)(tokenChars.length() * Math.random()); 
  
            // add Character one by one in end of sb 
            sb.append(tokenChars.charAt(index)); 
        } 
  
        return sb.toString(); 
    }
  
    public AccountType getAccountType(){
        return this.accountType;
    }
    
    public void setAccountType(AccountType accountType){
        this.accountType = accountType;
    }

    @PrePersist
    protected void onCreate()
    {
        this.dateCreated = new Date();
        // generate auth token, which will be a 12 digit alphanumeric string.
        // Todo: Prevent duplicate tokens
        userToken=generateToken();
    }

}