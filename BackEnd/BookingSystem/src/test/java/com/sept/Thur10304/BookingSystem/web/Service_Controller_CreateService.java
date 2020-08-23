package com.sept.Thur10304.BookingSystem.web;

import com.sept.Thur10304.BookingSystem.model.Service_;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class Service_Controller_CreateService {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private Service_Repository serviceRepository;

    @MockBean
    private Service_Service service;

    Service_ service1;

    @BeforeEach
    void setUp() {

    }

    @Test
    void createNewService_accepted() throws Exception {
        service1 = new Service_();
        service1.setServiceName("Sweetums");
        service1.setServiceDescription("If you can't beat 'em, Sweetums.");

        given(service.saveOrUpdateService(any(Service_.class))).willReturn(serviceRepository.save(service1));

        // When
        mvc.perform(post("/api/service/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"serviceName\": \"Sweetums\",\n" +
                        "    \"serviceDescription\": \"If you can't beat 'em, Sweetums.\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.serviceId").value("1"))
                .andExpect(jsonPath("$.serviceName").value("Sweetums"));
    }

    @Test
    void createNewService_nameTooSmall() throws Exception {
        // When
        mvc.perform(post("/api/service/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"serviceName\": \"S\",\n" +
                        "    \"serviceDescription\": \"If you can't beat 'em, Sweetums.\"\n" +
                        "}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid Service Object"));
    }

    @Test
    void createNewService_nameTooLong() throws Exception {
        // When
        mvc.perform(post("/api/service/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"serviceName\": \"Sweetums: Leslie Knope hates us!\",\n" +
                        "    \"serviceDescription\": \"If you can't beat 'em, Sweetums.\"\n" +
                        "}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid Service Object"));
    }

    @Test
    void createNewService_nameBlank() throws Exception {
        // When
        mvc.perform(post("/api/service/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"serviceDescription\": \"If you can't beat 'em, Sweetums.\"\n" +
                        "}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid Service Object"));
    }

    @Test
    void createNewService_descTooSmall() throws Exception {
        // When
        mvc.perform(post("/api/service/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"serviceName\": \"Sweetums\",\n" +
                        "    \"serviceDescription\": \"Vote BN\"\n" +
                        "}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid Service Object"));
    }

    @Test
    void createNewService_descTooLong() throws Exception {
        // When
        mvc.perform(post("/api/service/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"serviceName\": \"Sweetums\",\n" +
                        "    \"serviceDescription\": \"If you can't beat 'em, Sweetums. Sweetums was formerly owned by Nick Newport, Sr., when it used corn syrup to fatten cows, which would later be slaughtered. It is currently operated by Nick Newport, Jr. who appears in Sweetums' commercials with his dog Shoelace and his children Dakota Newport and Denver Newport.\"\n" +
                        "}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid Service Object"));
    }

    @Test
    void createNewService_descBlank() throws Exception {
        // When
        mvc.perform(post("/api/service/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"serviceName\": \"Sweetums\"\n" +
                        "}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid Service Object"));
    }

}