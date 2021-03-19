package com.spring.boot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.spring.boot.dto.ProductDTO;
import com.spring.boot.mapper.ProductMapperImpl;

class ProductServiceMapImplTest {
	
	private static final String UPDATED_PRODUCT_NAME = "Updated Product Name";

	private ProductService productService;
	
	private static final Long ID = 1L;
	private static final String NAME = "LAPTOP";
	private static final String DESCRIPTION = "LAPTOP";
	private static final BigDecimal PRICE = new BigDecimal(100);
	
	@BeforeEach
	void setUp() throws Exception {
		productService = new ProductServiceMapImpl(new ProductMapperImpl());
		
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(1L);
		productDTO.setName(NAME);
		productDTO.setDescription(DESCRIPTION);
		productDTO.setPrice(PRICE);

		productService.createProduct(productDTO);
	}

	@Test
	void testGetAllProducts() {
		List<ProductDTO> productDTOList = productService.getAllProducts();
		assertEquals(1, productDTOList.size());
		
		// Assertions for the Product Values - ID, Name, Description, Price
		ProductDTO productDTO = productDTOList.get(0);
		assertEquals(ID, productDTO.getId());
		assertEquals(NAME, productDTO.getName());
		assertEquals(DESCRIPTION, productDTO.getDescription());
		assertEquals(PRICE, productDTO.getPrice());
	}

	@Test
	void testGetProductById() {
		ProductDTO productDTO = productService.getProductById(ID);
		assertNotNull(productDTO);

		assertEquals(ID, productDTO.getId());
		assertEquals(NAME, productDTO.getName());
		assertEquals(DESCRIPTION, productDTO.getDescription());
		assertEquals(PRICE, productDTO.getPrice());
	}
	
	@Test
	void testGetProductByNonExistentId() {
		ProductDTO productDTO = productService.getProductById(5L);
		assertNull(productDTO);
	}

	@Test
	void testCreateProduct() {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(2L);
		productDTO.setName("HP");
		productDTO.setDescription("HP");
		productDTO.setPrice(PRICE);

		ProductDTO savedProductDTO = productService.createProduct(productDTO);
		assertNotNull(savedProductDTO);
		assertEquals(2L, savedProductDTO.getId());
		assertEquals(productDTO.getName(), savedProductDTO.getName());
		assertEquals(productDTO.getDescription(), savedProductDTO.getDescription());
		assertEquals(productDTO.getPrice(), savedProductDTO.getPrice());
	}
	
	@Test
	void testCreateProductForNullValue() {
		ProductDTO savedProductDTO = productService.createProduct(null);
		assertNull(savedProductDTO);
	}
	
	@Test
	void testCreateProductWithoutId() {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setName("HP");
		productDTO.setDescription("HP");
		productDTO.setPrice(PRICE);

		ProductDTO savedProductDTO = productService.createProduct(productDTO);
		assertNotNull(savedProductDTO);
		assertEquals(2L, savedProductDTO.getId());
		assertEquals(productDTO.getName(), savedProductDTO.getName());
		assertEquals(productDTO.getDescription(), savedProductDTO.getDescription());
		assertEquals(productDTO.getPrice(), savedProductDTO.getPrice());
	}

	@Test
	void testUpdateProduct() {
		ProductDTO productDTO = productService.getProductById(ID);
		assertNotNull(productDTO);
		
		productDTO.setName(UPDATED_PRODUCT_NAME);
		
		ProductDTO updatedProductDTO = productService.updateProduct(productDTO);
		assertNotNull(updatedProductDTO);
		assertEquals(UPDATED_PRODUCT_NAME, updatedProductDTO.getName());
		assertEquals(ID, updatedProductDTO.getId());
		assertEquals(DESCRIPTION, updatedProductDTO.getDescription());
		assertEquals(PRICE, updatedProductDTO.getPrice());
	}

	@Test
	void testDeleteProduct() {
		productService.deleteProduct(ID);
		
		List<ProductDTO> productDTOList = productService.getAllProducts();
		assertEquals(0, productDTOList.size());
	}

}
