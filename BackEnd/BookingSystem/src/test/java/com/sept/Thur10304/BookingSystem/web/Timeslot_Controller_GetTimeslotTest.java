package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.model.Service_;
import com.sept.Thur10304.BookingSystem.model.Timeslot;
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
        service1 = new Service_();
        service1.setServiceId((long) 1);
        service1.setServiceName("Paunch Burger");
        service1.setServiceDescription("Home of the Greasy Lard Bomb");
        serviceRepository.save(service1);

        service2 = new Service_();
        service2.setServiceId((long) 2);
        service2.setServiceName("Entertainment 720");
        service2.setServiceDescription("premiere, high-end, all-media entertainment conglomerate");
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