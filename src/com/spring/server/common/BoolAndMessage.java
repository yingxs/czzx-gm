package com.spring.server.common;

public class BoolAndMessage{
	private boolean bool;
	private String message;
	public BoolAndMessage(boolean bool, String message) {
		super();
		this.bool = bool;
		this.message = message;
	}
	public BoolAndMessage() {
		super();
		this.bool = true;
	}
	public boolean isBool() {
		return bool;
	}
	public void setBool(boolean bool) {
		this.bool = bool;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "BoolAndMessage [bool=" + bool + ", message=" + message + "]";
	}
	
	
	
	
}