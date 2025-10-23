package com.gudmumic.spring.rest.repositories;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.gudmumic.spring.rest.entities.Beer;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@ActiveProfiles("docker-mysql")
public class MySqlIT {

    @Container
    @ServiceConnection
    static final MySQLContainer<?> mySqlTestContainer = new MySQLContainer<>("mysql:9");

/*
    @DynamicPropertySource
    static void mySqlProperties(org.springframework.test.context.DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySqlTestContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySqlTestContainer::getUsername);
        registry.add("spring.datasource.password", mySqlTestContainer::getPassword);
    }

    @Autowired
    DataSource dataSource;
*/

    @Autowired
    BeerRepository beerRepository;

    @Test
    public void getBeerList() {
        List<Beer> beerList = beerRepository.findAll();

        assertThat(beerList).isNotNull();
        assertThat(beerList.size()).isGreaterThan(0);
    }
}

