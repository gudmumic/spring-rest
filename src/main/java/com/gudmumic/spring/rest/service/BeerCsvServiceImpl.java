package com.gudmumic.spring.rest.service;

import com.gudmumic.spring.rest.model.BeerCSVRecord;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
public class BeerCsvServiceImpl implements BeerCsvService {
    @Override
    public List<BeerCSVRecord> convertCsvToBean(File csvFileName) {

        try {
            List<BeerCSVRecord> beerCSVRecordList = new CsvToBeanBuilder<BeerCSVRecord>(new FileReader(csvFileName))
                                                                    .withType(BeerCSVRecord.class)
                                                                    .build()
                                                                    .parse();
            return beerCSVRecordList;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
