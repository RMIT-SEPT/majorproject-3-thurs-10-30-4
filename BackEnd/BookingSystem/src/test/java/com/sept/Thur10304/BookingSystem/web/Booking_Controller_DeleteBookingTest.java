package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.model.Account;
import com.sept.Thur10304.BookingSystem.model.Booking;
import com.sept.Thur10304.BookingSystem.model.Service_;
import com.sept.Thur10304.BookingSystem.model.Timeslot;
import com.sept.Thur10304.BookingSystem.repositories.AccountRepository;
import com.sept.Thur10304.BookingSystem.repositories.BookingRepository;
import com.sept.Thur10304.BookingSystem.repositories.Service_Repository;
import com.sept.Thur10304.BookingSystem.repositories.TimeslotRepository;
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

    Service_ service1;
    Timeslot timeslot1;
    Account account1;
    Booking booking1;

    String str_tomorrow;

    @BeforeEach
    void setUp() throws ParseException, Exception {
        service1 = new Service_();
        service1.setServiceId((long) 1);
        service1.setServiceName("Paunch Burger");
        service1.setServiceDescription("Home of the Greasy Lard Bomb");
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

        timeslot1 = new Timeslot();
        timeslot1.setService(service1);
        timeslot1.setDate(tomorrow);
        timeslot1.setTimeslotId((long) 1);
        timeslot1.setStartTime(start);
        timeslot1.setEndTime(end);
        timeslotRepository.save(timeslot1);

        account1 = new Account();
        account1.setEmail("jeremy.jamm@pawnee.gov");
        account1.setFirstName("Jeremy");
        account1.setLastName("Jamm");
        account1.setId((long) 1);
        account1.setPassword("YouJustGotJammed");
        accountRepository.save(account1);

        mvc.perform(post("/api/booking/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"timeslotId\": 1,\n" +
                        "    \"customerId\": 1\n" +
                        "}"));

    }

    @Test
    void deleteBooking_accepted() throws Exception {
        mvc.perform(delete("/api/booking/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBooking_badId() throws Exception {
        mvc.perform(delete("/api/booking/delete/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Booking not found"));
    }
}