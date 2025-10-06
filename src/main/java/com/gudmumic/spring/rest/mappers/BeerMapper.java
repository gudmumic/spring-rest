package com.gudmumic.spring.rest.mappers;

import com.gudmumic.spring.rest.entities.Beer;
import com.gudmumic.spring.rest.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDTOToBeer(BeerDTO beerDTO);

    BeerDTO beerToBeerDTO(Beer beer);
}
