package com.spring.base.utils;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * 全局的静态变量,用于全局变量的存放
 * 
 * @version 2013-03-19 11:08:15
 */
public class GlobalStatic {
	public static final String tempRootpath = System.getProperty("user.dir") + "/temp/";
	public static final int excelPageSize = 1000;
	public static final String suffix = ".html";
	public static final String excelext = ".xls";
	public static final String exportexcel = "exportexcel";// 是否是导出操作的key
	public static final String dataUpdate = "更新";
	public static final String dataSave = "保存";
	public static final String dataDelete = "删除";
	public static final String cacheKey = "springraincache";
	public static final String qxCacheKey = "springrainqxcache";
	public static final String tableExt = "_history_";
	public static final String frameTableAlias = "frameTableAlias";

	// 认证
	// public static final String reloginsession="shiro-reloginsession";
	// 认证
	public static final String authenticationCacheName = "shiro-authenticationCacheName";
	// 授权
	public static final String authorizationCacheName = "shiro-authorizationCacheName";

	/**
	 * 默认验证码参数名称
	 */
	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
	// 默认保存在session中的登录名称
	public static final String DEFAULT_LOGIN_SESSION_NAME = "loginUser";

	/**
	 * 登录次数超出allowLoginNum时，存储在session记录是否展示验证码的key默认名称
	 */
	public static final String DEFAULT_SHOW_CAPTCHA_KEY_ATTRIBUTE = "showCaptcha";

	/**
	 * 默认在session中存储的登录次数名称
	 */
	public static final String DEFAULT_LOGIN_NUM_KEY_ATTRIBUTE = "loginNum";
	// 允许登录次数，当登录次数大于该数值时，会在页面中显示验证码
	public static final Integer allowLoginNum = 1;

	/***
	 * 上传格式限制
	 * @author 1695446827@qq.com
	 */
	public static HashMap<String, String> extMap = new HashMap<String, String>();

	/***
	 * 上传大小
	 * @author 1695446827@qq.com
	 */
	public static Long maxSize = 1000000l;

	/***
	 * @author 1695446827@qq.com
	 * 上传格式
	 */
	static {
		if (!extMap.isEmpty())
			extMap.clear();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
	}

	/***
	 * @author 1695446827@qq.com
	 * 访问路径
	 */
	public static String getUrl(HttpServletRequest request) {
		//http://hjtech.wicp.net:8058/czzx-gm/
		System.out.println("访问路径-》"+request.getContextPath());
		System.out.println("getUrl->"+"http://218.75.33.77/czzx-gm/upload" + "/");
		return "http://218.75.33.77/czzx-gm/upload" + "/";
	}
	
	/***
	 * @author 1695446827@qq.com
	 * @see 
	 *  用于静态服务器访问路径
	 */
	public static String serverPath = "/WebContent/upload/"; 
//	System.getProperty("user.dir")+ 
	//F:\Projects\.metadata\.plugins\org.eclipse.wst.server.core\tmp4\wtpwebapps\
//	public static String serverPath = "D:/Project/project/czzx-gm/upload/"; 

	/***
	 * @author 1695446827@qq.com
	 * 目录路径
	 */
	public static String getDriUrl(PageContext pageContext) {
		return pageContext.getServletContext().getRealPath("/") + "/upload/";
	}

}
