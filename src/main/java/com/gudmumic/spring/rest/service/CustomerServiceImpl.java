package com.gudmumic.spring.rest.service;

import com.gudmumic.spring.rest.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    Map<UUID, Customer> customerMap;

    public CustomerServiceImpl() {

        log.debug("Initializing customers");

        customerMap = new HashMap<>();

        Customer michael = Customer.builder()
                                .id(UUID.randomUUID())
                                .name("Michael Nielsen")
                                .version(58)
                                .createdDate(LocalDateTime.now())
                                .updatedDate(LocalDateTime.now())
                                .build();

        Customer marianne = Customer.builder()
                                .id(UUID.randomUUID())
                                .name("Marianne Gudmundseth Nielsen")
                                .version(56)
                                .createdDate(LocalDateTime.now())
                                .updatedDate(LocalDateTime.now())
                                .build();

        customerMap.put(michael.getId(), michael);
        customerMap.put(marianne.getId(), marianne);
    }

    @Override
    public List<Customer> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getCustomerById(UUID id) {
        return customerMap.get(id);
    }

    @Override
    public Customer createCustomer(Customer customer) {

        Customer newCustomer = Customer.builder()
                                        .id(UUID.randomUUID())
                                        .name(customer.getName())
                                        .version(1)
                                        .createdDate(LocalDateTime.now())
                                        .updatedDate(LocalDateTime.now())
                                        .build();

        customerMap.put(newCustomer.getId(), newCustomer);
        return newCustomer;
    }

    @Override
    public Customer updateCustomer(UUID id, Customer customer) {
        if(customerMap.containsKey(id)) {
            log.debug("updating customer with id" + id);
            Customer updatedCustomer = Customer.builder()
                                                .id(id)
                                                .name(customer.getName())
                                                .version(customerMap.get(id).getVersion() + 1)
                                                .createdDate(customerMap.get(id).getCreatedDate())
                                                .updatedDate(LocalDateTime.now())
                                                .build();

            customerMap.put(id, updatedCustomer);

            log.info("Updated Customer added to collection of Customers", updatedCustomer);

            return updatedCustomer;
        }
        return null;
    }

    @Override
    public void deleteCustomer(UUID id) {
        customerMap.remove(id);
    }
}
