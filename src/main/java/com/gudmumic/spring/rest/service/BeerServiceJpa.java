package com.gudmumic.spring.rest.service;

import com.gudmumic.spring.rest.entities.Beer;
import com.gudmumic.spring.rest.mappers.BeerMapper;
import com.gudmumic.spring.rest.model.BeerDTO;
import com.gudmumic.spring.rest.model.BeerStyle;
import com.gudmumic.spring.rest.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJpa implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 25;
    private static final int DEFAULT_MAX_PAGE_SIZE = 1000;

    @Override
    public Page<BeerDTO> getBeerList(String beerName, BeerStyle style, Boolean showInventory, Integer pageNumber, Integer pageSize) {

        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

        Page<Beer> beerMap;

        if (StringUtils.hasText(beerName) && style == null) {
            beerMap = getBeerListByName(beerName, pageRequest);
        }
        else if (!StringUtils.hasText(beerName) && style != null) {
            beerMap = getBeerListByStyle(style, pageRequest);
        }
        else if (StringUtils.hasText(beerName) && style != null) {
            beerMap = getBeerListByNameAndStyle(beerName, style, pageRequest);
        }
        else {
            beerMap = beerRepository.findAll(pageRequest);
        }

        if (showInventory != null && !showInventory) {
            beerMap.forEach(beer -> beer.setQuantityOnHand(null));
        }

        return beerMap.map(beerMapper::beerToBeerDTO);
    }

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber = pageNumber == null || pageNumber < 0 ? DEFAULT_PAGE_NUMBER  : pageNumber - 1;
        int queryPageSize = pageSize == null || pageSize < 1 ? DEFAULT_PAGE_SIZE : pageSize;

        if (queryPageNumber > DEFAULT_MAX_PAGE_SIZE) {
            queryPageNumber = DEFAULT_MAX_PAGE_SIZE;
        }

        return  PageRequest.of(queryPageNumber, queryPageSize);
    }

    public Page<Beer> getBeerListByNameAndStyle(String beerName, BeerStyle style, PageRequest pageRequest) {
        return beerRepository.findAllByNameIsLikeIgnoreCaseAndStyle("%" + beerName + "%", style, pageRequest);
    }

    public Page<Beer> getBeerListByStyle(BeerStyle style, PageRequest pageRequest) {
        return beerRepository.findAllByStyle(style, pageRequest);
    }

    public Page<Beer> getBeerListByName(String beerName, PageRequest pageRequest) {
        return beerRepository.findAllByNameIsLikeIgnoreCase("%" + beerName + "%", pageRequest);
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
