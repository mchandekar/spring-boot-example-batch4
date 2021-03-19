package com.spring.boot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.spring.boot.dto.ProductDTO;
import com.spring.boot.mapper.ProductMapper;
import com.spring.boot.mapper.ProductMapperImpl;
import com.spring.boot.model.Product;
import com.spring.boot.repository.ProductRepository;

class ProductServiceJpaImplTest {
	
	private static final String LENOVO = "Lenovo";

	private static final String HP = "HP";

	private ProductService productService;
	
	private ProductMapper productMapper;
	
	@Mock
	private ProductRepository productRepository;

	private Product product1;
	
	private Product product2;
	
	@BeforeEach
	void setUp() throws Exception {
		// Mockito
		MockitoAnnotations.openMocks(this);
		
		productMapper = new ProductMapperImpl();
		productService = new ProductServiceJpaImpl(productMapper, productRepository);
		
		loadProducts();
	}
	
	private void loadProducts() {
		product1 = new Product();
		product1.setId(1L);
		product1.setName(HP);
		product1.setDescription(HP);
		product1.setPrice(BigDecimal.TEN);
		
		product2 = new Product();
		product2.setId(2L);
		product2.setName(LENOVO);
		product2.setDescription(LENOVO);
		product2.setPrice(new BigDecimal(20));
	}

	@Test
	void testGetAllProducts() {
		// Mock Initialization
		when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
		
		List<ProductDTO> products = productService.getAllProducts();
		assertEquals(2, products.size());
	}

	@Test
	void testGetProductById() {
		
		when(productRepository.findById(anyLong())).thenReturn(Optional.of(product2));
		// when(productRepository.findById(2l)).thenReturn(Optional.of(product2));
		
		ProductDTO productDTO = productService.getProductById(112L);
		assertNotNull(productDTO);
		assertEquals(2L, productDTO.getId());
	}

	@Test
	void testCreateProduct() {
		ProductDTO productDTO = new ProductDTO(1L, "New Product", "New Product", BigDecimal.TEN);
		Product product = productMapper.productDTOToProduct(productDTO);
		
		when(productRepository.save(any())).thenReturn(product);
		
		ProductDTO createdProductDTO = productService.createProduct(productDTO);
		assertNotNull(createdProductDTO);
		assertEquals(1L, createdProductDTO.getId());
		assertEquals(product.getName(), createdProductDTO.getName());
		
		verify(productRepository, times(1)).save(any());
	}
	
	@Test
	void testCreateProductWithoutPassingId() {
		ProductDTO productDTO = new ProductDTO(null, "New Product", "New Product", BigDecimal.TEN);
		
		Product product = productMapper.productDTOToProduct(productDTO);
		product.setId(1L);
		when(productRepository.save(any())).thenReturn(product);
		
		ProductDTO createdProductDTO = productService.createProduct(productDTO);
		assertNotNull(createdProductDTO);
		assertEquals(1L, createdProductDTO.getId());
		assertEquals(product.getName(), createdProductDTO.getName());
	}

	@Test
	void testUpdateProduct() {
		// TODO-FIXME Batch 4 is to implement this method Today !!! 8th March 2021
		
	}

	@Test
	void testDeleteProduct() {
		productService.deleteProduct(1L);
		
		verify(productRepository, times(1)).deleteById(anyLong());
	}
}
