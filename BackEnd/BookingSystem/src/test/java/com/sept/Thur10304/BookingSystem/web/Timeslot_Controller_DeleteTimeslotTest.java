package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.model.Account;
import com.sept.Thur10304.BookingSystem.model.Admin;
import com.sept.Thur10304.BookingSystem.model.Service_;
import com.sept.Thur10304.BookingSystem.model.Timeslot;
import com.sept.Thur10304.BookingSystem.model.Worker;
import com.sept.Thur10304.BookingSystem.repositories.AccountRepository;
import com.sept.Thur10304.BookingSystem.repositories.AdminRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class Timeslot_Controller_DeleteTimeslotTest {

    @Autowired
    private MockMvc mvc;

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


    Service_ service1;
    Timeslot timeslot1;
    String str_tomorrow;

    Worker worker;
    String datePattern;
    DateFormat df;
    Date tomorrow;

    @BeforeEach
    void setUp() throws ParseException {

        Account account1 = new Account();
        account1.setEmail("jeremy.jamm@pawnee.gov");
        account1.setFirstName("Jeremy");
        account1.setLastName("Jamm");
        // account1.setId((long) 1);
        account1.setPassword("YouJustGotJammed");
        Admin admin = new Admin();
        admin.setAccount(account1);
        // accountRepository.save(account1);
        adminRepository.save(admin);

        Account account2 = new Account();
        account2.setEmail("geremy.gamm@pawnee.gov");
        account2.setFirstName("Geremy");
        account2.setLastName("Gamm");
        // account2.setId((long) 2);
        account2.setPassword("YouJustGotGammed");
        worker = new Worker();
        worker.setAccount(account2);
        worker.setAdmin(admin);
        // accountRepository.save(account2);
        workerRepository.save(worker);

        service1 = new Service_();
        service1.setServiceId((long) 1);
        service1.setServiceName("Entertainment 720");
        service1.setServiceDescription("premiere, high-end, all-media entertainment conglomerate");
        service1.setAdmin(admin);
        serviceRepository.save(service1);

        tomorrow = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(tomorrow);
        c.add(Calendar.DATE, 1);
        tomorrow = c.getTime();
        datePattern = "yyyy-MM-dd";
        df = new SimpleDateFormat(datePattern);
        str_tomorrow = df.format(tomorrow);

        DateFormat tf = new SimpleDateFormat("hh:mm");

        timeslot1 = new Timeslot();
        timeslot1.setService(service1);
        timeslot1.setDate(tomorrow);
        timeslot1.setTimeslotId((long) 1);
        Date start1 = tf.parse("07:00");
        Date end1 = tf.parse("08:00");
        timeslot1.setStartTime(start1);
        timeslot1.setEndTime(end1);
        timeslot1.setPrice(10.00);
        timeslot1.setWorker(worker);
        timeslotRepository.save(timeslot1);
    }

    @Test
    void deleteService_accepted() throws Exception{
        mvc.perform(delete("/api/timeslot/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void deleteService_badTimeslot() throws Exception{
        mvc.perform(delete("/api/timeslot/delete/7")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}