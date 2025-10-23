package com.gudmumic.spring.rest.repositories;

import com.gudmumic.spring.rest.entities.Beer;
import com.gudmumic.spring.rest.model.BeerStyle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
    Page<Beer> findAllByNameIsLikeIgnoreCase(String beerName, Pageable pageable);
    Page<Beer> findAllByStyle(BeerStyle style, Pageable pageable);
    Page<Beer> findAllByNameIsLikeIgnoreCaseAndStyle(String beerName, BeerStyle style, Pageable pageable);
}
