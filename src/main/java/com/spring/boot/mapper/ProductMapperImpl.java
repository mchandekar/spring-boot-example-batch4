package com.spring.boot.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.boot.dto.ProductDTO;
import com.spring.boot.model.Product;

@Component
public class ProductMapperImpl implements ProductMapper {
	
	private CustomerMapper customerMapper;
	
	public CustomerMapper getCustomerMapper() {
		return customerMapper;
	}

	@Autowired
	public void setCustomerMapper(CustomerMapper customerMapper) {
		this.customerMapper = customerMapper;
	}

	public ProductMapperImpl() {
	}
	
	@Override
	public ProductDTO productToProductDTO(Product product) {
		if(product == null) {
			return null;
		}
		
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setDescription(product.getDescription());
		productDTO.setPrice(product.getPrice());
		
		if(product.getCustomer() != null) {
			productDTO.setCustomer(customerMapper.customerToCustomerDTO(product.getCustomer()));
		}

		return productDTO;
	}

	@Override
	public Product productDTOToProduct(final ProductDTO productDTO) {
		if(productDTO == null) {
			return null;
		}
		
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		product.setPrice(productDTO.getPrice());
		
		if(productDTO.getCustomer() != null) {
			product.setCustomer(customerMapper.customerDTOToCustomer(productDTO.getCustomer()));
		}
		
		return product;
	}

}
