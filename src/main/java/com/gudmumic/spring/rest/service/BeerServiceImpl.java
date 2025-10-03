package com.gudmumic.spring.rest.service;

import com.gudmumic.spring.rest.model.Beer;
import com.gudmumic.spring.rest.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    @Override
    public Beer getBeerById(UUID id) {

        log.debug("Get Beer Information from Beer Id from service");

        return Beer.builder()
                .id(id)
                .version(1)
                .name("Dahls")
                .style(BeerStyle.PILSNER)
                .upc("12345")
                .price(new BigDecimal("105"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
    }
}
