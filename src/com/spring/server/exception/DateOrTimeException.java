package com.spring.server.exception;

public class DateOrTimeException extends ParameterException {
	
	private String message;
	
	public DateOrTimeException() {
		super();
		this.message="日期或时间格式错误";
		
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
