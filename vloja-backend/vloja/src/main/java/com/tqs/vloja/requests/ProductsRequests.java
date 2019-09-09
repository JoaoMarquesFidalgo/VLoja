package com.tqs.vloja.requests;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import com.tqs.vloja.classes.ApiResponse;
import com.tqs.vloja.classes.Product;
import com.tqs.vloja.repositories.ProductRepository;
import com.tqs.vloja.utils.Utils;

@RestController
@CrossOrigin
@RequestMapping(path="/api")
public class ProductsRequests {

	@Autowired
	private ProductRepository productRepository;
	
	ApiResponse apiResponse = new ApiResponse();
	Utils utils = new Utils();
	
	/*
	 * Function to return all products
	 */
	
	@GetMapping(path="/product")
	public @ResponseBody ApiResponse getProduct() throws SQLException {
		Iterable<Product> products = productRepository.findAll();
		if (products.iterator().hasNext())
		{
			apiResponse = utils.setMessage(false, 1100, "Products returned with success.", products);
		} else {
			apiResponse = utils.setMessage(true, 2100, "No data to return.", null);
		}
		return this.apiResponse;
	}
	
	/*
	 * Function to add a product to the db
	 */
	
	@PostMapping(path="/product") 
	public @ResponseBody ApiResponse addProduct(@RequestBody(required = false) Product product) 
			throws SQLException {
		try {
			Product productDB = productRepository.save(product);
			apiResponse = utils.setMessage(false, 1101, "Product saved with sucess", productDB);
	    } catch (DataIntegrityViolationException e) {
	    	apiResponse = utils.setMessage(true, 2101, e.getMessage(), null);
	    }
		return apiResponse;
	}
	
	/*
	 * Function to upload an image of a product
	 */
	
	@PostMapping(path="/productImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE) 
	public @ResponseBody ApiResponse addProductImage(@RequestParam("file") MultipartFile file) 
			throws SQLException, IOException {
		try {
			String fileName = System.currentTimeMillis() + file.getOriginalFilename();
			String url = Paths.get("./src\\main\\resources\\images").toAbsolutePath().normalize().toString()
					+ "\\" + fileName;
			File convertFile = new File(url);
			FileOutputStream fou = new FileOutputStream(convertFile);
			fou.write(file.getBytes());
			fou.close();
			apiResponse = utils.setMessage(false, 1105, "Product image saved with sucess", fileName);
	    } catch (DataIntegrityViolationException e) {
	    	apiResponse = utils.setMessage(true, 2110, e.getMessage(), null);
	    }
		return apiResponse;
	}
	
	/*
	 * Function to return one product
	 */
	
	@GetMapping(path="/product/{productId}") 
	public @ResponseBody ApiResponse getProductById(@PathVariable Integer productId) throws SQLException {
		try {
			Product productDB = productRepository.findById(productId).get();
			apiResponse = utils.setMessage(false, 1102, "Product returned with success", 
					productDB);
		} catch (NoSuchElementException e) {
			apiResponse = utils.setMessage(true, 2102, "Product does not exist", null);
		}
		return apiResponse;
	}
	
	/*
	 * Function to update one product, given a userId, checks if its the same that originally
	 * created the product and then proceds to update it
	 */
	
	@PutMapping(path="/product/{productId}/{userId}") 
	public @ResponseBody ApiResponse updateProduct(@PathVariable Integer productId, 
			@RequestBody(required = false) Product product, @PathVariable Integer userId
			) throws SQLException, IllegalArgumentException {
		try {
			Product productDB = productRepository.findById(productId).get();
			if (utils.checkUser(productDB.getUserId(), userId)) {
				productDB.setName(product.getName());
				productDB.setCategory(product.getCategory());
				productDB.setPrice(product.getPrice());
				productDB.setImage(product.getImage());
				productDB.setBrand(product.getBrand());
				productDB.setUserId(product.getUserId());
				productRepository.save(productDB);
				apiResponse = utils.setMessage(false, 1103, "Product updated with success", 
						productDB);
			} else {
				apiResponse = utils.setMessage(true, 2103, "The user does not have rights to "
						+ "update this product", null);
			}
		} catch (NoSuchElementException e) {
			apiResponse = utils.setMessage(true, 2104, "Product does not exist", null);
		} catch (DataIntegrityViolationException ex) {
	    	apiResponse = utils.setMessage(true, 2105, ex.getMessage(), null);
	    }
		return apiResponse;
	}
	
	/*
	 * Function to delete one product, given a userId, checks if its the same that originally
	 * created the product and then proceds to remove it
	 */
	
	@DeleteMapping(path="/product/{productId}/{userId}") 
	public @ResponseBody ApiResponse deleteProduct(@PathVariable Integer productId, 
			@PathVariable Integer userId) throws SQLException, IllegalArgumentException {
		try {
			Product productDB = productRepository.findById(productId).get();
			if (utils.checkUser(productDB.getUserId(), userId)) {
				productRepository.deleteById(productDB.getId());
				apiResponse = utils.setMessage(false, 1104, "Product deleted with success"
							, productDB);
			} else {
				apiResponse = utils.setMessage(true, 2106, "The user does not have rights to " +
						"delete this product", null);
			}
		} catch (NoSuchElementException e) {
			apiResponse = utils.setMessage(true, 2107, "Product does not exist", null);
		} catch (DataIntegrityViolationException ex) {
	    	apiResponse = utils.setMessage(true, 2108, ex.getMessage(), null);
	    }
		return apiResponse;
	}
}
