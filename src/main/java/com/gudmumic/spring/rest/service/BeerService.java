package com.gudmumic.spring.rest.service;

import com.gudmumic.spring.rest.model.Beer;

import java.util.UUID;

public interface BeerService {

    public Beer getBeerById(UUID id);
}
