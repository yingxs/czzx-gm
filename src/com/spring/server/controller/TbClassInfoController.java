package com.spring.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.spring.base.shiro.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import com.spring.base.utils.StringUtil;
import com.spring.server.entity.TbClassInfo;
import com.spring.server.service.TbClassInfoService;
import com.spring.base.controller.BaseController;

/**
 * 班级表控制器
 *
 * @author zhouyuan
 * @Date 2019-03-16 23:40:17
 */
@Controller
@RequestMapping("/TbClassInfo")
public class TbClassInfoController extends BaseController {

    private String PREFIX = "/TbClassInfo";

    @Autowired
    private TbClassInfoService TbClassInfoService;

    @RequestMapping(value = "/index/{paper}")
	public String view(HttpServletRequest request, @PathVariable String paper,
			 Long TbClassInfoId) {
		
		if ("list".equals(paper)) {
			//返回当前会员对此菜单的按钮权限操作
			request.setAttribute("btnList", saveMenuId(request));
			return PREFIX+"/TbClassInfo_list";
		}
		if ("add".equals(paper)) {
			return PREFIX+"/TbClassInfo_add";
		}
		if ("edit".equals(paper)) {

			TbClassInfo entity = TbClassInfoService.findById(TbClassInfoId);
			request.setAttribute("entity", entity);
			return PREFIX+"/TbClassInfo_edit";
		}

		return PREFIX +"/"+ paper;
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public Map<String, Object> list(HttpServletRequest request) {
		//params包含所有前端提交的参数
		HashMap<String, String> params =getRequestParams(request);
		return TbClassInfoService.findForJson(params);
	}

	@ResponseBody
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request,TbClassInfo TbClassInfo) {
		if (TbClassInfo == null){
            return "101";
        }
		//entity对象二次加工程序start
		
		//entity对象二次加工程序end
		try {
			ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject()
					.getPrincipals().getPrimaryPrincipal();// 获取当前登录用户
			TbClassInfoService.save(TbClassInfo);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbClassInfo-add()]：错误原因:" + e.getMessage());
			return "110";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,TbClassInfo TbClassInfo){
		if (TbClassInfo == null){
            return "101";
        }
		//entity对象二次加工程序start
		
		//entity对象二次加工程序end
		try {
			TbClassInfoService.update(TbClassInfo);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbClassInfo-edit()]：错误原因:" + e.getMessage());
			return "110";
		}

	}

	/**
	 * 
	 * @Title: del
	 * @Description: 删除
	 * @param @param tcId
	 * @param @return
	 * @return String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/del")
	public String del(HttpServletRequest request,Long TbClassInfoId) {

		if (TbClassInfoId==null) {
			return "101";
		}
		try {
			TbClassInfoService.delete(TbClassInfoId);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbClassInfo-del()]：错误原因:" + e.getMessage());
			return "110";
		}
	}
	
	/*
	 * 根据学院id，系别id，年级id下拉框加载班级
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_getClass_byGradeId_AndInstituteId_AndInstituteDepartmentId")
	public List<Map<String, Object>> ajax_getClass_byGradeId_AndInstituteId_AndInstituteDepartmentId(
			HttpServletRequest request, long tc_institute_id, long tc_institute_department_id, long tc_grade) {
		return TbClassInfoService.ajax_getClass_byGradeId_AndInstituteId_AndInstituteDepartmentId(tc_institute_id,tc_institute_department_id,tc_grade);
	}
}
