package com.gudmumic.spring.rest.service;

import com.gudmumic.spring.rest.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface BeerCsvService {
    List<BeerCSVRecord> convertCsvToBean(File csvFileName);
}
