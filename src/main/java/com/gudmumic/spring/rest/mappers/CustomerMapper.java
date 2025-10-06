package com.gudmumic.spring.rest.mappers;

import com.gudmumic.spring.rest.entities.Customer;
import com.gudmumic.spring.rest.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDTOToCustomer(CustomerDTO customerDTO);

    CustomerDTO customerToCustomerDTO(Customer customer);
}
