package com.gudmumic.spring.rest.service;

import com.gudmumic.spring.rest.model.BeerDTO;
import com.gudmumic.spring.rest.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    Map<UUID, BeerDTO> beerList;

    public BeerServiceImpl() {
        beerList = new HashMap<>();

        BeerDTO dahls = BeerDTO.builder()
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

        BeerDTO calsberg = BeerDTO.builder()
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

        BeerDTO tuborg = BeerDTO.builder()
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

        BeerDTO paleAle = BeerDTO.builder()
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

        BeerDTO ipa = BeerDTO.builder()
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
    public Page<BeerDTO> getBeerList(String beerName, BeerStyle style, Boolean showInventory, Integer pageNumber, Integer pageSize) {
        return new PageImpl<>(new ArrayList<>(beerList.values()));
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {

        log.debug("Get Beer Information from Beer Id from service");

        return Optional.of(beerList.get(id));
    }

    @Override
    public BeerDTO createBeer(BeerDTO beerDTO) {

        BeerDTO createdBeerDTO = BeerDTO.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .version(1)
                .name(beerDTO.getName())
                .quantityOnHand(beerDTO.getQuantityOnHand())
                .price(beerDTO.getPrice())
                .upc(beerDTO.getUpc())
                .style(beerDTO.getStyle())
                .build();

        beerList.put(createdBeerDTO.getId(), createdBeerDTO);

        return createdBeerDTO;
    }

    @Override
    public Optional<BeerDTO> updateBeer(UUID id, BeerDTO beerDTO) {
        BeerDTO existing = beerList.get(id);
        if (existing != null) {
            existing.setName(beerDTO.getName());
            existing.setStyle(beerDTO.getStyle());
            existing.setPrice(beerDTO.getPrice());
            existing.setQuantityOnHand(beerDTO.getQuantityOnHand());
            existing.setUpc(beerDTO.getUpc());
            existing.setUpdatedDate(LocalDateTime.now());
            beerList.put(existing.getId(), existing);
        }
        return Optional.ofNullable(existing);
    }

    @Override
    public Boolean deleteBeer(UUID id) {

        beerList.remove(id);
        return true;
    }
}
