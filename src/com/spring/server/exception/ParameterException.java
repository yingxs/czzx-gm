package com.spring.server.exception;

public class ParameterException extends Exception {

	protected String paramName;
	private String message;
	public ParameterException() {
		super();
		this.message="参数异常";
		// TODO Auto-generated constructor stub
	}


	public ParameterException(String message) {
		super();
		// TODO Auto-generated constructor stub
	}

	
	

	public void setMessage(String message) {
		this.message = message;
	}


	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.message;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}


	public String getParamName() {
		return paramName;
	}


	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	
	
	
	
	
	
	
	
	

}
