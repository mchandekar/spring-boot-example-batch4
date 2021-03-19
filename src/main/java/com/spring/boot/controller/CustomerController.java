package com.spring.boot.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.dto.CustomerDTO;
import com.spring.boot.dto.CustomerListDTO;
import com.spring.boot.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	private CustomerService customerService;
	
	public CustomerController(final CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@GetMapping({"", "/"})
	public CustomerListDTO getAllCustomers() {
		return new CustomerListDTO(customerService.getAllCustomers());
	}
	
	@GetMapping("/{id}")
	public CustomerDTO getCustomerById(@PathVariable String id) {
		return customerService.getCustomerById(Long.valueOf(id));
	}

	@PostMapping({"", "/"})
	public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {
		return customerService.createCustomer(customerDTO);
	}
	
	@PutMapping("/{id}")
	public CustomerDTO updateCustomer(@PathVariable String id, @RequestBody CustomerDTO customerDTO) {
		CustomerDTO updatedCustomerDTO = null;
		if(customerDTO != null && id != null) {
			customerDTO.setId(Long.valueOf(id));
			updatedCustomerDTO = customerService.createCustomer(customerDTO);
		}
		return updatedCustomerDTO;
	}
	
	@DeleteMapping("/{id}")
	public void deleteCustomerById(@PathVariable String id) {
		customerService.deleteCustomerById(Long.valueOf(id));
	}
	
}
