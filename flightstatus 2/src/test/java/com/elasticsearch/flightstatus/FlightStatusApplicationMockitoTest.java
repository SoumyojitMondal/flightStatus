package com.elasticsearch.flightstatus;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.elasticsearch.flightstatus.controller.FlightStatusController;
import com.elasticsearch.flightstatus.entity.FlightEvent;
import com.elasticsearch.flightstatus.entity.SearchDTO;
import com.elasticsearch.flightstatus.repository.FlightEventRepo;
import com.elasticsearch.flightstatus.service.FlightStatusService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(FlightStatusController.class)
@AutoConfigureMockMvc(addFilters = false)
public class FlightStatusApplicationMockitoTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private FlightStatusService service;

    @MockBean
    private FlightEventRepo flightEventRepo;

    @Before
    public void setUp() {
        flightEventRepo.deleteAll();
    }


    @Test
    public void testCreateStatusWithValidInputs() throws Exception 
    {
        FlightEvent flightEvent = new FlightEvent("EW1234","Scheduled","2023-08-03 10:10:10");
        mvc.perform( MockMvcRequestBuilders
            .post("/setStatus")
            .content(asJsonString(flightEvent))
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
            .accept(org.springframework.http.MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCreateStatusInvalidEventTimeFormat() throws Exception 
    {
        FlightEvent flightEvent = new FlightEvent("EW1234","Scheduled","03-08-2023 10:10:10");
        mvc.perform( MockMvcRequestBuilders
            .post("/setStatus")
            .content(asJsonString(flightEvent))
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
            .accept(org.springframework.http.MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().string("{\"eventTime\":\"Input must be in YYYY-MM-DD HH:MM:SS format\"}"));

    }

    @Test
    public void testCreateStatusInvalidEventName() throws Exception 
    {
        FlightEvent flightEvent = new FlightEvent("EW1234","","2023-08-03 10:10:10");
        mvc.perform( MockMvcRequestBuilders
            .post("/setStatus")
            .content(asJsonString(flightEvent))
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
            .accept(org.springframework.http.MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().string("{\"eventName\":\"Event Name must not be blank\"}"));

    }

    @Test
    public void testCreateStatusInvalidFlightNo() throws Exception 
    {
        FlightEvent flightEvent = new FlightEvent("EW123477","Scheduled","2023-08-03 10:10:10");
        mvc.perform( MockMvcRequestBuilders
            .post("/setStatus")
            .content(asJsonString(flightEvent))
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
            .accept(org.springframework.http.MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().string("{\"flightNo\":\"Flight no. must not be more than 6 chracter long\"}"));

    }

    @Test
    public void testGetStatusWithValidInputs() throws Exception 
    {
        SearchDTO searchDTO = new SearchDTO("EW1234", "2023-08-03");
        mvc.perform( MockMvcRequestBuilders
            .get("/getStatus")
            .content(asJsonString(searchDTO))
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
            .accept(org.springframework.http.MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testGetStatusWithInvalidFlightNo() throws Exception 
    {
        SearchDTO searchDTO = new SearchDTO("EW123499", "2023-08-03");
        mvc.perform( MockMvcRequestBuilders
            .get("/getStatus")
            .content(asJsonString(searchDTO))
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
            .accept(org.springframework.http.MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().string("{\"flightNo\":\"Flight no. must not be more than 6 chracter long\"}"));

    }

    @Test
    public void testGetStatusWithInvalidFlightDate() throws Exception 
    {
        SearchDTO searchDTO = new SearchDTO("EW1234", "10-10-2023");
        mvc.perform( MockMvcRequestBuilders
            .get("/getStatus")
            .content(asJsonString(searchDTO))
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
            .accept(org.springframework.http.MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().string("{\"flightDate\":\"Input must be in YYYY-MM-DD format\"}"));

    }
 
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
