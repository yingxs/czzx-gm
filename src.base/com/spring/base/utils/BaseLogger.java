package com.spring.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *  Log基类,所有的类默认继承此类,可以直接使用 logger 记录日志,例如 logger.error("error");
 */
public class BaseLogger {
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	public String showSql;
	
	public void console(String msg) {
		if (showSql!=null && showSql.equals("true")) {
			System.out.println(msg);
		}
	}

	public String getShowSql() {
		return showSql;
	}

	public void setShowSql(String showSql) {
		this.showSql = showSql;
	}
	
	
}
