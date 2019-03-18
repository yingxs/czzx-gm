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
import com.spring.common.service.TbStudentService;

@Controller
@RequestMapping(value="/graduated")
public class GraduatedController extends BaseController{

	@Resource
	TbStudentService tbStudentService;
	
	@RequestMapping(value="/index/{paper}")
	public String view(HttpServletRequest request,@PathVariable String paper,
			@RequestParam(value = "tsId",required = false)String tsId){
		if("list".equals(paper)){
			System.out.println("进入毕业生管理界面=====>");
			return "graduated/graduated_list";
		}
		
		return "graduated/"+paper;
	}
	
	@ResponseBody
	@RequestMapping(value = "/list")
	public HashMap<String,Object> list(HttpServletRequest request){
		System.out.println("graduated/list");
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("tsName", request.getParameter("tsName"));
		params.put("page", request.getParameter("page"));
		params.put("pageSize", request.getParameter("rows"));
		params.put("AddDate", request.getParameter("AddDate"));
		params.put("AddDateEnd", request.getParameter("AddDateEnd"));
		params.put("sort", request.getParameter("sort"));
		params.put("order", request.getParameter("order"));
		return tbStudentService.findForJson(params);
	}
}
