package com.gudmumic.spring.rest.controller;

import com.gudmumic.spring.rest.model.BeerDTO;
import com.gudmumic.spring.rest.model.CustomerDTO;
import com.gudmumic.spring.rest.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void getCustomerList() {
        List<CustomerDTO> customerDTOList = customerController.getCustomerList();
        assertThat(customerDTOList.size()).isEqualTo(2);
    }

    @Rollback
    @Transactional
    @Test
    void getEmptyCustomerList() {
        customerRepository.deleteAll();
        List<CustomerDTO> customerDTOList = customerController.getCustomerList();
        assertThat(customerDTOList.size()).isEqualTo(0);
    }

    @Test
    void getCustomerById() {
        CustomerDTO customerDTO = customerController.getCustomerById(customerRepository.findAll().get(0).getId());
        assertThat(customerDTO).isNotNull();
    }

    @Test
    void getBeerByIdNotFound() {

        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(java.util.UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void createNewCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("New Customer")
                .build();
        ResponseEntity response = customerController.createCustomer(customerDTO);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().get("Location").get(0)).isNotEmpty();
        assertThat(response.getBody()).isNotNull();

        String[] locationUUID = response.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[locationUUID.length - 1]);
        assertThat(customerRepository.findById(savedUUID).isPresent()).isTrue();
    }

    @Rollback
    @Transactional
    @Test
    void updateCustomer() {
        CustomerDTO customerDTO = customerController.getCustomerList().get(0);
        CustomerDTO updatedCustomerDTO = CustomerDTO.builder()
                                                    .name("Updated Customer")
                                                    .build();
        ResponseEntity response = customerController.updateCustomer(customerDTO.getId(), updatedCustomerDTO);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        CustomerDTO fetchedCustomerDTO = customerController.getCustomerById(customerDTO.getId());
        assertThat(fetchedCustomerDTO.getName()).isEqualTo("Updated Customer");
    }

    @Rollback
    @Transactional
    @Test
    void updateCustomerNotFound() {
        assertThrows(NotFoundException.class , () -> {
            customerController.updateCustomer(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void deleteCustomer() {
        CustomerDTO customerDTO = customerController.getCustomerList().get(0);
        ResponseEntity response = customerController.deleteCustomer(customerDTO.getId());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(customerRepository.findById(customerDTO.getId()).isEmpty()).isTrue();
        assertThat(response.getHeaders().getLocation()).isNotNull();
    }

    @Rollback
    @Transactional
    @Test
    void deleteCustomerNotFound() {
        assertThrows(NotFoundException.class , () -> {
            customerController.deleteCustomer(UUID.randomUUID());
        });
    }

}