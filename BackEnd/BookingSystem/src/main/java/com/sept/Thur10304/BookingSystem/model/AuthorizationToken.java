package com.sept.Thur10304.BookingSystem.model;

// JSON Web Token
// Should be passed to authorise user actions.
// This will most likely be some kind of key as a string

public class AuthorizationToken
{
    private String loginAuth;
    
    AuthorizationToken()
    {
        loginAuth="TEST";
    }
    
    public Boolean authorise(String token)
    {
        if (token.equals(loginAuth))
        {
            return true;
        }
        return false;
    }
}
