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

import javax.annotation.Resource;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Resource
    private CustomerRepository customerRepository;

    @Resource
    private BookingRepository bookingRepository;

    Service_ service1;
    Timeslot timeslot1;
    String str_tomorrow;

    String datePattern;
    DateFormat df;
    Date tomorrow;

    int adminId;
    int workerId;
    String adminToken;

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
        String adminCreateString = mvc.perform(post("/api/Account/saveadmin")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\n" +
                "    \"firstName\": \"" + account1.getFirstName() + "\",\n" +
                "    \"lastName\": \"" + account1.getLastName() + "\",\n" +
                "    \"password\": \"" + account1.getPassword() + "\",\n" +
                "    \"email\": \"" + account1.getEmail() + "\"\n" +
                "}"))
        // Expects that creation is successful and returns raw json string
        .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        // Converts raw json string into a map, then retrieves the admin id and puts in into a
        //   global variable for use in the tests
        Map<String, Object> adminCreateMap = new ObjectMapper().readValue(adminCreateString, Map.class);
        adminId = (int) adminCreateMap.get("id");

        // Logs in using admin values
        String loginResponse = mvc.perform(post("/api/Account/Login")
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
        Map<String, Object> map = new ObjectMapper().readValue(loginResponse, Map.class);
        adminToken = (String) map.get("token");

        Account account2 = new Account();
        account2.setEmail("geremy.gamm@pawnee.gov");
        account2.setFirstName("Geremy");
        account2.setLastName("Gamm");
        account2.setPassword("YouJustGotGammed");

         // Formats request to use admin id and token
         String request = String.format("/api/Account/saveworker/%d?token=%s",adminId, adminToken);
        
         // Performs add worker request
         String workerCreateString = mvc.perform(post(request)
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
        Map<String, Object> workerCreateMap = new ObjectMapper().readValue(workerCreateString, Map.class);
        workerId = (int) workerCreateMap.get("id");

        service1 = new Service_();
        service1.setServiceName("Entertainment 720");
        service1.setServiceDescription("premiere, high-end, all-media entertainment conglomerate");
        service1.setAdmin(adminRepository.findById(Long.valueOf(adminId)).get());
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
        Date start1 = tf.parse("07:00");
        Date end1 = tf.parse("08:00");
        timeslot1.setStartTime(start1);
        timeslot1.setEndTime(end1);
        timeslot1.setPrice(10.00);
        timeslot1.setWorker(workerRepository.findById(Long.valueOf(workerId)).get());
        timeslot1 = timeslotRepository.save(timeslot1);
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
    void deleteService_accepted() throws Exception{

        // Create request for deleting timeslot
        String request = String.format("/api/timeslot/delete/%d?token=%s", timeslot1.getTimeslotId(), adminToken);

        // Perform delete request
        mvc.perform(delete(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void deleteService_badTimeslot() throws Exception{

        // Create request for deleting timeslot, with id being above the last one added
        String request = String.format("/api/timeslot/delete/%d?token=%s", timeslot1.getTimeslotId() + 1, adminToken);

        // Perform delete request
        mvc.perform(delete(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}