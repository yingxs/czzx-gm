package com.spring.server.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.base.controller.BaseController;
import com.spring.base.shiro.ShiroUser;
import com.spring.base.util.DateUtil;
import com.spring.common.entity.TbWords;
import com.spring.common.service.TbWordsService;
import com.spring.server.entity.TbTeacherInfo;
import com.spring.server.service.TbTeacherInfoService;

/**
 * 教师表控制器
 *
 * @author 
 * @param <V>
 * @Date 2019-03-13 16:55:07
 */
@Controller
@RequestMapping("/TbTeacherInfo")
public class TbTeacherInfoController<V> extends BaseController {

    private String PREFIX = "/TbTeacherInfo";

    @Autowired
    private TbTeacherInfoService TbTeacherInfoService;
    
    @Autowired
    private TbWordsService tbwordsservice;

    
    //进入列表
    @RequestMapping(value = "/index/{paper}")     //TbTeacherInfo/index/list
	public String view(HttpServletRequest request, @PathVariable String paper,
			 Long TbTeacherInfoId) {
		
    	//教师管理列表
		if ("list".equals(paper)) {
			//返回当前会员对此菜单的按钮权限操作
			request.setAttribute("btnList", saveMenuId(request));
			return PREFIX+"/TbTeacherInfo_list";
		}
		//添加
		if ("add".equals(paper)) {
			return PREFIX+"/TbTeacherInfo_add";
		}
		//修改
		if ("edit".equals(paper)) {

			TbTeacherInfo entity = TbTeacherInfoService.findById(TbTeacherInfoId);
			request.setAttribute("entity", entity);
			return PREFIX+"/TbTeacherInfo_edit";
		}

		return PREFIX +"/"+ paper;
	}

    
    
    //加载列表
	@ResponseBody
	@RequestMapping(value = "/Ajax_Get_TbTeacherInfo_List")
	public Map<String, Object> ajax_get_tbteacherinfo_list(HttpServletRequest request) {
		//params包含所有前端提交的参数
		HashMap<String, String> params =getRequestParams(request);
		Map<String, Object> TbTeacherInfomap = TbTeacherInfoService.findForJson(params);
		if(TbTeacherInfomap.isEmpty()) {
			TbTeacherInfomap.put("code", "101");
			TbTeacherInfomap.put("message", "返回数据失败");
		}
		TbTeacherInfomap.put("code", "100");
		TbTeacherInfomap.put("message", "success");
		return TbTeacherInfomap;
	}

	
	/**
	 * 添加教师列表
	 * @param request
	 * @param TbTeacherInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_get_tbteacherinfo_add")
	public HashMap<String, Object> ajax_get_tbteacherinfo_add(HttpServletRequest request,TbTeacherInfo TbTeacherInfo,String ttiBirthday_add) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		System.out.println("##########################添加#######################################");
		Date birthdayDate = DateUtil.toDayStartDate(ttiBirthday_add);
		TbTeacherInfo.setTtiBirthday(new Timestamp(birthdayDate.getTime()));
		
		logger.info("TbTeacherInfo"+TbTeacherInfo);
		if (TbTeacherInfo == null){
			map.put("code", "101");
			map.put("message", "查询数据为空");
			return map;
        }
		
		try {
			//ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject() .getPrincipals().getPrimaryPrincipal();// 获取当前登录用户

			
			TbTeacherInfoService.save(TbTeacherInfo);
			map.put("code", "101");
			map.put("message", "查询数据为空");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbTeacherInfo-add()]：错误原因:" + e.getMessage());
			map.put("code", "-101");
			map.put("message", "查询数据为空");
			return map;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,TbTeacherInfo TbTeacherInfo){
		if (TbTeacherInfo == null){
            return "101";
        }
		//entity对象二次加工程序start
		
		//entity对象二次加工程序end
		try {
			TbTeacherInfoService.update(TbTeacherInfo);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbTeacherInfo-edit()]：错误原因:" + e.getMessage());
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
	public String del(HttpServletRequest request,Long TbTeacherInfoId) {

		if (TbTeacherInfoId==null) {
			return "101";
		}
		try {
			TbTeacherInfoService.delete(TbTeacherInfoId);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbTeacherInfo-del()]：错误原因:" + e.getMessage());
			return "110";
		}
	}
	
	
	
	
	
	
	/**
	 * 
	 * @Description: 下拉框民族
	 * @param @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/Ajax_Get_ttiNation_FindAll")
	public List<TbWords> ajax_get_ttination_findall(HttpServletRequest request) {
		
		List<TbWords> findall = tbwordsservice.ajax_get_ttination_findall();
		System.out.println("Ajax_Get_ttiNation_FindAll民族"+findall);
		return findall;

	
	}
	
	
	
	/**
	 * 
	 * @Description: 政治面貌
	 * @param @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/Ajax_Get_tti_Political_FindAll")
	public List<TbWords> ajax_get_tti_political_findall(HttpServletRequest request) {
		
		List<TbWords> findall = tbwordsservice.ajaxGetTtipoliticalFindAll();
		System.out.println("Ajax_Get_ttiNation_FindAll政治面貌"+findall);
		return findall;
		
		
	}
	
	
	
	/**
	 * 
	 * @Description: 学历层次
	 * @param @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/Ajax_Get_tti_ttiInDegree_FindAll")
	public List<TbWords> ajax_get_tti_ttiindegree_findall(HttpServletRequest request) {
		
		List<TbWords> findall = tbwordsservice.ajaxGetttittiInDegreeFindAll();
		System.out.println("Ajax_Get_ttiNation_FindAll学历层次"+findall);
		return findall;
		
		
	}
	/**
	 * 
	 * @Description: 职务职称
	 * @param @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/Ajax_Get_ttiPosition_FindAll")
	public List<TbWords> Ajax_Get_ttiPosition_FindAll(HttpServletRequest request) {
		
		List<TbWords> findall = tbwordsservice.ajaxGetttiPositionFindAll();
		System.out.println("Ajax_Get_ttiNation_FindAll职务职称"+findall);
		return findall;
		
		
	}
	
	
	
	
	
	/**
	 * 
	 * @Description: 教职工类别
	 * @param @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/Ajax_Get_ttiPositionType_FindAll")
	public List<TbWords> ajax_get_ttipositiontype_findall(HttpServletRequest request) {
		
		List<TbWords> findall = tbwordsservice.ajaxGetttiPositionTypeFindAll();
		System.out.println("Ajax_Get_ttiNation_FindAll教职工类别"+findall);
		return findall;
		
		
	}
	
	
	/**
	 * 
	 * @Description: 现任工作
	 * @param @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/Ajax_Get_ttiPost_FindAll")
	public List<TbWords> ajax_get_ttipost_findall(HttpServletRequest request) {
		
		List<TbWords> findall = tbwordsservice.ajaxGetttiPostFindAll();
		System.out.println("Ajax_Get_ttiNation_FindAll现任工作"+findall);
		return findall;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
