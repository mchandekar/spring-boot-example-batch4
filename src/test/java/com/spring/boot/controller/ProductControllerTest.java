package com.spring.boot.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.dto.ProductDTO;
import com.spring.boot.service.ProductService;

// @SpringBootTest
class ProductControllerTest {
	
	private static final String HP = "HP";

	private static final String LENOVO = "Lenovo";

	private static final String BASE_URL = "/api/products";
	
	// @Autowired
	private ProductController productController;
	
	@Mock
	private ProductService productService;
	
	private MockMvc mockMvc;

	private ProductDTO prod1;
	
	private ProductDTO prod2;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		
		productController = new ProductController(productService);
		
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
		
		loadProducts();
	}
	
	private void loadProducts() {
		prod1 = new ProductDTO();
		prod1.setId(1L);
		prod1.setName(LENOVO);
		prod1.setDescription(LENOVO);
		prod1.setPrice(new BigDecimal(150));
		
		prod2 = new ProductDTO();
		prod2.setId(2L);
		prod2.setName(HP);
		prod2.setDescription(HP);
		prod2.setPrice(new BigDecimal(100));
	}

	@Test
	void testGetAllProducts() throws Exception {
		// Mock Initialization
		when(productService.getAllProducts()).thenReturn(Arrays.asList(prod1, prod2));
	
		mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.products", Matchers.hasSize(2)));
	}

	@Test
	void testGetProductById() throws Exception {
		
		when(productService.getProductById(ArgumentMatchers.anyLong())).thenReturn(prod1);
		
		mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/1").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name", Matchers.equalTo(LENOVO)))
		.andExpect(jsonPath("$.description", Matchers.equalTo(LENOVO)));
	}
	
	@Test
	void testCreateProduct() throws Exception {
		ProductDTO givenProduct = new ProductDTO();
		// prod.setId(2L);
		givenProduct.setName(HP);
		givenProduct.setDescription(HP);
		givenProduct.setPrice(new BigDecimal(100));
		
		ProductDTO createdProduct = new ProductDTO();
		createdProduct.setId(2L);
		createdProduct.setName(HP);
		createdProduct.setDescription(HP);
		createdProduct.setPrice(new BigDecimal(100));
		
		when(productService.createProduct(ArgumentMatchers.any())).thenReturn(createdProduct);
		
		mockMvc.perform(
				MockMvcRequestBuilders.post(BASE_URL).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonString(givenProduct)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", Matchers.equalTo(2)))
		.andExpect(jsonPath("$.name", Matchers.equalTo(HP)))
		.andExpect(jsonPath("$.description", Matchers.equalTo(HP)));
	}
	
	private String jsonString(Object object) {
		String jsonString = null;
		if(object != null) {
			// Jackson Libray
			try {
				jsonString = new ObjectMapper().writeValueAsString(object);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return jsonString;
	}

	@Test
	void testUpdateProduct() {
		// TODO-FIXME Batch 4 to implement it after today's class
	}

	@Test
	void testDeleteProduct() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL+"/2"));
		
		verify(productService, times(1)).deleteProduct(2L);
	}
}
