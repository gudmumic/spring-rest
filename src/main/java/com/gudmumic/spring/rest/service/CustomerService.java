package com.gudmumic.spring.rest.service;

import com.gudmumic.spring.rest.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<Customer> listCustomers();

    Customer getCustomerById(UUID id);

    Customer createCustomer(Customer customer);

    Customer updateCustomer(UUID id, Customer customer);
}
