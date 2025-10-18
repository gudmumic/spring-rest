package com.gudmumic.spring.rest.repositories;

import com.gudmumic.spring.rest.entities.Beer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@SpringBootTest
@ActiveProfiles("docker-mysql")
public class MySqlIT {

    @Container
    @ServiceConnection
    static final MySQLContainer<?> mySqlTestContainer = new MySQLContainer<>("mysql:9");

    @Autowired
    BeerRepository beerRepository;

    @Test
    public void getBeerList() {
        List<Beer> beerList = beerRepository.findAll();

        assertThat(beerList).isNotNull();
        assertThat(beerList.size()).isGreaterThan(0);
    }
}

