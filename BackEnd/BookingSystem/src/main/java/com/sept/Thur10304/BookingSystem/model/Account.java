package com.sept.Thur10304.BookingSystem.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank; // require non-null fields
import javax.validation.constraints.Size; // require fields of min, max length

import com.fasterxml.jackson.annotation.*;

import ch.qos.logback.core.joran.conditional.ElseAction;

import java.util.Date; // for registration date

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
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateCreated;


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

    @PrePersist
    protected void onCreate()
    {
        this.dateCreated = new Date();
    }

}