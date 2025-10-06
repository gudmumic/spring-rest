package com.gudmumic.spring.rest.service;

import com.gudmumic.spring.rest.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<CustomerDTO> getCustomerList();

    Optional<CustomerDTO> getCustomerById(UUID id);

    CustomerDTO createCustomer(CustomerDTO customerDTO);

    Optional<CustomerDTO> updateCustomer(UUID id, CustomerDTO customerDTO);

    Boolean deleteCustomer(UUID id);
}
