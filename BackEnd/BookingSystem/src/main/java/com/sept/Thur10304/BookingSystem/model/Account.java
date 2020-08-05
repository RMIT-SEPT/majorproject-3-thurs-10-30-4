package com.sept.Thur10304.BookingSystem.model;

import javax.persistence.*;
// import javax.validation.constraints.NotBlank;
// import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.*;
import java.util.Date;

@Entity
public class Account {

    // Still needs more variables (email,etc)
    // Also needs validation

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}