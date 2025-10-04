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
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> getCustomerList() {
        return customerService.getCustomerList();
    }

    @RequestMapping(value = "{customerId}", method = RequestMethod.GET)
    public Customer getCustomerByStyle(@PathVariable("customerId") UUID customerId) {

        log.debug("Bet Customer by ID - from controller");

        return customerService.getCustomerById(customerId);
    }

    @PostMapping
    public ResponseEntity createCustomer(@RequestBody Customer customer) {
        log.debug("Create new Customer - from controller");
        Customer newCustomer = customerService.createCustomer(customer);
        log.info("New Customer added to collection of Customers", newCustomer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + newCustomer.getId().toString());

        return new ResponseEntity(newCustomer, headers, HttpStatus.CREATED);
    }

    @PutMapping("{customerId}")
    public ResponseEntity updateCustomer(@PathVariable("customerId") UUID id, @RequestBody Customer customer) {
        log.debug("Update Customer - from controller");
        customerService.updateCustomer(id, customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + id);

        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity deleteCustomer(@PathVariable("customerId") UUID id) {
        log.debug("Delete a Customer - from controller");
        customerService.deleteCustomer(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + id);

        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
    }

}
