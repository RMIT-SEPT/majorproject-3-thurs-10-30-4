package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.model.Account;
import com.sept.Thur10304.BookingSystem.model.Admin;
import com.sept.Thur10304.BookingSystem.model.enums.AccountType;
import com.sept.Thur10304.BookingSystem.repositories.AccountRepository;
import com.sept.Thur10304.BookingSystem.repositories.AdminRepository;
import com.sept.Thur10304.BookingSystem.repositories.Service_Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class Account_Controller_CreateWorkerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AccountRepository accountRepository;

    Admin admin1;
    Account account1;

    @BeforeEach
    void setUp() {
        account1 = new Account();
        account1.setId((long) 1);
        account1.setFirstName("Daniel");
        account1.setLastName("Levy");
        account1.setPassword("IMissGareth<3");
        account1.setEmail("dlevy@hotspurway.com");
        account1.setAccountType(AccountType.ADMIN);
        accountRepository.save(account1);

//        admin1 = new Admin();
//        admin1.setAccount(account1);
//        adminRepository.save(admin1);
    }

    @Test
    void createNewWorker_accepted() throws Exception {

        mvc.perform(post("/api/Account/saveworker/1")
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