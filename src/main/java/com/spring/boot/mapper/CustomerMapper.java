package com.spring.boot.mapper;

import com.spring.boot.dto.CustomerDTO;
import com.spring.boot.model.Customer;

public interface CustomerMapper {

	CustomerDTO customerToCustomerDTO(Customer customer);

	Customer customerDTOToCustomer(CustomerDTO customerDTO);

}
