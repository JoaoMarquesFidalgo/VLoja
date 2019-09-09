package com.tqs.vloja.classes;

public class ApiResponse {
	
	/*
	 * Body of a normal response from the server, fields:
	 * Error: returns a boolean that informs the front-end if the request was successfully made
	 * Code: returns an integer that indicates the type of error, usefull to treat errors
	 * Description: a description of the response to the request
	 * Data: the body of the response to the request made, when there is no error
	 */
	
	private Boolean error;
	private Integer code;
	private String description;
	private Object data;
	
	public ApiResponse(Boolean error, Integer code, String description, Object data) {
		super();
		this.error = error;
		this.code = code;
		this.description = description;
		this.data = data;
	}
	
	public ApiResponse(Boolean error, Integer code, String description) {
		super();
		this.error = error;
		this.code = code;
		this.description = description;
	}
	
	public ApiResponse() {
		super();
	}
	
	public Boolean getError() {
		return error;
	}
	
	public void setError(Boolean error) {
		this.error = error;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public void setCode(Integer code) {
		this.code = code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
}