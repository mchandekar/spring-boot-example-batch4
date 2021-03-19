package com.spring.boot.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.spring.boot.dto.ProductDTO;
import com.spring.boot.mapper.ProductMapper;
import com.spring.boot.model.Product;

@Service
@Profile("map")
public class ProductServiceMapImpl implements ProductService {

	private Map<Long, Product> productMap = new HashMap<>();
	
	private ProductMapper productMapper;
	
	public ProductServiceMapImpl(final ProductMapper productMapper) {
		this.productMapper = productMapper;
	}
	
	@Override
	public List<ProductDTO> getAllProducts() {
		System.out.println("Product Map: Get All Products");
		
		List<ProductDTO> productDTOList = new ArrayList<>();
		
		if(productMap != null && productMap.size() > 0) {
			/*
			Collection<Product> products = productMap.values();
			for(Product product : products) {
				if(product != null) {
					ProductDTO productDTO = productMapper.productToProductDTO(product);
					if(productDTO != null) {
						productDTOList.add(productDTO);
					}
				}
			}
			*/
			productDTOList = productMap.values().stream().map(productMapper::productToProductDTO).collect(Collectors.toList());
		}
		
		return productDTOList;
	}

	@Override
	public ProductDTO getProductById(Long id) {
		System.out.println("Product Map: Get Product:"+id);
		
		ProductDTO productDTO = null;
		if(id != null) {
			if(productMap != null && productMap.containsKey(id)) {
				Product product = productMap.get(id);
				if(product != null) {
					productDTO = productMapper.productToProductDTO(product);
				}
			}
		}
		return productDTO;
	}

	@Override
	public ProductDTO createProduct(final ProductDTO productDTO) {
		System.out.println("Product Map: Save Product ");
		
		ProductDTO savedProductDTO = null;
		if(productDTO != null) {
			if(productDTO.getId() == null) {
				productDTO.setId(getNextId());
			}
			
			Product product = productMapper.productDTOToProduct(productDTO);
			if(product != null) {
				productMap.put(product.getId(), product);
				
				savedProductDTO = productMapper.productToProductDTO(product);
			}
		}
		return savedProductDTO;
	}

	@Override
	public ProductDTO updateProduct(ProductDTO productDTO) {
		System.out.println("Product Map: Update Product ");
		return createProduct(productDTO);
	}

	@Override
	public void deleteProduct(final Long id) {
		System.out.println("Product Map: Delete Id:"+id);
		if(id != null) {
			if(productMap != null && productMap.containsKey(id)) {
				productMap.remove(id);
			}
		}
	}
	
	private Long getNextId() {
		Long nextId = null;
		if(productMap != null) {
			if(productMap.size() == 0) {
				nextId = 1L;
			} else {
				nextId = Collections.max(productMap.keySet()) + 1;
			}
		}
		return nextId;
	}
}
