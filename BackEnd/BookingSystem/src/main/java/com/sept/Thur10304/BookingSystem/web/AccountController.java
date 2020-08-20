package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.model.Account;
import com.sept.Thur10304.BookingSystem.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.FieldError; // validation errors

import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid; // field validation
import java.util.List;
import java.util.Map; // error map.
import java.util.HashMap; // error map

@RestController
@RequestMapping("/api/Account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("")
    public ResponseEntity<?> createNewAccount(@Valid @RequestBody Account account, BindingResult result) {
        if (result.hasErrors()){
            //Map <String, String> errorMap = new HashMap<>();

            //for (FieldError error : result.getFieldErrors()){
                return new ResponseEntity <List<FieldError>>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
            //}

            //return new ResponseEntity<String>("Invalid Account Object", HttpStatus.BAD_REQUEST);
        }

        if ( allEmails().contains(account.getEmail()))
        {
            // return duplicate email error message
            FieldError fe = new FieldError("", "", null, false, null, null, "Email already registered");
            return new ResponseEntity <FieldError>(fe, HttpStatus.BAD_REQUEST);
        }

        Account account1 = accountService.saveOrUpdateAccount(account);
        return new ResponseEntity<Account>(account, HttpStatus.CREATED);
    }


    @GetMapping("")
    public List<Account> allUsers() {

        return accountService.findAll();
    }

    // for demonstration purposes:
    // this maps http://localhost:8080/api/Account/test
    // and returns the test function
    @GetMapping("test")
    public String test() {

        return accountService.test();
    }

    @GetMapping("emails")
    public List<String> allEmails() {

        return accountService.findAllEmails();
    }

}