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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class Timeslot_Controller_GetTimeslotTest {

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
    Service_ service2;
    Timeslot timeslot1;
    Timeslot timeslot2;
    String str_tomorrow;

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
        Worker worker = new Worker();
        worker.setAccount(account2);
        worker.setAdmin(admin);
        // accountRepository.save(account2);
        workerRepository.save(worker);

        service1 = new Service_();
        service1.setServiceId((long) 1);
        service1.setServiceName("Paunch Burger");
        service1.setServiceDescription("Home of the Greasy Lard Bomb");
        service1.setAdmin(admin);
        serviceRepository.save(service1);

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

        service2 = new Service_();
        service2.setServiceId((long) 2);
        service2.setServiceName("Entertainment 720");
        service2.setServiceDescription("premiere, high-end, all-media entertainment conglomerate");
        service2.setAdmin(admin2);
        serviceRepository.save(service2);

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

        timeslot2 = new Timeslot();
        timeslot2.setService(service1);
        timeslot2.setDate(tomorrow);
        timeslot2.setTimeslotId((long) 2);
        Date start2 = tf.parse("10:00");
        Date end2 = tf.parse("11:00");
        timeslot2.setStartTime(start2);
        timeslot2.setEndTime(end2);
        timeslot2.setPrice(10.00);
        timeslot2.setWorker(worker2);
        timeslotRepository.save(timeslot2);
    }

    @Test
    void getByService_accepted() throws Exception {

        mvc.perform(get("/api/timeslot/getbyservice/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$[0].timeslotId").value(timeslot1.getTimeslotId()))
                .andExpect(jsonPath("$[1].timeslotId").value(timeslot2.getTimeslotId()))
                .andExpect(jsonPath("$[1].service.serviceId").value(timeslot2.getService().getServiceId()));
    }

    @Test
    void getByService_noSlotsForService() throws Exception {

        mvc.perform(get("/api/timeslot/getbyservice/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(content().string("[]"));
    }

    @Test
    void getByService_badServiceId() throws Exception {

        mvc.perform(get("/api/timeslot/getbyservice/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getById_accepted() throws Exception {

        mvc.perform(get("/api/timeslot/getbyid/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.timeslotId").value(timeslot1.getTimeslotId()))
                .andExpect(jsonPath("$.service.serviceId").value(timeslot1.getService().getServiceId()));
    }

    @Test
    void getById_rejected() throws Exception {

        mvc.perform(get("/api/timeslot/getbyid/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }



}