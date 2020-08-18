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
    private String email;



    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {

        // validation
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {

        // validation
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        
        // validation
        this.password = password;
    }
    
    public String getEmail()
    {
        return this.email;
    }

    public void setEmail(String email)
    {
        
        // validation
        this.email = email;
    }


}