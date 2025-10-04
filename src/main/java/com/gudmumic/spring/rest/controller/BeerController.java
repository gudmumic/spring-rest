package com.gudmumic.spring.rest.controller;

import com.gudmumic.spring.rest.model.Beer;
import com.gudmumic.spring.rest.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    private final BeerService beerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> getBeerList() {
        return beerService.listBeers();
    }

    @RequestMapping(value = "{beerId}", method = RequestMethod.GET)
    public Beer getBeerById(@PathVariable("beerId") UUID beerId) {

        log.debug("Bet Beer by ID - from beer controller");

        return beerService.getBeerById(beerId);
    }

    @PostMapping
    //@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createBeer(@RequestBody Beer beer) {

        log.debug("Create new Beer - from beer controller");

        Beer newBeer = beerService.createBeer(beer);

        log.info("New Beer added to collection of Beers", newBeer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + newBeer.getId().toString());

        return new ResponseEntity(newBeer, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "{beerId}")
    public ResponseEntity updateBeer(@PathVariable("beerId")  UUID id, @RequestBody Beer beer) {

        log.debug("Update existing Beer - from beer controller");

        Beer updatedBeer = beerService.updateBeer(id, beer);

        log.info("Updated Beer from collection of Beers", updatedBeer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + updatedBeer.getId().toString());

        return new ResponseEntity(updatedBeer, headers, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "{beerId}")
    public ResponseEntity deleteBeer(@PathVariable("beerId")  UUID id) {

        log.debug("Delete Beer - from beer controller");

        beerService.deleteBeer(id);

        log.info("Beer deleted from collection of Beers");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + id);

        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
    }
}
