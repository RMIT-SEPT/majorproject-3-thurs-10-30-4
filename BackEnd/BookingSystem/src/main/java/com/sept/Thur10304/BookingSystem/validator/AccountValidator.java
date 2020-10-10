package com.sept.Thur10304.BookingSystem.validator;

import com.sept.Thur10304.BookingSystem.model.Account;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AccountValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Account.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {

        Account account = (Account) object;

        if(account.getPassword().length() <8){
            errors.rejectValue("password","Length", "Password must be at least 6 characters");
        }

        if(!account.getPassword().equals(account.getPassword())){
            errors.rejectValue("confirmPassword","Match", "Passwords must match");

        }

        //confirmPassword



    }
}