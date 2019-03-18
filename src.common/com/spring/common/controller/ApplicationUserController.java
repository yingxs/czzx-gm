package com.spring.common.controller;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.base.controller.BaseController;
import com.spring.common.entity.ApplicatonInfo;
import com.spring.common.service.ApplicatonInfoService;

/**
 * ht
 * @author bohm
 *
 */

@Controller
@RequestMapping(value = "/application")
public class ApplicationUserController extends BaseController{

	@Resource
	ApplicatonInfoService applicatonInfoService;
	
	@RequestMapping(value = "/index/{paper}")
	public String view(HttpServletRequest request,@PathVariable String paper,
			@RequestParam(value = "taId",required = false)String taId){
		if("list".equals(paper)){
			System.out.println("进入资源站列表=====>");
			
			return "application/application_list";
		}
		if("add".equals(paper)){
			System.out.println("进入添加界面====>");
			
		}
		
		return "application/"+paper;
	}
	
	@ResponseBody
	@RequestMapping(value = "/list")
	public HashMap<String,Object> list(HttpServletRequest request){
		System.out.println("application/list");
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("ttName", request.getParameter("ttName"));
		params.put("page", request.getParameter("page"));
		params.put("pageSize", request.getParameter("rows"));
		params.put("AddDate", request.getParameter("AddDate"));
		params.put("AddDateEnd", request.getParameter("AddDateEnd"));
		params.put("sort", request.getParameter("sort"));
		params.put("order", request.getParameter("order"));
		return applicatonInfoService.findForJson(params);
	}
	
	@ResponseBody
	@RequestMapping(value  = "/del")
	public String delApplication(HttpServletRequest request,String taiId){
		//参数验证
		if(taiId==null){
			return "101";
		}
		ApplicatonInfo applicatonInfo = applicatonInfoService.findById(Long.valueOf(taiId));
		
		applicatonInfo.setTaiStatus(0);//删除
		try {
			applicatonInfoService.update(applicatonInfo);
			return "100";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "110";
		}
	}
	
	/**
	 * 添加资源
	 * @param request
	 * @param applicatonInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add")
	public String addApplication(HttpServletRequest request,ApplicatonInfo applicatonInfo){
		
		try {
			applicatonInfoService.save(applicatonInfo);
			
			return "100";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "110";
		}
	}
}
