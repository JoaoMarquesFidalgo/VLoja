package com.tqs.vloja.requests;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
}
