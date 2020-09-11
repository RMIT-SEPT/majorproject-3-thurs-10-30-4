package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.model.Account;
import com.sept.Thur10304.BookingSystem.model.JWT;
import com.sept.Thur10304.BookingSystem.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.FieldError; // validation errors

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // user details encryption

import javax.validation.Valid; // field validation
import java.util.List;
import java.util.Map; // error map.
import java.util.HashMap; // error map

@RestController
@RequestMapping("/api/Account")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AccountController {

    @Autowired
    private AccountService accountService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("Register")
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

        // encrypt the user password and store in database
        //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        Account account1 = accountService.saveOrUpdateAccount(account);
        return new ResponseEntity<Account>(account1, HttpStatus.CREATED);
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

    // to login, make sure the account details match up with email and password.
    // I assume we can just construct an account object with nulls for everything except email and password
    // Todo: Add password check
    @PostMapping("Login")
    public ResponseEntity<?> loginAccount(@Valid @RequestBody Account account, BindingResult result) {
        if (result.hasErrors()){
            // Todo: return cleaner error codes using tutorial
            // https://web.microsoftstream.com/video/a2eee04a-9636-45c7-aa67-47d934e76acf @ 4:21
            //Map <String, String> errorMap = new HashMap<>();

            //for (FieldError error : result.getFieldErrors()){
                return new ResponseEntity <List<FieldError>>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
            //}

            //return new ResponseEntity<String>("Invalid Account Object", HttpStatus.BAD_REQUEST);
        }

        if (accountService.verifyAccount(account.getEmail(), account.getPassword()))
        {
            // login authorised
            // presumably we send an authentication token or do a redirect
            //Account account1 = accountService.saveOrUpdateAccount(account);
            // HTTP Status for successful login should be 200 OK
            return new ResponseEntity<Account>(account, HttpStatus.OK);
        }
        // login not authorised
        // return failed login message
        FieldError fe = new FieldError("", "", null, false, null, null, "Invalid login credentials");
        // HTTP Status for failed login is 401 Unauthorized
        return new ResponseEntity <FieldError>(fe, HttpStatus.UNAUTHORIZED);
    }


    // Frontend POSTs their JWT. Backend returns the user Account.
    @PostMapping("Profile")
    public ResponseEntity<?> authoriseJWT(@Valid @RequestBody JWT jwt, BindingResult result) {
        if (result.hasErrors()){
            // Todo: return cleaner error codes using tutorial
            // https://web.microsoftstream.com/video/a2eee04a-9636-45c7-aa67-47d934e76acf @ 4:21
            //Map <String, String> errorMap = new HashMap<>();

            //for (FieldError error : result.getFieldErrors()){
                return new ResponseEntity <List<FieldError>>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
            //}

            //return new ResponseEntity<String>("Invalid Account Object", HttpStatus.BAD_REQUEST);
        }
        // for now this simply returns the first account in the repo, for testing
        // it returns null if repo is empty
        Account authorisedAccount = accountService.authoriseJWT(jwt);

        if (authorisedAccount!=null)
        {
            //jwt authorisation failed
            FieldError fe = new FieldError("", "", null, false, null, null, "jwt authorisation failed");
            // 401 Unauthorized
            return new ResponseEntity <FieldError>(fe, HttpStatus.UNAUTHORIZED);
        }
        // 200 OK and return matching account
        return new ResponseEntity<Account>(authorisedAccount, HttpStatus.OK);
    }

    // Frontend POSTs JWT to logout. Backend will delete the token, requiring user to login again.
    @PostMapping("Logout")
    public ResponseEntity<?> deauthoriseJWT(@Valid @RequestBody JWT jwt, BindingResult result) {
        if (result.hasErrors()){
            // Todo: return cleaner error codes using tutorial
            // https://web.microsoftstream.com/video/a2eee04a-9636-45c7-aa67-47d934e76acf @ 4:21
            //Map <String, String> errorMap = new HashMap<>();

            //for (FieldError error : result.getFieldErrors()){
                return new ResponseEntity <List<FieldError>>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
            //}

            //return new ResponseEntity<String>("Invalid Account Object", HttpStatus.BAD_REQUEST);
        }

        // pass the jwt token to deauthorise
        // if the token doesn't exist or is wrong, function will return false
        if (accountService.deauthoriseJWT(jwt))
        {
            // 200 OK, jwt deauthorised
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
        // jwt deauthorisation failed
        // there is probably nothing we can do about this except ignore it
        FieldError fe = new FieldError("", "", null, false, null, null, "jwt deauthorisation failed");
        // 417 expectatoin failed (expected valid jwt to deauthorise)
        return new ResponseEntity <FieldError>(fe, HttpStatus.EXPECTATION_FAILED);
    }
}