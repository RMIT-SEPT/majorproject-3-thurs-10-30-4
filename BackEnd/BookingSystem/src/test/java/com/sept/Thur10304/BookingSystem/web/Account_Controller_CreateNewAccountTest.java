package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.model.Account;
import com.sept.Thur10304.BookingSystem.repositories.AccountRepository;
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
class Account_Controller_CreateNewAccountTest {

    @Autowired
    private MockMvc mvc;

    //@Resource
    //private AccountRepository accountRepository;


    //Account testAccount1;
    //Account testAccount2;

    @BeforeEach
    void setUp() throws ParseException {
        //testAccount1 = new Account();

        //testAccount1.setId((long) 1);
        //testAccount2.setId((long) 2);

       // testAccount1.setFirstName("Test");
        //testAccount1.setLastName("McTest");
       // testAccount1.setPassword("TEST");
       // testAccount1.setEmail("Test@Testmail.com");
       // accountRepository.save(testAccount1);
    }

    @Test
    void createNewAccount_accepted() throws Exception {

        mvc.perform(post("/api/Account/")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\n\"firstName\": \"Test\",\n\"lastName\": \"McTest\",\n\"password\": \"TEST123\",\n\"email\": \"Test@Testmail.com\"\n}"))
        .andExpect(status().isCreated());

    }

    @Test
    void createNewAccount_shortPassword() throws Exception {

        mvc.perform(post("/api/Account/")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\n\"firstName\": \"Test\",\n\"lastName\": \"McTest\",\n\"password\": \"TEST\",\n\"email\": \"Test@Testmail.com\"\n}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string(org.hamcrest.Matchers.containsString("Password must be between 6-20 characters")));

    }
    @Test
    void createNewAccount_badEmail() throws Exception {

        mvc.perform(post("/api/Account/")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\n\"firstName\": \"Test\",\n\"lastName\": \"McTest\",\n\"password\": \"TEST123\",\n\"email\": \"Test.Testmail.com\"\n}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string(org.hamcrest.Matchers.containsString("Email format is not valid")));

    }

    @Test
    void createNewAccount_duplicateEmail() throws Exception {

        mvc.perform(post("/api/Account/")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\n\"firstName\": \"Test\",\n\"lastName\": \"McTest\",\n\"password\": \"TEST123\",\n\"email\": \"Test2@Testmail.com\"\n}"))
        .andExpect(status().isCreated());

        mvc.perform(post("/api/Account/")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\n\"firstName\": \"Test\",\n\"lastName\": \"McTest\",\n\"password\": \"TEST123\",\n\"email\": \"Test2@Testmail.com\"\n}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string(org.hamcrest.Matchers.containsString("Email already registered")));

    }



}