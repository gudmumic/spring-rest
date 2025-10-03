package com.gudmumic.spring.rest.controller;

import com.gudmumic.spring.rest.model.Beer;
import com.gudmumic.spring.rest.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Controller
public class BeerController {

    private final BeerService beerService;

    public Beer getBeerByStyle(UUID id) {

        log.debug("Bet Beer by ID - from controller");

        return beerService.getBeerById(id);
    }
}
