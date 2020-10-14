package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.repositories.AccountRepository;
import com.sept.Thur10304.BookingSystem.repositories.AdminRepository;
import com.sept.Thur10304.BookingSystem.repositories.CustomerRepository;
import com.sept.Thur10304.BookingSystem.repositories.WorkerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class Account_Controller_CreateAdminTest {

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

    @BeforeEach
    void setUp() throws Exception{
        // Clears all repositories
        workerRepository.deleteAll();
        adminRepository.deleteAll();
        customerRepository.deleteAll();
        accountRepository.deleteAll();

    }

    @AfterEach
    void cleanUp() throws Exception {
        // Clears all repositories
        workerRepository.deleteAll();
        adminRepository.deleteAll();
        customerRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void createNewAdmin_accepted() throws Exception {

        mvc.perform(post("/api/Account/saveadmin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"Ron\",\n" +
                        "    \"lastName\": \"Swanson\",\n" +
                        "    \"password\": \"IAmDukeSilver\",\n" +
                        "    \"email\": \"ron@pawnee.gov\",\n" +
                        "    \"dateCreated\": \"2020-08-23\"\n" +
                        "}"))
                .andExpect(status().isCreated());
    }

    @Test
    void createNewAdmin_firstNameTooShort() throws Exception {

        mvc.perform(post("/api/Account/saveadmin")
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
    void createNewAdmin_firstNameTooLong() throws Exception {

        mvc.perform(post("/api/Account/saveadmin")
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
    void createNewAdmin_firstNameBlank() throws Exception {

        mvc.perform(post("/api/Account/saveadmin")
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
    void createNewAdmin_lastNameTooShort() throws Exception {

        mvc.perform(post("/api/Account/saveadmin")
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
    void createNewAdmin_lastNameTooLong() throws Exception {

        mvc.perform(post("/api/Account/saveadmin")
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
    void createNewAdmin_lastNameBlank() throws Exception {

        mvc.perform(post("/api/Account/saveadmin")
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
//     void createNewAdmin_passwordTooShort() throws Exception {

//         mvc.perform(post("/api/Account/saveadmin")
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
//     void createNewAdmin_passwordTooLong() throws Exception {

//         mvc.perform(post("/api/Account/saveadmin")
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
    void createNewAdmin_passwordBlank() throws Exception {

        mvc.perform(post("/api/Account/saveadmin")
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
    void createNewAdmin_emailTooShort() throws Exception {

        mvc.perform(post("/api/Account/saveadmin")
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
    void createNewAdmin_emailTooLong() throws Exception {

        mvc.perform(post("/api/Account/saveadmin")
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
    void createNewAdmin_emailIncorrectFormat() throws Exception {

        mvc.perform(post("/api/Account/saveadmin")
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
    void createNewAdmin_emailBlank() throws Exception {

        mvc.perform(post("/api/Account/saveadmin")
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