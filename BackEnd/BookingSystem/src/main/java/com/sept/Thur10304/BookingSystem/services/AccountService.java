package com.sept.Thur10304.BookingSystem.services;

import com.sept.Thur10304.BookingSystem.repositories.AccountRepository;
import com.sept.Thur10304.BookingSystem.repositories.AdminRepository;
import com.sept.Thur10304.BookingSystem.repositories.CustomerRepository;
import com.sept.Thur10304.BookingSystem.repositories.WorkerRepository;
import com.sept.Thur10304.BookingSystem.model.Account;
import com.sept.Thur10304.BookingSystem.model.AuthorizationToken;
import com.sept.Thur10304.BookingSystem.model.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sept.Thur10304.BookingSystem.model.Customer;
import com.sept.Thur10304.BookingSystem.model.Service_;
import com.sept.Thur10304.BookingSystem.model.Worker;
import com.sept.Thur10304.BookingSystem.model.enums.AccountType;

@Service
public class AccountService {
    @Autowired
    private AccountRepository AccountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private AdminRepository adminRepository;

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


// original account creation, now uses account creation for account type
    // public Account saveOrUpdateAccount(Account account) {

    //     //logic
    //     return AccountRepository.save(account);
    // }

    


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

// original account creation, now uses account creation for account type
    // public Account saveOrUpdateAccount(Account account) {

    //     //logic
    //     return AccountRepository.save(account);
    // }

  public Account saveCustomer(Account account){
        AccountRepository.save(account);
        Customer customer = new Customer();
        customer.setAccount(account);
        customerRepository.save(customer);
        return account;
    
    }

    public Account saveWorker(Account account, Long adminId) throws Exception{
        Optional<Admin> findAdmin = adminRepository.findById(adminId);
        if (!findAdmin.isPresent()){
            throw new Exception("Admin not found");
        }
        Admin admin = findAdmin.get();
        AccountRepository.save(account);
        Worker worker = new Worker();
        worker.setAccount(account);
        worker.setAdmin(admin);
        workerRepository.save(worker);
        return account;
    }

    public Account saveAdmin(Account account){
        // TODO add connection to service

        AccountRepository.save(account);
        Admin admin = new Admin();
        admin.setAccount(account);
        adminRepository.save(admin);
        return account;
    }

    public Customer findCustomer(Long customerId) throws Exception{
        Optional<Customer> findCustomer = customerRepository.findById(customerId);
        if (findCustomer.isPresent()){
            return findCustomer.get();
        } else {
            throw new Exception("Customer not found");
        }
    }

    public Admin findAdmin(Long adminId) throws Exception{
        Optional<Admin> findAdmin = adminRepository.findById(adminId);
        if (findAdmin.isPresent()){
            return findAdmin.get();
        } else {
            throw new Exception("Admin not found");
        }
    }

    public Worker findWorker(Long workerId) throws Exception{
        Optional<Worker> findWorker = workerRepository.findById(workerId);
        if (findWorker.isPresent()){
            return findWorker.get();
        } else {
            throw new Exception("Worker not found");
        }
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