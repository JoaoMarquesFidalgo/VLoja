package com.tqs.vloja.utils;

import com.tqs.vloja.classes.ApiResponse;

public class Utils {
	
	public ApiResponse setMessage(Boolean error, Integer code, String description, Object body)
	{
		ApiResponse apiResponse = new ApiResponse(error, code, description, body);
		return apiResponse;
	}
}
