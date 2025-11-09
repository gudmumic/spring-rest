package com.gudmumic.spring.rest.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.gudmumic.spring.rest.model.BeerCSVRecord;
import com.gudmumic.spring.rest.service.BeerCsvService;
import com.gudmumic.spring.rest.service.BeerCsvServiceImpl;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

public class BeerCsvServiceTest {

    BeerCsvService beerCsvService = new BeerCsvServiceImpl();

    @Test
    void convertCsvToBean() throws FileNotFoundException {

        File csvFile = ResourceUtils.getFile("classpath:csvdata/beers.csv");
        List<BeerCSVRecord> beerList = beerCsvService.convertCsvToBean(csvFile);

        System.out.println(beerList.size());

        assertThat(beerList).isNotNull();
        assertThat(beerList.size()).isGreaterThan(0);
    }
}
