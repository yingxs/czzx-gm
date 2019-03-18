package com.spring.server.exception;

public class MobilePhoneNumberFormatException extends ParameterException {


	private String message;
	
	public MobilePhoneNumberFormatException() {
		super();
		this.message="手机号码格式错误";
		
	}
	
	
	public MobilePhoneNumberFormatException(String paramName,int length) {
		super();
		this.message=paramName+"长度不能超过"+length+"个字符！";
		super.paramName = paramName;
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
