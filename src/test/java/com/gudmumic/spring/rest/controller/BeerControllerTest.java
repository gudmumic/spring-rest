package com.gudmumic.spring.rest.controller;

import com.gudmumic.spring.rest.model.Beer;
import com.gudmumic.spring.rest.service.BeerService;
import com.gudmumic.spring.rest.service.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl;

    Beer testBeer;

    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
        testBeer  = beerServiceImpl.getBeerList().get(0);
    }

    @Test
    void getBeerById() throws Exception {

        given(beerService.getBeerById(testBeer.getId())).willReturn(testBeer);

        mockMvc.perform(get("/api/v1/beer/" + testBeer.getId())
                .accept((MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.name", is(testBeer.getName())));
    }

    @Test
    void getBeerList() throws Exception {

        given(beerService.getBeerList()).willReturn(beerServiceImpl.getBeerList());

        mockMvc.perform(get("/api/v1/beer")
                .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(beerServiceImpl.getBeerList().size())));
    }

    @Test
    void createNewBeer() throws Exception {
        testBeer.setId(null);
        testBeer.setVersion(null);
        testBeer.setCreatedDate(null);
        testBeer.setUpdatedDate(null);

        given(beerService.createBeer(any(Beer.class))).willReturn(beerServiceImpl.getBeerList().get(1));

        mockMvc.perform(post("/api/v1/beer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBeer)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id", is(beerServiceImpl.getBeerList().get(1).getId().toString())))
                .andExpect(jsonPath("$.name", is(beerServiceImpl.getBeerList().get(1).getName())));
    }

    @Test
    void updateNewBeer() throws Exception {
        testBeer.setName("My New Beer Name");

        mockMvc.perform(put("/api/v1/beer/" + testBeer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBeer)));

        verify(beerService).updateBeer(any(UUID.class), any(Beer.class));
    }
}