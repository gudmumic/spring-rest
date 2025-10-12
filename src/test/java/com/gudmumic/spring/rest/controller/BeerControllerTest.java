package com.gudmumic.spring.rest.controller;

import com.gudmumic.spring.rest.model.BeerDTO;
import com.gudmumic.spring.rest.service.BeerService;
import com.gudmumic.spring.rest.service.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<BeerDTO> beerArgumentCaptor;

    BeerServiceImpl beerServiceImpl;

    BeerDTO testBeerDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        beerServiceImpl = new BeerServiceImpl();
        testBeerDTO = beerServiceImpl.getBeerList(null).get(0);
    }

    @Test
    void getBeerById() throws Exception {

        given(beerService.getBeerById(testBeerDTO.getId())).willReturn(Optional.of(testBeerDTO));

        mockMvc.perform(get(BeerController.BEER_PATH_ID, testBeerDTO.getId())
                .accept((MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeerDTO.getId().toString())))
                .andExpect(jsonPath("$.name", is(testBeerDTO.getName())));
    }

    @Test
    void getBeerByIdNotFound() throws Exception {

        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(BeerController.BEER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getBeerList() throws Exception {

        given(beerService.getBeerList(null)).willReturn(beerServiceImpl.getBeerList(null));

        mockMvc.perform(get(BeerController.BEER_PATH)
                .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(beerServiceImpl.getBeerList(null).size())));
    }

    @Test
    void createNewBeer() throws Exception {
        testBeerDTO.setId(null);
        testBeerDTO.setVersion(null);
        testBeerDTO.setCreatedDate(null);
        testBeerDTO.setUpdatedDate(null);

        given(beerService.createBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.getBeerList(null).get(1));

        mockMvc.perform(post(BeerController.BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBeerDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id", is(beerServiceImpl.getBeerList(null).get(1).getId().toString())))
                .andExpect(jsonPath("$.name", is(beerServiceImpl.getBeerList(null).get(1).getName())));
    }

    @Test
    void createNewBeerNotValid() throws Exception {
        testBeerDTO.setId(null);
        testBeerDTO.setVersion(null);
        testBeerDTO.setName(null);
        testBeerDTO.setPrice(BigDecimal.valueOf(-1.00));
        testBeerDTO.setUpc("");
        testBeerDTO.setStyle(null);
        testBeerDTO.setCreatedDate(null);
        testBeerDTO.setUpdatedDate(null);

        given(beerService.createBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.getBeerList(null).get(1));

        MvcResult mvcResult = mockMvc.perform(post(BeerController.BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBeerDTO)))
                        .andExpect(status().isBadRequest()).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void updateNewBeer() throws Exception {
        testBeerDTO.setName("My New Beer Name");

        given(beerService.updateBeer(any(UUID.class), any(BeerDTO.class))).willReturn(Optional.of(testBeerDTO));

        mockMvc.perform(put(BeerController.BEER_PATH_ID, testBeerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBeerDTO)))
                        .andExpect(status().isNoContent());

        verify(beerService).updateBeer(any(UUID.class), any(BeerDTO.class));
    }

    @Test
    void updateBeerNotValid() throws Exception {
        testBeerDTO.setName("My New Beer Name");
        testBeerDTO.setPrice(BigDecimal.valueOf(-1.00));
        testBeerDTO.setUpc("");
        testBeerDTO.setStyle(null);

        given(beerService.updateBeer(any(UUID.class), any(BeerDTO.class))).willReturn(Optional.of(testBeerDTO));

        MvcResult mvcResult = mockMvc.perform(put(BeerController.BEER_PATH_ID, testBeerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBeerDTO)))
                        .andExpect(status().isBadRequest()).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void deleteBeer() throws Exception {

        given(beerService.deleteBeer(any())).willReturn(true);

        mockMvc.perform(delete(BeerController.BEER_PATH_ID, testBeerDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());

        verify(beerService).deleteBeer(uuidArgumentCaptor.capture());

        assertThat(uuidArgumentCaptor.getValue().equals(testBeerDTO.getId()));
    }
}