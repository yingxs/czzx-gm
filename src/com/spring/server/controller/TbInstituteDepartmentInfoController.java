package com.spring.server.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.base.controller.BaseController;
import com.spring.base.shiro.ShiroUser;
import com.spring.base.utils.StringUtil;
import com.spring.server.entity.TbInstituteInfo;
import com.spring.server.entity.TbMajorInfo;
import com.spring.server.entity.TbInstituteDepartmentInfo;
import com.spring.server.service.TbInstituteDepartmentInfoService;
import com.spring.server.service.TbInstituteInfoService;
import com.spring.server.service.TbMajorInfoService;

/**
 * 系信息表控制器
 *
 * @author hxx
 * @Date 2019-03-05 16:46:54
 */
@Controller
@RequestMapping("/tbinstituteDepartmentInfo")
public class TbInstituteDepartmentInfoController extends BaseController {

	private String PREFIX = "/tbinstituteDepartmentInfo";

	// 学院Service
	@Autowired
	private TbInstituteInfoService tbInstituteInfoService;

	// 系表Service
	@Autowired
	private TbInstituteDepartmentInfoService tbinstituteDepartmentInfoService;

	// 专业表Service
	@Autowired
	private TbMajorInfoService tbMajorInfoService;

	@RequestMapping(value = "/index/{paper}")
	public String view(HttpServletRequest request, @PathVariable String paper, Long tbinstituteDepartmentInfoId) {

		/* 进入到列表页面 */
		if ("list".equals(paper)) {
			// 返回当前会员对此菜单的按钮权限操作
			request.setAttribute("btnList", saveMenuId(request));

			return PREFIX + "/tbinstituteDepartmentInfo_list";
		}
		/* 进入到添加页面 */
		if ("add".equals(paper)) {

			return PREFIX + "/tbinstituteDepartmentInfo_add";
		}
		/* 进入到修改页面 */
		if ("edit".equals(paper)) {

			TbInstituteDepartmentInfo TbinstituteDepartmentInfo_entity = tbinstituteDepartmentInfoService
					.findById(tbinstituteDepartmentInfoId); // 系信息表 -----获取一个对象
			List<TbInstituteInfo> TbInstituteInfo_list = tbInstituteInfoService.findAll(); // 学院信息表
																							// -----------------学院选择

			request.setAttribute("TbinstituteDepartmentInfo", TbinstituteDepartmentInfo_entity);
			request.setAttribute("TbInstituteInfo", TbInstituteInfo_list);
			return PREFIX + "/tbinstituteDepartmentInfo_edit";
		}

		return PREFIX + "/" + paper;
	}

	/* 加载Ajax */
	@ResponseBody
	@RequestMapping(value = "/Ajax_Get_TbinstituteDepartmentInfo_List")
	public Map<String, Object> Ajax_Get_TbinstituteDepartmentInfo_List(HttpServletRequest request) {
		HashMap<String, String> params = getRequestParams(request);
		return tbinstituteDepartmentInfoService.findForJson(params);
	}

	/**
	 * 系别表添加
	 * 
	 * @param request
	 * @param tbinstituteDepartmentInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add")
	public Map<String, Object> add(HttpServletRequest request, TbInstituteDepartmentInfo tbinstituteDepartmentInfo) {
		Map<String, Object> map = new HashMap<String, Object>();

		long integer = tbinstituteDepartmentInfo.getTidiOrder();
		TbInstituteDepartmentInfo findById = tbinstituteDepartmentInfoService.findById(integer);
		Integer tidiOrder = findById.getTidiOrder();

		if (tbinstituteDepartmentInfo == null) {
			map.put("Code", "101");
			map.put("Message", "暂无数据！");
			return map;
		} else if (tbinstituteDepartmentInfo.getTidiInstituteId().equals(null)) {
			map.put("Code", "101");
			map.put("Message", "所属学院不允许为空");
			return map;
		} else if (StringUtils.isEmpty(tbinstituteDepartmentInfo.getTidiName())) {
			map.put("Code", "101");
			map.put("Message", "系名称不允许为空");
			return map;
		} else if (StringUtils.isEmpty(tbinstituteDepartmentInfo.getTidiCode())) {
			map.put("Code", "101");
			map.put("Message", "系编码不允许为空");
			return map;
		} else if (tbinstituteDepartmentInfo.getTidiOrder().equals(null)) {
			map.put("Code", "101");
			map.put("Message", "显示顺序不允许为空");
			return map;

		} else if (tidiOrder == integer) {
			map.put("Code", "101");
			map.put("Message", "排序已经存在！请重新输入");
			return map;
		}

		else if (tbinstituteDepartmentInfo.getTidiIcon().equals(null)) {
			map.put("Code", "101");
			map.put("Message", "没有此图片");
			return map;
		}

		try {
			ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();// 获取当前登录用户
			tbinstituteDepartmentInfo.setTidiAddDate(new Timestamp(new Date().getTime())); // 生成当前时间
			tbinstituteDepartmentInfo.setTidiAddPerson(loginUser.getId()); // 添加当前用户
			tbinstituteDepartmentInfo.setTidiStatus(1); // 添加状态
			tbinstituteDepartmentInfoService.save(tbinstituteDepartmentInfo);
			map.put("Code", "100");
			map.put("Message", "success");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbinstituteDepartmentInfo-add()]：错误原因:" + e.getMessage());
			map.put("Code", "-101");
			map.put("Message", "数据异常！请联系管理员");
			return map;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/edit")
	public Map<String, Object> edit(HttpServletRequest request, TbInstituteDepartmentInfo tbinstituteDepartmentInfo) {
		Map<String, Object> map = new HashMap<String, Object>();

		System.out.println("###########TBINSTITUTEDEPARTMENTINFO###############" + tbinstituteDepartmentInfo);
		if (tbinstituteDepartmentInfo == null) {
			map.put("Code", "101");
			map.put("Message", "暂无数据！");
			return map;
		} else if (StringUtils.isEmpty(tbinstituteDepartmentInfo.getTidiName())) {
			map.put("Code", "101");
			map.put("Message", "系名称不允许为空");
			return map;
		} else if (StringUtils.isEmpty(tbinstituteDepartmentInfo.getTidiCode())) {
			map.put("Code", "101");
			map.put("Message", "系编码不允许为空");
			return map;
		} else if (tbinstituteDepartmentInfo.getTidiOrder().equals(null)) {
			map.put("Code", "101");
			map.put("Message", "显示顺序不允许为空");
			return map;
		} else if (tbinstituteDepartmentInfo.getTidiIcon().equals(null)) {
			map.put("Code", "101");
			map.put("Message", "没有此图片");
			return map;
		}

		try {
			tbinstituteDepartmentInfoService.update(tbinstituteDepartmentInfo);
			map.put("Code", "100");
			map.put("Message", "Success");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbinstituteDepartmentInfo-edit()]：错误原因:" + e.getMessage());
			map.put("Code", "-101");
			map.put("Message", "数据库异常！请联系开发人员");
			return map;
		}

	}

	/**
	 * 
	 * @Title: del
	 * @Description: 删除
	 * @param @param
	 *            tcId
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/del")
	public Map<String, Object> del(HttpServletRequest request, String tbinstituteDepartmentInfoId) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			if (StringUtil.isEmptyNull(tbinstituteDepartmentInfoId)) {
				map.put("Code", "101");
				map.put("Message", "系id不可为空！");
				return map;
			}
			TbInstituteDepartmentInfo tbinstituteDepartmentInfo = tbinstituteDepartmentInfoService
					.findById(Long.valueOf(tbinstituteDepartmentInfoId));
			if (tbinstituteDepartmentInfo == null || tbinstituteDepartmentInfo.getTidiStatus() == 0) {
				map.put("Code", "100");
				map.put("Message", "删除成功！");
				return map;
			}
			// 专业
			TbMajorInfo tbMajorInfo = new TbMajorInfo();
			tbMajorInfo.setTmiStatus(1);
			tbMajorInfo.setTmiDepartmentId(tbinstituteDepartmentInfo.getTidiId());
			tbMajorInfo = tbMajorInfoService.seachOne(tbMajorInfo);
			if (tbMajorInfo != null) {
				map.put("Code", "102");
				map.put("Message", "该系下有相关联专业，不可删除！");
				return map;
			}
			tbinstituteDepartmentInfo.setTidiStatus(0);
			tbinstituteDepartmentInfoService.update(tbinstituteDepartmentInfo);
			map.put("Code", "100");
			map.put("Message", "删除成功！");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbinstituteDepartmentInfo-del()]：错误原因:" + e.getMessage());
			map.put("Code", "-101");
			map.put("Message", "数据库异常！请联系开发人员");
			return map;
		}

	}

	/**
	 * 系名
	 * 
	 * @param request
	 * @param tbinstituteDepartmentInfoId
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "/Ajax_Get_TbinstituteInfo_Name")
	public List<TbInstituteInfo> Ajax_Get_TbinstituteInfo_Name(HttpServletRequest request) {
		try {
			List<TbInstituteInfo> list = tbInstituteInfoService.findAll();
			if (list.isEmpty() && list.size() < 0) {
				return null;
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbinstituteDepartmentInfo-del()]：错误原因:" + e.getMessage());
			return null;
		}
	}

	
	/*
	 * 根据学院id加载系的下拉框
	 */
	@ResponseBody
	@RequestMapping(value = "/getInstituteDepartmentInfo_bytiiId")
	public List<Map<String, Object>> getInstituteDepartmentInfo_bytiiId(HttpServletRequest request,
			long tidi_institute_id) {
		return tbinstituteDepartmentInfoService.getInstituteDepartmentInfo_bytiiId(tidi_institute_id);
	}
	
}
