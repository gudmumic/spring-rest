package com.gudmumic.spring.rest.repositories;

import com.gudmumic.spring.rest.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

}
