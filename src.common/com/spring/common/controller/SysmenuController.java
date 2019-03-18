package com.spring.common.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.base.controller.BaseController;
import com.spring.base.shiro.ShiroUser;
import com.spring.base.utils.GlobalStatic;
import com.spring.common.entity.MenuTree;
import com.spring.common.entity.Sysmenu;
import com.spring.common.entity.Userinfo;
import com.spring.common.service.GroupandmenuService;
import com.spring.common.service.SysmenuService;
import com.spring.common.service.UserandmenuService;

@Controller
@RequestMapping(value="/menu")
public class SysmenuController extends BaseController {

	@Resource SysmenuService sysmenuService;
	@Resource UserandmenuService userandmenuService;
	@Resource GroupandmenuService groupandmenuService;
	
	@RequestMapping(value="/index")
	@Override
	public String index(String view,HttpServletRequest request)  {
		//返回当前会员对此菜单的按钮权限操作
		request.setAttribute("btnList", saveMenuId(request));
		return "system/menu";
	}
	
	@ResponseBody
	@RequestMapping(value="/list")
	public List<Sysmenu> list() {
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("parentId", "-1");
		List<Sysmenu> list = sysmenuService.list(params);
		return list;
	}
	/**
	 * 获取菜单树形列表
	 * @Author ylj
	 * 2014年9月30日下午5:37:54
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/listTree", method=RequestMethod.POST)
	public List<Sysmenu> listTree() {
		//获取最上级
		List<Sysmenu> list = sysmenuService.listByParent(0L);
		if(null != list && list.size() > 0){
			//然后获取子集
			for (Sysmenu menu : list) {
				menu.setChildren(this.tree(Long.valueOf(menu.getId())));
			}
		}
		return list;
	}
	
	/**
	 * 子集
	 * @authhor ylj
	 * 2014年10月3日下午1:55:22
	 * @param parentId
	 * @return
	 */
	protected List<Sysmenu> tree(Long parentId){
		List<Sysmenu> list = sysmenuService.listByParent(parentId);
		if(null != list && list.size() > 0){
			for (Sysmenu menu : list) {
				menu.setChildren(this.tree(Long.valueOf(menu.getId())));
			}
		}
		return list;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/leftmenu")
	public List<MenuTree> lefTrees(HttpServletRequest request) {
		Userinfo userinfo = (Userinfo) request.getSession().getAttribute(GlobalStatic.DEFAULT_LOGIN_SESSION_NAME);
		//ShiroUser userinfo = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		//根据用户权限显示左边菜单
		List<MenuTree> list = sysmenuService.findMenuByUser(userinfo);
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value="add",method=RequestMethod.POST)
	public String add(HttpServletRequest request) {
		
		ShiroUser userinfo = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
//		Userinfo userinfo = (Userinfo) session.getAttribute(GlobalStatic.DEFAULT_LOGIN_SESSION_NAME);
		String name = request.getParameter("name");
		String url = request.getParameter("url");
		Integer parentId = request.getParameter("parentId") == null ? 0:Integer.parseInt(request.getParameter("parentId"));
		Integer sortCode = request.getParameter("sortCode") == null ? 0:Integer.parseInt(request.getParameter("sortCode"));
		Integer type = request.getParameter("type") == null ? 0:Integer.parseInt(request.getParameter("type"));
		
		Sysmenu menu = new Sysmenu();
		menu.setParentId(parentId);
		menu.setName(name);
		menu.setUrl(url);
		menu.setAllowEdit(1);
		menu.setAllowDelete(1);
		menu.setSortCode(sortCode);
		menu.setStatus(1);
		menu.setType(type);
		menu.setAddtime(new Timestamp(new Date().getTime()));
		menu.setAddUserId(userinfo.getId());
		menu.setAddUsername(userinfo.getAccount());
		try{
			sysmenuService.save(menu);
		}catch (Exception e) {
			logger.error("保存菜单时出错", e);
			e.printStackTrace();
			return "0";
		}
		return "1";
	}
	
	@ResponseBody
	@RequestMapping(value="update",method=RequestMethod.POST)
	public String update(HttpServletRequest request) {
		Sysmenu menu = new Sysmenu();
		//获取旧数据
		System.out.println("=========================================");
		ShiroUser userinfo = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String url = request.getParameter("url");
		Integer parentId = request.getParameter("parentId") == null ? 0:Integer.parseInt(request.getParameter("parentId"));
		Integer sortCode = request.getParameter("sortCode") == null ? 0:Integer.parseInt(request.getParameter("sortCode"));
		Integer type = request.getParameter("type") == null ? 0:Integer.parseInt(request.getParameter("type"));
		
		Sysmenu oldmenu = sysmenuService.findById(Integer.parseInt(id));
		
		if(null != oldmenu){
			menu.setId(Integer.valueOf(id));
			menu.setParentId(parentId);
			menu.setName(name);
			menu.setUrl(url);
			menu.setAllowEdit(1);
			menu.setAllowDelete(1);
			menu.setSortCode(sortCode);
			menu.setStatus(1);
			menu.setType(type);
			menu.setAddtime(new Timestamp(new Date().getTime()));
			menu.setAddUserId(userinfo.getId());
			menu.setAddUsername(userinfo.getAccount());
		}
		try {
			sysmenuService.update(menu);
			return "1";
		} catch (Exception e) {
			logger.error("修改菜单时出错", e);
			e.printStackTrace();
			return "0";
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/del")
	public String del(HttpServletRequest request) {
//		Sysmenu menu = sysmenuService.findById(id);
		String id = request.getParameter("id");
		if (id == null) {
			return "2";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentId", id);
		List<Sysmenu> list = sysmenuService.list(params);
		if (list!=null && list.size()>0) {
			return "3";
		}
		try{
			sysmenuService.del(Integer.valueOf(id));
			return "1";
		}catch (Exception e) {
			logger.error("删除菜单时出错", e);
			e.printStackTrace();
			return "0";
		}
		
	}
	
	/**
	 * combotree
	 * @authhor ylj
	 * 2014年10月3日下午3:36:00
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/get_json", method=RequestMethod.POST)
	public String get_json(){
		StringBuffer tree = new StringBuffer("[{\"id\":\"0\",\"text\":\"最上级\",\"children\":[");
		//定级
		List<Sysmenu> toplist = sysmenuService.listByParent(0L);
		if(null != toplist && toplist.size() > 0){
			int i = 0;
			for (Sysmenu top : toplist) {
				i++;
				tree.append("{\"id\":\"" + top.getId() + "\",\"text\":\"" + top.getName() + "\",")
					.append("\"children\":" + this.combotree(Long.valueOf(top.getId())) + "}");
				if(toplist.size() != i){
					tree.append(",");
				}
			}
		}
		tree.append("]}]");
		return tree.toString();
	}
	
	protected String combotree(Long parentId){
		StringBuffer tree = new StringBuffer("[");
		List<Sysmenu> menulist = sysmenuService.listByParent(parentId);
		if(null != menulist && menulist.size() > 0){
			int i = 0;
			for (Sysmenu menu : menulist) {
				i++;
				tree.append("{\"id\":\"" + menu.getId() + "\",\"text\":\"" + menu.getName() + "\",")
					.append("\"children\":" + this.combotree(Long.valueOf(menu.getId())) + "}");
				if(menulist.size() != i){
					tree.append(",");
				}
			}
		}
		tree.append("]");
		return tree.toString();
	}
	
}
