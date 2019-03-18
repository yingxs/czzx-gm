package com.spring.server.exception;

public class ParameterLengthException extends ParameterException {

	
	private String message;
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.message;
	}
	
	public ParameterLengthException() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ParameterLengthException(String paramName,int length) {
		super();
		this.message=paramName+"长度不能超过"+length+"个字符！";
		super.paramName = paramName;
		// TODO Auto-generated constructor stub
	}


	
	
}
