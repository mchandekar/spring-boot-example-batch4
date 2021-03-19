package com.spring.boot.service;

import java.util.List;

import com.spring.boot.dto.CustomerDTO;

public interface CustomerService {

	
	List<CustomerDTO> getAllCustomers();
	
	CustomerDTO getCustomerById(Long id);
	
	CustomerDTO createCustomer(CustomerDTO customerDTO);
	
	CustomerDTO updateCustomer(CustomerDTO customerDTO);
	
	void deleteCustomerById(Long id);
}
