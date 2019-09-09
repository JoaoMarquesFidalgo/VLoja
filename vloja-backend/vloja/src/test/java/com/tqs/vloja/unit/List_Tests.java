package com.tqs.vloja.unit;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tqs.vloja.classes.List_;

@RunWith(SpringRunner.class)
@SpringBootTest
public class List_Tests {
	
	List_ list = new List_();
	
	@Test
	public void nullTest() {
		Assert.assertEquals(list.getAlias(), null);
	}
	
	@Test
	public void aliasTest() {
		list.setAlias("alias");
		Assert.assertEquals(list.getAlias(), "alias");
	}
	
	@Test
	public void categoryTest() {
		list.setCategory("category");
		Assert.assertEquals(list.getCategory(), "category");
	}
	
	@Test
	public void dateTest() {
		list.setDate("date");
		Assert.assertEquals(list.getDate(), "date");
	}
}
