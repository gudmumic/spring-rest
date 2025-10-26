package com.gudmumic.spring.rest.repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.gudmumic.spring.rest.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class BeerOrderRepositoryTest {

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    Beer testBeer;
    Customer testCustomer;

    @BeforeEach
    void setUp() {
        testBeer = beerRepository.findAll().get(0);
        testCustomer = customerRepository.findAll().get(0);
    }

    @Transactional
    @Test
    public void getBeerOrdersTtest() {

        BeerOrder beerOrder = BeerOrder.builder()
                                       .customer(testCustomer)
                                       .customerRef("Test Customer Ref")
                                        .beerOrderShipment(BeerOrderShipment.builder()
                                                                            .trackingNumber("TRACK12345")
                                                                            .build())
                                       .build();
        BeerOrder savedBeerOrder = beerOrderRepository.save(beerOrder);

        assertNotNull(savedBeerOrder);
        assertEquals(testCustomer.getId(), savedBeerOrder.getCustomer().getId());
        assertEquals(testCustomer.getBeerOrders().size(), savedBeerOrder.getCustomer().getBeerOrders().size());

        System.out.println("Saved BeerOrder Customer Ref: " + savedBeerOrder.getCustomerRef());
    }
}
