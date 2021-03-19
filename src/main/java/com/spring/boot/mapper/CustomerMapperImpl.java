package com.spring.boot.mapper;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.boot.dto.CustomerDTO;
import com.spring.boot.model.Customer;

@Component
public class CustomerMapperImpl implements CustomerMapper {
	
	private ProductMapper productMapper;
	
	public ProductMapper getProductMapper() {
		return productMapper;
	}

	@Autowired
	public void setProductMapper(ProductMapper productMapper) {
		this.productMapper = productMapper;
	}

	public CustomerMapperImpl() {
	
	}
	
	@Override
	public CustomerDTO customerToCustomerDTO(Customer customer) {
		CustomerDTO customerDTO = null;
		if(customer != null) {
			customerDTO = new CustomerDTO();
			customerDTO.setId(customer.getId());
			customerDTO.setFirstName(customer.getFirstName());
			customerDTO.setLastName(customer.getLastName());
			customerDTO.setEmail(customer.getEmail());
			customerDTO.setCity(customer.getCity());
			
//			if(customer.getProducts() != null && customer.getProducts().size() > 0) {
//				customerDTO.setProducts(
//						customer.getProducts()
//						.stream()
//						.map(productMapper::productToProductDTO)
//						.collect(Collectors.toList()));
//			}
		}
		return customerDTO;
	}

	@Override
	public Customer customerDTOToCustomer(CustomerDTO customerDTO) {
		Customer customer = null;
		if(customerDTO != null) {
			customer = new Customer();
			customer.setId(customerDTO.getId());
			customer.setFirstName(customerDTO.getFirstName());
			customer.setLastName(customerDTO.getLastName());
			customer.setEmail(customerDTO.getEmail());
			customer.setCity(customerDTO.getCity());

//			if(customerDTO.getProducts() != null && customerDTO.getProducts().size() > 0) {
//				customer.setProducts(
//						customerDTO.getProducts()
//						.stream()
//						.map(productMapper::productDTOToProduct)
//						.collect(Collectors.toList()));
//			}
		}
		return customer;
	}

}
