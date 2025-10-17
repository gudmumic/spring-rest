package com.gudmumic.spring.rest.service;

import com.gudmumic.spring.rest.model.BeerDTO;
import com.gudmumic.spring.rest.model.BeerStyle;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    List<BeerDTO> getBeerList(String beerName, BeerStyle style, Boolean showInventory);

    Optional<BeerDTO> getBeerById(UUID id);

    BeerDTO createBeer(BeerDTO beerDTO);

    Optional<BeerDTO> updateBeer(UUID id, BeerDTO beerDTO);

    Boolean deleteBeer(UUID id);
}
