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
    private AccountRepository accountRepository;

    @Resource
    private AdminRepository adminRepository;

    @Resource
    private WorkerRepository workerRepository;

    @Resource
    private CustomerRepository customerRepository;

    @Resource
    private AccountService accountService;

    Account adminBeforeCreate;
    int adminId;
    String adminToken;

    @BeforeEach
    void setUp() throws Exception{
        // Clears all repositories
        workerRepository.deleteAll();
        adminRepository.deleteAll();
        customerRepository.deleteAll();
        accountRepository.deleteAll();

        // Creates admin account entity
        adminBeforeCreate = new Account();
        adminBeforeCreate.setFirstName("Daniel");
        adminBeforeCreate.setLastName("Levy");
        adminBeforeCreate.setPassword("IMissGareth");
        adminBeforeCreate.setEmail("dlevy@hotspurway.com");

        // Saves admin into database through post request
        // (not done manually as this way automatically does the password encryption)
        String adminCreateString = mvc.perform(post("/api/Account/saveadmin")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\n" +
                "    \"firstName\": \"" + adminBeforeCreate.getFirstName() + "\",\n" +
                "    \"lastName\": \"" + adminBeforeCreate.getLastName() + "\",\n" +
                "    \"password\": \"" + adminBeforeCreate.getPassword() + "\",\n" +
                "    \"email\": \"" + adminBeforeCreate.getEmail() + "\"\n" +
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
                        "    \"firstName\": \"" + adminBeforeCreate.getFirstName() + "\",\n" +
                        "    \"lastName\": \"" + adminBeforeCreate.getLastName() + "\",\n" +
                        "    \"password\": \"" + adminBeforeCreate.getPassword() + "\",\n" +
                        "    \"email\": \"" + adminBeforeCreate.getEmail() + "\"\n" +
                        "}"))
                // Expects login to be successful, recieves json output as string
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        // Extracts token from login response, puts it into global variable
        Map<String, Object> map = new ObjectMapper().readValue(loginResponse, Map.class);
        adminToken = (String) map.get("token");
        
    }

    @Test
    void createNewWorker_accepted() throws Exception {
        
        // Formats request to use admin id and token
        String request = String.format("/api/Account/saveworker/%d?token=%s",adminId, adminToken);
        
        // Performs add worker request
        mvc.perform(post(request)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"Ron\",\n" +
                        "    \"lastName\": \"Swanson\",\n" +
                        "    \"password\": \"IAmDukeSilver\",\n" +
                        "    \"email\": \"ron@pawnee.gov\"\n" +
                        "}"))
                // Expects worker to be successful
                .andExpect(status().isCreated());
    }

    @Test
    void createNewWorker_firstNameTooShort() throws Exception {

        // Formats request to use admin id and token
        String request = String.format("/api/Account/saveworker/%d?token=%s",adminId, adminToken);
        
        // Performs add worker request
        mvc.perform(post(request)
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

        // Formats request to use admin id and token
        String request = String.format("/api/Account/saveworker/%d?token=%s",adminId, adminToken);
        
        // Performs add worker request
        mvc.perform(post(request)
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

        // Formats request to use admin id and token
        String request = String.format("/api/Account/saveworker/%d?token=%s",adminId, adminToken);
        
        // Performs add worker request
        mvc.perform(post(request)
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

        // Formats request to use admin id and token
        String request = String.format("/api/Account/saveworker/%d?token=%s",adminId, adminToken);
        
        // Performs add worker request
        mvc.perform(post(request)
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

        // Formats request to use admin id and token
        String request = String.format("/api/Account/saveworker/%d?token=%s",adminId, adminToken);
        
        // Performs add worker request
        mvc.perform(post(request)
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

        // Formats request to use admin id and token
        String request = String.format("/api/Account/saveworker/%d?token=%s",adminId, adminToken);
        
        // Performs add worker request
        mvc.perform(post(request)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"Ron\",\n" +
                        "    \"password\": \"IAmDukeSilver\",\n" +
                        "    \"email\": \"ron@pawnee.gov\",\n" +
                        "    \"dateCreated\": \"2020-08-23\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

//     @Test
//     void createNewWorker_passwordTooShort() throws Exception {

//         // Formats request to use admin id and token
//         String request = String.format("/api/Account/saveworker/%d?token=%s",adminId, adminToken);
        
//         // Performs add worker request
//         mvc.perform(post(request)
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content("{\n" +
//                         "    \"firstName\": \"Ron\",\n" +
//                         "    \"lastName\": \"Swanson\",\n" +
//                         "    \"password\": \"Tammy\",\n" +
//                         "    \"email\": \"ron@pawnee.gov\",\n" +
//                         "    \"dateCreated\": \"2020-08-23\"\n" +
//                         "}"))
//                 .andExpect(status().isBadRequest());
//     }

//     @Test
//     void createNewWorker_passwordTooLong() throws Exception {

//         // Formats request to use admin id and token
//         String request = String.format("/api/Account/saveworker/%d?token=%s",adminId, adminToken);
        
//         // Performs add worker request
//         mvc.perform(post(request)
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content("{\n" +
//                         "    \"firstName\": \"Ron\",\n" +
//                         "    \"lastName\": \"Swanson\",\n" +
//                         "    \"password\": \"IAmDukeSilverandIWouldLikeAllYourEggsPlease\",\n" +
//                         "    \"email\": \"ron@pawnee.gov\",\n" +
//                         "    \"dateCreated\": \"2020-08-23\"\n" +
//                         "}"))
//                 .andExpect(status().isBadRequest());
//     }

    @Test
    void createNewWorker_passwordBlank() throws Exception {

        // Formats request to use admin id and token
        String request = String.format("/api/Account/saveworker/%d?token=%s",adminId, adminToken);
        
        // Performs add worker request
        mvc.perform(post(request)
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

        // Formats request to use admin id and token
        String request = String.format("/api/Account/saveworker/%d?token=%s",adminId, adminToken);
        
        // Performs add worker request
        mvc.perform(post(request)
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

        // Formats request to use admin id and token
        String request = String.format("/api/Account/saveworker/%d?token=%s",adminId, adminToken);
        
        // Performs add worker request
        mvc.perform(post(request)
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

        // Formats request to use admin id and token
        String request = String.format("/api/Account/saveworker/%d?token=%s",adminId, adminToken);
        
        // Performs add worker request
        mvc.perform(post(request)
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

        // Formats request to use admin id and token
        String request = String.format("/api/Account/saveworker/%d?token=%s",adminId, adminToken);
        
        // Performs add worker request
        mvc.perform(post(request)
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