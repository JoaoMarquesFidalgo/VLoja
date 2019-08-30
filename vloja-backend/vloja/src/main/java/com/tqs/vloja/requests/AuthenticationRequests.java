package com.tqs.vloja.requests;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tqs.vloja.classes.ApiResponse;
import com.tqs.vloja.classes.Utilizador;
import com.tqs.vloja.repositories.UtilizadorRepository;
import com.tqs.vloja.utils.Utils;

@Controller
@RequestMapping(path="/api")
public class AuthenticationRequests {
	
	@Autowired
	private UtilizadorRepository utilizadorRepository;
	
	ApiResponse apiResponse = new ApiResponse();
	Utils utils = new Utils();
	
	/*
	 * Gets the body of a user, then initiates a try catch in order to save the user in the db,
	 * if save is sucessfull, returns a message with the user, else, returns the exception error
	 */
	
	@PostMapping(path="/addUser") 
	public @ResponseBody ApiResponse addUser(@RequestBody Utilizador utilizador) throws SQLException {
		try {
			utilizadorRepository.save(utilizador);
			apiResponse = utils.setMessage(false, 1001, "Utilizador gravado com sucesso", utilizador);
	    }
	    catch (DataIntegrityViolationException e) {
	    	apiResponse = utils.setMessage(true, 2001, e.getMessage(), null);
	    }
		return apiResponse;
	}
	
	/*
	 * Gets the inserted email and password and compares against the custom JPA query, in the try
	 * catch, checks if there is an id, if not, catchs a null pointer exception and returns an error
	 */

	@PostMapping(path="/user/login") 
	public @ResponseBody ApiResponse loginUser(@RequestBody Utilizador utilizador) throws SQLException {
		Utilizador utilizadorDB = utilizadorRepository.findByEmailAndPassword(utilizador.getEmail(), 
					utilizador.getPassword());
		try {
			utilizadorDB.getId();
			apiResponse = utils.setMessage(false, 1002, "Utilizador logado com sucesso", utilizadorDB);
		} catch (NullPointerException e) {
			apiResponse = utils.setMessage(false, 2002, "Credenciais incorretas", null);
		}
		return apiResponse;
	}
	
	/*
	 * TODO: Because of time constraints, it was not possible to add a session functionality to the
	 * application, because of this, the frontend will receive a message with error and act upon it
	 */
	
	@GetMapping(path="/user/logout/{userId}") 
	public @ResponseBody ApiResponse logoutUser(@PathVariable Integer userId) throws SQLException {
		Optional<Utilizador> utilizadorDB = utilizadorRepository.findById(userId);
		try {
			utilizadorDB.get();
			apiResponse = utils.setMessage(false, 1003, "Sessão terminada", null);
		} catch (NoSuchElementException e) {
			apiResponse = utils.setMessage(true, 3003, "Utilizador não existe", null);
		}
		return apiResponse;
	}
}
