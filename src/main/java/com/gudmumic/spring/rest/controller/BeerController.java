package com.gudmumic.spring.rest.controller;

import com.gudmumic.spring.rest.model.Beer;
import com.gudmumic.spring.rest.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    private final BeerService beerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> getBeerList() {
        return beerService.listBeers();
    }

    @RequestMapping(value = "{beerId}", method = RequestMethod.GET)
    public Beer getBeerByStyle(@PathVariable("beerId") UUID beerId) {

        log.debug("Bet Beer by ID - from controller");

        return beerService.getBeerById(beerId);
    }
}
