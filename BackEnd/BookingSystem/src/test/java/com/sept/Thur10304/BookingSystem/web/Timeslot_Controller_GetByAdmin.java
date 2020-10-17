package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.model.Account;
import com.sept.Thur10304.BookingSystem.model.Admin;
import com.sept.Thur10304.BookingSystem.model.Service_;
import com.sept.Thur10304.BookingSystem.model.Timeslot;
import com.sept.Thur10304.BookingSystem.model.Worker;
import com.sept.Thur10304.BookingSystem.repositories.AccountRepository;
import com.sept.Thur10304.BookingSystem.repositories.AdminRepository;
import com.sept.Thur10304.BookingSystem.repositories.BookingRepository;
import com.sept.Thur10304.BookingSystem.repositories.CustomerRepository;
import com.sept.Thur10304.BookingSystem.repositories.Service_Repository;
import com.sept.Thur10304.BookingSystem.repositories.TimeslotRepository;
import com.sept.Thur10304.BookingSystem.repositories.WorkerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class Timeslot_Controller_GetByAdmin {
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

    @Resource
    private CustomerRepository customerRepository;

    @Resource
    private BookingRepository bookingRepository;

    Service_ service1;
    Timeslot timeslot1;
    Timeslot timeslot2;
    String str_tomorrow;

    String datePattern;
    DateFormat df;
    Date tomorrow;

    int workerId1;
    int adminId1;
    String adminToken1;

    @BeforeEach
    void setUp() throws Exception {
        // Clears all repositories
        bookingRepository.deleteAll();
        timeslotRepository.deleteAll();
        serviceRepository.deleteAll();
        workerRepository.deleteAll();
        adminRepository.deleteAll();
        customerRepository.deleteAll();
        accountRepository.deleteAll();

        Account account1 = new Account();
        account1.setEmail("jeremy.jamm@pawnee.gov");
        account1.setFirstName("Jeremy");
        account1.setLastName("Jamm");
        account1.setPassword("YouJustGotJammed");

        // Saves admin into database through post request
        // (not done manually as this way automatically does the password encryption)
        String adminCreateString1 = mvc.perform(post("/api/Account/saveadmin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"" + account1.getFirstName() + "\",\n" +
                        "    \"lastName\": \"" + account1.getLastName() + "\",\n" +
                        "    \"password\": \"" + account1.getPassword() + "\",\n" +
                        "    \"email\": \"" + account1.getEmail() + "\"\n" +
                        "}"))
                // Expects that creation is successful and returns raw json string
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        // // Converts raw json string into a map, then retrieves the admin id and puts in into a
        // //   global variable for use in the tests
        Map<String, Object> adminCreateMap1 = new ObjectMapper().readValue(adminCreateString1, Map.class);
        adminId1 = (int) adminCreateMap1.get("id");

        // Logs in using admin values
        String loginResponse1 = mvc.perform(post("/api/Account/Login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"" + account1.getFirstName() + "\",\n" +
                        "    \"lastName\": \"" + account1.getLastName() + "\",\n" +
                        "    \"password\": \"" + account1.getPassword() + "\",\n" +
                        "    \"email\": \"" + account1.getEmail() + "\"\n" +
                        "}"))
                // Expects login to be successful, recieves json output as string
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        // Extracts token from login response, puts it into global variable
        Map<String, Object> map1 = new ObjectMapper().readValue(loginResponse1, Map.class);
        adminToken1 = (String) map1.get("token");

        Account account2 = new Account();
        account2.setEmail("geremy.gamm@pawnee.gov");
        account2.setFirstName("Geremy");
        account2.setLastName("Gamm");
        account2.setPassword("YouJustGotGammed");

        // Formats request to use admin id and token
        String workerRequest1 = String.format("/api/Account/saveworker/%d?token=%s",adminId1, adminToken1);

        // Performs add worker request
        String workerCreateString1 = mvc.perform(post(workerRequest1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"" + account2.getFirstName() + "\",\n" +
                        "    \"lastName\": \"" + account2.getLastName() + "\",\n" +
                        "    \"password\": \"" + account2.getPassword() + "\",\n" +
                        "    \"email\": \"" + account2.getEmail() + "\"\n" +
                        "}"))
                // Expects worker to be successful
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        // Converts raw json string into a map, then retrieves the admin id and puts in into a
        //   global variable for use in the tests
        Map<String, Object> workerCreateMap1 = new ObjectMapper().readValue(workerCreateString1, Map.class);
        workerId1 = (int) workerCreateMap1.get("id");

        service1 = new Service_();
        service1.setServiceName("Paunch Burger");
        service1.setServiceDescription("Home of the Greasy Lard Bomb");
        service1.setAdmin(adminRepository.findById(Long.valueOf(adminId1)).get());
        service1 = serviceRepository.save(service1);

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
        timeslot1.setWorker(workerRepository.findById(Long.valueOf(workerId1)).get());
        timeslot1 = timeslotRepository.save(timeslot1);

        timeslot2 = new Timeslot();
        timeslot2.setService(service1);
        timeslot2.setDate(tomorrow);
        timeslot2.setTimeslotId((long) 2);
        Date start2 = tf.parse("10:00");
        Date end2 = tf.parse("11:00");
        timeslot2.setStartTime(start2);
        timeslot2.setEndTime(end2);
        timeslot2.setPrice(10.00);
        timeslot2.setWorker(workerRepository.findById(Long.valueOf(workerId1)).get());
        timeslot2 = timeslotRepository.save(timeslot2);
    }

    @AfterEach
    void cleanUp() throws Exception {
        // Clears all repositories
        bookingRepository.deleteAll();
        timeslotRepository.deleteAll();
        serviceRepository.deleteAll();
        workerRepository.deleteAll();
        adminRepository.deleteAll();
        customerRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void getByAdmin_accepted() throws Exception {
        // Format request to use admin id of first admin and
        String request = String.format("/api/timeslot/getbyadmin/%d?token=%s", adminId1, adminToken1);
        // Perform get request
        mvc.perform(get(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].timeslotId").value(timeslot1.getTimeslotId()))
                .andExpect(jsonPath("$[1].timeslotId").value(timeslot2.getTimeslotId()))
                .andExpect(jsonPath("$[1].service.serviceId").value(timeslot2.getService().getServiceId()));
    }

    @Test
    void getByAdmin_badid() throws Exception {
        // Format request to use service id of second service
        String request = String.format("/api/timeslot/getbyservice/%d?token=%s", 2, adminToken1);
        // Perform get request
        mvc.perform(get(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}