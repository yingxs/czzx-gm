package com.spring.common.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.base.controller.BaseController;
import com.spring.base.shiro.ShiroUser;
import com.spring.base.utils.StringTool;
import com.spring.common.entity.ChinaCity;
import com.spring.common.entity.ChinaCounty;
import com.spring.common.entity.ChinaProvince;
import com.spring.common.entity.Userinfo;
import com.spring.common.service.ChinaCityService;
import com.spring.common.service.ChinaCountyService;
import com.spring.common.service.ChinaProvinceService;
import com.spring.common.service.UserinfoService;

@Controller
@RequestMapping(value="/user")
public class UserinfoController extends BaseController {
	private Log log = LogFactory.getLog(UserinfoController.class);
	@Resource 
	UserinfoService userinfoService;
	@Resource 
	ChinaProvinceService chinaProvinceService;
	@Resource 
	ChinaCityService chinaCityService;
	@Resource 
	ChinaCountyService chinaCountyService;
	
	@RequestMapping(value="/index")
	@Override
	public String index(String view,HttpServletRequest request) {
		//返回当前会员对此菜单的按钮权限操作
		request.setAttribute("btnList", saveMenuId(request));
		return "system/user";
	}
	/**
	 * 
	 * @Title: view
	 * @Description: 页面跳转
	 * @param @param request
	 * @param @param paper
	 * @param @param id
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/paper/{paper}")
	public String view(HttpServletRequest request, @PathVariable String paper,
			@RequestParam(value = "id", required = false) Long id) {
		log.info("操作对象的ID：" + id);
		if (StringUtils.isBlank(paper)) {
			return "";
		}

		if ("add".equals(paper)) {// 跳转到添加页面
			return "system/add_user";
		} else if ("edit".equals(paper)) {// 跳转到编辑页面
			Userinfo userinfo = userinfoService.findUserinfoById(id);// 根据传递过来的id查询消息
			request.setAttribute("userinfo", userinfo);
			return "system/edit_user";
		}

		return "system/" + paper;
	}
	
	@ResponseBody
	@RequestMapping(value="/list")
	public HashMap<String, Object> list(HttpServletRequest request) {
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("page", request.getParameter("page"));
		params.put("pageSize", request.getParameter("rows"));
		params.put("sort", request.getParameter("sort"));
		params.put("order", request.getParameter("order"));
		params.put("group", request.getParameter("group"));
		params.put("groupName", request.getParameter("groupName"));
		params.put("account", request.getParameter("account"));
		
		return userinfoService.findForJson(params);
	}
	
	@ResponseBody
	@RequestMapping(value="/list1")
	public HashMap<String, Object> list1(HttpServletRequest request) {
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("page", request.getParameter("page"));
		params.put("pageSize", request.getParameter("rows"));
		params.put("sort", request.getParameter("sort"));
		params.put("order", request.getParameter("order"));
		params.put("group", request.getParameter("group"));
		
		return userinfoService.findForJson1(params);
	}
	
	
	/**
	 * 
	* @Title: findUserGroup 
	* @Description: 查找用户组 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/findUserGroup", method = RequestMethod.POST)
	public String findUserGroup() {

		StringBuilder json = new StringBuilder();
		List<Userinfo> list = userinfoService.findUserGroup();
		
		/**
		 * 将list对象转换成json格式
		 */
		json.append("[");
		if (list != null && list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				json.append("{");
				Userinfo userinfo = list.get(i);
				json.append("\"groupName\":\"" + userinfo.getGroupName() + "\",");
				json.append("\"groupid\":\"" + userinfo.getGroupId() + "\"");
				if (i == list.size() - 1) {
					json.append("}");
				} else {
					json.append("},");
				}
			}
		}
		json.append("]");
		log.info("分组:" + json.toString());
		return json.toString();
	}
	
	/**
	 * 
	* @Title: add 
	* @Description: 添加用户 
	* @param @param userinfo
	* @param @return
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/add")
	public String add(HttpServletRequest request,Userinfo userinfo) {
		String groupid = request.getParameter("groupid");
		String account = request.getParameter("account");
		String name = request.getParameter("name");
		String psw = request.getParameter("psw");
		String sex = request.getParameter("sex");
		String title = request.getParameter("title");
//		String theme = request.getParameter("theme");
		String email = request.getParameter("email");
		String remark = request.getParameter("remark");
		//检查登录用户是否已存在
		Userinfo searchParams = new Userinfo();
		searchParams.setAccount(userinfo.getAccount());
		Userinfo u = userinfoService.findUserinfo(searchParams);
		if (u != null) {
			return "-2";//登录用户名已存在
		}
		try {
			ShiroUser loginUser = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();//获取当前登录用户
			userinfo.setCreateTime(new Timestamp(new Date().getTime()));
			userinfo.setCreateUserId(loginUser.getId());
			userinfo.setPassword(StringTool.md5(psw).toLowerCase());
			userinfo.setAccount(account);
			userinfo.setGroupId(Integer.valueOf(groupid));
			userinfo.setName(name);
			userinfo.setSex(Short.valueOf(sex));
			userinfo.setTitle(title);
//			userinfo.setTheme(theme);
			userinfo.setEmail(email);
			userinfo.setRemark(remark);
			
			userinfoService.save(userinfo);
			return "0";
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[UserinfoController-add()]：错误原因:"
					+ e.getMessage());
			return "";
		}
	}
	
	/**
	 * 修改用户信息
	* @Title: update 
	* @Description: TODO 
	* @param @param userinfo
	* @param @return
	* @return String
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/update")
	public String update(HttpServletRequest request,Userinfo userinfo) {
		String id = request.getParameter("id");
		String groupid = request.getParameter("groupid");
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String title = request.getParameter("title");
		String theme = request.getParameter("theme");
		String email = request.getParameter("email");
		String remark = request.getParameter("remark");
		
		//根据ID查询需要修改的对象的
		Userinfo oldUserinfo = userinfoService.findUserinfoById(Long.valueOf(id));	
		try {
			userinfo.setId(Long.valueOf(id));
			userinfo.setAccount(oldUserinfo.getAccount());
			userinfo.setName(name);
			userinfo.setPassword(oldUserinfo.getPassword());
			userinfo.setSex(Short.valueOf(sex));
			userinfo.setTitle(title);
			userinfo.setTheme(theme);
			userinfo.setEmail(email);
			userinfo.setGroupId(Integer.valueOf(groupid));
			userinfo.setRemark(remark);
			userinfo.setCreateTime(new Timestamp(new Date().getTime()));
			ShiroUser loginUser = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();//获取当前登录用户
			userinfo.setCreateUserId(loginUser.getId());
			userinfoService.update(userinfo);
			return "0";
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[UserinfoController-update()]：错误原因:"
					+ e.getMessage());
			return "";
		}
		
	}
	/**
	 * 
	* @Title: del 
	* @Description: 删除用户信息 
	* @param @param request
	* @param @param id
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public String delete(HttpServletRequest request){
		String id = request.getParameter("id");
		
		Userinfo userinfo = userinfoService.findUserinfoById(Long.valueOf(id));
		
		try {
			userinfoService.delete(Long.valueOf(id));
			return "0";
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[UserinfoController-delete()]：错误原因:"
					+ e.getMessage());
			return "";
		}
		
	}
	/**
	 * 
	* @Title: modify 
	* @Description: 修改密码
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public String modify(HttpServletRequest request){
		String id = request.getParameter("id");
		String psw1 = request.getParameter("psw1");
		String psw2 = request.getParameter("psw2");
		
		//根据ID查询用户
		Userinfo userinfo = userinfoService.findUserinfoById(Long.valueOf(id));
		
		if(!psw1.equals(psw2)){
			return "-1";//密码不一致
		}
		
		try {
			
			//修改用户管理里面的用户密码
			userinfo.setPassword(StringTool.md5(psw1).toLowerCase());
			userinfoService.update(userinfo);
			
			
			return "0";
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[UserinfoController-modify()]：错误原因:"
					+ e.getMessage());
		}
		return "";
	}

	
	/**
	 * 
	* @Title: getProvince 
	* @Description: 获取所有省 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/getProvince", method = RequestMethod.POST)
	public String getProvince() {
		
		StringBuilder json = new StringBuilder();
		List<ChinaProvince> list = chinaProvinceService.findAllPro();
		/**
		 * 将list对象转换成json格式
		 */
		json.append("[");
		
		if (list != null && list.size() != 0) {
				json.append("{");
				json.append("\"provName\":\"" + "请选择省" + "\",");
				json.append("\"provId\":\"" + "" + "\"");
				json.append("},");
			for (int i = 0; i < list.size(); i++) {
				ChinaProvince province = list.get(i);
				json.append("{");
				json.append("\"provName\":\"" + province.getPname() + "\",");
				json.append("\"provId\":\"" + province.getPid() + "\"");
				if (i == list.size() - 1) {
					json.append("}");
				} else {
					json.append("},");
				}
			}
		}
		json.append("]");
		System.out.println("类别:" + json.toString());
		log.info("类别:" + json.toString());
		return json.toString();
	}
	
	/**
	 * 
	* @Title: getCity 
	* @Description:  根据省id查询市
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/getCity", method = RequestMethod.POST)
	public String getCity(HttpServletRequest request) {
		
		String provId = request.getParameter("provId");
		
		StringBuilder json = new StringBuilder();
		List<ChinaCity> list = chinaCityService.findAllCityByPro(Long.valueOf(provId));
		/**
		 * 将list对象转换成json格式
		 */
		json.append("[");
		
		if (list != null && list.size() != 0) {
			json.append("{");
			json.append("\"cityName\":\"" + "请选择市" + "\",");
			json.append("\"cityId\":\"" + "" + "\"");
			json.append("},");
			for (int i = 0; i < list.size(); i++) {
				ChinaCity city = list.get(i);
				json.append("{");
				json.append("\"cityName\":\"" + city.getCname() + "\",");
				json.append("\"cityId\":\"" + city.getCid() + "\"");
				if (i == list.size() - 1) {
					json.append("}");
				} else {
					json.append("},");
				}
			}
		}
		json.append("]");
		System.out.println("类别:" + json.toString());
		log.info("类别:" + json.toString());
		return json.toString();
	}
	
	/**
	 * 
	* @Title: getRegion 
	* @Description:  根据市id查询区
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/getRegion", method = RequestMethod.POST)
	public String getRegion(HttpServletRequest request) {
		
		String cityId = request.getParameter("cityId");
		
		StringBuilder json = new StringBuilder();
		List<ChinaCounty> list = chinaCountyService.findAllCountyByCity(Long.valueOf(cityId));
		/**
		 * 将list对象转换成json格式
		 */
		json.append("[");
		
		if (list != null && list.size() != 0) {
			json.append("{");
			json.append("\"regionName\":\"" + "请选择县区" + "\",");
			json.append("\"regionId\":\"" + "" + "\"");
			json.append("},");
			for (int i = 0; i < list.size(); i++) {
				ChinaCounty county = list.get(i);
				json.append("{");
				json.append("\"regionName\":\"" + county.getOname() + "\",");
				json.append("\"regionId\":\"" + county.getOid() + "\"");
				if (i == list.size() - 1) {
					json.append("}");
				} else {
					json.append("},");
				}
			}
		}
		json.append("]");
		System.out.println("类别:" + json.toString());
		log.info("类别:" + json.toString());
		return json.toString();
	}
	
	
	
	/**
	 * 跳转修改密码页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/passwordPage")
	public String passwordPage(HttpServletRequest request){
		
		ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject()
				.getPrincipals().getPrimaryPrincipal();
		loginUser.getId();
		loginUser.getName();
		//根据ID查询用户
		Userinfo userinfo = userinfoService.findUserinfoById(loginUser.getId());
		//传回页面名称和帐号，要不要传密码？传密码的话直接在前台判断暂时不传
		request.setAttribute("userinfo", userinfo);

		return "system/userPassword";
	}
	
	/**
	 * 修改密码
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/password",method=RequestMethod.POST)
	public String password(HttpServletRequest request){
		
		ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject()
				.getPrincipals().getPrimaryPrincipal();
		loginUser.getId();
		loginUser.getName();
		
		
		String oldPassword1 = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String repPassword = request.getParameter("repPassword");
		
		String oldPassword = StringTool.md5(oldPassword1).toLowerCase();
		
		//根据ID查询用户
		Userinfo userinfo = userinfoService.findById(loginUser.getId());
		if(!oldPassword.equals(userinfo.getPassword())){
			return "103";	
		}
		
		if(!newPassword.equals(repPassword)){
			return "101";//密码不一致
		}
		
		try {	
			
//			TbFactory tbFactory = new TbFactory();
//			tbFactory.setTfStatus(1);
//			tbFactory.setTfLoginUser(userinfo.getAccount());
//			tbFactory = tbFactoryService.findOne(tbFactory);
//			if(tbFactory!=null){
//				tbFactory.setTfLoginPass(StringTool.md5(newPassword).toLowerCase());
//				tbFactoryService.update(tbFactory);
//			}
			//修改用户管理里面的用户密码
			userinfo.setPassword(StringTool.md5(newPassword).toLowerCase());
			userinfoService.update(userinfo);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[UserinfoController-modify()]：错误原因:"
					+ e.getMessage());
		}
		return "";
	}

}
