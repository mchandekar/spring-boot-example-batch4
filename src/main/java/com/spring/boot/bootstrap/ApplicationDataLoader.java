package com.spring.boot.bootstrap;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.spring.boot.dto.CustomerDTO;
import com.spring.boot.dto.ProductDTO;
import com.spring.boot.service.CustomerService;
import com.spring.boot.service.ProductService;

@Component
public class ApplicationDataLoader implements CommandLineRunner {

	private ProductService productService;
	
	private CustomerService customerService;
	
	public ApplicationDataLoader(final ProductService productService, final CustomerService customerService) {
		this.productService = productService;
		this.customerService = customerService;
	}
	
	@Override
	public void run(String... args) throws Exception {

		System.out.println("Inside Product Data Loader");
		
		// Products
//		List<ProductDTO> products = productService.getAllProducts();
//		if(products != null && products.size() == 0) {
//			System.out.println("Loading Products... !!!");
//			loadProductData();
//		}
//		
//		// Customers
//		List<CustomerDTO> customers = customerService.getAllCustomers();
//		if(customers != null && customers.size() == 0) {
//			loadCustomerData();
//		}
		
		
		// Aggregate Customer and Product
		loadAggregateData();
	}
	
	private void loadAggregateData() {
		CustomerDTO customer1 = new CustomerDTO(null, "Customer 1", "Customer 1", "abc@abc.com", "Mumbai");
		customer1 = customerService.createCustomer(customer1);
		
		ProductDTO laptop = new ProductDTO();
		laptop.setId(1L);
		laptop.setName("Laptop");
		laptop.setDescription("Laptop");
		laptop.setPrice(new BigDecimal(1000));
		laptop.setCustomer(customer1);
		productService.createProduct(laptop);
		
		ProductDTO tablet = new ProductDTO();
		tablet.setId(2L);
		tablet.setName("Tablet");
		tablet.setDescription("Tablet");
		tablet.setPrice(new BigDecimal(500));
		tablet.setCustomer(customer1);
		productService.createProduct(tablet);

	}
	
	private void loadCustomerData() {
		CustomerDTO customer1 = new CustomerDTO(null, "Customer 1", "Customer 1", "abc@abc.com", "Mumbai");
		customerService.createCustomer(customer1);

		CustomerDTO customer2 = new CustomerDTO(null, "Customer 2", "Customer 2", "abc@abc.com", "Chennai");
		customerService.createCustomer(customer2);

		CustomerDTO customer3 = new CustomerDTO(null, "Customer 3", "Customer 3", "abc@abc.com", "Delhi");
		customerService.createCustomer(customer3);
	}
	
	private void loadProductData() {
		ProductDTO laptop = new ProductDTO();
		laptop.setId(1L);
		laptop.setName("Laptop");
		laptop.setDescription("Laptop");
		laptop.setPrice(new BigDecimal(1000));
		productService.createProduct(laptop);
		
		ProductDTO tablet = new ProductDTO();
		tablet.setId(2L);
		tablet.setName("Tablet");
		tablet.setDescription("Tablet");
		tablet.setPrice(new BigDecimal(500));
		productService.createProduct(tablet);
		
		ProductDTO mobile = new ProductDTO();
		mobile.setId(3L);
		mobile.setName("Mobile");
		mobile.setDescription("Mobile");
		mobile.setPrice(new BigDecimal(300));
		productService.createProduct(mobile);
		
		ProductDTO penDrive = new ProductDTO();
		penDrive.setId(4L);
		penDrive.setName("PenDrive");
		penDrive.setDescription("PenDrive");
		penDrive.setPrice(new BigDecimal(10));
		productService.createProduct(penDrive);

		ProductDTO car = new ProductDTO();
		car.setId(5L);
		car.setName("Car");
		car.setDescription("Car");
		car.setPrice(new BigDecimal(20000));
		productService.createProduct(car);
	}
}
