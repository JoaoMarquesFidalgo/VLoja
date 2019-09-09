package com.tqs.vloja;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VlojaApplication {

	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(VlojaApplication.class);
		builder.headless(false);
	    ConfigurableApplicationContext context = builder.run(args);
	}

}
