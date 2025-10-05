package com.gudmumic.spring.rest.service;

import com.gudmumic.spring.rest.model.Beer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    List<Beer> getBeerList();

    Optional<Beer> getBeerById(UUID id);

    Beer createBeer(Beer beer);

    void updateBeer(UUID id, Beer beer);

    void deleteBeer(UUID id);
}
