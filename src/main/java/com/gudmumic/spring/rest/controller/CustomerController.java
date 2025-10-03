package com.gudmumic.spring.rest.controller;

import com.gudmumic.spring.rest.model.Beer;
import com.gudmumic.spring.rest.model.Customer;
import com.gudmumic.spring.rest.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> getCustomerList() {
        return customerService.listCustomers();
    }

    @RequestMapping(value = "{customerId}", method = RequestMethod.GET)
    public Customer getCustomerByStyle(@PathVariable("customerId") UUID customerId) {

        log.debug("Bet Customer by ID - from controller");

        return customerService.getCustomerById(customerId);
    }
}
