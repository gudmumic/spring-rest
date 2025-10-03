package com.gudmumic.spring.rest.service;

import com.gudmumic.spring.rest.model.Beer;
import com.gudmumic.spring.rest.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    Map<UUID, Beer> beerList;

    public BeerServiceImpl() {
        beerList = new HashMap<>();

        Beer dahls = Beer.builder()
                        .id(UUID.randomUUID())
                        .version(1)
                        .name("Dahls")
                        .style(BeerStyle.PILSNER)
                        .upc("12345")
                        .price(new BigDecimal("105"))
                        .quantityOnHand(122)
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .build();

        Beer calsberg = Beer.builder()
                        .id(UUID.randomUUID())
                        .version(1)
                        .name("Carlsberg")
                        .style(BeerStyle.PILSNER)
                        .upc("147852")
                        .price(new BigDecimal("125"))
                        .quantityOnHand(50)
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .build();

        Beer tuborg = Beer.builder()
                        .id(UUID.randomUUID())
                        .version(1)
                        .name("Tuborg")
                        .style(BeerStyle.PILSNER)
                        .upc("962145")
                        .price(new BigDecimal("99"))
                        .quantityOnHand(80)
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .build();

        Beer paleAle = Beer.builder()
                        .id(UUID.randomUUID())
                        .version(1)
                        .name("Dahls Pale Ale")
                        .style(BeerStyle.PALE_ALE)
                        .upc("1234555")
                        .price(new BigDecimal("155"))
                        .quantityOnHand(90)
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .build();

        Beer ipa = Beer.builder()
                        .id(UUID.randomUUID())
                        .version(1)
                        .name("Dahls IPA")
                        .style(BeerStyle.IPA)
                        .upc("1200345")
                        .price(new BigDecimal("185"))
                        .quantityOnHand(156)
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .build();

        beerList.put(dahls.getId(), dahls);
        beerList.put(calsberg.getId(), calsberg);
        beerList.put(tuborg.getId(), tuborg);
        beerList.put(paleAle.getId(), paleAle);
        beerList.put(ipa.getId(), ipa);
    }

    @Override
    public List<Beer> listBeers() {
        return new ArrayList<>(beerList.values());
    }


    @Override
    public Beer getBeerById(UUID id) {

        log.debug("Get Beer Information from Beer Id from service");

        return beerList.get(id);
    }
}
