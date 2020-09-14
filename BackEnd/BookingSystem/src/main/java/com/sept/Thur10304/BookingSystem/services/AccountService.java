package com.sept.Thur10304.BookingSystem.services;

import com.sept.Thur10304.BookingSystem.repositories.AccountRepository;
import com.sept.Thur10304.BookingSystem.repositories.CustomerRepository;
import com.sept.Thur10304.BookingSystem.model.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sept.Thur10304.BookingSystem.model.Customer;

@Service
public class AccountService {
    @Autowired
    private AccountRepository AccountRepository;

    @Autowired
    private CustomerRepository customerRepository;

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

    // same as above but return an authentication token which frontend can use
    // note, authentication token may generate duplicates, therefore it is important
    // for frontend to also track the account id or email.
    public String authoriseAccount(String email, String password)
    {
        List <Account> lAccount = findAll();
        for (int i=0; i<lAccount.size();++i)
        {
            if (lAccount.get(i).getEmail().equals(email))
            {
                if (lAccount.get(i).getPassword().equals(password))
                {
                    // return/generate auth token for this account
                    return "AUTH";
                }
                // assume no duplicate emails in system
                return "FAIL";
            }
        }
        return "FAIL";
    }


    public Account saveOrUpdateAccount(Account account) {

        //logic
        return AccountRepository.save(account);
    }

    public Account saveOrUpdateCustomer(Account account){

        AccountRepository.save(account);
        Customer customer = new Customer();
        customer.setHostAccount(account);
        System.out.println(customerRepository.save(customer));
        return account;
    }

    // public Account saveOrUpdateWorker(Account worker, Long adminId) throws Exception{

    //     Optional<Account> admin = AccountRepository.findById(adminId);
    //     if (!admin.isPresent()){
    //         throw new Exception("Admin not found");
    //     } else if (!admin.get().getType().equals("admin")){
    //         throw new Exception("Account is not admin");
    //     }
    //     AdminWorkerLink adminWorkerLink = new AdminWorkerLink();
    //     adminWorkerLink.setAdminAccount(admin.get());
    //     adminWorkerLink.setWorkerAccount(worker);
    //     adminWorkerLinkRepository.save(adminWorkerLink);
    //     worker.setType("worker");
    //     AccountRepository.save(worker);
    // }

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