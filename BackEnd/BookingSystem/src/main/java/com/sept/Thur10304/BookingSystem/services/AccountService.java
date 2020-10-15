package com.sept.Thur10304.BookingSystem.services;

import com.sept.Thur10304.BookingSystem.repositories.AccountRepository;
import com.sept.Thur10304.BookingSystem.repositories.AdminRepository;
import com.sept.Thur10304.BookingSystem.repositories.CustomerRepository;
import com.sept.Thur10304.BookingSystem.repositories.WorkerRepository;
import com.sept.Thur10304.BookingSystem.model.Account;
//import com.sept.Thur10304.BookingSystem.model.AuthorizationToken;
import com.sept.Thur10304.BookingSystem.model.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;

import com.sept.Thur10304.BookingSystem.model.Customer;
import com.sept.Thur10304.BookingSystem.model.Service_;
import com.sept.Thur10304.BookingSystem.model.Timeslot;
import com.sept.Thur10304.BookingSystem.model.Worker;
import com.sept.Thur10304.BookingSystem.model.enums.AccountType;

// JWT
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

// This service now implements a UserDetailsService interface, which
// I think helps it interface with JWT. UserDetailsService expects
// username instead of email, but I think we can just use email as
// username. Homy's implementation added a wrapper class
// CustomUserDetailsService.java but I just added it directly into
// our main class here.

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepository AccountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @Override
    //UserDetailsService interface (expects username but use email as username)
    public Account loadUserByUsername(String email)
    {
        Account user = AccountRepository.findByEmail(email);
        //if(user==null) new UsernameNotFoundException("User not found");
        if (user==null)
        {
            return null;
        }
        return user;
    }


    @Transactional
    public Account loadAccountById(Long id){
        Account account = AccountRepository.getById(id);
        //if(user==null) new UsernameNotFoundException("User not found");
        if (account==null)
        {
            return null;
        }
        return account;

    }


// original account creation, now uses account creation for account type
    // public Account saveOrUpdateAccount(Account account) {

    //     //logic
    //     return AccountRepository.save(account);
    // }

// original account creation, now uses account creation for account type
    // public Account saveOrUpdateAccount(Account account) {

    //     //logic
    //     return AccountRepository.save(account);
    // }


    // New system template using JWT
    // TODO: Merge this template into our Customer/Worker/Admin design
    public Account saveUser (Account newUser)
    {
        // email has to be unique (exception)
        // Make sure that password and confirmPassword match
        // We don't persist or show the confirmPassword

        // encrypt the password so it can't be read from db as cleartext
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        //Username has to be unique (exception)
        newUser.setEmail(newUser.getEmail());

        return AccountRepository.save(newUser);
    }

  public Account saveCustomer(Account account) {
        // encrypt the password so it can't be read from db as cleartext
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));

        Customer customer = new Customer();
        customer.setAccount(account);
        customerRepository.save(customer);
        return account;
    
    }

    public Account saveWorker(Account account, Long adminId) throws Exception {
        Optional<Admin> findAdmin = adminRepository.findById(adminId);
        if (!findAdmin.isPresent()){
            throw new Exception("Admin not found");
        }

        // encrypt the password so it can't be read from db as cleartext
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));

        Admin admin = findAdmin.get();
        AccountRepository.save(account);
        Worker worker = new Worker();
        worker.setAccount(account);
        worker.setAdmin(admin);
        workerRepository.save(worker);
        return account;
    }

    public Account saveAdmin(Account account) {
        // TODO add connection to service

        // encrypt the password so it can't be read from db as cleartext
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));

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


    public List<Worker> getWorkersByAdminId(Long adminId) throws Exception{
        Admin admin = findAdmin(adminId);
        return admin.getWorkers();
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

    public List<String> findAllEmails() {

        Iterable<Account> it = AccountRepository.findAll();

        ArrayList<String> emails = new ArrayList<String>();
        it.forEach(e -> emails.add(e.getEmail()));

        return emails;
    }

    public Map<String, Object> getAdminAnalytics(Long adminId) throws Exception{
        Admin admin = findAdmin(adminId);

        Map<String, Object> analyticsData = new HashMap<String, Object>();

        // Find amount of workers
        int numberOfWorkers = admin.getWorkers().size();

        int complededBookings = 0;
        int activeBookings = 0;
        int bookedToday = 0;
        int unbookedToday = 0;
        double[] expectedIncome = new double[7];

        // Today
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        // Calculate days that will need to have income checked on
        Calendar[] nextDays = new Calendar[6];
        for (int i = 0; i < nextDays.length; i++){
            nextDays[i] = Calendar.getInstance();
            nextDays[i].add(Calendar.DATE, i + 1);
        }

        if (admin.getService() == null){
            throw new Exception("Admin does not have a service");
        }

        // Number of completed/active bookings
        Set<Timeslot> timeslots = admin.getService().getTimeslots();

        if (timeslots == null){
            throw new Exception("Admin does not have any timeslots");
        }

        Iterator<Timeslot> iterator = timeslots.iterator();
        while (iterator.hasNext()){
            Timeslot timeslot = iterator.next();
            Calendar timeslotTime = Calendar.getInstance();

            // Used {} so timeslotDate and timeslotEndTime don't get mixed up with timeslotTime later in the method
            {
                Calendar timeslotDate = Calendar.getInstance();
                timeslotDate.setTime(timeslot.getDate());
                Calendar timeslotEndTime = Calendar.getInstance();
                timeslotEndTime.setTime(timeslot.getEndTime());

                // Add 14 because timezones acting weird
                timeslotEndTime.add(Calendar.HOUR_OF_DAY, 14);

                timeslotTime.set(Calendar.YEAR, timeslotDate.get(Calendar.YEAR));
                timeslotTime.set(Calendar.DAY_OF_YEAR, timeslotDate.get(Calendar.DAY_OF_YEAR));
                timeslotTime.set(Calendar.HOUR_OF_DAY, timeslotEndTime.get(Calendar.HOUR_OF_DAY));
                timeslotTime.set(Calendar.MINUTE, timeslotEndTime.get(Calendar.MINUTE));
            }

            // Check if today
            if (today.get(Calendar.DAY_OF_YEAR) == timeslotTime.get(Calendar.DAY_OF_YEAR) && today.get(Calendar.YEAR) == timeslotTime.get(Calendar.YEAR)){
                if (timeslot.getBooking() != null){
                    bookedToday += 1;
                } else {
                    unbookedToday += 1;
                }
                expectedIncome[0] += timeslot.getPrice();
            } else {
                // Check if booking is on during the next week
                if (timeslot.getBooking() != null){
                    for (int i = 0; i < nextDays.length; i++){
                        if (nextDays[i].get(Calendar.DATE) == timeslotTime.get(Calendar.DATE) && nextDays[i].get(Calendar.YEAR) == timeslotTime.get(Calendar.YEAR)){
                                expectedIncome[i + 1] += timeslot.getPrice();
                                break;
                        }
                    }
                }
            }

            

            // Check if booked and active or completed
            if (timeslot.getBooking() != null){
                if (today.after(timeslotTime)){
                    complededBookings += 1;
                } else {
                    activeBookings += 1;
                }
            }
        }

        // Add fields into map
        analyticsData.put("active_bookings", activeBookings);
        analyticsData.put("completed_bookings", complededBookings);
        analyticsData.put("worker_count", numberOfWorkers);
        analyticsData.put("unbooked_today", unbookedToday);
        analyticsData.put("booked_today", bookedToday);

        String[] dayStrings = new String[nextDays.length + 1];
        dayStrings[0] = String.format("%d-%d-%d", today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE));

        // Put Next week dates into an array
        for (int i = 0; i < nextDays.length; i++){
            dayStrings[i + 1] = String.format("%d-%d-%d", nextDays[i].get(Calendar.YEAR), nextDays[i].get(Calendar.MONTH), nextDays[i].get(Calendar.DATE));
        }

        analyticsData.put("next_week_dates", dayStrings);
        analyticsData.put("next_week_expected_income", expectedIncome);

        return analyticsData;
    }
}