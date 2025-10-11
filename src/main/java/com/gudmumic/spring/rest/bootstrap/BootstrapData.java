package com.gudmumic.spring.rest.bootstrap;

import com.gudmumic.spring.rest.entities.Beer;
import com.gudmumic.spring.rest.entities.Customer;
import com.gudmumic.spring.rest.model.BeerStyle;
import com.gudmumic.spring.rest.repositories.BeerRepository;
import com.gudmumic.spring.rest.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData(); 
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
                    .version(58)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Customer marianne = Customer.builder()
                    .name("Marianne Gudmundseth Nielsen")
                    .email("gudmundseth@gmail.com")
                    .version(56)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            customerRepository.saveAll(Arrays.asList(michael, marianne));
        }
    }
}
