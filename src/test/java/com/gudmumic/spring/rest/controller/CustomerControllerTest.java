package com.gudmumic.spring.rest.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gudmumic.spring.rest.TestConstands;
import com.gudmumic.spring.rest.model.CustomerDTO;
import com.gudmumic.spring.rest.service.CustomerService;
import com.gudmumic.spring.rest.service.CustomerServiceImpl;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureWebMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(CustomerController.class)
@AutoConfigureWebMvc
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @MockitoBean
    CustomerService customerService;

    CustomerServiceImpl customerServiceImpl;

    CustomerDTO testCustomerDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerServiceImpl = new CustomerServiceImpl();
        testCustomerDTO = customerServiceImpl.getCustomerList().get(0);
    }

    @Test
    void getCustomerById() throws Exception {

        given(customerService.getCustomerById(testCustomerDTO.getId())).willReturn(Optional.ofNullable(testCustomerDTO));

        mockMvc.perform(get( CustomerController.CUSTOMER_PATH_ID, testCustomerDTO.getId())
                        .with(httpBasic(TestConstands.USER, TestConstands.PASSWORD))
                .accept((MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testCustomerDTO.getId().toString())))
                .andExpect(jsonPath("$.name", is(testCustomerDTO.getName())));
    }

    @Test
    void getCustomerByIdNotFound() throws Exception {

        given(customerService.getCustomerById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get( CustomerController.CUSTOMER_PATH_ID, UUID.randomUUID())
                .with(httpBasic(TestConstands.USER, TestConstands.PASSWORD)))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCustomerList() throws Exception {

        given(customerService.getCustomerList()).willReturn(customerServiceImpl.getCustomerList());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH)
                .with(httpBasic(TestConstands.USER, TestConstands.PASSWORD))
                .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(customerServiceImpl.getCustomerList().size())));
    }

    @Test
    void createNewCustomer() throws Exception {
        testCustomerDTO.setId(null);
        testCustomerDTO.setVersion(null);
        testCustomerDTO.setCreatedDate(null);
        testCustomerDTO.setUpdatedDate(null);

        given(customerService.createCustomer(any(CustomerDTO.class))).willReturn(customerServiceImpl.getCustomerList().get(1));

        mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomerDTO))
                .with(httpBasic(TestConstands.USER, TestConstands.PASSWORD)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id", is(customerServiceImpl.getCustomerList().get(1).getId().toString())))
                .andExpect(jsonPath("$.name", is(customerServiceImpl.getCustomerList().get(1).getName())));
    }

    @Test
    void updateNewCustomer() throws Exception {
        testCustomerDTO.setName("My New Beer Name");

        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID, testCustomerDTO.getId())
                .with(httpBasic(TestConstands.USER, TestConstands.PASSWORD))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomerDTO)));

        verify(customerService).updateCustomer(any(UUID.class), any(CustomerDTO.class));
    }

    @Test
    void deleteCustomer() throws Exception {

        given(customerService.deleteCustomer(any(UUID.class))).willReturn(true);

        mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID, testCustomerDTO.getId())
                .with(httpBasic(TestConstands.USER, TestConstands.PASSWORD))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService).deleteCustomer(uuidArgumentCaptor.capture());

        assertThat(uuidArgumentCaptor.getValue().equals(testCustomerDTO.getId()));
    }

}