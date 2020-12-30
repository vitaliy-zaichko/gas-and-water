package com.coolcompany.gasandwaterusage.controllers;

import com.coolcompany.gasandwaterusage.model.Measurement;
import com.coolcompany.gasandwaterusage.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GasAndWaterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testGetUsers() throws Exception {
        String json = mockMvc.perform(
                MockMvcRequestBuilders.get("/measurements/users"))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<User> allUsers = objectMapper.readValue(json, List.class);
        assertThat(allUsers).isNotEmpty();
    }

    @Test
    void testGetMeasurements() throws Exception {
        String json = mockMvc.perform(
                MockMvcRequestBuilders.get("/measurements/users/{userId}", "1a1407a6-ed6e-4f35-ad3d-840c9b9335d6"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Measurement> userMms = objectMapper.readValue(json, List.class);
        assertThat(userMms).isNotEmpty();
    }


    @Test
    void testAddMeasurement() throws Exception {
        Measurement m = Measurement.builder()
                .hotWater(10)
                .coldWater(10)
                .gas(10).build();

        MockHttpServletRequestBuilder req =
                post("/measurements/users/{userId}", "1a1407a6-ed6e-4f35-ad3d-840c9b9335d6")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(m));

        String json = mockMvc.perform(req)
                .andReturn()
                .getResponse()
                .getContentAsString();

        Measurement resultMmt = objectMapper.readValue(json, Measurement.class);
        assertThat(resultMmt.getId()).isNotBlank();
        assertThat(resultMmt.getMeasurementDate()).isNotNull();
    }

    @Test
    void testWrongUser() throws Exception {
        Measurement m = Measurement.builder()
                .hotWater(10)
                .coldWater(10)
                .gas(10)
                .build();

        UUID wrongUserId = UUID.randomUUID();

        MockHttpServletRequestBuilder req = post("/measurements/users/{userId}", wrongUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(m));

        mockMvc.perform(req).andExpect(status().is5xxServerError());
    }

    @Test
    void testWrongHotWaterValue() throws Exception {
        Measurement m = Measurement.builder()
                .measurementDate(LocalDate.now().plus(1, ChronoUnit.DAYS))
                .hotWater(0)
                .build();

        UUID wrongUserId = UUID.randomUUID();

        MockHttpServletRequestBuilder req = post("/measurements/users/{userId}", wrongUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(m));

        String resp = mockMvc.perform(req).andExpect(status().isBadRequest()).andDo(print()).andReturn().getResponse().getContentAsString();
        Map<String, String> res = objectMapper.readValue(resp, Map.class);
        assertThat(res.get("measurementDate")).isEqualTo("must be a date in the past or in the present");
        assertThat(res.get("coldWater")).isEqualTo("must not be null");
        assertThat(res.get("hotWater")).isEqualTo("Must be greater then zero");
    }
}