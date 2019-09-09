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
import com.tqs.vloja.classes.Favorite;
import com.tqs.vloja.repositories.FavoriteRepository;
import com.tqs.vloja.utils.Utils;

@Controller
@CrossOrigin
@RequestMapping(path="/api")
public class FavoriteRequests {
	@Autowired
	private FavoriteRepository favoriteRepository;
	
	ApiResponse apiResponse = new ApiResponse();
	Utils utils = new Utils();
	
	/*
	 * Function to return all favorites
	 */
	
	@GetMapping(path="/favorite")
	public @ResponseBody ApiResponse getFavorite() throws SQLException {
		Iterable<Favorite> favorites = favoriteRepository.findAll();
		if (favorites.iterator().hasNext())
		{
			apiResponse = utils.setMessage(false, 1300, "Favorites returned with success.", favorites);
		} else {
			apiResponse = utils.setMessage(true, 2300, "No data to return.", null);
		}
		return this.apiResponse;
	}
	
	/*
	 * Function to add a favorite to the db
	 */
	
	@PostMapping(path="/favorite") 
	public @ResponseBody ApiResponse addFavorite(@RequestBody(required = false) Favorite favorite) 
			throws SQLException {
		try {
			Favorite favoriteDB = favoriteRepository.save(favorite);
			apiResponse = utils.setMessage(false, 1301, "Favorite saved with sucess", favoriteDB);
	    }
	    catch (DataIntegrityViolationException e) {
	    	apiResponse = utils.setMessage(true, 2301, e.getMessage(), null);
	    }
		return apiResponse;
	}
	/*
	 * Function to return one favorite
	 */
	
	@GetMapping(path="/favorite/{favoriteId}") 
	public @ResponseBody ApiResponse getFavoriteById(@PathVariable Integer favoriteId) throws SQLException {
		try {
			Favorite favoriteDB = favoriteRepository.findById(favoriteId).get();
			apiResponse = utils.setMessage(false, 1302, "Favorite returned with success", 
					favoriteDB);
		} catch (NoSuchElementException e) {
			apiResponse = utils.setMessage(true, 2302, "Favorite does not exist", null);
		}
		return apiResponse;
	}
	
	/*
	 * Function to update one favorite, given a userId, checks if its the same that originally
	 * created the favorite and then proceds to update it
	 */
	
	@PutMapping(path="/favorite/{favoriteId}/{userId}") 
	public @ResponseBody ApiResponse updateFavorite(@PathVariable Integer favoriteId, 
			@RequestBody(required = false) Favorite favorite, @PathVariable Integer userId
			) throws SQLException, IllegalArgumentException {
		try {
			Favorite favoriteDB = favoriteRepository.findById(favoriteId).get();
			if (utils.checkUser(favoriteDB.getUser(), userId)) {
				favoriteDB.setDate(favorite.getDate());
				favoriteDB.setAlias(favorite.getAlias());
				favoriteDB.setProductId(favorite.getProduct());
				favoriteDB.setListId(favorite.getList());
				favoriteRepository.save(favoriteDB);
				apiResponse = utils.setMessage(false, 1303, "Favorite updated with success", 
						favoriteDB);
			} else {
				apiResponse = utils.setMessage(true, 2303, "The user does not have rights to "
						+ "update this favorite", null);
			}
		} catch (NoSuchElementException e) {
			apiResponse = utils.setMessage(true, 2304, "Favorite does not exist", null);
		} catch (DataIntegrityViolationException ex) {
	    	apiResponse = utils.setMessage(true, 2305, ex.getMessage(), null);
	    }
		return apiResponse;
	}
	
	/*
	 * Function to delete one favorite, given a userId, checks if its the same that originally
	 * created the favorite and then proceds to remove it
	 */
	
	@DeleteMapping(path="/favorite/{favoriteId}/{userId}") 
	public @ResponseBody ApiResponse deleteFavorite(@PathVariable Integer favoriteId, 
			@PathVariable Integer userId) throws SQLException, IllegalArgumentException {
		try {
			Favorite favoriteDB = favoriteRepository.findById(favoriteId).get();
			if (utils.checkUser(favoriteDB.getUser(), userId)) {
				favoriteRepository.deleteById(favoriteDB.getId());
				apiResponse = utils.setMessage(false, 1304, "Favorite deleted with success"
							, favoriteDB);
			} else {
				apiResponse = utils.setMessage(true, 2306, "The user does not have rights to " +
						"delete this favorite", null);
			}
		} catch (NoSuchElementException e) {
			apiResponse = utils.setMessage(true, 2307, "Favorite does not exist", null);
		} catch (DataIntegrityViolationException ex) {
	    	apiResponse = utils.setMessage(true, 2308, ex.getMessage(), null);
	    }
		return apiResponse;
	}
}
