package com.gudmumic.spring.rest.service;

import com.gudmumic.spring.rest.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<Customer> getCustomerList();

    Optional<Customer> getCustomerById(UUID id);

    Customer createCustomer(Customer customer);

    void updateCustomer(UUID id, Customer customer);

    void deleteCustomer(UUID id);
}
