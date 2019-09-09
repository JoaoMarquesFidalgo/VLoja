package com.tqs.vloja.unit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import com.tqs.vloja.classes.List_;
import com.tqs.vloja.classes.Product;
import com.tqs.vloja.classes.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {
	
	User user = new User();
	
	@Test
	public void nullTest() {
		Assert.assertEquals(user.getName(), null);
	}
	
	@Test
	public void nameTest() {
		user.setName("name");
		Assert.assertEquals(user.getName(), "name");
	}
	
	@Test
	public void emailTest() {
		user.setEmail("email");
		Assert.assertEquals(user.getEmail(), "email");
	}
	
	@Test
	public void passwordTest() {
		user.setPassword("password");
		Assert.assertEquals(user.getPassword(), "password");
	}
	
	@Test
	public void languageTest() {
		user.setLanguage("language");
		Assert.assertEquals(user.getLanguage(), "language");
	}
	
	@Test
	public void productTest() {
		List<Product> products = new ArrayList<Product>();
		Product newProduct = new Product();
		newProduct.setBrand("brand");
		products.add(newProduct);
		Assert.assertEquals(products.size() > 0, true);
		Assert.assertEquals(products.get(0).getBrand(), "brand");
	}
	
	@Test
	public void listTest() {
		List<List_> list = new ArrayList<List_>();
		List_ newList = new List_();
		newList.setAlias("alias");
		list.add(newList);
		Assert.assertEquals(list.size() > 0, true);
		Assert.assertEquals(list.get(0).getAlias(), "alias");
	}
}
