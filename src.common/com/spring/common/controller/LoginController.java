package com.spring.common.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.base.controller.BaseController;
import com.spring.base.utils.CaptchaUtils;
import com.spring.base.utils.GlobalStatic;
import com.spring.base.utils.StringTool;
import com.spring.common.entity.Userinfo;
import com.spring.common.service.UserandmenuService;
import com.spring.common.service.UserinfoService;

@Controller
public class LoginController extends BaseController {
	
	@Resource UserinfoService userinfoService;

	@Resource UserandmenuService userandmenuService;

	@RequestMapping(value="/index")
	public String index(Model model, HttpServletRequest request) {
		return "index";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(HttpServletRequest request) {
//		Cookie cokLoginName = CookieTool.getCookieByName(request, "loginName");
//		Cookie cokLoginPwd = CookieTool.getCookieByName(request, "loginPwd");
//		if (cokLoginName != null && cokLoginPwd != null
//				&& cokLoginName.getValue() != null
//				&& cokLoginPwd.getValue() != null) {
//			String loginName = cokLoginName.getValue();
//			String loginPwd = cokLoginPwd.getValue();
//			request.setAttribute("account", loginName);
//			request.setAttribute("password", loginPwd);
//			// 然后跳转到登录页面
//		}
		return "login";
	}
	/**
	 * 
	* @Title: loginAuth 
	* @Description: 登录验证
	* @param @param session
	* @param @param request
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String loginAuth(HttpSession session,HttpServletRequest request,HttpServletResponse response,Model model) {
		
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String submitCode = request.getParameter("code");
		String rember = request.getParameter("rember");

		//Userinfo searchParams = new Userinfo();
		//searchParams.setAccount(account);
		String code = (String) session.getAttribute(GlobalStatic.DEFAULT_CAPTCHA_PARAM);
		if (StringUtils.isNotBlank(code)) {
			code = code.toLowerCase().toString();
		}
		if (StringUtils.isNotBlank(submitCode)) {
			submitCode = submitCode.toLowerCase().toString();
		}
		
		if (StringUtils.isBlank(account)) {
			model.addAttribute("message", "请输入用户名！");
			return "/login";
		}
		if (StringUtils.isBlank(password)) {
			model.addAttribute("message", "请输入密码！");
			return "/login";
		}

		if (StringUtils.isBlank(submitCode) || StringUtils.isBlank(code)
				|| !code.equals(submitCode)) {
			model.addAttribute("message", "验证码错误!");
			return "/login";
		}
		//Userinfo userinfo = userinfoService.findUserinfo(searchParams);
		Userinfo userinfo = userinfoService.findUserByName(account);
		
		if (userinfo == null || !userinfo.getAccount().equals(account)) {
			model.addAttribute("message", "用户名不存在!");
			return "/login";
		}
		if (!userinfo.getPassword().equals(
				StringTool.md5(password).toLowerCase())) {
			model.addAttribute("message", "密码错误!");
			return "/login";
		}
		if (rember != null && rember.equals("on")) {
			int loginMaxAge = 30 * 24 * 60 * 60; // 定义账户密码的生命周期，这里是一个月。单位为秒
//			CookieTool.addCookie(response, "loginName", account, loginMaxAge);
//			CookieTool.addCookie(response, "loginPwd", password, loginMaxAge);
		}
		
		model.addAttribute(userinfo);
		request.setAttribute("account", userinfo.getAccount());
		session.setAttribute(GlobalStatic.DEFAULT_LOGIN_SESSION_NAME,userinfo);
		
		UsernamePasswordToken token = new UsernamePasswordToken(userinfo.getAccount(), userinfo.getPassword());
		Subject user = SecurityUtils.getSubject();
		user.login(token);
		return "redirect:/index";
	}
	
	/**
	 * 生成验证码
	 * 
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/getCaptcha")
	public void getCaptcha(HttpSession session,HttpServletResponse response) throws IOException {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);

		CaptchaUtils tool = new CaptchaUtils();
		StringBuffer code = new StringBuffer();
		BufferedImage image = tool.genRandomCodeImage(code);
		session.removeAttribute(GlobalStatic.DEFAULT_CAPTCHA_PARAM);
		session.setAttribute(GlobalStatic.DEFAULT_CAPTCHA_PARAM, code.toString());
		// 将内存中的图片通过流动形式输出到客户端
		ImageIO.write(image, "JPEG", response.getOutputStream());
		return;
	}
	
	/**
	 * 注销 登录
	 * @return
	 */
	@RequestMapping(value="/logout")
	public String logout(Model model , HttpSession session){
		//User user2 = (User)session.getAttribute(GlobalStatic.DEFAULT_LOGIN_SESSION_NAME);
		session.removeAttribute(GlobalStatic.DEFAULT_LOGIN_SESSION_NAME);
		//SecurityUtils.getSubject().logout();
		return "/login";
	}
	
}
