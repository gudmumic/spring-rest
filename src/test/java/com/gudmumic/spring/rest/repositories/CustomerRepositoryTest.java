package com.gudmumic.spring.rest.repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.gudmumic.spring.rest.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testSaveCustomer() {
        Customer customer = customerRepository.save(Customer.builder()
                                                            .name("Marianne Gudmundseth Nielsen")
                                                            .build());
        assertThat(customer).isNotNull();
        assertThat(customer.getId()).isNotNull();
    }
}