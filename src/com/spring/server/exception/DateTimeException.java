package com.spring.server.exception;

public class DateTimeException extends ParameterException {
	
	private String message;
	
	public DateTimeException() {
		super();
		this.message="日期时间格式错误";
		
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
