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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class Timeslot_Controller_CreateTimeslotTest {

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
    Timeslot timeslot_duplicate;
    String str_tomorrow;
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
        service1.setServiceName("Paunch Burger");
        service1.setServiceDescription("Home of the Greasy Lard Bomb");
        service1.setAdmin(adminRepository.findById(Long.valueOf(adminId)).get());
        service1 = serviceRepository.save(service1);

        Date tomorrow = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(tomorrow);
        c.add(Calendar.DATE, 1);
        tomorrow = c.getTime();
        String datePattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(datePattern);
        str_tomorrow = df.format(tomorrow);

        DateFormat formatter = new SimpleDateFormat("hh:mm");
        Date start = formatter.parse("07:00");
        Date end = formatter.parse("08:00");

        timeslot_duplicate = new Timeslot();
        timeslot_duplicate.setService(service1);
        timeslot_duplicate.setDate(tomorrow);
        timeslot_duplicate.setTimeslotId((long) 2);
        timeslot_duplicate.setStartTime(start);
        timeslot_duplicate.setEndTime(end);
        timeslot_duplicate.setPrice(10.00);
        timeslot_duplicate.setWorker(workerRepository.findById(Long.valueOf(workerId)).get());
        timeslotRepository.save(timeslot_duplicate);
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
    void createNewTimeslot_accepted() throws Exception {
        // Format ids and token into the request
        String request = String.format("/api/timeslot/save/%d/%d?token=%s", service1.getServiceId(), workerId, adminToken);
        // Use post request
        mvc.perform(post(request)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"date\": \"" + str_tomorrow + "\",\n" +
                        "    \"price\": 10.00,\n" +
                        "    \"startTime\": \"01:00\",\n" +
                        "    \"endTime\": \"02:00\"\n" +
                        "}"))
                .andExpect(status().isCreated());
    }

    @Test
    void createNewTimeslot_badDateFormat() throws Exception {
        // Format ids and token into the request
        String request = String.format("/api/timeslot/save/%d/%d?token=%s", adminId, workerId, adminToken);
        // Use post request
        mvc.perform(post(request)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"date\": \"06-09-2024\",\n" +
                        "    \"price\": 10.00,\n" +
                        "    \"startTime\": \"09:00\",\n" +
                        "    \"endTime\": \"11:00\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewTimeslot_badStartTime() throws Exception {
        // Format ids and token into the request
        String request = String.format("/api/timeslot/save/%d/%d?token=%s", adminId, workerId, adminToken);
        // Use post request
        mvc.perform(post(request)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"date\": \"" + str_tomorrow + "\",\n" +
                        "    \"price\": 10.00,\n" +
                        "    \"startTime\": \"59:12\",\n" +
                        "    \"endTime\": \"11:00\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewTimeslot_badEndTime() throws Exception {
        // Format ids and token into the request
        String request = String.format("/api/timeslot/save/%d/%d?token=%s", adminId, workerId, adminToken);
        // Use post request
        mvc.perform(post(request)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"date\": \"" + str_tomorrow + "\",\n" +
                        "    \"price\": 10.00,\n" +
                        "    \"startTime\": \"05:00\",\n" +
                        "    \"endTime\": \"77-00\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewTimeslot_endBeforeStart() throws Exception {
        // Format ids and token into the request
        String request = String.format("/api/timeslot/save/%d/%d?token=%s", adminId, workerId, adminToken);
        // Use post request
        mvc.perform(post(request)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"date\": \"" + str_tomorrow + "\",\n" +
                        "    \"price\": 10.00,\n" +
                        "    \"startTime\": \"05:00\",\n" +
                        "    \"endTime\": \"03:00\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewTimeslot_badServiceId() throws Exception {
        // Format ids and token into the request
        String request = String.format("/api/timeslot/save/%d/%d?token=%s", adminId, workerId, adminToken);
        // Use post request
        mvc.perform(post(request)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"date\": \"" + str_tomorrow + "\",\n" +
                        "    \"price\": 10.00,\n" +
                        "    \"startTime\": \"03:00\",\n" +
                        "    \"endTime\": \"05:00\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewTimeslot_notInFuture() throws Exception {
        // Format ids and token into the request
        String request = String.format("/api/timeslot/save/%d/%d?token=%s", adminId, workerId, adminToken);
        // Use post request
        mvc.perform(post(request)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"date\": \"2020-09-04\",\n" +
                        "    \"price\": 10.00,\n" +
                        "    \"startTime\": \"03:00\",\n" +
                        "    \"endTime\": \"05:00\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewTimeslot_duplicate() throws Exception {
        // Format ids and token into the request
        String request = String.format("/api/timeslot/save/%d/%d?token=%s", adminId, workerId, adminToken);
        // Use post request
        mvc.perform(post(request)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"date\": \"" + str_tomorrow + "\",\n" +
                        "    \"price\": 10.00,\n" +
                        "    \"startTime\": \"07:00\",\n" +
                        "    \"endTime\": \"08:00\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }


}