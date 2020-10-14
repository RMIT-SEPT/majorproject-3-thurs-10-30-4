package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.model.Account;
import com.sept.Thur10304.BookingSystem.model.Service_;
import com.sept.Thur10304.BookingSystem.repositories.AccountRepository;
import com.sept.Thur10304.BookingSystem.repositories.AdminRepository;
import com.sept.Thur10304.BookingSystem.repositories.CustomerRepository;
import com.sept.Thur10304.BookingSystem.repositories.WorkerRepository;
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

    @Resource
    private AdminRepository adminRepository;

    @Resource
    private WorkerRepository workerRepository;

    @Resource
    private CustomerRepository customerRepository;


    Account account1;

    @BeforeEach
    void setUp() throws Exception {
        // Clears all repositories
        workerRepository.deleteAll();
        adminRepository.deleteAll();
        customerRepository.deleteAll();
        accountRepository.deleteAll();

        account1 = new Account();
        account1.setFirstName("Ron");
        account1.setLastName("Swanson");
        account1.setEmail("ron@pawnee.gov");
        account1.setPassword("IAmDukeSilver");

        // Save customer through post request, so it encrypts password
        mvc.perform(post("/api/Account")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\n" +
                "    \"firstName\": \"" + account1.getFirstName() + "\",\n" +
                "    \"lastName\": \"" + account1.getLastName() + "\",\n" +
                "    \"password\": \"" + account1.getPassword() + "\",\n" +
                "    \"email\": \"" + account1.getEmail() + "\"\n" +
                "}"))
        // Expects that creation is successful
        .andExpect(status().isCreated());

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