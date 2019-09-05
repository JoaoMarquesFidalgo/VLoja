package com.tqs.vloja.requests;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tqs.vloja.classes.ApiResponse;
import com.tqs.vloja.classes.List_;
import com.tqs.vloja.classes.ProductList;
import com.tqs.vloja.repositories.ListRepository;
import com.tqs.vloja.repositories.ProductListRepository;
import com.tqs.vloja.utils.Utils;

@Controller
@CrossOrigin
@RequestMapping(path="/api")
public class ProductListRequests {

	@Autowired
	private ProductListRepository productListRepository;
	
	@Autowired
	private ListRepository listRepository;
	
	ApiResponse apiResponse = new ApiResponse();
	Utils utils = new Utils();
	
	/*
	 * Function to return all productLists
	 */
	
	@GetMapping(path="/productList")
	public @ResponseBody ApiResponse getProductList() throws SQLException {
		Iterable<ProductList> productLists = productListRepository.findAll();
		if (productLists.iterator().hasNext())
		{
			apiResponse = utils.setMessage(false, 1400, "Product lists returned with success.", productLists);
		} else {
			apiResponse = utils.setMessage(true, 2400, "No data to return.", null);
		}
		return this.apiResponse;
	}
	
	/*
	 * Function to add a product list to the db
	 */
	
	@PostMapping(path="/productList") 
	public @ResponseBody ApiResponse addProductList(@RequestBody(required = false) ProductList productList) 
			throws SQLException {
		List_ listDb;
		try {
			listDb = listRepository.findById(productList.getList()).get();
		} catch (NoSuchElementException e) {
			apiResponse = utils.setMessage(true, 2409, "List does not exist", null);
			return apiResponse;
		}
		if (productListRepository.findByProductIdAndListId(productList.getProduct(), 
				listDb.getId()) != null) {
			apiResponse = utils.setMessage(true, 2410, "Product already registed for this list", null);
			return apiResponse;
		}
		try {
			ProductList productListDB = productListRepository.save(productList);
			apiResponse = utils.setMessage(false, 1401, "Product list saved with sucess", productListDB);
	    } catch (DataIntegrityViolationException e) {
	    	apiResponse = utils.setMessage(true, 24011, e.getMessage(), null);
	    }
		return apiResponse;
	}
	/*
	 * Function to return one product list
	 */
	
	@GetMapping(path="/productList/{productListId}") 
	public @ResponseBody ApiResponse getProductListById(@PathVariable Integer productListId) 
			throws SQLException {
		try {
			ProductList productListDB = productListRepository.findById(productListId).get();
			apiResponse = utils.setMessage(false, 1402, "Product list returned with success", 
					productListDB);
		} catch (NoSuchElementException e) {
			apiResponse = utils.setMessage(true, 2402, "Product list does not exist", null);
		}
		return apiResponse;
	}
	
	/*
	 * Function to update one product list, given a userId, checks if its the same that originally
	 * created the list and then proceds to update it
	 */
	
	@PutMapping(path="/productList/{productListId}/{listId}/{userId}") 
	public @ResponseBody ApiResponse updateProduct(@PathVariable Integer productListId, 
			@RequestBody(required = false) ProductList productList, @PathVariable Integer listId,
			@PathVariable Integer userId) throws SQLException, IllegalArgumentException {
		try {
			ProductList productListDb = productListRepository.findById(productListId).get();
			List_ listDb = listRepository.findById(listId).get();
			if (utils.checkUser(listDb.getUserId(), userId)) {
				if (productListRepository.findByProductIdAndListId(productList.getProduct(), 
						listId) != null) {
					apiResponse = utils.setMessage(true, 2412, "Product already registed for this list", null);
					return apiResponse;
				}
				productListDb.setProduct(productList.getProduct());
				productListDb.setList(productList.getList());
				productListDb.setWasBought(productList.getWasBought());
				productListRepository.save(productListDb);
				apiResponse = utils.setMessage(false, 1403, "Product list updated with success", 
						productListDb);
			} else {
				apiResponse = utils.setMessage(true, 2403, "The user does not have rights to "
						+ "update this product list", null);
			}
		} catch (NoSuchElementException e) {
			apiResponse = utils.setMessage(true, 2404, "Product list does not exist", null);
		} catch (DataIntegrityViolationException ex) {
	    	apiResponse = utils.setMessage(true, 2405, ex.getMessage(), null);
	    }
		return apiResponse;
	}
	
	/*
	 * Function to delete one product list, given a userId, checks if its the same that originally
	 * created the list and then proceds to remove it
	 */
	
	@DeleteMapping(path="/productList/{productListId}/{listId}/{userId}") 
	public @ResponseBody ApiResponse deleteProduct(@PathVariable Integer productListId,
			@PathVariable Integer listId, @PathVariable Integer userId) 
					throws SQLException, IllegalArgumentException {
		try {
			ProductList productListDb = productListRepository.findById(productListId).get();
			List_ listDb = listRepository.findById(listId).get();
			if (utils.checkUser(listDb.getUserId(), userId)) {
				productListRepository.deleteById(productListDb.getProductListId());
				apiResponse = utils.setMessage(false, 1404, "Product deleted with success"
							, productListDb);
			} else {
				apiResponse = utils.setMessage(true, 2406, "The user does not have rights to " +
						"delete this product list", null);
			}
		} catch (NoSuchElementException e) {
			apiResponse = utils.setMessage(true, 2407, "Product list does not exist", null);
		} catch (DataIntegrityViolationException ex) {
	    	apiResponse = utils.setMessage(true, 2408, ex.getMessage(), null);
	    }
		return apiResponse;
	}
}
