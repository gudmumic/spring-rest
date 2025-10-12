package com.gudmumic.spring.rest.bootstrap;

import com.gudmumic.spring.rest.entities.Beer;
import com.gudmumic.spring.rest.entities.Customer;
import com.gudmumic.spring.rest.model.BeerCSVRecord;
import com.gudmumic.spring.rest.model.BeerStyle;
import com.gudmumic.spring.rest.repositories.BeerRepository;
import com.gudmumic.spring.rest.repositories.CustomerRepository;
import com.gudmumic.spring.rest.service.BeerCsvService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    private final BeerCsvService beerCsvService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCsvBeerData();
        loadCustomerData(); 
    }

    private void loadCsvBeerData() throws FileNotFoundException {
        if (beerRepository.count() < 10) {
            File csvFile = ResourceUtils.getFile("classpath:csvdata/beers.csv");

            List<BeerCSVRecord> beerList = beerCsvService.convertCsvToBean(csvFile);
            beerList.forEach(record -> {

                BeerStyle beerStyle = switch (record.getStyle()) {
                    case "American Pale Lager" -> BeerStyle.LAGER;
                    case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" ->
                            BeerStyle.ALE;
                    case "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA;
                    case "American Porter" -> BeerStyle.PORTER;
                    case "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT;
                    case "Saison / Farmhouse Ale" -> BeerStyle.SAISON;
                    case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT;
                    case "English Pale Ale" -> BeerStyle.PALE_ALE;
                    default -> BeerStyle.PILSNER;
                };

                Beer beer = Beer.builder()
                        .name(StringUtils.abbreviate(record.getBeer(), 50))
                        .style(beerStyle)
                        .upc(record.getRow().toString())
                        .price(BigDecimal.TEN)
                        .quantityOnHand(Integer.valueOf(record.getCount()))
                        .build();
                beerRepository.save(beer);
            });
        }
    }

    private void loadBeerData() {
        if (beerRepository.count() == 0) {
            Beer dahls = Beer.builder()
                    .name("Dahls")
                    .style(BeerStyle.PILSNER)
                    .upc("12345")
                    .price(new BigDecimal("105"))
                    .quantityOnHand(122)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Beer calsberg = Beer.builder()
                    .name("Carlsberg")
                    .style(BeerStyle.PILSNER)
                    .upc("147852")
                    .price(new BigDecimal("125"))
                    .quantityOnHand(50)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Beer tuborg = Beer.builder()
                    .name("Tuborg")
                    .style(BeerStyle.PILSNER)
                    .upc("962145")
                    .price(new BigDecimal("99"))
                    .quantityOnHand(80)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Beer paleAle = Beer.builder()
                    .name("Dahls Pale Ale")
                    .style(BeerStyle.PALE_ALE)
                    .upc("1234555")
                    .price(new BigDecimal("155"))
                    .quantityOnHand(90)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Beer ipa = Beer.builder()
                    .name("Dahls IPA")
                    .style(BeerStyle.IPA)
                    .upc("1200345")
                    .price(new BigDecimal("185"))
                    .quantityOnHand(156)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            beerRepository.save(dahls);
            beerRepository.save(calsberg);
            beerRepository.save(tuborg);
            beerRepository.save(paleAle);
            beerRepository.save(ipa);
        }
    }

    private void loadCustomerData() {
        if (customerRepository.count() == 0) {
            Customer michael = Customer.builder()
                    .name("Michael Nielsen")
                    .email("gudmundseth@gmail.com")
                    .address("Henrik Ourens Vei 66, 4632 Kristiansand")
                    .zipCode("NO-7965")
                    .city("Trondheim")
                    .country("Norway")
                    .version(58)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Customer marianne = Customer.builder()
                    .name("Marianne Gudmundseth Nielsen")
                    .email("gudmundseth@gmail.com")
                    .address("Henrik Ourens Vei 66, 4632 Kristiansand")
                    .zipCode("NO-7965")
                    .city("Trondheim")
                    .country("Norway")
                    .version(56)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            customerRepository.saveAll(Arrays.asList(michael, marianne));
        }
    }
}
