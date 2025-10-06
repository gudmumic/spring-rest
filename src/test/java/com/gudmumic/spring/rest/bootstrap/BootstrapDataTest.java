package com.gudmumic.spring.rest.bootstrap;

import com.gudmumic.spring.rest.repositories.BeerRepository;
import com.gudmumic.spring.rest.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class BootstrapDataTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(beerRepository, customerRepository);
    }

    @Test
    void run() throws Exception {
        assertThat(beerRepository.count()).isEqualTo(0);
        assertThat(customerRepository.count()).isEqualTo(0);

        bootstrapData.run();

        assertThat(beerRepository.count()).isEqualTo(5);
        assertThat(customerRepository.count()).isEqualTo(2);
    }
}