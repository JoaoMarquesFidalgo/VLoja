package com.tqs.vloja.requests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.tqs.vloja.classes.ApiResponse;
import com.tqs.vloja.utils.Utils;

@Controller
@CrossOrigin
@RequestMapping(path="/api")
public class JsonRequests {
	
	ApiResponse apiResponse = new ApiResponse();
	Utils utils = new Utils();
	
	/*
	 * Returns a JSON file from the server containing categories and brands of products
	 */
	
	@GetMapping(path="/category-brand") 
	public @ResponseBody ApiResponse getCategoryBrand() throws URISyntaxException, IOException{
		
		URL res = getClass().getClassLoader().getResource("category-brand.json");
		File jsonFile = Paths.get(res.toURI()).toFile();
		
		String jsonData;
		FileReader fileReader = new FileReader(jsonFile);
		BufferedReader br = new BufferedReader(fileReader);
		StringBuilder sb=  new StringBuilder();
		while((jsonData = br.readLine())!=null)
		{
		    sb.append(jsonData);
		}
		jsonData = sb.toString();
		br.close();

		apiResponse = utils.setMessage(false, 1701, "File returned with success", jsonData);
	    
		return apiResponse;
	}
}
