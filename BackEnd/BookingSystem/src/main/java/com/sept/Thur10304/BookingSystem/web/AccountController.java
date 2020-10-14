package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.model.Account;
import com.sept.Thur10304.BookingSystem.model.AuthorizationToken;
import com.sept.Thur10304.BookingSystem.model.Worker;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.validation.FieldError; // validation errors

import org.springframework.web.bind.annotation.GetMapping;

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

    @PostMapping("")
    public ResponseEntity<?> createNewAccount(@Valid @RequestBody Account account, BindingResult result) {
        if (result.hasErrors()){

            System.out.println("Create new account.");

            // for now return the first error. No need to handle multiple errors at once.
            for (FieldError error : result.getFieldErrors()){
                System.out.println("ERR1, returning error: " + error.toString());
                //FieldError fe = new FieldError("", "", null, false, null, null, error.toString());
                return new ResponseEntity <FieldError>(error, HttpStatus.BAD_REQUEST);
            }

            //Map <String, String> errorMap = new HashMap<>();
            //for (FieldError error : result.getFieldErrors()){
                //return new ResponseEntity <List<FieldError>>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
            //}
            //return new ResponseEntity<String>("Invalid Account Object", HttpStatus.BAD_REQUEST);
        }

        if ( allEmails().contains(account.getEmail()))
        {
            // return duplicate email error message
            FieldError fe = new FieldError("", "", null, false, null, null, "Errr Email already registered: "+account.getEmail());
            System.out.println("ERR2, returning error: " + fe.toString());
            return new ResponseEntity <FieldError>(fe, HttpStatus.BAD_REQUEST);
        }
        System.out.println("Account created successfully.");
        Account account1 = accountService.saveCustomer(account);
        return new ResponseEntity<Account>(account1, HttpStatus.CREATED);
    }

    @PostMapping("/saveworker/{adminId}")
    public ResponseEntity<?> createNewWorker(@Valid @RequestBody Account account, @PathVariable Long adminId, BindingResult result) {
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
        try {
            Account account1 = accountService.saveWorker(account, adminId);
            return new ResponseEntity<Account>(account, HttpStatus.CREATED);
        } catch (Exception e){
            FieldError fe = new FieldError("", "", null, false, null, null, e.getMessage());
            return new ResponseEntity <FieldError>(fe, HttpStatus.BAD_REQUEST);
        }
    }

    // TODO remove this, used for testing
    @PostMapping("/saveadmin")
    public ResponseEntity<?> createNewAdmin(@Valid @RequestBody Account account, BindingResult result) {
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
        try {
            Account account1 = accountService.saveAdmin(account);
            return new ResponseEntity<Account>(account, HttpStatus.CREATED);
        } catch (Exception e){
            FieldError fe = new FieldError("", "", null, false, null, null, e.getMessage());
            return new ResponseEntity <FieldError>(fe, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    public List<Account> allUsers() {
        return accountService.findAll();
    }

    @GetMapping("/getworkersbyadmin/{adminId}")
    public ResponseEntity<?> getWorkersByAdmin(@Valid @PathVariable Long adminId) {
        try {
            List<Worker> workers = accountService.getWorkersByAdminId(adminId);
            return new ResponseEntity<List<Worker>>(workers, HttpStatus.CREATED);
        } catch (Exception e){
            FieldError fe = new FieldError("", "", null, false, null, null, e.getMessage());
            return new ResponseEntity <FieldError>(fe, HttpStatus.BAD_REQUEST);
        }
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
            Account loginAccount = accountService.getAccount(account.getEmail(), account.getPassword());
            // login authorised
            // presumably we send an authentication token or do a redirect
            //Account account1 = accountService.saveOrUpdateAccount(account);
            // HTTP Status for successful login should be 200 OK
            return new ResponseEntity<Account>(loginAccount, HttpStatus.OK);
        }
        // login not authorised
        // return failed login message
        FieldError fe = new FieldError("", "", null, false, null, null, "Invalid login credentials");
        // HTTP Status for failed login is 401 Unauthorized
        return new ResponseEntity <FieldError>(fe, HttpStatus.UNAUTHORIZED);
    }


    // Frontend POSTs their JWT. Backend returns the user Account.
    @PostMapping("Profile")
    public ResponseEntity<?> authoriseToken(@Valid @RequestBody AuthorizationToken token, BindingResult result) {
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
        Account authorisedAccount = accountService.authoriseJWT(token);

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
    public ResponseEntity<?> deauthoriseToken(@Valid @RequestBody AuthorizationToken token, BindingResult result) {
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
        if (accountService.deauthoriseJWT(token))
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

    @GetMapping("/adminanalytics/{adminId}")
    public ResponseEntity<?> adminAnalytics(@Valid @PathVariable Long adminId) {

        try{
            Map<String, Object> adminAnalytics = accountService.getAdminAnalytics(adminId);
            return new ResponseEntity<Map<String, Object>>(adminAnalytics, HttpStatus.OK);
        } catch (Exception e){
            FieldError fe = new FieldError(e.getClass().getName(), "", null, false, null, null, e.getMessage());
            return new ResponseEntity<FieldError>(fe, HttpStatus.BAD_REQUEST);
        }
    }

}