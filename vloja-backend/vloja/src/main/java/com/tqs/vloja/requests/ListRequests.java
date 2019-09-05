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
import com.tqs.vloja.repositories.ListRepository;
import com.tqs.vloja.utils.Utils;

@Controller
@CrossOrigin
@RequestMapping(path="/api")
public class ListRequests {
	@Autowired
	private ListRepository listRepository;
	
	ApiResponse apiResponse = new ApiResponse();
	Utils utils = new Utils();
	
	/*
	 * Function to return all lists
	 */
	
	@GetMapping(path="/list")
	public @ResponseBody ApiResponse getProduct() throws SQLException {
		Iterable<List_> lists = listRepository.findAll();
		if (lists.iterator().hasNext())
		{
			apiResponse = utils.setMessage(false, 1200, "Lists returned with success.", lists);
		} else {
			apiResponse = utils.setMessage(true, 2200, "No data to return.", null);
		}
		return this.apiResponse;
	}
	
	/*
	 * Function to add a list to the db
	 */
	
	@PostMapping(path="/list") 
	public @ResponseBody ApiResponse addProduct(@RequestBody(required = false) List_ list) 
			throws SQLException {
		try {
			List_ listDB = listRepository.save(list);
			apiResponse = utils.setMessage(false, 1201, "List saved with sucess", listDB);
	    }
	    catch (DataIntegrityViolationException e) {
	    	apiResponse = utils.setMessage(true, 2201, e.getMessage(), null);
	    }
		return apiResponse;
	}
	/*
	 * Function to return one list
	 */
	
	@GetMapping(path="/list/{listId}") 
	public @ResponseBody ApiResponse getProductById(@PathVariable Integer listId) 
			throws SQLException {
		try {
			List_ listDB = listRepository.findById(listId).get();
			apiResponse = utils.setMessage(false, 1202, "List returned with success", listDB);
		} catch (NoSuchElementException e) {
			apiResponse = utils.setMessage(true, 2202, "List does not exist", null);
		}
		return apiResponse;
	}
	
	/*
	 * Function to update one list, given a userId, checks if its the same that originally
	 * created the list and then proceds to update it
	 */
	
	@PutMapping(path="/list/{listId}/{userId}") 
	public @ResponseBody ApiResponse updateProduct(@PathVariable Integer listId, 
			@RequestBody(required = false) List_ list, @PathVariable Integer userId
			) throws SQLException, IllegalArgumentException {
		try {
			List_ listDB = listRepository.findById(listId).get();
			if (utils.checkUser(listDB.getUserId(), userId)) {
				listDB.setAlias(list.getAlias());
				listDB.setCategory(list.getCategory());
				listDB.setDate(list.getDate());
				listDB.setUserId(list.getUserId());
				listRepository.save(listDB);
				apiResponse = utils.setMessage(false, 1203, "List updated with success", listDB);
			} else {
				apiResponse = utils.setMessage(true, 2203, "The user does not have rights to "
						+ "update this list", null);
			}
		} catch (NoSuchElementException e) {
			apiResponse = utils.setMessage(true, 2204, "List does not exist", null);
		} catch (DataIntegrityViolationException ex) {
	    	apiResponse = utils.setMessage(true, 2205, ex.getMessage(), null);
	    }
		return apiResponse;
	}
	
	/*
	 * Function to delete one list, given a userId, checks if its the same that originally
	 * created the list and then proceds to remove it
	 */
	
	@DeleteMapping(path="/list/{listId}/{userId}") 
	public @ResponseBody ApiResponse deleteProduct(@PathVariable Integer listId, 
			@PathVariable Integer userId) throws SQLException, IllegalArgumentException {
		try {
			List_ listDB = listRepository.findById(listId).get();
			if (utils.checkUser(listDB.getUserId(), userId)) {
				listRepository.deleteById(listDB.getId());
				apiResponse = utils.setMessage(false, 1204, "List deleted with success"
							, listDB);
			} else {
				apiResponse = utils.setMessage(true, 2206, "The user does not have rights to " +
						"delete this list", null);
			}
		} catch (NoSuchElementException e) {
			apiResponse = utils.setMessage(true, 2207, "List does not exist", null);
		} catch (DataIntegrityViolationException ex) {
	    	apiResponse = utils.setMessage(true, 2208, ex.getMessage(), null);
	    }
		return apiResponse;
	}
}
