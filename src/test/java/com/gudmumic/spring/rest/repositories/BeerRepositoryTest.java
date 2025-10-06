package com.gudmumic.spring.rest.repositories;

import com.gudmumic.spring.rest.entities.Beer;
import com.gudmumic.spring.rest.model.BeerStyle;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer() {
        Beer beer = beerRepository.save(Beer.builder()
                                            .name("Dahls")
                                            .style(BeerStyle.PILSNER)
                                            .upc("123456789012")
                                            .price(BigDecimal.valueOf(12.99))
                                            .build());

        beerRepository.flush();

        assertThat(beer).isNotNull();
        assertThat(beer.getId()).isNotNull();
    }

    @Test
    void testSaveBeerConstraintViolation() {
        assertThrows(ConstraintViolationException.class, () -> {
            Beer beer = beerRepository.save(Beer.builder()
                                                .name("******************************************************************************************************************************************************************Michael Nielsen")
                                                .style(BeerStyle.PILSNER)
                                                .upc("123456789012")
                                                .price(BigDecimal.valueOf(12.99))
                                                .build());

            beerRepository.flush();
        });
    }
}