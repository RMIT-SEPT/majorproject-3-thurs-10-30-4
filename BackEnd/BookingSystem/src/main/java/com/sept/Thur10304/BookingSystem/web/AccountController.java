package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.model.Account;
import com.sept.Thur10304.BookingSystem.model.Worker;
import com.sept.Thur10304.BookingSystem.services.AccountService;
import com.sept.Thur10304.BookingSystem.validator.AccountValidator; //JWT
import com.sept.Thur10304.BookingSystem.payload.JWTLoginSucessReponse; // JWT
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.validation.FieldError; // validation errors

import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid; // field validation
import java.util.List;
import java.util.Map; // error map.
import java.util.HashMap; // error map

//import com.rmit.sept.turtorial.demo.payload.JWTLoginSucessReponse;
//import com.rmit.sept.turtorial.demo.payload.LoginRequest;
import com.sept.Thur10304.BookingSystem.security.JwtTokenProvider;
//import com.rmit.sept.turtorial.demo.services.MapValidationErrorService;
//import com.rmit.sept.turtorial.demo.services.UserService;
//import com.rmit.sept.turtorial.demo.validator.UserValidator;

// JWT and Spring security Authentication/authorisation
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.sept.Thur10304.BookingSystem.security.SecurityConstant.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/Account")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountValidator accountValidator;

    // Register
    @PostMapping("")
    public ResponseEntity<?> createNewAccount(@Valid @RequestBody Account account, BindingResult result) {
        if (result.hasErrors()){
            accountValidator.validate(account,result);

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
    public ResponseEntity<?> createNewWorker(@Valid @RequestBody Account account, @PathVariable Long adminId,
      @RequestParam(name="token", required=true) String token, BindingResult result) {
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

        // Check that token is valid and id of login token is same as admin id
        if (!(tokenProvider.validateToken(token))){
            FieldError fe = new FieldError("", "", null, false, null, null, "Invalid token");
            return new ResponseEntity <FieldError>(fe, HttpStatus.BAD_REQUEST);
        } else if (tokenProvider.getUserIdFromJWT(token) != adminId.longValue()){
            FieldError fe = new FieldError("", "", null, false, null, null, "Not logged in as same account as admin id");
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


    @GetMapping("emails")
    public List<String> allEmails() {

        return accountService.findAllEmails();
    }

    // Authentication for login
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

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

        // new Spring security authentication
        // first authenticate email and password,
        // then generate JWT.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    account.getEmail(),
                    account.getPassword()
                    //account.getId(),
                    //account.getEmail()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String rawtoken = tokenProvider.generateToken(authentication);
        String jwt = TOKEN_PREFIX +  rawtoken;

        if (tokenProvider.validateToken(rawtoken))
        {
            System.out.println("Token validated");
            //return ResponseEntity.ok("VALID: "+jwt);
        }
        else
        {
            System.out.println("Token did not validate");
            //return ResponseEntity.ok("INVALID:"+jwt);
        }

        // Try getting user ID from token
        long userID = tokenProvider.getUserIdFromJWT(rawtoken);
        System.out.println("Token matches with user ID: "+userID);

        // I don't know why we are prefixing the token (except perhaps so frontend knows it got it)
        // So I am just returning the raw token because that's what you want to be passing anyway.
        return ResponseEntity.ok(new JWTLoginSucessReponse(true, rawtoken));
    }

    // TODO: Integrate this code with all FrontEnd mappings.
    // You pass the raw JWT to this mapping and it will return the Account tied to it.
    // This expects the token and nothing else, however if there are { and " characters
    // we can just strip them and we should be fine.

    //Note that Frontend can POST the JWT to Authenticate at any time to get the Account details
    @PostMapping("Authenticate")
    public ResponseEntity<?> authenticateAccount(@Valid @RequestBody String token, BindingResult result) {
        System.out.println("Authenticate called");

        if (result.hasErrors()){
                return new ResponseEntity <List<FieldError>>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        System.out.println("Authenticating: "+token);
        // Passing the raw token seems to work, for example just posting:
        // eyJhbGciOiJIUzUxMiJ9.eyJpZCI6IjEiLCJleHAi...
        // I'm not sure if this is possible using AXIOS.

        try
        {
            if ( tokenProvider.validateToken(token) )
            {
                // Token validated okay, return the account we want
                long userID = tokenProvider.getUserIdFromJWT(token);
                Account retAccount = accountService.loadAccountById(userID);
                return new ResponseEntity<Account>(retAccount, HttpStatus.OK);
            }
        }
        catch (Exception e)
        {
            // tokenProvider threw exception.
            FieldError fe = new FieldError("", "", null, false, null, null, "JWT EXCEPTION");
            return new ResponseEntity <FieldError>(fe, HttpStatus.UNAUTHORIZED);
            //return ResponseEntity.ok("EXCEPTION");
        }
        // tokenProvider returned false on validate.
        FieldError fe = new FieldError("", "", null, false, null, null, "JWT FAIL");
        return new ResponseEntity <FieldError>(fe, HttpStatus.UNAUTHORIZED);
        //return ResponseEntity.ok("BAD");

    }

    //TODO: Update all mappings to use JWT

    // Frontend POSTs their JWT. Backend returns the user Account.
    // This is basically doing exactly the same thing as "Authenticate" mapping
    @PostMapping("Profile")
    public ResponseEntity<?> profileMapping(@Valid @RequestBody String token, BindingResult result) {
        if (result.hasErrors()){
            return new ResponseEntity <List<FieldError>>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
    }

    System.out.println("Authenticating: "+token);
    // Passing the raw token seems to work, for example just posting:
    // eyJhbGciOiJIUzUxMiJ9.eyJpZCI6IjEiLCJleHAi...
    // I'm not sure if this is possible using AXIOS.

    try
    {
        if ( tokenProvider.validateToken(token) )
        {
            // Token validated okay, return the account we want
            long userID = tokenProvider.getUserIdFromJWT(token);
            Account retAccount = accountService.loadAccountById(userID);
            return new ResponseEntity<Account>(retAccount, HttpStatus.OK);
        }
    }
    catch (Exception e)
    {
        // tokenProvider threw exception.
        FieldError fe = new FieldError("", "", null, false, null, null, "JWT EXCEPTION");
        return new ResponseEntity <FieldError>(fe, HttpStatus.UNAUTHORIZED);
    }
    // tokenProvider returned false on validate.
    FieldError fe = new FieldError("", "", null, false, null, null, "JWT FAIL");
    return new ResponseEntity <FieldError>(fe, HttpStatus.UNAUTHORIZED);
}


    // I don't know if backend needs a logout function. I think FrontEnd can just throw away the JWT.
    // But there might be some JWT function to deauthorise a token for security.
/*
    // Frontend POSTs JWT to logout. Backend will delete the token, requiring user to login again.
    @PostMapping("Logout")
    public ResponseEntity<?> deauthoriseToken(@Valid @RequestBody AuthorizationToken token, BindingResult result) {
        if (result.hasErrors()){

        }
    }
*/
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