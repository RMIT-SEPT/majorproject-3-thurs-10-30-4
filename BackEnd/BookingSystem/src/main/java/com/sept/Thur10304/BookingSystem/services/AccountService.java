package com.sept.Thur10304.BookingSystem.services;

import com.sept.Thur10304.BookingSystem.repositories.AccountRepository;
import com.sept.Thur10304.BookingSystem.model.Account;
import com.sept.Thur10304.BookingSystem.model.AuthorizationToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository AccountRepository;


    public List<Account> findAll() {

        Iterable<Account> it = AccountRepository.findAll();

        ArrayList<Account> users = new ArrayList<Account>();
        it.forEach(e -> users.add(e));

        return users;
    }

    public Boolean verifyAccount(String email, String password)
    {
        List <Account> lAccount = findAll();
        for (int i=0; i<lAccount.size();++i)
        {
            if (lAccount.get(i).getEmail().equals(email))
            {
                if (lAccount.get(i).getPassword().equals(password))
                {
                    return true;
                }
                // assume no duplicate emails in system
                return false;
            }
        }
        return false;
    }
    public Account getAccount(String email, String password)
    {
        List <Account> lAccount = findAll();
        for (int i=0; i<lAccount.size();++i)
        {
            if (lAccount.get(i).getEmail().equals(email))
            {
                if (lAccount.get(i).getPassword().equals(password))
                {
                    return lAccount.get(i);
                }
                // assume no duplicate emails in system
                return null;
            }
        }
        return null;
    }

    // same as above but return an authentication token which frontend can use
    // note, authentication token may generate duplicates, therefore it is important
    // for frontend to also track the account id or email.
    public Account authoriseJWT(AuthorizationToken jwt)
    {
        List <Account> lAccount = findAll();

        if (lAccount.size()>0)
        {
            // for now just return the first account in the list, no verification needed.
            return lAccount.get(0);
        }
        // there is no account
        return null;
    }

    // When a user session needs to be terminated, jwt should be removed from repo
    // return true if this is successful, return false if jwt doesn't authorise
    // for now we can't delete the token because it is not generated.
    public Boolean deauthoriseJWT(AuthorizationToken jwt)
    {
        // deauthorisation code
        return false;
    }


    public Account saveOrUpdateAccount(Account Account) {

        //logic
        return AccountRepository.save(Account);
    }

    public String test() {
        return "THIS IS A TEST OF BACKEND OUTPUT.<br/><br/><marquee>AYYY</marquee>";
    }

    public List<String> findAllEmails() {

        Iterable<Account> it = AccountRepository.findAll();

        ArrayList<String> emails = new ArrayList<String>();
        it.forEach(e -> emails.add(e.getEmail()));

        return emails;
    }
}