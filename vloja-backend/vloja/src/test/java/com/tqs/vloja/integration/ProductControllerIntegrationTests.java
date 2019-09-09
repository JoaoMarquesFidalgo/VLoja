package com.tqs.vloja.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tqs.vloja.VlojaApplication;
import com.tqs.vloja.classes.Product;
import com.tqs.vloja.classes.User;
import com.tqs.vloja.repositories.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(
		classes={VlojaApplication.class}, 
		properties = {
	        "spring.jpa.hibernate.ddl-auto=create-drop",
	        "spring.liquibase.enabled=false",
	        "spring.flyway.enabled=false",
	        "spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/tqs?useTimezone=true&serverTimezone=UTC",
	    	"spring.datasource.username=fidalgo",
	    	"spring.datasource.password=1234"
})
@AutoConfigureMockMvc
public class ProductControllerIntegrationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	public void makesApiGetCall() throws Exception {
		mockMvc.perform(get("/api/product")).andDo(print()).andExpect(status().is(200));
	}
	
	@Test
	public void addUserThenAddProductThenDeleteProduct() throws Exception {
		User user = new User();
		user.setEmail("email");
		user.setImage("image");
		user.setLanguage("PT");
		user.setPassword("password");
		user.setName("name");
		
	    mockMvc.perform(post("/api/addUser")
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(user)))
	            .andExpect(status().isOk());

	    mockMvc.perform(get("/api/user"))
	    		.andDo(print()).andExpect(status().is(200));
	    
	    Product product = new Product();
	    product.setBrand("brand");
	    product.setCategory("category");
	    product.setImage("image");
	    product.setName("name");
	    product.setPrice(1.2);
	    product.setUserId(4);
	    
	    mockMvc.perform(post("/api/product")
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(product)))
	            .andExpect(status().isOk());
	    
	    mockMvc.perform(get("/api/product"))
				.andDo(print())
				.andExpect(status().is(200));
	    
	    // @DeleteMapping(path="/product/{productId}/{userId}") 
	    mockMvc.perform(delete("/api/product/" + 1 + "/" + 4)
	            .contentType("application/json"))
	    		.andDo(print())
	            .andExpect(status().isOk());
	    
	    mockMvc.perform(get("/api/product")
	    		.contentType("application/json"))
	    		.andDo(print())
	    		.andExpect(status().is(200))
	    		.andExpect(content().string("{\"error\":true,\"code\":2100,\"description\":\"No data to return.\",\"data\":null}"));
	}
}
