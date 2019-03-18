package com.spring.common.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.base.controller.BaseController;
import com.spring.base.shiro.ShiroUser;
import com.spring.common.entity.MenuTree;
import com.spring.common.entity.Usergroup;
import com.spring.common.entity.Userinfo;
import com.spring.common.service.UsergroupService;
import com.spring.common.service.UserinfoService;

@Controller
@RequestMapping(value="/group")
public class UserGroupController extends BaseController{
	private Log log = LogFactory.getLog(UserGroupController.class);
	@RequestMapping(value="/index")
	@Override
	public String index(String view,HttpServletRequest request) {
		//返回当前会员对此菜单的按钮权限操作
		request.setAttribute("btnList", saveMenuId(request));
		return "system/group";
	}
	
	@Resource UsergroupService usergroupService;
	
	@Resource UserinfoService userinfoService;
	
	@ResponseBody
	@RequestMapping(value="/treelist")
	public List<MenuTree> treeList() {
		System.out.println(usergroupService.findForTree());
		return usergroupService.findForTree();
	}
	
	@ResponseBody
	@RequestMapping(value="/list")
	public List<Usergroup> list() {
		
		return usergroupService.findAll();
	}
	/**
	 * 
	* @Title: add 
	* @Description: 添加用户组
	* @param @param request
	* @param @param userGroup
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="add",method=RequestMethod.POST)
	public String add(HttpServletRequest request, Usergroup userGroup) {
		String name = request.getParameter("name");
		System.out.println(name);
		/**
		 * 判断用户组是否已存在
		 */
		Usergroup check_group = new Usergroup();
		check_group.setName(name);
		Usergroup ug = usergroupService.findUsergroup(check_group);
		if(ug != null){
			return "-2";// 已存在
		}
		userGroup.setAllowEdit(0);
		userGroup.setAllowDelete(0);
		userGroup.setParentId(0);
		userGroup.setSortcode(0);
		userGroup.setStatus(1);
		userGroup.setCreateTime(new Timestamp(new Date().getTime()));
		// 获取当前登录用户
		ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject()
				.getPrincipals().getPrimaryPrincipal();
		userGroup.setCreateUserId(loginUser.getId());
		userGroup.setCreateUserName(loginUser.getName());

		try {
			usergroupService.save(userGroup);
			return "0";
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[UserGroupController-add()]：错误原因:"
					+ e.getMessage());
			return "";
		}
	}
	
	/**
	 * 
	* @Title: update 
	* @Description: 修改用户组 
	* @param @param request
	* @param @param userGroup
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="update",method=RequestMethod.POST)
	public String update(HttpServletRequest request, Usergroup userGroup) {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		//获取旧数据
		Usergroup oldUserGroup = usergroupService.findById(Integer.valueOf(id));
		if(null != oldUserGroup){
			/**
			 * 判断用户组是否已存在
			 */
			Usergroup check_group = new Usergroup();
			check_group.setName(name);
			Usergroup ug = usergroupService.findUsergroup(check_group);
			if(!oldUserGroup.getName().equals(name)){
				if(ug != null){
					return "-2";// 已存在
				}
			}
		}
		try {
			userGroup.setAllowEdit(0);
			userGroup.setAllowDelete(0);
			userGroup.setParentId(0);
			userGroup.setSortcode(0);
			userGroup.setStatus(1);
			userGroup.setCreateTime(new Timestamp(new Date().getTime()));
			// 获取当前登录用户
			ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject()
					.getPrincipals().getPrimaryPrincipal();
			userGroup.setCreateUserId(loginUser.getId());
			userGroup.setCreateUserName(loginUser.getName());
			
			usergroupService.update(userGroup);
			return "0";
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[UserGroupController-update()]：错误原因:"
					+ e.getMessage());
			return "";
		}
	}
	
	/**
	 * 
	* @Title: delete 
	* @Description: 删除用户组
	* @param @param request
	* @param @param id
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(HttpServletRequest request){
		String id = request.getParameter("id");
		Usergroup userGroup = usergroupService.findById(Integer.valueOf(id));
		/**
		 * 判断该用户组下面是否存在子组织
		 */
		Userinfo user = new Userinfo();
		user.setGroupId(Integer.valueOf(id));
		Userinfo u = userinfoService.findUserinfo(user);
		if(u != null){
			return "-1";//存在子组织
		}
		
		try {
			usergroupService.delete(Integer.valueOf(id));
			return "0";
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[UserGroupController-delete()]：错误原因:"
					+ e.getMessage());
			return "";
		}
	}
}
