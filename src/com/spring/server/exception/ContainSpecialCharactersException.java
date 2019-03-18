package com.spring.server.exception;

public class ContainSpecialCharactersException extends ParameterException {

	private String message ;
	
	
	public ContainSpecialCharactersException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ContainSpecialCharactersException(String paramName) {
		this.message = paramName+"不能包含特殊字符";
		super.setParamName(paramName);
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
