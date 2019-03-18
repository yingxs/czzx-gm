package com.spring.common.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.base.controller.BaseController;
import com.spring.common.entity.TbClass;
import com.spring.common.service.TbClassService;
@Controller
@RequestMapping(value = "/class")
public class TbClassController extends BaseController{
	
	@Resource
	TbClassService tbClassService;	
	
	@ResponseBody
	@RequestMapping(value = "/getClassName")
	public List<TbClass> getClassName(HttpServletRequest request) {
		// String strCode = request.getParameter("code");
		return tbClassService.getClassName();
	}
	
}
