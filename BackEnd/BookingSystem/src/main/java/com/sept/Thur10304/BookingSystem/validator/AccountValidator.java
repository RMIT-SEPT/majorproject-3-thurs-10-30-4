package com.sept.Thur10304.BookingSystem.validator;

import com.sept.Thur10304.BookingSystem.model.Account;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

// This seems to be a JWT-required validator class.
// Failure to route POSTs through the validator will result in
// a 500 internal server error.

@Component
public class AccountValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Account.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {

        Account account = (Account) object;

        //if(account.getPassword().length() <8){
         //   errors.rejectValue("password","Length", "Password must be at least 8 characters");
        //}

    }
}