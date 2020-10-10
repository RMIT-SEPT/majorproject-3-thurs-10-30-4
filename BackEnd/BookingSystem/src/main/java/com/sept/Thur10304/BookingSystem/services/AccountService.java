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

    public String test() {
        return "THIS IS A TEST OF BACKEND OUTPUT.<br/><br/><marquee>AYYY</marquee>";
    }

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
        int[] expectedIncome = new int[7];

        // Today
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        // Calculate days that will need to have income checked on
        Calendar[] days = new Calendar[7];
        for (int i = 0; i < days.length; i++){
            days[i] = Calendar.getInstance();
            days[i].add(Calendar.DATE, i + 1);
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
            }

            // Check if booking is on during the next week
            if (timeslot.getBooking() != null){
                for (int i = 0; i < days.length; i++){
                    if (days[i].get(Calendar.DATE) == timeslotTime.get(Calendar.DATE) && days[i].get(Calendar.YEAR) == timeslotTime.get(Calendar.YEAR)){
                            expectedIncome[i] += timeslot.getPrice();
                            break;
                    }
                }
            }

            // Check if booked and active or completed
            if (timeslot.getBooking() != null){
                if (today.after(timeslotTime)){
                    complededBookings += 1;
                } else {
                    System.out.println(Integer.toString(today.get(Calendar.DATE)) + Integer.toString(today.get(Calendar.HOUR_OF_DAY)) + " is before " + Integer.toString(timeslotTime.get(Calendar.DATE)) + Integer.toString(timeslotTime.get(Calendar.HOUR_OF_DAY)));
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


        return analyticsData;
    }
}