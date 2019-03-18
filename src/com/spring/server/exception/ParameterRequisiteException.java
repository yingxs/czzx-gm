package com.spring.server.exception;

public class ParameterRequisiteException extends ParameterException {

	private String message;
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.message;
		
	}
	
	public ParameterRequisiteException() {
		super();
	}

	public ParameterRequisiteException(String paramName) {
		super();
		this.message = paramName+"不能为空";
		super.setParamName(paramName);
	}

	

	

	
}
