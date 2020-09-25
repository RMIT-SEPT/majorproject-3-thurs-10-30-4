package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.model.Account;
import com.sept.Thur10304.BookingSystem.model.Admin;
import com.sept.Thur10304.BookingSystem.model.Service_;
import com.sept.Thur10304.BookingSystem.model.enums.AccountType;
import com.sept.Thur10304.BookingSystem.repositories.AccountRepository;
import com.sept.Thur10304.BookingSystem.repositories.AdminRepository;
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
import javax.annotation.Resource;

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

    @Resource
    private AccountRepository accountRepository;
    
    @Resource
    private AdminRepository adminRepository;

    Service_ service1;
    Service_ service2;
    List<Service_> allServices;

    @BeforeEach
    void setUp() {
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
        Admin admin2 = new Admin();
        admin2.setAccount(account2);
        // accountRepository.save(account2);
        adminRepository.save(admin2);
        // Given
        service1 = new Service_();
        service1.setServiceName("Sweetums");
        service1.setServiceDescription("If you can't beat 'em, Sweetums.");
        service1.setAdmin(admin);

        service2 = new Service_();
        service2.setServiceName("Paunch Burger");
        service2.setServiceDescription("Home of the Greasy Lard Bomb");
        service2.setAdmin(admin2);

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