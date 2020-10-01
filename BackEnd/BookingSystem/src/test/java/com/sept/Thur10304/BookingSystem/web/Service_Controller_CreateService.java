package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.model.Account;
import com.sept.Thur10304.BookingSystem.model.Admin;
import com.sept.Thur10304.BookingSystem.model.Service_;
import com.sept.Thur10304.BookingSystem.repositories.AccountRepository;
import com.sept.Thur10304.BookingSystem.repositories.AdminRepository;
import com.sept.Thur10304.BookingSystem.repositories.Service_Repository;
import com.sept.Thur10304.BookingSystem.services.Service_Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import javax.annotation.Resource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class Service_Controller_CreateService {

    @Autowired
    private MockMvc mvc;

    @Resource
    private AccountRepository accountRepository;

    @MockBean
    private Service_Service service;

    Service_ service1;

    @BeforeEach
    void setUp() {
//        Account account1 = new Account();
//        account1.setEmail("jeremy.jamm@pawnee.gov");
//        account1.setFirstName("Jeremy");
//        account1.setLastName("Jamm");
//        account1.setId((long) 1);
//        account1.setPassword("YouJustGotJammed");
//        accountRepository.save(account1);
    }

    @Test
    void createNewService_accepted() throws Exception {
        service1 = new Service_();
        service1.setServiceName("Sweetums");
        service1.setServiceDescription("If you can't beat 'em, Sweetums.");

        // given(service.saveOrUpdateService(any(Service_.class))).willReturn(serviceRepository.save(service1)); needs updating

        // When
        mvc.perform(post("/api/service/save/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"serviceName\": \"Sweetums\",\n" +
                        "    \"serviceDescription\": \"If you can't beat 'em, Sweetums.\"\n" +
                        "}"))
                .andExpect(status().isCreated());
    }

    @Test
    void createNewService_nameTooSmall() throws Exception {
        // When
        mvc.perform(post("/api/service/save/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"serviceName\": \"S\",\n" +
                        "    \"serviceDescription\": \"If you can't beat 'em, Sweetums.\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewService_nameTooLong() throws Exception {
        // When
        mvc.perform(post("/api/service/save/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"serviceName\": \"Sweetums: Leslie Knope hates us! She doesn't want you to enjoy a nice cold glass of sugar water.\",\n" +
                        "    \"serviceDescription\": \"If you can't beat 'em, Sweetums.\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewService_nameBlank() throws Exception {
        // When
        mvc.perform(post("/api/service/save/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"serviceDescription\": \"If you can't beat 'em, Sweetums.\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewService_descTooSmall() throws Exception {
        // When
        mvc.perform(post("/api/service/save/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"serviceName\": \"Sweetums\",\n" +
                        "    \"serviceDescription\": \"Vote BN\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewService_descTooLong() throws Exception {
        // When
        mvc.perform(post("/api/service/save/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"serviceName\": \"Sweetums\",\n" +
                        "    \"serviceDescription\": \"If you can't beat 'em, Sweetums. Sweetums was formerly owned by Nick Newport, Sr., when it used corn syrup to fatten cows, which would later be slaughtered. It is currently operated by Nick Newport, Jr. who appears in Sweetums' commercials with his dog Shoelace and his children Dakota Newport and Denver Newport. The pollution... wait no, leftover treats! from the factory results in beautiful sunsets over the city of Pawnee. Leslie Knope hates beautiful sunsets. She doesn't want Eagleton to be worse than us, she wants them to be better. She wasn't even born in Pawnee, she is an Eagletonian. Buy our NutriYums.\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewService_descBlank() throws Exception {
        // When
        mvc.perform(post("/api/service/save/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"serviceName\": \"Sweetums\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

}