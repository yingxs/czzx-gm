package com.spring.server.exception;

public class NotNumbersException extends ParameterException {

	private String message;
	
	public NotNumbersException() {
		super();
		// TODO Auto-generated constructor stub
	}


	public NotNumbersException(String paramName) {
		this.message = paramName + "必须是数字";
		// TODO Auto-generated constructor stub
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
	
	


	
	
}
