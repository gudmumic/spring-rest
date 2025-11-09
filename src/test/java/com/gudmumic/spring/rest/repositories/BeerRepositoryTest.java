package com.gudmumic.spring.rest.repositories;

import com.gudmumic.spring.rest.bootstrap.BootstrapData;
import com.gudmumic.spring.rest.entities.Beer;
import com.gudmumic.spring.rest.model.BeerStyle;
import com.gudmumic.spring.rest.service.BeerCsvServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import({BootstrapData.class, BeerCsvServiceImpl.class})
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

    @Test
    void testFindAllByBeerNameIsLikeIgnoreCase() {
        Page<Beer> result = beerRepository.findAllByNameIsLikeIgnoreCase("%daHls%", null);

        assertThat(result).isNotNull();
        assertThat(result.get().toList().size()).isGreaterThan(0);
    }
}