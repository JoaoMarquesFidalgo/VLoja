package com.tqs.vloja.functional;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class FunctionalTests {

	private WebDriver driver;

	@Before
	public void setUp() throws Exception {
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	/*
	@Test
	@Deprecated
	public void testUntitledTestCase() throws Exception {
		driver.get("http://localhost:8080/api/start");
		driver.findElement(By.xpath(".//a[@href='/login'][1]")).click();
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("electricmoon");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("credo123");
		driver.findElement(By.name("login")).click();
		assertEquals("electricmoon", driver.findElement(By.linkText("electricmoon")).getText());
		driver.findElement(By.linkText("Sair")).click();
		assertEquals("Registar", driver.findElement(By.xpath(".//a[@href='/account/register'][1]")).getText());
	}
	*/
}
