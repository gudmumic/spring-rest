package com.gudmumic.spring.rest.controller;

import com.gudmumic.spring.rest.mappers.BeerMapper;
import com.gudmumic.spring.rest.model.BeerDTO;
import com.gudmumic.spring.rest.model.BeerStyle;
import com.gudmumic.spring.rest.repositories.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BeerControllerIT {

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Test
    void getBeerList() {
        List<BeerDTO> beerDTOList = beerController.getBeerList();
        assertThat(beerDTOList.size()).isEqualTo(5);
    }

    @Rollback
    @Transactional
    @Test
    void getEmptyBeerList() {
        beerRepository.deleteAll();
        List<BeerDTO> beerDTOList = beerController.getBeerList();
        assertThat(beerDTOList.size()).isEqualTo(0);
    }

    @Test
    void getBeerById() {
        BeerDTO beerDTO = beerController.getBeerById(beerRepository.findAll().get(0).getId());
        assertThat(beerDTO).isNotNull();
    }

    @Test
    void getBeerByIdNotFound() {

        assertThrows(NotFoundException.class, () -> {
            beerController.getBeerById(java.util.UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void createNewBeer() {
        BeerDTO beerDTO = BeerDTO.builder()
                                .name("New Beer")
                                .style(BeerStyle.PALE_ALE)
                                .build();
        ResponseEntity response = beerController.createBeer(beerDTO);
        List<BeerDTO> beerDTOList = beerController.getBeerList();
        assertThat(beerDTOList.size()).isEqualTo(6);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = response.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[locationUUID.length - 1]);
        assertThat(beerRepository.findById(savedUUID).isPresent()).isTrue();
    }

    @Rollback
    @Transactional
    @Test
    void updateBeer() {
        BeerDTO beerDTO = beerMapper.beerToBeerDTO(beerRepository.findAll().get(0));
        beerDTO.setName("Updated Beer");
        ResponseEntity response = beerController.updateBeer(beerDTO.getId(), beerDTO);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(beerRepository.findById(beerDTO.getId()).get().getName()).isEqualTo("Updated Beer");
    }

    @Rollback
    @Transactional
    @Test
    void updateBeerNotFound() {
        assertThrows(NotFoundException.class , () -> {
            beerController.updateBeer(java.util.UUID.randomUUID(), BeerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void deleteBeer() {
        BeerDTO beerDTO = beerMapper.beerToBeerDTO(beerRepository.findAll().get(0));
        ResponseEntity response = beerController.deleteBeer(beerDTO.getId());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(beerRepository.findById(beerDTO.getId()).isEmpty()).isTrue();
    }

    @Rollback
    @Transactional
    @Test
    void deleteBeerNotFound() {
        assertThrows(NotFoundException.class , () -> {
            beerController.deleteBeer(java.util.UUID.randomUUID());
        });
    }
}