package com.spring.base.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.spring.base.utils.GlobalStatic;
import com.spring.base.utils.RequestUtil;

public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		
		String basePath = arg0.getScheme() + "://" + arg0.getServerName() + ":" + arg0.getServerPort()
				+ arg0.getContextPath() + "/";
		String requestPath = RequestUtil.getRequestPath(arg0);// 用户访问的资源地址
//		System.out.println("[INFO]"+requestPath);
		if (requestPath.startsWith("/login") || requestPath.startsWith("/api")) {
			return true;
		}
		
		Object obj = arg0.getSession().getAttribute(GlobalStatic.DEFAULT_LOGIN_SESSION_NAME);
		String isAjax = arg0.getHeader("X-Requested-With");
		if (obj == null) {
			if (isAjax!=null && isAjax.equals("XMLHttpRequest")) {
				arg1.getWriter().write("-1");
			}else {
				arg1.sendRedirect(basePath+"login");				
			}
			return false;
		}else {
			return true;
		}
	}

}
