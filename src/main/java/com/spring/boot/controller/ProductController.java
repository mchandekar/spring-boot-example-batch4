package com.spring.boot.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.dto.ProductDTO;
import com.spring.boot.dto.ProductListDTO;
import com.spring.boot.exception.ProductValidationException;
import com.spring.boot.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	private static final String PIPE = "|";
	private ProductService productService;
	
	public ProductController(final ProductService productService) {
		this.productService = productService;
	}
	
	/*
	@RequestMapping("/")
	public String greetings() {
		return "WELCOME BACK - Product Services";
	}
	*/
	
	@GetMapping(path = {"/", ""})
	public ProductListDTO getAllProducts(){
		return new ProductListDTO(productService.getAllProducts());
	}
	
	// Get Product - One Product
	// @GetMapping("/{id}")
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ProductDTO getProductById(@PathVariable final String id) {
		return productService.getProductById(Long.valueOf(id));
	}
	
	// Create Product
	@PostMapping({"/", ""})
	public ProductDTO createProduct(@Valid final @RequestBody ProductDTO productDTO, final BindingResult bindingResult) {
		
		StringBuilder stringBuilder = new StringBuilder();
		if(bindingResult != null && bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(error -> {
				stringBuilder.append(PIPE);
				stringBuilder.append(error.getDefaultMessage());
			});
			
			String validationErrros = stringBuilder.toString();
			throw new ProductValidationException(validationErrros);
		}
		
		return productService.createProduct(productDTO);
	}
	
	
	
	// Update Product - id
	@PutMapping("/{id}")
	// @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	// @PostMapping("/{id}")
	public ProductDTO updateProduct(final @PathVariable("id") String id, @RequestBody ProductDTO productDTO) {
		ProductDTO updatedProduct = null;
		if(productDTO != null) {
			if(id != null) {
				productDTO.setId(Long.valueOf(id));
			}
			updatedProduct = productService.updateProduct(productDTO);
		}
		return updatedProduct;
	}
	
	// Delete Product
	@DeleteMapping("/{id}")
	public void deleteProduct(final @PathVariable String id) {
		if(id != null) {
			productService.deleteProduct(Long.valueOf(id));
		}
	}
	
	// CRUD - Create, Read, Update, Delete
	// HTTP - POST	, GET,	PUT,	DELETE 
	
}
