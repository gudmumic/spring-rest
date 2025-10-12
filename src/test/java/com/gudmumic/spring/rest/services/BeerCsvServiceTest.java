package com.gudmumic.spring.rest.services;

import com.gudmumic.spring.rest.model.BeerCSVRecord;
import com.gudmumic.spring.rest.service.BeerCsvService;
import com.gudmumic.spring.rest.service.BeerCsvServiceImpl;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BeerCsvServiceTest {

    BeerCsvService beerCsvService = new BeerCsvServiceImpl();

    @Test
    public void convertCsvToBean() throws FileNotFoundException {

        File csvFile = ResourceUtils.getFile("classpath:csvdata/beers.csv");
        List<BeerCSVRecord> beerList = beerCsvService.convertCsvToBean(csvFile);

        System.out.println(beerList.size());

        assertThat(beerList).isNotNull();
        assertThat(beerList.size()).isGreaterThan(0);
    }
}
