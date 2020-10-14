package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.model.Account;
import com.sept.Thur10304.BookingSystem.model.Admin;
import com.sept.Thur10304.BookingSystem.model.enums.AccountType;
import com.sept.Thur10304.BookingSystem.repositories.AccountRepository;
import com.sept.Thur10304.BookingSystem.repositories.AdminRepository;
import com.sept.Thur10304.BookingSystem.repositories.CustomerRepository;
import com.sept.Thur10304.BookingSystem.repositories.Service_Repository;
import com.sept.Thur10304.BookingSystem.repositories.WorkerRepository;
import com.sept.Thur10304.BookingSystem.services.AccountService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
@AutoConfigureMockMvc
class Account_Controller_CreateWorkerTest {

    @Autowired
    private MockMvc mvc;

    @Resource
    private AdminRepository adminRepository;

    @Resource
    private AccountRepository accountRepository;

    @Resource
    private WorkerRepository workerRepository;

    @Resource
    private CustomerRepository customerRepository;

    @Resource
    private AccountService accountService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    Account adminBeforeCreate;
//     Account adminAfterCreate;
//     Account account1;

    @BeforeEach
    void setUp() throws Exception{
        workerRepository.deleteAll();
        adminRepository.deleteAll();
        customerRepository.deleteAll();
        accountRepository.deleteAll();

        adminBeforeCreate = new Account();
        // account1.setId((long) 1);
        adminBeforeCreate.setFirstName("Daniel");
        adminBeforeCreate.setLastName("Levy");
        adminBeforeCreate.setPassword("IMissGareth");
        adminBeforeCreate.setEmail("dlevy@hotspurway.com");
        // account1.setAccountType(AccountType.ADMIN);
        // account1 = accountRepository.save(account1);

        mvc.perform(post("/api/Account/saveadmin")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\n" +
                "    \"firstName\": \"" + adminBeforeCreate.getFirstName() + "\",\n" +
                "    \"lastName\": \"" + adminBeforeCreate.getLastName() + "\",\n" +
                "    \"password\": \"" + adminBeforeCreate.getPassword() + "\",\n" +
                "    \"email\": \"" + adminBeforeCreate.getEmail() + "\"\n" +
                "}"));
        
//        adminBeforeCreate = new Admin();
//        adminBeforeCreate.setAccount(account1);
//         adminAfterCreate = adminBeforeCreate;
//         adminAfterCreate.setPassword(bCryptPasswordEncoder.encode(adminBeforeCreate.getPassword()));
//         Admin admin1 = new Admin();
//         admin1.setAccount(adminAfterCreate);
//        admin1 =  adminRepository.save(admin1); //accountService.saveAdmin(adminBeforeCreate);

       
       // To test that admin is being created
//        for (int i = 0; i < 200; i++){
//                System.out.println(adminAfterCreate.getAccount().getAccountType());
//        }
    }

    @Test
    void createNewWorker_accepted() throws Exception {
        String loginResponse = mvc.perform(post("/api/Account/Login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"" + adminBeforeCreate.getFirstName() + "\",\n" +
                        "    \"lastName\": \"" + adminBeforeCreate.getLastName() + "\",\n" +
                        "    \"password\": \"" + adminBeforeCreate.getPassword() + "\",\n" +
                        "    \"email\": \"" + adminBeforeCreate.getEmail() + "\"\n" +
                        "}")).andReturn().getResponse().getContentAsString(); //.getHeader("token"); // 
        Map<String, Object> map = new ObjectMapper().readValue(loginResponse, Map.class);
        String token = (String) map.get("token");

                        // // // To test the output for request
                        for (int i = 0; i < 100; i++){
                                System.out.println(token);
                        }
                        String request = String.format("/api/Account/saveworker/1?token=%s",token);
        String output = mvc.perform(post(request)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"Ron\",\n" +
                        "    \"lastName\": \"Swanson\",\n" +
                        "    \"password\": \"IAmDukeSilver\",\n" +
                        "    \"email\": \"ron@pawnee.gov\"\n" +
                        // "    \"dateCreated\": \"2020-08-23\"\n" +
                        "}"))
                .andReturn().getResponse().getContentAsString();//andExpect(status().isCreated());
                for (int i = 0; i < 100; i++){
                        System.out.println(output);
                }
    }

    @Test
    void createNewWorker_firstNameTooShort() throws Exception {

        mvc.perform(post("/api/Account/saveworker/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"\",\n" +
                        "    \"lastName\": \"Swanson\",\n" +
                        "    \"password\": \"IAmDukeSilver\",\n" +
                        "    \"email\": \"ron@pawnee.gov\",\n" +
                        "    \"dateCreated\": \"2020-08-23\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewWorker_firstNameTooLong() throws Exception {

        mvc.perform(post("/api/Account/saveworker/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"Ronald Duke Silver Tammy I and Tammy II\",\n" +
                        "    \"lastName\": \"Swanson\",\n" +
                        "    \"password\": \"IAmDukeSilver\",\n" +
                        "    \"email\": \"ron@pawnee.gov\",\n" +
                        "    \"dateCreated\": \"2020-08-23\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewWorker_firstNameBlank() throws Exception {

        mvc.perform(post("/api/Account/saveworker/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"lastName\": \"Swanson\",\n" +
                        "    \"password\": \"IAmDukeSilver\",\n" +
                        "    \"email\": \"ron@pawnee.gov\",\n" +
                        "    \"dateCreated\": \"2020-08-23\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewWorker_lastNameTooShort() throws Exception {

        mvc.perform(post("/api/Account/saveworker/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"Ron\",\n" +
                        "    \"lastName\": \"\",\n" +
                        "    \"password\": \"IAmDukeSilver\",\n" +
                        "    \"email\": \"ron@pawnee.gov\",\n" +
                        "    \"dateCreated\": \"2020-08-23\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewWorker_lastNameTooLong() throws Exception {

        mvc.perform(post("/api/Account/saveworker/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"Ron\",\n" +
                        "    \"lastName\": \"Swanson, Pawnee's Resident Libertarian\",\n" +
                        "    \"password\": \"IAmDukeSilver\",\n" +
                        "    \"email\": \"ron@pawnee.gov\",\n" +
                        "    \"dateCreated\": \"2020-08-23\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewWorker_lastNameBlank() throws Exception {

        mvc.perform(post("/api/Account/saveworker/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"Ron\",\n" +
                        "    \"password\": \"IAmDukeSilver\",\n" +
                        "    \"email\": \"ron@pawnee.gov\",\n" +
                        "    \"dateCreated\": \"2020-08-23\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewWorker_passwordTooShort() throws Exception {

        mvc.perform(post("/api/Account/saveworker/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"Ron\",\n" +
                        "    \"lastName\": \"Swanson\",\n" +
                        "    \"password\": \"Tammy\",\n" +
                        "    \"email\": \"ron@pawnee.gov\",\n" +
                        "    \"dateCreated\": \"2020-08-23\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewWorker_passwordTooLong() throws Exception {

        mvc.perform(post("/api/Account/saveworker/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"Ron\",\n" +
                        "    \"lastName\": \"Swanson\",\n" +
                        "    \"password\": \"IAmDukeSilverandIWouldLikeAllYourEggsPlease\",\n" +
                        "    \"email\": \"ron@pawnee.gov\",\n" +
                        "    \"dateCreated\": \"2020-08-23\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewWorker_passwordBlank() throws Exception {

        mvc.perform(post("/api/Account/saveworker/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"Ron\",\n" +
                        "    \"lastName\": \"Swanson\",\n" +
                        "    \"email\": \"ron@pawnee.gov\",\n" +
                        "    \"dateCreated\": \"2020-08-23\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewWorker_emailTooShort() throws Exception {

        mvc.perform(post("/api/Account/saveworker/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"Ron\",\n" +
                        "    \"lastName\": \"Swanson\",\n" +
                        "    \"password\": \"IAmDukeSilver\",\n" +
                        "    \"email\": \"r@\",\n" +
                        "    \"dateCreated\": \"2020-08-23\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewWorker_emailTooLong() throws Exception {

        mvc.perform(post("/api/Account/saveworker/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"Ron\",\n" +
                        "    \"lastName\": \"Swanson\",\n" +
                        "    \"password\": \"IAmDukeSilver\",\n" +
                        "    \"email\": \"ron@pawnefjhberifgbsaiffvbcaswfncvawsibnvasZPfvncwsiaufcvnbsaufchnsjufchnsdfchnSJDCvhnsdujfvhbcnsdafjgvbldfazsikjguvbnsiyfvgblsdfvjubnasdfvgbsdafyhkvbzdlfivbnzdsfiyuvbsdfjvnbzfvjhnsdzfiaugvhnasdrnfgbvisdbfnvszdjufvbnhzsdjufnvbsdifzvbnzisfbnzsduihfncvizdusfhbnvclzsdidhjdhjuehueduedbnfebnfejndfjbdbefdhjefdujhnbvuijhvne.gov\",\n" +
                        "    \"dateCreated\": \"2020-08-23\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewWorker_emailIncorrectFormat() throws Exception {

        mvc.perform(post("/api/Account/saveworker/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"Ron\",\n" +
                        "    \"lastName\": \"Swanson\",\n" +
                        "    \"password\": \"IAmDukeSilver\",\n" +
                        "    \"email\": \"r$o%n@paw.nee@.gov\",\n" +
                        "    \"dateCreated\": \"2020-08-23\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewWorker_emailBlank() throws Exception {

        mvc.perform(post("/api/Account/saveworker/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"Ron\",\n" +
                        "    \"lastName\": \"Swanson\",\n" +
                        "    \"password\": \"IAmDukeSilver\",\n" +
                        "    \"dateCreated\": \"2020-08-23\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }
}