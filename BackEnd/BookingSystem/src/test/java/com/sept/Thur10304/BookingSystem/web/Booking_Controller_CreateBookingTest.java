package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.model.Account;
import com.sept.Thur10304.BookingSystem.model.Admin;
import com.sept.Thur10304.BookingSystem.model.Booking;
import com.sept.Thur10304.BookingSystem.model.Customer;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class Booking_Controller_CreateBookingTest {

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
    Account account3;
    Customer customer;

    String str_tomorrow;

    int customerId;
    int adminId;
    int workerId;
    String adminToken;
    String customerToken;

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

        account3 = new Account();
        account3.setEmail("feremy.famm@pawnee.gov");
        account3.setFirstName("Feremy");
        account3.setLastName("Famm");
        account3.setPassword("YouJustGotFammed");

        // Saves customer into database through post request
        // (not done manually as this way automatically does the password encryption)
        String customerCreateString = mvc.perform(post("/api/Account")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\n" +
                "    \"firstName\": \"" + account3.getFirstName() + "\",\n" +
                "    \"lastName\": \"" + account3.getLastName() + "\",\n" +
                "    \"password\": \"" + account3.getPassword() + "\",\n" +
                "    \"email\": \"" + account3.getEmail() + "\"\n" +
                "}"))
        // Expects that creation is successful and returns raw json string
        .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        // Converts raw json string into a map, then retrieves the admin id and puts in into a
        //   global variable for use in the tests
        Map<String, Object> customerCreateMap = new ObjectMapper().readValue(customerCreateString, Map.class);
        customerId = (int) customerCreateMap.get("id");

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

        timeslot1 = new Timeslot();
        timeslot1.setService(service1);
        timeslot1.setDate(tomorrow);
        timeslot1.setStartTime(start);
        timeslot1.setEndTime(end);
        timeslot1.setPrice(10.00);
        timeslot1.setWorker(workerRepository.findById(Long.valueOf(workerId)).get());
        timeslot1 = timeslotRepository.save(timeslot1);

        // Log in as customer
        String customerLoginResponse = mvc.perform(post("/api/Account/Login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"" + account3.getFirstName() + "\",\n" +
                        "    \"lastName\": \"" + account3.getLastName() + "\",\n" +
                        "    \"password\": \"" + account3.getPassword() + "\",\n" +
                        "    \"email\": \"" + account3.getEmail() + "\"\n" +
                        "}"))
                // Expects login to be successful, recieves json output as string
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        // Extracts token from login response, puts it into global variable
        Map<String, Object> customerLoginMap = new ObjectMapper().readValue(customerLoginResponse, Map.class);
        customerToken = (String) customerLoginMap.get("token");
    }

    @Test
    void createNewBooking_accepted() throws Exception {
        String request = String.format("/api/booking/save?token=%s", customerToken);
        mvc.perform(post(request)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\n" +
                        "    \"timeslotId\": %d,\n" +
                        "    \"customerId\": %d\n" +
                        "}", timeslot1.getTimeslotId(), customerId)))
                .andExpect(status().isCreated());
    }

    @Test
    void createNewBooking_badtimeslotid() throws Exception {
        String request = String.format("/api/booking/save?token=%s", customerToken);
        mvc.perform(post(request)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\n" +
                "    \"timeslotId\": %d,\n" +
                "    \"customerId\": %d\n" +
                "}", timeslot1.getTimeslotId() + 1, customerId)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Timeslot not found"));
    }

    @Test
    void createNewBooking_badaccountid() throws Exception {
        String request = String.format("/api/booking/save?token=%s", customerToken);
        mvc.perform(post(request)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\n" +
                "    \"timeslotId\": %d,\n" +
                "    \"customerId\": %d\n" +
                "}", timeslot1.getTimeslotId(), workerId + 1)))
                // Due to jwt, some of this test had to be disabled
                .andExpect(status().isBadRequest());
                // .andExpect(content().string("Customer not found"));
    }

    @Test
    void createNewBooking_alreadyBooked() throws Exception {
        String request = String.format("/api/booking/save?token=%s", customerToken);
        mvc.perform(post(request)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\n" +
                "    \"timeslotId\": %d,\n" +
                "    \"customerId\": %d\n" +
                "}", timeslot1.getTimeslotId(), customerId)))
                .andExpect(status().isCreated());


        Account account2 = new Account();
        account2.setEmail("leslie.knope@pawnee.gov");
        account2.setFirstName("Leslie");
        account2.setLastName("Knope");
        // account2.setId((long) 2);
        account2.setPassword("RecallKnope?Don't!");
        // // accountRepository.save(account2);
        // Customer customer2 = new Customer();
        // customer2.setAccount(account2);
        // customerRepository.save(customer2);

        // Saves customer into database through post request
        // (not done manually as this way automatically does the password encryption)
        String customerCreateString = mvc.perform(post("/api/Account")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\n" +
                "    \"firstName\": \"" + account2.getFirstName() + "\",\n" +
                "    \"lastName\": \"" + account2.getLastName() + "\",\n" +
                "    \"password\": \"" + account2.getPassword() + "\",\n" +
                "    \"email\": \"" + account2.getEmail() + "\"\n" +
                "}"))
        // Expects that creation is successful and returns raw json string
        .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        // Converts raw json string into a map, then retrieves the admin id and puts in into a
        //   global variable for use in the tests
        Map<String, Object> customerCreateMap = new ObjectMapper().readValue(customerCreateString, Map.class);
        int newCustomerId = (int) customerCreateMap.get("id");

        // Log in as customer
        String customerLoginResponse = mvc.perform(post("/api/Account/Login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"" + account2.getFirstName() + "\",\n" +
                        "    \"lastName\": \"" + account2.getLastName() + "\",\n" +
                        "    \"password\": \"" + account2.getPassword() + "\",\n" +
                        "    \"email\": \"" + account2.getEmail() + "\"\n" +
                        "}"))
                // Expects login to be successful, recieves json output as string
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        // Extracts token from login response, puts it into global variable
        Map<String, Object> customerLoginMap = new ObjectMapper().readValue(customerLoginResponse, Map.class);
        String newCustomerToken = (String) customerLoginMap.get("token");


        String newRequest = String.format("/api/booking/save?token=%s", newCustomerToken);
        mvc.perform(post(newRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\n" +
                "    \"timeslotId\": %d,\n" +
                "    \"customerId\": %d\n" +
                "}", timeslot1.getTimeslotId(), newCustomerId)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Timeslot already booked"));
    }
}