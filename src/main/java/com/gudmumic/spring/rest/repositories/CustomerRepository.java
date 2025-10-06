package com.gudmumic.spring.rest.repositories;

import com.gudmumic.spring.rest.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
