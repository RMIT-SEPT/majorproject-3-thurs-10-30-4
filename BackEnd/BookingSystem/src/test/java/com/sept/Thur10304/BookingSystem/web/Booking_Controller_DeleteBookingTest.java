package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.model.Account;
import com.sept.Thur10304.BookingSystem.model.Admin;
import com.sept.Thur10304.BookingSystem.model.Booking;
import com.sept.Thur10304.BookingSystem.model.Customer;
import com.sept.Thur10304.BookingSystem.model.Service_;
import com.sept.Thur10304.BookingSystem.model.Timeslot;
import com.sept.Thur10304.BookingSystem.model.Worker;
import com.sept.Thur10304.BookingSystem.model.enums.AccountType;
import com.sept.Thur10304.BookingSystem.repositories.AccountRepository;
import com.sept.Thur10304.BookingSystem.repositories.AdminRepository;
import com.sept.Thur10304.BookingSystem.repositories.BookingRepository;
import com.sept.Thur10304.BookingSystem.repositories.CustomerRepository;
import com.sept.Thur10304.BookingSystem.repositories.Service_Repository;
import com.sept.Thur10304.BookingSystem.repositories.TimeslotRepository;
import com.sept.Thur10304.BookingSystem.repositories.WorkerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class Booking_Controller_DeleteBookingTest {

    @Autowired
    private MockMvc mvc;

    @Resource
    private BookingRepository bookingRepository;

    @Resource
    private TimeslotRepository timeslotRepository;

    @Resource
    private Service_Repository serviceRepository;

    @Resource
    private AccountRepository accountRepository;

    @Resource
    private AdminRepository adminRepository;
    
    @Resource
    private WorkerRepository workerRepository;

    @Resource
    private CustomerRepository customerRepository;

    Service_ service1;
    Timeslot timeslot1;
    Timeslot timeslot2;
    Account account1;
    Booking booking1;
    Booking booking2;

    String str_aweekaway;
    String str_tomorrow;

    @BeforeEach
    void setUp() throws ParseException, Exception {

        Account account3 = new Account();
        account3.setEmail("teremy.jamm@pawnee.gov");
        account3.setFirstName("Teremy");
        account3.setLastName("Tamm");
        // account3.setId((long) 3);
        account3.setPassword("YouJustGotTammed");
        Admin admin2 = new Admin();
        admin2.setAccount(account3);
        // accountRepository.save(account3);
        adminRepository.save(admin2);

        Account account4 = new Account();
        account4.setEmail("beremy.gamm@pawnee.gov");
        account4.setFirstName("Beremy");
        account4.setLastName("Bamm");
        // account4.setId((long) 4);
        account4.setPassword("YouJustGotBammed");
        Worker worker2 = new Worker();
        worker2.setAccount(account4);
        worker2.setAdmin(admin2);
        // accountRepository.save(account4);
        workerRepository.save(worker2);

        service1 = new Service_();
        service1.setServiceId((long) 1);
        service1.setServiceName("Paunch Burger");
        service1.setServiceDescription("Home of the Greasy Lard Bomb");
        service1.setAdmin(admin2);
        serviceRepository.save(service1);

        Date aweekaway = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(aweekaway);
        c.add(Calendar.DATE, 7);
        aweekaway = c.getTime();
        String datePattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(datePattern);
        str_aweekaway = df.format(aweekaway);

        Date tomorrow = new Date();
        Calendar c2 = Calendar.getInstance();
        c2.setTime(tomorrow);
        c2.add(Calendar.DATE, 7);
        tomorrow = c2.getTime();
        str_tomorrow = df.format(tomorrow);

        DateFormat formatter = new SimpleDateFormat("hh:mm");
        Date start = formatter.parse("07:00");
        Date end = formatter.parse("08:00");

        timeslot1 = new Timeslot();
        timeslot1.setService(service1);
        timeslot1.setDate(aweekaway);
        timeslot1.setTimeslotId((long) 1);
        timeslot1.setStartTime(start);
        timeslot1.setEndTime(end);
        timeslot1.setPrice(10.00);
        timeslot1.setWorker(worker2);
        timeslotRepository.save(timeslot1);

        timeslot2 = new Timeslot();
        timeslot2.setService(service1);
        timeslot2.setDate(aweekaway);
        timeslot2.setTimeslotId((long) 2);
        timeslot2.setStartTime(start);
        timeslot2.setEndTime(end);
        timeslot2.setPrice(10.00);
        timeslot2.setWorker(worker2);
        timeslotRepository.save(timeslot2);

        account1 = new Account();
        account1.setEmail("jeremy.jamm@pawnee.gov");
        account1.setFirstName("Jeremy");
        account1.setLastName("Jamm");
        // account1.setId((long) 1);
        account1.setPassword("YouJustGotJammed");
        // account1.setAccountType(AccountType.CUSTOMER);
        // accountRepository.save(account1);

        Customer customer1 = new Customer();
        customer1.setAccount(account1);
        customerRepository.save(customer1);

        booking1 = new Booking();
        booking1.setTimeslot(timeslot1);
        booking1.setCustomer(customer1);
        bookingRepository.save(booking1);

        booking2 = new Booking();
        booking2.setTimeslot(timeslot2);
        booking2.setCustomer(customer1);
        bookingRepository.save(booking2);

    }

    @Test
    void deleteBooking_accepted() throws Exception {
        mvc.perform(delete("/api/booking/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBooking_badId() throws Exception {
        mvc.perform(delete("/api/booking/delete/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Booking not found"));
    }

    @Test
    void deleteBooking_within48hours() throws Exception {
        mvc.perform(delete("/api/booking/delete/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Can't cancel less than 48 hours before booking"));
    }
}