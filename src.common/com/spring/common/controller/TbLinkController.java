package com.spring.common.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.base.controller.BaseController;
import com.spring.base.shiro.ShiroUser;
import com.spring.common.entity.TbLink;
import com.spring.common.service.TbLinkService;
/**
 * 2016-09-24 10:22
 * @author ht
 *
 */
@Controller
@RequestMapping(value = "/link")
public class TbLinkController extends BaseController{
	
	@Resource
	TbLinkService tbLinkService;

	@RequestMapping(value = "/index/{paper}")
	public String view(HttpServletRequest request,@PathVariable String paper,
			@RequestParam(value = "tlId",required = false)String tlId){
		if("list".equals(paper)){
			System.out.println("进入友情链接列表====》");
			return "link/link_list";
		}
		if("add".equals(paper)){
			System.out.println("进入添加友情链接界面=====>");
			return "link/link_add";
		}
		if("edit".equals(paper)){
			System.out.println("进入修改界面======>");
			TbLink tbLink = tbLinkService.findById(Long.valueOf(tlId));
			if (tbLink != null) {
				request.setAttribute("tbLink", tbLink);
			} else {
				request.setAttribute("tbLink", new HashMap<String, Object>());
			}
			
			return "link/link_edit";
		}
		
		return "link/"+paper;
	}
	
	@ResponseBody
	@RequestMapping(value = "/list")
	public HashMap<String,Object> list(HttpServletRequest request){
		System.out.println("link/list");
		HashMap<String, String> params = new HashMap<String,String>();
		params.put("tlName",request.getParameter("tlName"));
		params.put("tlCategory",request.getParameter("tlCategory"));
		params.put("page", request.getParameter("page"));
		params.put("pageSize", request.getParameter("rows"));
		params.put("AddDate", request.getParameter("AddDate"));
		params.put("AddDateEnd", request.getParameter("AddDateEnd"));
		params.put("sort", request.getParameter("sort"));
		params.put("order", request.getParameter("order"));
		return tbLinkService.findForJson(params);
	}
	
	@ResponseBody
	@RequestMapping(value = "/add")
	public String addLink(HttpServletRequest request,TbLink tbLink){
		System.out.println("link/add");
		ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject()
				.getPrincipals().getPrimaryPrincipal();// 获取当前登录用户
		String addUser = String.valueOf(loginUser.getId());
		
		TbLink link = new TbLink();
		link.setTlAddTime(new Timestamp(new Date().getTime()));
		link.setTlAddUser(Integer.valueOf(addUser));
		link.setTlCategory(tbLink.getTlCategory());
		link.setTlIndex(tbLink.getTlIndex());
		link.setTlName(tbLink.getTlName());
		link.setTlPicture(tbLink.getTlPicture());
		link.setTlUrl(tbLink.getTlUrl());
		link.setTlStatus(1);//正常
		

		try {
			tbLinkService.save(link);
			return "100";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "110";
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/del")
	public String delLink(HttpServletRequest request){
		String tlId = request.getParameter("tlId");
		TbLink tbLink =  tbLinkService.findById(Long.valueOf(tlId));
		if(tbLink==null){
			return "101";
		}
		if (tbLink.getTlStatus() == 1) {
			tbLink.setTlStatus(0);// 删除
		}
		
		try {
			tbLinkService.update(tbLink);
			return "100";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "110";
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/edit")
	public String editLink(HttpServletRequest request,TbLink tbLink){
		//参数验证
		String tlId = request.getParameter("tlId");
		System.err.println("tlId:"+tlId);
		
		if(tbLink.getTlId()==null||tlId==null){
			return "101";
		}
		
		TbLink link = tbLinkService.findById(Long.valueOf(tlId));
		link.setTlCategory(tbLink.getTlCategory());
		link.setTlIndex(tbLink.getTlIndex());
		link.setTlName(tbLink.getTlName());
		link.setTlUrl(tbLink.getTlUrl());
		link.setTlPicture(tbLink.getTlPicture());
		
		try {
			tbLinkService.update(link);
			return "100";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "101";
		}
	}
	
}
