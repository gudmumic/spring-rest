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
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";
    public static final String PATH_VARIABLE_ID = "beerId";

    private final BeerService beerService;

    @GetMapping(value = BEER_PATH)
    public List<Beer> getBeerList() {
        return beerService.getBeerList();
    }

    @GetMapping(value = BEER_PATH_ID)
    public Beer getBeerById(@PathVariable(PATH_VARIABLE_ID) UUID beerId) {

        log.debug("Get Beer by ID - from beer controller");

        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }

    @PostMapping(value = BEER_PATH)
    public ResponseEntity createBeer(@RequestBody Beer beer) {

        log.debug("Create new Beer - from beer controller");

        Beer newBeer = beerService.createBeer(beer);

        log.info("New Beer added to collection of Beers", newBeer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + newBeer.getId().toString());

        return new ResponseEntity(newBeer, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = BEER_PATH_ID)
    public ResponseEntity updateBeer(@PathVariable(PATH_VARIABLE_ID)  UUID id, @RequestBody Beer beer) {

        log.debug("Update existing Beer - from beer controller");

        beerService.updateBeer(id, beer);

        log.info("Updated Beer from collection of Beers");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + id);

        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = BEER_PATH_ID)
    public ResponseEntity deleteBeer(@PathVariable(PATH_VARIABLE_ID)  UUID id) {

        log.debug("Delete Beer - from beer controller");

        beerService.deleteBeer(id);

        log.info("Beer deleted from collection of Beers");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + id);

        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
    }


}
