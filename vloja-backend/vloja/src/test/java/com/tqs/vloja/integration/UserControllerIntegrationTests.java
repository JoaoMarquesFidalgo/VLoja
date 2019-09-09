package com.tqs.vloja.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tqs.vloja.VlojaApplication;
import com.tqs.vloja.classes.User;
import com.tqs.vloja.repositories.UserRepository;

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
public class UserControllerIntegrationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void makesApiGetCall() throws Exception {
		mockMvc.perform(get("/api/user")).andDo(print()).andExpect(status().is(200));
	}
	
	@Test
	public void makesApiPostCallThenUseRepository() throws Exception {
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

	    User newUser = userRepository.findById(1).get();
	    assertThat(newUser.getEmail()).isEqualTo("email");
	}
	
	@Test
	public void makesApiPostCallThenUseGetCall() throws Exception {
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

	    mockMvc.perform(get("/api/user")).andDo(print()).andExpect(status().is(200))
	    .andExpect(content().string("{\"error\":false,\"code\":1000,\"description\":\"Users returned with sucess\",\"data\":[{\"email\":\"email\",\"password\":\"password\",\"name\":\"name\",\"language\":\"PT\",\"products\":[],\"lists\":[],\"favorites\":[],\"image\":\"image\",\"userId\":1}]}"));	    
	}
	
	@Test
	public void makesApiPostCallThenUsePutCallAndThenGetCall() throws Exception {
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
	    		.andDo(print()).andExpect(status().is(200))
	    		.andExpect(content().string("{\"error\":false,\"code\":1000,\"description\":\"Users returned with sucess\",\"data\":[{\"email\":\"email\",\"password\":\"password\",\"name\":\"name\",\"language\":\"PT\",\"products\":[],\"lists\":[],\"favorites\":[],\"image\":\"image\",\"userId\":1}]}"));	    
	    
	    user.setEmail("email_");
		user.setImage("image_");
		user.setLanguage("PT_");
		user.setPassword("password_");
		user.setName("name_");
	    
	    mockMvc.perform(put("/api/user/" + 1)
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(user)))
	    		.andDo(print())
	            .andExpect(status().isOk());
	    
	    mockMvc.perform(get("/api/user"))
	    		.andDo(print())
	    		.andExpect(status().is(200))
    			.andExpect(content().string("{\"error\":false,\"code\":1000,\"description\":\"Users returned with sucess\",\"data\":[{\"email\":\"email_\",\"password\":\"password_\",\"name\":\"name_\",\"language\":\"PT_\",\"products\":[],\"lists\":[],\"favorites\":[],\"image\":\"image_\",\"userId\":1}]}"));	    
	}
}
