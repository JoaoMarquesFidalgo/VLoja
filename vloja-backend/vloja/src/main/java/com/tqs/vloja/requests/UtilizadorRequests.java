package com.tqs.vloja.requests;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tqs.vloja.classes.ApiResponse;
import com.tqs.vloja.classes.Utilizador;
import com.tqs.vloja.repositories.UtilizadorRepository;
import com.tqs.vloja.utils.Utils;

@Controller
@RequestMapping(path="/api")
public class UtilizadorRequests {
	
	@Autowired
	private UtilizadorRepository utilizadorRepository;
	
	ApiResponse apiResponse = new ApiResponse();
	Utils utils = new Utils();
	
	/*
	 * Creates a new Api Response object, searchs for users in the database using user repository
	 * checks if there are any records, if so, returns a list of users, otherwise, returns an error
	 */
	
	@GetMapping(path="/getUser")
	public @ResponseBody ApiResponse getAllUsers() throws SQLException {
		Iterable<Utilizador> utilizadores = utilizadorRepository.findAll();
		if (utilizadores.iterator().hasNext())
		{
			apiResponse = utils.setMessage(false, 1000, "Consulta realizada com sucesso.", utilizadores);
		} else {
			apiResponse = utils.setMessage(true, 2000, "Não há registos a devolver.", null);
		}
		return this.apiResponse;
	}
	
	/*
	 * Operation similar to logoutUser, however, in this case, it returns the user information
	 */
	
	@GetMapping(path="/user/{userId}") 
	public @ResponseBody ApiResponse getUserById(@PathVariable Integer userId) throws SQLException {
		try {
			Utilizador utilizadorDB = utilizadorRepository.findById(userId).get();
			apiResponse = utils.setMessage(false, 1004, "Utilizador retornado com sucesso", 
					utilizadorDB);
		} catch (NoSuchElementException e) {
			apiResponse = utils.setMessage(true, 2004, "Utilizador não existe", null);
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
			@RequestBody(required = false) Utilizador utilizador) throws SQLException, 
			IllegalArgumentException {
		try {
			Utilizador utilizadorDB = utilizadorRepository.findById(userId).get();
			utilizadorDB.setEmail(utilizador.getEmail());
			utilizadorDB.setPassword(utilizador.getPassword());
			utilizadorDB.setNome(utilizador.getNome());
			utilizadorDB.setLingua(utilizador.getLingua());
			utilizadorDB.setImagem(utilizador.getImagem());
			utilizadorRepository.save(utilizadorDB);
			apiResponse = utils.setMessage(false, 1005, "Utilizador atualizado com sucesso", 
					utilizadorDB);
		} catch (NoSuchElementException e) {
			apiResponse = utils.setMessage(true, 2005, "Utilizador não existe", null);
		} catch (DataIntegrityViolationException ex) {
	    	apiResponse = utils.setMessage(true, 2006, ex.getMessage(), null);
	    }
		return apiResponse;
	}
}
