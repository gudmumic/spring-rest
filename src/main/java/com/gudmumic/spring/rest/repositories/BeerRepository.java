package com.gudmumic.spring.rest.repositories;

import com.gudmumic.spring.rest.entities.Beer;
import com.gudmumic.spring.rest.model.BeerStyle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
    List<Beer> findAllByNameIsLikeIgnoreCase(String beerName);
    List<Beer> findAllByStyle(BeerStyle style);
    List<Beer> findAllByNameIsLikeIgnoreCaseAndStyle(String beerName, BeerStyle style);
}
