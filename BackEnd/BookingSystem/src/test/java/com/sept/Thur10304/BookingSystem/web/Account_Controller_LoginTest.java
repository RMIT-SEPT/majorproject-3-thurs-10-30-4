package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.model.Account;
import com.sept.Thur10304.BookingSystem.model.Service_;
import com.sept.Thur10304.BookingSystem.repositories.AccountRepository;
import com.sept.Thur10304.BookingSystem.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class Account_Controller_LoginTest {

    @Autowired
    private MockMvc mvc;

    @Resource
    private AccountRepository accountRepository;

    Account account1;

    @BeforeEach
    void setUp() {
        account1 = new Account();
        account1.setId((long) 1);
        account1.setFirstName("Ron");
        account1.setLastName("Swanson");
        account1.setEmail("ron@pawnee.gov");
        account1.setPassword("IAmDukeSilver");
        account1.setDateCreated(new Date());
        accountRepository.save(account1);

    }

    @Test
    void login_accepted() throws Exception{
        mvc.perform(post("/api/Account/Login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n" +
                    "    \"firstName\": \"Ron\",\n" +
                    "    \"lastName\": \"Swanson\",\n" +
                    "    \"password\": \"IAmDukeSilver\",\n" +
                    "    \"email\": \"ron@pawnee.gov\"\n" +
                    "}"))
                .andExpect(status().isOk());
    }

    @Test
    void login_emailblank_rejected() throws Exception{
        mvc.perform(post("/api/Account/Login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"Ron\",\n" +
                        "    \"lastName\": \"Swanson\",\n" +
                        "    \"password\": \"IAmDukeSilver\",\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_emaildoesnotexist_rejected() throws Exception{
        mvc.perform(post("/api/Account/Login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"April\",\n" +
                        "    \"lastName\": \"Ludgate\",\n" +
                        "    \"password\": \"IAmJanetSnakehole\",\n" +
                        "    \"email\": \"april@pawnee.gov\"\n" +
                        "}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void login_passwordblank_rejected() throws Exception{
        mvc.perform(post("/api/Account/Login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"Ron\",\n" +
                        "    \"lastName\": \"Swanson\",\n" +
                        "    \"email\": \"ron@pawnee.gov\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_passwordincorrect_rejected() throws Exception{
        mvc.perform(post("/api/Account/Login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"Ron\",\n" +
                        "    \"lastName\": \"Swanson\",\n" +
                        "    \"password\": \"IAmRonSwanson\",\n" +
                        "    \"email\": \"ron@pawnee.gov\"\n" +
                        "}"))
                .andExpect(status().is4xxClientError());
    }
}