package com.gudmumic.spring.rest.repositories;

import com.gudmumic.spring.rest.entities.BeerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {}
