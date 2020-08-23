package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.model.Service_;
import com.sept.Thur10304.BookingSystem.services.Service_Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc
class Service_Controller_GetServicesTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private Service_Service service;

    Service_ service1;
    Service_ service2;
    List<Service_> allServices;

    @BeforeEach
    void setUp() {
        // Given
        service1 = new Service_();
        service1.setServiceName("Sweetums");
        service1.setServiceDescription("If you can't beat 'em, Sweetums.");

        service2 = new Service_();
        service2.setServiceName("Paunch Burger");
        service2.setServiceDescription("Home of the Greasy Lard Bomb");

        allServices = new ArrayList<>();
        allServices.add(service1);
        allServices.add(service2);
    }

    @Test
    public void getAllServices() throws Exception {
        given(service.getAllServices()).willReturn(allServices);

        // When
        mvc.perform(get("/api/service/getall")
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isFound())
                .andExpect(jsonPath("$[0].serviceName").value(service1.getServiceName()))
                .andExpect(jsonPath("$[1].serviceName").value(service2.getServiceName()));
    }

    @Test
    void getService() throws Exception {
        given(service.getServiceById("2")).willReturn(service2);

        //When
        mvc.perform(get("/api/service/get?id=2")
                .contentType(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.serviceName").value(service2.getServiceName()));
    }
}