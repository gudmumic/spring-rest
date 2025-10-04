package com.gudmumic.spring.rest.controller;

import com.gudmumic.spring.rest.model.Customer;
import com.gudmumic.spring.rest.service.CustomerService;
import com.gudmumic.spring.rest.service.CustomerServiceImpl;
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
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
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

    Customer testCustomer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerServiceImpl = new CustomerServiceImpl();
        testCustomer = customerServiceImpl.getCustomerList().get(0);
    }

    @Test
    void getCustomerById() throws Exception {

        given(customerService.getCustomerById(testCustomer.getId())).willReturn(testCustomer);

        mockMvc.perform(get( CustomerController.CUSTOMER_PATH_ID, testCustomer.getId())
                .accept((MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testCustomer.getId().toString())))
                .andExpect(jsonPath("$.name", is(testCustomer.getName())));
    }

    @Test
    void getCustomerList() throws Exception {

        given(customerService.getCustomerList()).willReturn(customerServiceImpl.getCustomerList());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH)
                .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(customerServiceImpl.getCustomerList().size())));
    }

    @Test
    void createNewCustomer() throws Exception {
        testCustomer.setId(null);
        testCustomer.setVersion(null);
        testCustomer.setCreatedDate(null);
        testCustomer.setUpdatedDate(null);

        given(customerService.createCustomer(any(Customer.class))).willReturn(customerServiceImpl.getCustomerList().get(1));

        mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id", is(customerServiceImpl.getCustomerList().get(1).getId().toString())))
                .andExpect(jsonPath("$.name", is(customerServiceImpl.getCustomerList().get(1).getName())));
    }

    @Test
    void updateNewCustomer() throws Exception {
        testCustomer.setName("My New Beer Name");

        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID, testCustomer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomer)));

        verify(customerService).updateCustomer(any(UUID.class), any(Customer.class));
    }

    @Test
    void deleteCustomer() throws Exception {

        mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID, testCustomer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService).deleteCustomer(uuidArgumentCaptor.capture());

        assertThat(uuidArgumentCaptor.getValue().equals(testCustomer.getId()));
    }

}