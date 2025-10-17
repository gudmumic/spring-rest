package com.gudmumic.spring.rest.service;

import com.gudmumic.spring.rest.entities.Beer;
import com.gudmumic.spring.rest.mappers.BeerMapper;
import com.gudmumic.spring.rest.model.BeerDTO;
import com.gudmumic.spring.rest.model.BeerStyle;
import com.gudmumic.spring.rest.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJpa implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> getBeerList(String beerName, BeerStyle style, Boolean showInventory) {

        List<Beer> beerList = List.of();

        if (StringUtils.hasText(beerName) && style == null) {
            beerList = getBeerListByName(beerName);
        }
        else if (!StringUtils.hasText(beerName) && style != null) {
            beerList = getBeerListByStyle(style);
        }
        else if (StringUtils.hasText(beerName) && style != null) {
            beerList = getBeerListByNameAndStyle(beerName, style);
        }

        if (showInventory != null && !showInventory) {
            beerList.forEach(beer -> beer.setQuantityOnHand(null));
        }

        return beerList.stream()
                       .map(beerMapper::beerToBeerDTO)
                       .collect(Collectors.toList());
    }

    public List<Beer> getBeerListByNameAndStyle(String beerName, BeerStyle style) {
        return beerRepository.findAllByNameIsLikeIgnoreCaseAndStyle(beerName, style);
    }

    public List<Beer> getBeerListByStyle(BeerStyle style) {
        return beerRepository.findAllByStyle(style);
    }

    public List<Beer> getBeerListByName(String beerName) {
        return beerRepository.findAllByNameIsLikeIgnoreCase("%" + beerName + "%");
    }


    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {

        return Optional.ofNullable(beerMapper.beerToBeerDTO(beerRepository.findById(id).orElse(null)));
    }

    @Override
    public BeerDTO createBeer(BeerDTO beerDTO) {

        return beerMapper.beerToBeerDTO(beerRepository.save(beerMapper.beerDTOToBeer(beerDTO)));
    }

    @Override
    public Optional<BeerDTO> updateBeer(UUID id, BeerDTO beerDTO) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>(Optional.empty());
        beerRepository.findById(id).ifPresentOrElse(beer -> {
            beer.setName(beerDTO.getName());
            beer.setStyle(beerDTO.getStyle());
            beer.setPrice(beerDTO.getPrice());
            beer.setUpc(beerDTO.getUpc());
            atomicReference.set(Optional.of(beerMapper.beerToBeerDTO(beerRepository.save(beer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });
        return atomicReference.get();
    }

    @Override
    public Boolean deleteBeer(UUID id) {
        if (beerRepository.existsById(id)) {
            beerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
