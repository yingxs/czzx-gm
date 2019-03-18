package com.spring.server.exception;

public class DateException extends ParameterException {
	
	private String message;
	
	public DateException() {
		super();
		this.message="日期格式错误";
		
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
