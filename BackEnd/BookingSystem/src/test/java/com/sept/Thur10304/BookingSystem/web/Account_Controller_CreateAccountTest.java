package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.services.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountController_CreateAccount {

    @Autowired
    private MockMvc mvc;

    @Test
    void createNewAccount_accepted() throws Exception {

        mvc.perform(post("/api/Account")
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
    void createNewAccount_firstNameTooShort() throws Exception {

        mvc.perform(post("/api/Account")
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
    void createNewAccount_firstNameTooLong() throws Exception {

        mvc.perform(post("/api/Account")
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
    void createNewAccount_firstNameBlank() throws Exception {

        mvc.perform(post("/api/Account")
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
    void createNewAccount_lastNameTooShort() throws Exception {

        mvc.perform(post("/api/Account")
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
    void createNewAccount_lastNameTooLong() throws Exception {

        mvc.perform(post("/api/Account")
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
    void createNewAccount_lastNameBlank() throws Exception {

        mvc.perform(post("/api/Account")
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
    void createNewAccount_passwordTooShort() throws Exception {

        mvc.perform(post("/api/Account")
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
    void createNewAccount_passwordTooLong() throws Exception {

        mvc.perform(post("/api/Account")
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
    void createNewAccount_passwordBlank() throws Exception {

        mvc.perform(post("/api/Account")
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
    void createNewAccount_emailTooShort() throws Exception {

        mvc.perform(post("/api/Account")
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
    void createNewAccount_emailTooLong() throws Exception {

        mvc.perform(post("/api/Account")
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
    void createNewAccount_emailIncorrectFormat() throws Exception {

        mvc.perform(post("/api/Account")
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
    void createNewAccount_emailBlank() throws Exception {

        mvc.perform(post("/api/Account")
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