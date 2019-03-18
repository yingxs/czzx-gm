package com.spring.server.exception;


public class ChineseException extends ParameterException {

	private String message ;
	
	
	public ChineseException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChineseException(String paramName) {
		this.message = paramName+"必须是中文";
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