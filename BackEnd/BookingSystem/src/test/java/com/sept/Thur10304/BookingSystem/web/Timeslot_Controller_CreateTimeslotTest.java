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

    Service_ service1;
    Worker worker;
    Timeslot timeslot_duplicate;
    String str_tomorrow;

    @BeforeEach
    void setUp() throws ParseException {

        Account account1 = new Account();
        account1.setEmail("jeremy.jamm@pawnee.gov");
        account1.setFirstName("Jeremy");
        account1.setLastName("Jamm");
        // account1.setId((long) 1);
        account1.setPassword("YouJustGotJammed");
        // accountRepository.save(account1);
        Admin admin = new Admin();
        admin.setAccount(account1);
        adminRepository.save(admin);

        Account account2 = new Account();
        account2.setEmail("geremy.gamm@pawnee.gov");
        account2.setFirstName("Geremy");
        account2.setLastName("Gamm");
        // account2.setId((long) 2);
        account2.setPassword("YouJustGotGammed");
        // accountRepository.save(account2);
        worker = new Worker();
        worker.setAccount(account2);
        worker.setAdmin(admin);
        workerRepository.save(worker);


        service1 = new Service_();
        service1.setServiceId((long) 1);
        service1.setServiceName("Paunch Burger");
        service1.setServiceDescription("Home of the Greasy Lard Bomb");
        service1.setAdmin(admin);
        serviceRepository.save(service1);

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
        timeslot_duplicate.setWorker(worker);
        timeslotRepository.save(timeslot_duplicate);
    }

    @Test
    void createNewTimeslot_accepted() throws Exception {
        mvc.perform(post("/api/timeslot/save/1/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"date\": \"" + str_tomorrow + "\",\n" +
                        "    \"startTime\": \"01:00\",\n" +
                        "    \"endTime\": \"02:00\"\n" +
                        "}"))
                .andExpect(status().isCreated());
    }

    @Test
    void createNewTimeslot_badDateFormat() throws Exception {
        mvc.perform(post("/api/timeslot/save/1/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"date\": \"06-09-2024\",\n" +
                        "    \"startTime\": \"09:00\",\n" +
                        "    \"endTime\": \"11:00\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewTimeslot_badStartTime() throws Exception {
        mvc.perform(post("/api/timeslot/save/1/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"date\": \"" + str_tomorrow + "\",\n" +
                        "    \"startTime\": \"59:12\",\n" +
                        "    \"endTime\": \"11:00\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewTimeslot_badEndTime() throws Exception {
        mvc.perform(post("/api/timeslot/save/1/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"date\": \"" + str_tomorrow + "\",\n" +
                        "    \"startTime\": \"05:00\",\n" +
                        "    \"endTime\": \"77-00\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewTimeslot_endBeforeStart() throws Exception {
        mvc.perform(post("/api/timeslot/save/1/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"date\": \"" + str_tomorrow + "\",\n" +
                        "    \"startTime\": \"05:00\",\n" +
                        "    \"endTime\": \"03:00\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewTimeslot_badServiceId() throws Exception {
        mvc.perform(post("/api/timeslot/save/2/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"date\": \"" + str_tomorrow + "\",\n" +
                        "    \"startTime\": \"03:00\",\n" +
                        "    \"endTime\": \"05:00\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewTimeslot_notInFuture() throws Exception {
        mvc.perform(post("/api/timeslot/save/1/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"date\": \"2020-09-04\",\n" +
                        "    \"startTime\": \"03:00\",\n" +
                        "    \"endTime\": \"05:00\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewTimeslot_duplicate() throws Exception {
        mvc.perform(post("/api/timeslot/save/1/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"date\": \"" + str_tomorrow + "\",\n" +
                        "    \"startTime\": \"07:00\",\n" +
                        "    \"endTime\": \"08:00\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }


}