package com.gudmumic.spring.rest.repositories;

import com.gudmumic.spring.rest.entities.Beer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@SpringBootTest
@ActiveProfiles("docker-mysql")
public class MySqlTest {

    @Container
    static final MySQLContainer<?> mySqlTestContainer = new MySQLContainer<>("mysql:8.0.33");

    @DynamicPropertySource
    static void mySqlProperties(org.springframework.test.context.DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.username", mySqlTestContainer::getUsername);
        registry.add("spring.datasource.password", mySqlTestContainer::getPassword);
        registry.add("spring.datasource.url", mySqlTestContainer::getJdbcUrl);
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    BeerRepository beerRepository;

    @Test
    public void getBeerList() {
        List<Beer> beerList = beerRepository.findAll();

        assertThat(beerList).isNotNull();
        assertThat(beerList.size()).isGreaterThan(0);
    }
}

