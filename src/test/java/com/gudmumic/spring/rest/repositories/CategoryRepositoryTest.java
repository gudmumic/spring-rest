package com.gudmumic.spring.rest.repositories;

import com.gudmumic.spring.rest.entities.Beer;
import com.gudmumic.spring.rest.entities.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CategoryRepositoryTest {

  @Autowired CategoryRepository categoryRepository;

  @Autowired BeerRepository beerRepository;

  private Beer testBeer;

  @BeforeEach
  public void setUp() {
    testBeer = beerRepository.findAll().get(0);
  }

  @Transactional
  @Test
  public void addCategoryTest() {
      Category savedCategory = Category.builder()
                                       .description("Test Description")
                                       .build();

        testBeer.addCategory(savedCategory);
        Beer savedBeer = beerRepository.save(testBeer);

        assertNotNull(savedCategory);

        System.out.println(("Saved Category Description: " + savedCategory.getDescription()));
        System.out.println(("Saved Beer: " + savedBeer.getName()));
  }
}
