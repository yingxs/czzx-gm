package com.spring.common.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.base.controller.BaseController;
import com.spring.base.shiro.ShiroUser;
import com.spring.common.entity.Sysmenu;
import com.spring.common.service.GroupandmenuService;
import com.spring.common.service.UserandmenuService;

/**
 * 
* 项目名称：LinkbabyBackstage   
* 类名称：PermsController   
* 类描述：   权限控制  
* 创建人：李猛   
* 创建时间：2014年11月7日 下午3:10:40   
* @version    
*
 */
@Controller
@RequestMapping(value="/perms")
public class PermsController extends BaseController {

//	@RequestMapping(value="/index/{view}")
//	@Override
//	public String index(@PathVariable String view) {
//		return "system/"+view;
//	}
	
	/*
	 * @RequestMapping(value="/index")
	 * 
	 * @Override public String index(String view) { return "system/perms"; }
	 */
	
	@RequestMapping(value="/index")
	@Override
	public String index(String view,HttpServletRequest request) {
		//返回当前会员对此菜单的按钮权限操作
		request.setAttribute("btnList", saveMenuId(request));
		return "system/perms";
	}
	
	@Resource UserandmenuService userandmenuService;
	@Resource GroupandmenuService groupandmenuService;
	
	@ResponseBody
	@RequestMapping(value="/list")
	public List<Sysmenu> list(HttpServletRequest request) {
		
		String type = request.getParameter("type");
		String userId = request.getParameter("userId");

		
		if (StringUtils.isBlank(userId)) {
			return null;
		}
		
		if(StringUtils.isBlank(type)) type = "0";
		
		if (type.equals("0")) {//0查询用户组权限
			return groupandmenuService.list(userId);
		}else if (type.equals("1")) {//查询用户权限
			return userandmenuService.list(userId);
		}
		
		return null;
	}
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value="/setting", method = RequestMethod.POST) public String
	 * setting(HttpServletRequest request) {
	 * 
	 * String userType = request.getParameter("userType"); String menuIds =
	 * request.getParameter("menuIds"); String btnIds =
	 * request.getParameter("btnIds"); String userId =
	 * request.getParameter("userId");
	 * 
	 * if(StringUtils.isBlank(menuIds)) menuIds = "";
	 * if(StringUtils.isBlank(btnIds)) btnIds = "";
	 * 
	 * if (StringUtils.isBlank(userId) || StringUtils.isBlank(userType)) { return
	 * "2"; } if (userType.equals("1")) { userandmenuService.addPerms(menuIds,
	 * btnIds, userId); }else if (userType.equals("2")) {
	 * groupandmenuService.addPerms(menuIds, btnIds, userId); }
	 * 
	 * return "0"; }
	 */
	@ResponseBody
	@RequestMapping(value="/setting", method = RequestMethod.POST)
	public String setting(HttpServletRequest request) {
		
		String userType = request.getParameter("userType");
		String menuIds = request.getParameter("menuIds");
		String btnIds = request.getParameter("btnIds");
		String userId = request.getParameter("userId");
		System.out.println(userType);
		System.out.println(menuIds);
		System.out.println(btnIds);
		System.out.println(userId);
		if(StringUtils.isBlank(menuIds)) menuIds = "";
		if(StringUtils.isBlank(btnIds)) btnIds = "";
		
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(userType)) {
			return "2";
		}
		if (userType.equals("1")) {
			userandmenuService.addPermsEx(menuIds, btnIds, userId);
		}else if (userType.equals("2")) {
			groupandmenuService.addPermsEx(menuIds, btnIds, userId);
		}
		
		return "0";
	}
	
	@ResponseBody
	@RequestMapping(value="/com_auth", method=RequestMethod.POST)
	public String comAuth(String url, HttpServletRequest request){
		// 获取当前登录用户
				ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject()
						.getPrincipals().getPrimaryPrincipal();
		//根据登录用户URL判断是否有权限
		boolean bool = userandmenuService.findByParams(url, loginUser.getId());
		if(bool){
			return "1";
		} 
		
		return "0";
	}
	
}
