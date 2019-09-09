package com.tqs.vloja.unit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tqs.vloja.classes.ProductList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductListTests {

	ProductList productList = new ProductList();

	@Test
	public void nullTest() {
		Assert.assertEquals(productList.getProduct(), null);
	}

	@Test
	public void productTest() {
		productList.setProduct(1);
		Assert.assertEquals(productList.getProduct(), 1, 1);
	}

	@Test
	public void listTest() {
		productList.setList(1);
		Assert.assertEquals(productList.getList(), 1, 1);
	}
	
	@Test
	public void wasBoughtTest() {
		productList.setWasBought(false);
		Assert.assertEquals(productList.getWasBought(), false);
	}
}
