package com.gudmumic.spring.rest.service;

import com.gudmumic.spring.rest.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {

    List<Beer> listBeers();

    public Beer getBeerById(UUID id);
}
