package com.spring.boot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.spring.boot.dto.CustomerDTO;
import com.spring.boot.exception.ResourceNotFoundException;
import com.spring.boot.mapper.CustomerMapper;
import com.spring.boot.model.Customer;
import com.spring.boot.repository.CustomerRepository;

@Service
public class CustomerServiceJpaImpl implements CustomerService {

	private CustomerRepository customerRepository;
	private CustomerMapper customerMapper;
	
	public CustomerServiceJpaImpl(final CustomerRepository customerRepository, final CustomerMapper customerMapper) {
		this.customerRepository = customerRepository;
		this.customerMapper = customerMapper;
	}
	
	@Override
	public List<CustomerDTO> getAllCustomers() {
		List<CustomerDTO> customerDTOList = new ArrayList<>();

		List<Customer> customerList = customerRepository.findAll();
		if (customerList != null && customerList.size() > 0) {
			customerDTOList = customerList.stream().map(customerMapper::customerToCustomerDTO).collect(Collectors.toList());
		}

		return customerDTOList;

	}

	@Override
	public CustomerDTO getCustomerById(Long id) {
		CustomerDTO customerDTO = null;
		if(id != null) {
			Customer customerDB = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer with ID:"+id+" not found in the Repository. Invalid Input"));
			customerDTO = customerMapper.customerToCustomerDTO(customerDB);
		}
		return customerDTO;
	}

	@Override
	@Transactional
	public CustomerDTO createCustomer(CustomerDTO customerDTO) {
		CustomerDTO savedCustomerDTO = null;
		if(customerDTO != null) {
			Customer customerToSave = customerMapper.customerDTOToCustomer(customerDTO);
			if(customerToSave != null) {
				Customer savedCustomer = customerRepository.save(customerToSave);
				savedCustomerDTO = customerMapper.customerToCustomerDTO(savedCustomer);
			}
		}
		return savedCustomerDTO;
	}

	@Override
	@Transactional
	public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
		return createCustomer(customerDTO);
	}

	@Override
	public void deleteCustomerById(Long id) {
		if(id != null) {
			customerRepository.deleteById(id);
		}
	}
}
