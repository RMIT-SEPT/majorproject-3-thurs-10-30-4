package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.model.Account;
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
            return ResponseEntity.ok("VALID: "+jwt);
        }
        else
        {
            return ResponseEntity.ok("INVALID:"+jwt);
        }

        //return ResponseEntity.ok(new JWTLoginSucessReponse(true, jwt));



/*
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

        //TODO: JWT login authentication goes here.
*/
    }

    // pass jwt and should return user details
    @PostMapping("Authenticate")
    public ResponseEntity<?> authenticateAccount(@Valid @RequestBody String token, BindingResult result) {
        if (result.hasErrors()){
                return new ResponseEntity <List<FieldError>>(result.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }

        try
        {
            if ( tokenProvider.validateToken(token) )
            {
                return ResponseEntity.ok("GOOD");
            }
        }
        catch (Exception e)
        {
            return ResponseEntity.ok("EXCEPTION"); 
        }
        //validateToken
        return ResponseEntity.ok("BAD");

    }

/*
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
        //Account authorisedAccount = accountService.authoriseJWT(token);

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
*/


/*
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
*/
}