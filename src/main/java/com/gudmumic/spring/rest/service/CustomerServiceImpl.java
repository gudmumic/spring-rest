package com.gudmumic.spring.rest.service;

import com.gudmumic.spring.rest.model.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {

        log.debug("Initializing customers");

        customerMap = new HashMap<>();

        CustomerDTO michael = CustomerDTO.builder()
                                .id(UUID.randomUUID())
                                .name("Michael Nielsen")
                                .email("gudmundseth.dk@gmail.com")
                                .version(58)
                                .createdDate(LocalDateTime.now())
                                .updatedDate(LocalDateTime.now())
                                .build();

        CustomerDTO marianne = CustomerDTO.builder()
                                .id(UUID.randomUUID())
                                .name("Marianne Gudmundseth Nielsen")
                                .email("gudmundseth@gmail.com")
                                .version(56)
                                .createdDate(LocalDateTime.now())
                                .updatedDate(LocalDateTime.now())
                                .build();

        customerMap.put(michael.getId(), michael);
        customerMap.put(marianne.getId(), marianne);
    }

    @Override
    public List<CustomerDTO> getCustomerList() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.ofNullable(customerMap.get(id));
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {

        CustomerDTO newCustomerDTO = CustomerDTO.builder()
                                        .id(UUID.randomUUID())
                                        .name(customerDTO.getName())
                                        .version(1)
                                        .createdDate(LocalDateTime.now())
                                        .updatedDate(LocalDateTime.now())
                                        .build();

        customerMap.put(newCustomerDTO.getId(), newCustomerDTO);
        return newCustomerDTO;
    }

    @Override
    public Optional<CustomerDTO> updateCustomer(UUID id, CustomerDTO customerDTO) {
        log.debug("updating customer with id" + id);
        if(customerMap.containsKey(id)) {
            CustomerDTO updatedCustomerDTO = CustomerDTO.builder()
                                                .id(id)
                                                .name(customerDTO.getName())
                                                .version(customerMap.get(id).getVersion() + 1)
                                                .createdDate(customerMap.get(id).getCreatedDate())
                                                .updatedDate(LocalDateTime.now())
                                                .build();

            customerMap.put(id, updatedCustomerDTO);

            log.info("Updated Customer added to collection of Customers", updatedCustomerDTO);
            return Optional.of(updatedCustomerDTO);
        }
        return Optional.empty();
    }

    @Override
    public Boolean deleteCustomer(UUID id) {
        if (!customerMap.containsKey(id)) {
            customerMap.remove(id);
            return true;
        }
        return false;
    }
}
