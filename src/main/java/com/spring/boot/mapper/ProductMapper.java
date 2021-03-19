package com.spring.boot.mapper;

import com.spring.boot.dto.ProductDTO;
import com.spring.boot.model.Product;

public interface ProductMapper {
	
	ProductDTO productToProductDTO(final Product product);
	
	Product productDTOToProduct(final ProductDTO productDTO);
	
}
