package com.spring.server.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class ServerResponse<T> implements Serializable {

	
	public static final int  SUCCESS=100;
	public static final int  FAIL=110;
	public static final int  NEED_LOGIN=120;
	public static final int  INVALID_PARAM=130;
	private static Map<String,Object> mapResponse = new HashMap<>();
	

	public ServerResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static Map<String,Object> createServerResponse(int code, String message) {
		mapResponse.put("code", code);
		mapResponse.put("message", message);
		return mapResponse;
		
	}

	public static  Map<String,Object>  createServerResponse(int code, String message, Map<String,Object> data) {
		mapResponse.put("code", code);
		mapResponse.put("data", data);
		mapResponse.put("message", message);
		return mapResponse;
	}
	public static <T> Map<String,Object>  createServerResponse(int code, String message, T data) {
		mapResponse.put("code", code);
		mapResponse.put("data", data);
		mapResponse.put("message", message);
		return mapResponse;
	}
	public static  Map<String,Object>  createServerResponseBySUCCESS(String message) {
		mapResponse.put("code", SUCCESS);
		mapResponse.put("message", message);
		mapResponse.remove("data");
		return mapResponse;
	}
	public static <T>  Map<String,Object>  createServerResponseBySUCCESS(String message,T data) {
		mapResponse.put("code", SUCCESS);
		mapResponse.put("data", data);
		mapResponse.put("message", message);
		return mapResponse;
	}
	public static  Map<String,Object>  createServerResponseByFAIL(String message) {
		mapResponse.put("code", FAIL);
		mapResponse.put("message", message);
		mapResponse.remove("data");
		return mapResponse;
	}
	public static <T> Map<String,Object>  createServerResponseByFAIL(String message,T data) {
		mapResponse.put("code", FAIL);
		mapResponse.put("message", message);
		mapResponse.put("data",data);
		return mapResponse;
	}
	public static  Map<String,Object>  createServerResponseByFAIL(int Code,String message) {
		mapResponse.put("code", Code);
		mapResponse.put("message", message);
		mapResponse.remove("data");
		return mapResponse;
	}
	public static  Map<String,Object>  createServerResponseByINVALID_PARAM(String message) {
		mapResponse.put("code", INVALID_PARAM);
		mapResponse.put("message", message);
		mapResponse.remove("data");
		return mapResponse;
	}
	public static  Map<String,Object>  createServerResponseByNEED_LOGIN(String message) {
		mapResponse.put("code", NEED_LOGIN);
		mapResponse.put("message", "会话过期，请重新登录");
		mapResponse.remove("data");
		return mapResponse;
	}
	
}
