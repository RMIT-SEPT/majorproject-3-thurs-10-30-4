package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.model.Account;
import com.sept.Thur10304.BookingSystem.model.Admin;
import com.sept.Thur10304.BookingSystem.model.Service_;
import com.sept.Thur10304.BookingSystem.repositories.AccountRepository;
import com.sept.Thur10304.BookingSystem.repositories.AdminRepository;
import com.sept.Thur10304.BookingSystem.repositories.CustomerRepository;
import com.sept.Thur10304.BookingSystem.repositories.Service_Repository;
import com.sept.Thur10304.BookingSystem.repositories.WorkerRepository;
import com.sept.Thur10304.BookingSystem.services.Service_Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import javax.annotation.Resource;
import com.fasterxml.jackson.databind.ObjectMapper;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;


@SpringBootTest
@AutoConfigureMockMvc
class Service_Controller_CreateService {

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
    private Service_Repository serviceRepository;

    @MockBean
    private Service_Service service;

    Service_ service1;
    Account adminBeforeCreate;
    int adminId;
    String adminToken;

    @BeforeEach
    void setUp()  throws Exception{
        // Clears all repositories
        serviceRepository.deleteAll();
        workerRepository.deleteAll();
        adminRepository.deleteAll();
        customerRepository.deleteAll();
        accountRepository.deleteAll();

        // Creates admin account entity
        adminBeforeCreate = new Account();
        adminBeforeCreate.setFirstName("Jeremy");
        adminBeforeCreate.setLastName("Jamm");
        adminBeforeCreate.setPassword("YouJustGotJammed");
        adminBeforeCreate.setEmail("jeremy.jamm@pawnee.gov");

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

    @AfterEach
    void cleanUp() throws Exception {
        // Clears all repositories
        serviceRepository.deleteAll();
        workerRepository.deleteAll();
        adminRepository.deleteAll();
        customerRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void createNewService_accepted() throws Exception {
        service1 = new Service_();
        service1.setServiceName("Sweetums");
        service1.setServiceDescription("If you can't beat 'em, Sweetums.");

        // given(service.saveOrUpdateService(any(Service_.class))).willReturn(serviceRepository.save(service1)); needs updating
        // Create request string with admin's id and login token
        String request = String.format("/api/service/save/%d?token=%s", adminId, adminToken);
        // Post service creation request
        mvc.perform(post(request)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"serviceName\": \"Sweetums\",\n" +
                        "    \"serviceDescription\": \"If you can't beat 'em, Sweetums.\"\n" +
                        "}"))
                .andExpect(status().isCreated());
    }

    @Test
    void createNewService_nameTooSmall() throws Exception {
        // Create request string with admin's id and login token
        String request = String.format("/api/service/save/%d?token=%s", adminId, adminToken);
        // Post service creation request
        mvc.perform(post(request)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"serviceName\": \"S\",\n" +
                        "    \"serviceDescription\": \"If you can't beat 'em, Sweetums.\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewService_nameTooLong() throws Exception {
        // Create request string with admin's id and login token
        String request = String.format("/api/service/save/%d?token=%s", adminId, adminToken);
        // Post service creation request
        mvc.perform(post(request)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"serviceName\": \"Sweetums: Leslie Knope hates us! She doesn't want you to enjoy a nice cold glass of sugar water.\",\n" +
                        "    \"serviceDescription\": \"If you can't beat 'em, Sweetums.\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewService_nameBlank() throws Exception {
        // Create request string with admin's id and login token
        String request = String.format("/api/service/save/%d?token=%s", adminId, adminToken);
        // Post service creation request
        mvc.perform(post(request)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"serviceDescription\": \"If you can't beat 'em, Sweetums.\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewService_descTooSmall() throws Exception {
        // Create request string with admin's id and login token
        String request = String.format("/api/service/save/%d?token=%s", adminId, adminToken);
        // Post service creation request
        mvc.perform(post(request)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"serviceName\": \"Sweetums\",\n" +
                        "    \"serviceDescription\": \"Vote BN\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewService_descTooLong() throws Exception {
        // Create request string with admin's id and login token
        String request = String.format("/api/service/save/%d?token=%s", adminId, adminToken);
        // Post service creation request
        mvc.perform(post(request)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"serviceName\": \"Sweetums\",\n" +
                        "    \"serviceDescription\": \"If you can't beat 'em, Sweetums. Sweetums was formerly owned by Nick Newport, Sr., when it used corn syrup to fatten cows, which would later be slaughtered. It is currently operated by Nick Newport, Jr. who appears in Sweetums' commercials with his dog Shoelace and his children Dakota Newport and Denver Newport. The pollution... wait no, leftover treats! from the factory results in beautiful sunsets over the city of Pawnee. Leslie Knope hates beautiful sunsets. She doesn't want Eagleton to be worse than us, she wants them to be better. She wasn't even born in Pawnee, she is an Eagletonian. Buy our NutriYums.\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewService_descBlank() throws Exception {
        // Create request string with admin's id and login token
        String request = String.format("/api/service/save/%d?token=%s", adminId, adminToken);
        // Post service creation request
        mvc.perform(post(request)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"serviceName\": \"Sweetums\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

}