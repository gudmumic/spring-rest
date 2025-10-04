package com.gudmumic.spring.rest.controller;

import com.gudmumic.spring.rest.model.Customer;
import com.gudmumic.spring.rest.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CustomerController {

    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";
    public static final String CUSTOMER_ID = "customerId";

    private final CustomerService customerService;

    @GetMapping(value = CUSTOMER_PATH)
    public List<Customer> getCustomerList() {
        return customerService.getCustomerList();
    }

    @GetMapping(value = CUSTOMER_PATH_ID)
    public Customer getCustomerByStyle(@PathVariable(CUSTOMER_ID) UUID customerId) {

        log.debug("Bet Customer by ID - from controller");

        return customerService.getCustomerById(customerId);
    }

    @PostMapping(value = CUSTOMER_PATH)
    public ResponseEntity createCustomer(@RequestBody Customer customer) {
        log.debug("Create new Customer - from controller");
        Customer newCustomer = customerService.createCustomer(customer);
        log.info("New Customer added to collection of Customers", newCustomer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", CUSTOMER_PATH_ID + newCustomer.getId().toString());

        return new ResponseEntity(newCustomer, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = CUSTOMER_PATH_ID)
    public ResponseEntity updateCustomer(@PathVariable(CUSTOMER_ID) UUID id, @RequestBody Customer customer) {
        log.debug("Update Customer - from controller");
        customerService.updateCustomer(id, customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", CUSTOMER_PATH_ID + id);

        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity deleteCustomer(@PathVariable(CUSTOMER_ID) UUID id) {
        log.debug("Delete a Customer - from controller");
        customerService.deleteCustomer(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", CUSTOMER_PATH_ID + id);

        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
    }

}
