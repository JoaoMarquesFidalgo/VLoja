package com.tqs.vloja.requests;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tqs.vloja.classes.ApiResponse;
import com.tqs.vloja.classes.User;
import com.tqs.vloja.repositories.UserRepository;
import com.tqs.vloja.utils.Utils;

@Controller
@CrossOrigin
@RequestMapping(path="/api")
public class UserRequests {
	
	@Autowired
	private UserRepository userRepository;
	
	ApiResponse apiResponse = new ApiResponse();
	Utils utils = new Utils();
	
	/*
	 * Creates a new Api Response object, searchs for users in the database using user repository
	 * checks if there are any records, if so, returns a list of users, otherwise, returns an error
	 */
	
	@GetMapping(path="/user")
	public @ResponseBody ApiResponse getAllUsers() throws SQLException {
		Iterable<User> users = userRepository.findAll();
		if (users.iterator().hasNext())
		{
			apiResponse = utils.setMessage(false, 1000, "Users returned with sucess", users);
		} else {
			apiResponse = utils.setMessage(true, 2000, "No data to return", null);
		}
		return this.apiResponse;
	}
	
	/*
	 * Operation similar to logoutUser, however, in this case, it returns the user information
	 */
	
	@GetMapping(path="/user/{userId}") 
	public @ResponseBody ApiResponse getUserById(@PathVariable Integer userId) throws SQLException {
		try {
			User userDB = userRepository.findById(userId).get();
			apiResponse = utils.setMessage(false, 1004, "User returned with sucess", userDB);
		} catch (NoSuchElementException e) {
			apiResponse = utils.setMessage(true, 2004, "User does not exist", null);
		}
		return apiResponse;
	}
	
	/*
	 * In this put request, we receive the id of the user to update and the body, then, inside
	 * a try catch, it searches for a user with the given id, then, if does not fall into the 
	 * catch, re-sets the properties of the user and saves with the new information
	 */
	
	@PutMapping(path="/user/{userId}") 
	public @ResponseBody ApiResponse updateUser(@PathVariable Integer userId, 
			@RequestBody(required = false) User user) throws SQLException, 
			IllegalArgumentException {
		try {
			User userDB = userRepository.findById(userId).get();
			userDB.setEmail(user.getEmail());
			userDB.setPassword(user.getPassword());
			userDB.setName(user.getName());
			userDB.setLanguage(user.getLanguage());
			userDB.setImage(user.getImage());
			userRepository.save(userDB);
			apiResponse = utils.setMessage(false, 1005, "User updated with sucess", 
					userDB);
		} catch (NoSuchElementException e) {
			apiResponse = utils.setMessage(true, 2005, "User does not exist", null);
		} catch (DataIntegrityViolationException ex) {
	    	apiResponse = utils.setMessage(true, 2006, "Email already used", null);
	    }
		return apiResponse;
	}
}
