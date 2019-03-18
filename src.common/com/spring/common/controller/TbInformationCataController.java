package com.spring.common.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.base.controller.BaseController;
import com.spring.base.shiro.ShiroUser;
import com.spring.common.entity.TbInformationCata;
import com.spring.common.service.TbInformationCataService;
import com.spring.common.service.TbInformationService;

@Controller
@RequestMapping(value = "/tbInformationCata")
public class TbInformationCataController extends BaseController {

	@Resource
	TbInformationCataService tbInformationCataService;
	@Resource
	TbInformationService tbInformationService;

	@RequestMapping(value = "/index/{paper}")
	public String view(HttpServletRequest request, @PathVariable String paper,
			@RequestParam(value = "ticId", required = false) Long ticId) {
		if (StringUtils.isBlank(paper)) {
			return "";
		}

		if ("list".equals(paper)) {
			return "baseData/tbInformationCata_list";
		}
		if ("add".equals(paper)) {
			return "baseData/tbInformationCata_add";
		}
		if ("edit".equals(paper)) {
			TbInformationCata cata = tbInformationCataService.findTbInformationCataById(Long.valueOf(ticId));
			
			request.setAttribute("tbInformationCata", cata);
			return "baseData/tbInformationCata_edit";
		}

		return "baseData/" + paper;
	}

	/**
	 * 内容分类
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public HashMap<String, Object> list(HttpServletRequest request) {
		logger.error("进入内容分类");
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("page", request.getParameter("page"));
		params.put("pageSize", request.getParameter("rows"));
		params.put("ticName", request.getParameter("ticName"));// 分类名称
		params.put("tmAddDate", request.getParameter("tmAddDate"));// 时间段=---开始
		params.put("tmAddDateEnd", request.getParameter("tmAddDateEnd"));// 时间段=---结束
		params.put("sort", request.getParameter("sort"));
		params.put("order", request.getParameter("order"));

		return tbInformationCataService.findByParam(params);
	}

	/**
	 * 删除分类 实际为修改
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public String delete(HttpServletRequest request) {
		TbInformationCata tbInformationCata = tbInformationCataService
				.findById(Long.valueOf(request.getParameter("ticId")));
		try {
			List<TbInformationCata> list = tbInformationCataService.findByParentId(tbInformationCata.getTicId());
			if(list.size()>0){
				return "102";
			}
			
			tbInformationCata.setTicStatus(0);// 根据状态删除
		
			tbInformationCataService.update(tbInformationCata);
			return "100";
		} catch (Exception e) {
			logger.error("[TbInformationCataController-delete()]：错误原因:" + e.getMessage());
			e.printStackTrace();
			return "101";
		}

	}

	/**
	 * 添加所有分类
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request,
			TbInformationCata tbInformationCata) {

		List<TbInformationCata> findByName = tbInformationCataService
				.findByName(tbInformationCata.getTicName());// 根据分类名称查询是否存在\
		// 如果size > 0 表示存在 返回105
		if (findByName.size() > 0) {
			return "105";
		}
		
		ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();//当前登录人
		tbInformationCata.setTicAddPreson(loginUser.getId());
		
		if (tbInformationCata.getTicParentId() == null || tbInformationCata.getTicParentId().equals("")){
			tbInformationCata.setTicParentId(Long.valueOf(0));
		}
		
		tbInformationCata.setTicAddDate(new Timestamp(new Date().getTime()));// 添加时间
		tbInformationCata.setTicStatus(1);// 初始状态
		try {
			tbInformationCataService.save(tbInformationCata);
			return "100";
		} catch (Exception e) {
			logger.error("[TbInformationCataController-add()]：错误原因:" + e.getMessage());
			e.printStackTrace();
			return "101";
		}
	}

	/**
	 * 修改分类
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,
			TbInformationCata tbInformationCata) {
		try {
			TbInformationCata fundByTbInformationCata = tbInformationCataService
					.findById(tbInformationCata.getTicId());// 难道需要修改的
	
			if(!fundByTbInformationCata.getTicName().equals(tbInformationCata.getTicName())){
				TbInformationCata informationCata = new TbInformationCata();
				informationCata.setTicName(tbInformationCata.getTicName());
				informationCata.setTicStatus(1);
				TbInformationCata catalog = tbInformationCataService.findOne(informationCata);
				if(null != catalog){
					return "105";
				}
			}
			
			if (tbInformationCata.getTicParentId() == null
					|| tbInformationCata.getTicParentId().equals("")){
				fundByTbInformationCata.setTicParentId(Long.parseLong("0"));
			}else{
				fundByTbInformationCata.setTicParentId(tbInformationCata.getTicParentId());
			}
			fundByTbInformationCata.setTicName(tbInformationCata.getTicName());
			fundByTbInformationCata.setTicOrder(tbInformationCata.getTicOrder());
			fundByTbInformationCata.setTicMean(tbInformationCata.getTicMean());
			
			tbInformationCataService.update(fundByTbInformationCata);
			return "100";
		} catch (Exception e) {
			logger.error("[TbInformationCataController-edit()]：错误原因:" + e.getMessage());
			e.printStackTrace();
			return "101";
		}
	}

	/**
	 * 获取所有分类
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/listAllParent")
	public List<TbInformationCata> listAllParent(HttpServletRequest request) {
		return tbInformationCataService.findByTbInformationCata();
	}

	/**
	 * ajax 返回所有分类
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findChildCata")
	public List<TbInformationCata> findChildCata(HttpServletRequest request) {

		return tbInformationCataService.findChildCata();
	}
}
