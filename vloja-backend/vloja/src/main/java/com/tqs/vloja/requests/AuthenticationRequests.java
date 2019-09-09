package com.tqs.vloja.requests;

import java.sql.SQLException;	
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
public class AuthenticationRequests {
	
	@Autowired
	private UserRepository userRepository;
	
	ApiResponse apiResponse = new ApiResponse();
	Utils utils = new Utils();
	
	/*
	 * Gets the body of a user, then initiates a try catch in order to save the user in the db,
	 * if save is sucessfull, returns a message with the user, else, returns the exception error
	 */
	
	@PostMapping(path="/addUser") 
	public @ResponseBody ApiResponse addUser(@RequestBody User user) throws SQLException {
		try {
			userRepository.save(user);
			apiResponse = utils.setMessage(false, 1401, "User create with sucess", user);
	    }
	    catch (DataIntegrityViolationException e) {
	    	apiResponse = utils.setMessage(true, 2401, "Email already used", null);
	    }
		return apiResponse;
	}
	
	/*
	 * Gets the inserted email and password and compares against the custom JPA query, in the try
	 * catch, checks if there is an id, if not, catchs a null pointer exception and returns an error
	 */
	
	@PostMapping(path="/user/login") 
	public @ResponseBody ApiResponse loginUser(@RequestBody User user) throws SQLException {
		User userDB = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
		try {
			userDB.getUserId();
			apiResponse = utils.setMessage(false, 1402, "User logged in with success", userDB);
		} catch (NullPointerException e) {
			apiResponse = utils.setMessage(true, 2402, "Wrong credentials", null);
		}
		return apiResponse;
	}
	
	/*
	 * TODO: Because of time constraints, it was not possible to add a session functionality to the
	 * application, because of this, the frontend will receive a message with error and act upon it
	 */
	
	@GetMapping(path="/user/logout/{userId}") 
	public @ResponseBody ApiResponse logoutUser(@PathVariable Integer userId) throws SQLException {
		try {
			userRepository.findById(userId).get();
			apiResponse = utils.setMessage(false, 1403, "Session terminated", null);
		} catch (NoSuchElementException e) {
			apiResponse = utils.setMessage(true, 3403, "User does not exist", null);
		}
		return apiResponse;
	}
	
}
