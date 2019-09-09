package com.tqs.vloja.unit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tqs.vloja.classes.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductTests {
	
	Product product = new Product();
	
	@Test
	public void nullTest() {
		Assert.assertEquals(product.getName(), null);
	}
	
	@Test
	public void nameTest() {
		product.setName("name");
		Assert.assertEquals(product.getName(), "name");
	}
	
	@Test
	public void categoryTest() {
		product.setCategory("category");
		Assert.assertEquals(product.getCategory(), "category");
	}
	
	@Test
	public void priceTest() {
		product.setPrice(1.22);
		Assert.assertEquals(product.getPrice(), 1.22, 1.22);
	}
	
	@Test
	public void brandTest() {
		product.setBrand("brand");
		Assert.assertEquals(product.getBrand(), "brand");
	}
	
	@Test
	public void imageTest() {
		product.setImage("image");
		Assert.assertEquals(product.getImage(), "image");
	}
}
