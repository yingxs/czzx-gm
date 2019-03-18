package com.spring.server.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import com.spring.server.common.RegExpValidatorUtils;
import com.spring.server.common.ServerResponse;
import com.spring.server.entity.TbCourseInfo;
import com.spring.server.entity.TbInstituteDepartmentInfo;
import com.spring.server.entity.TbInstituteInfo;
import com.spring.server.entity.TbMajorInfo;
import com.spring.server.exception.ContainSpecialCharactersException;
import com.spring.server.exception.ParameterException;
import com.spring.server.exception.ParameterLengthException;
import com.spring.server.exception.ParameterRequisiteException;
import com.spring.server.service.TbCourseInfoService;
import com.spring.server.service.TbInstituteDepartmentInfoService;
import com.spring.server.service.TbInstituteInfoService;
import com.spring.server.service.TbMajorInfoService;

/**
 * 专业信息表控制器
 *
 * @author wanghao
 * @Date 2019-03-05 14:38:37
 */
@Controller
@RequestMapping("/TbMajorInfo")
public class TbMajorInfoController extends BaseController {

    private String PREFIX = "/TbMajorInfo";

    @Autowired
    private TbMajorInfoService TbMajorInfoService;
    
    @Autowired
    private TbInstituteInfoService tbInstituteInfoService;
    
    @Autowired
    private TbCourseInfoService TbCourseInfoService;
    
    @Autowired
    private TbWordsService tbWordsService;
    
    @Autowired
    private TbInstituteDepartmentInfoService tbInstituteDepartmentInfoService;

    @RequestMapping(value = "/index/{paper}")
	public String view(HttpServletRequest request, @PathVariable String paper,
			 Long TbMajorInfoId) {
		if ("list".equals(paper)) {
			//返回当前会员对此菜单的按钮权限操作
			System.out.println();
			System.out.println(saveMenuId(request));
			System.out.println();
			request.setAttribute("btnList", saveMenuId(request));
			return PREFIX+"/TbMajorInfo_list";
			
		}
		if ("upload".equals(paper)) {
			return PREFIX+"/TbMajorInfo_upload";
		}
		if ("add".equals(paper)) {
			return PREFIX+"/TbMajorInfo_add";
		}
		if ("edit".equals(paper)) {
			TbMajorInfo entity = TbMajorInfoService.findById(TbMajorInfoId);
			request.setAttribute("entity", entity);
			return PREFIX+"/TbMajorInfo_edit";
		}

		return PREFIX +"/"+ paper;
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public Map<String, Object> list(HttpServletRequest request) {
		Map<String, String> mapParams =new HashMap<>();
		try {
			RegExpValidatorUtils.isNumber(request.getParameter("page"), 10, "page", false);
			RegExpValidatorUtils.isNumber(request.getParameter("rows"), 10, "rows", false);
			RegExpValidatorUtils.isNotContainSpecialCharacters(request.getParameter("sort"), 25, "sort", true);
			RegExpValidatorUtils.isNotContainSpecialCharacters(request.getParameter("order"), 25, "order", true);
			String strStartDate = request.getParameter("dateStart");
			RegExpValidatorUtils.isDateOrTime(strStartDate, true);
			String strEndDate = request.getParameter("dateEnd");
			RegExpValidatorUtils.isDateOrTime(strEndDate, true);
			RegExpValidatorUtils.isNotContainSpecialCharacters(request.getParameter("majorName"),20,"专业名称", true);
			RegExpValidatorUtils.isNumber(request.getParameter("departmenId"),5,"系别ID", true);
			
			request.getParameter("dateStart");
			request.getParameter("dateEnd");
			mapParams.put("page", request.getParameter("page").trim());
			mapParams.put("rows", request.getParameter("rows").trim());
			mapParams.put("sort", request.getParameter("sort").trim());
			mapParams.put("order", request.getParameter("order").trim());
			
			
			if (strStartDate != null && !("".equals(strStartDate)) && strEndDate != null && !("".equals(strEndDate))) {
				Date startDate = DateUtil.parse(strStartDate, "yyyy-MM-dd");
				Date endDate = DateUtil.parse(strEndDate, "yyyy-MM-dd");
				long diff = endDate.getTime() - startDate.getTime();//这样得到的差值是微秒级别
			    long year = (diff / (1000 * 60 * 60 * 24))/365;
			    if(diff < 0) {
			    	return ServerResponse.createServerResponseByFAIL("起始时间不能大于结束时间");
			    }
//		     	if (year > 1) {
//		     		return ServerResponse.createServerResponseByFAIL("时间跨度不能超过一年");
//		     	}
			}
			
			
			
			
			
			mapParams.put("AddDate", strStartDate.trim());
			mapParams.put("AddDateEnd", strEndDate.trim());
			
			
			
			
			mapParams.put("majorName", request.getParameter("majorName").trim());
			mapParams.put("departmenId",  request.getParameter("departmenId").trim());
			return ServerResponse.createServerResponse(ServerResponse.SUCCESS, "数据查询成功", TbMajorInfoService.findForJson(mapParams));
		} catch (Exception e) {
			e.printStackTrace();
			return ServerResponse.createServerResponseByFAIL(e.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/add")
	public Map<String, Object> add(HttpServletRequest request,TbMajorInfo TbMajorInfo) {
		return addOrEdit(request,TbMajorInfo,"add");
	}

	@ResponseBody
	@RequestMapping(value = "/edit")
	public Map<String, Object> edit(HttpServletRequest request,TbMajorInfo TbMajorInfo){
		return addOrEdit(request,TbMajorInfo,"edit");
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
	public Map<String, Object> del(HttpServletRequest request,String TbMajorInfoId) {
		try {
			RegExpValidatorUtils.isNumber(TbMajorInfoId+"", 6, "专业ID", false);
			TbMajorInfo tbMajorInfo = TbMajorInfoService.findById(Long.valueOf(TbMajorInfoId));
			List<TbCourseInfo> listTbCourseInfo = TbCourseInfoService.findByMajor(tbMajorInfo);
			if (tbMajorInfo == null) {
				return ServerResponse.createServerResponseByFAIL("您传入的专业ID不存在,请检查");
			} else if (listTbCourseInfo.size() > 0) {
				return ServerResponse.createServerResponseByFAIL("该专业已经分配课程，不能删除！");
			} else {
				tbMajorInfo.setTmiStatus(0);
				TbMajorInfoService.update(tbMajorInfo);
			}
			return ServerResponse.createServerResponseBySUCCESS("删除成功");
		}  catch (ParameterException pe) {
			System.out.println(pe.getMessage());
			return ServerResponse.createServerResponseByFAIL(pe.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbMajorInfo-del()]：错误原因:" + e.getMessage());
			return ServerResponse.createServerResponseByFAIL(e.getMessage());
		}
		
	}
	
	/**
	 * Ajax异步获取所有学院
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_get_Institute_list")
	public Map<String, Object> ajax_get_Institute_list(){
		try {
			return  ServerResponse.createServerResponseBySUCCESS("学院列表查询成功", tbInstituteInfoService.findAll());
		} catch (Exception e){
			e.printStackTrace();
			logger.error("[TbMajorInfo-del()]：错误原因:" + e.getMessage());
			return  ServerResponse.createServerResponseByFAIL("学院列表查询失败:"+e.getMessage());
		}
	}  
	
	/**
	 * Ajax异步获取所有系别
	 * @return map
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_get_Department_list")
	public Map<String, Object> ajax_get_Department_list(){
		try {
			return  ServerResponse.createServerResponseBySUCCESS("系别列表查询成功", tbInstituteDepartmentInfoService.findAll());
		} catch (Exception e){
			e.printStackTrace();
			logger.error("[TbMajorInfo-del()]：错误原因:" + e.getMessage());
			return  ServerResponse.createServerResponseByFAIL("系别列表查询失败:"+e.getMessage());
		}
	}  
	/**
	 * Ajax异步根据学院ID获取其下属系别
	 * @return map
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_get_Department_list_ByInstituteId")
	public Map<String, Object> ajax_get_Department_list_ByInstituteId(String InstituteId){
		Map<String, String> params = new HashMap<>();
		try {
			RegExpValidatorUtils.isNumber(InstituteId, 5, "学院ID", false);
			params.put("InstituteId", InstituteId);
			return  ServerResponse.createServerResponseBySUCCESS("系别列表查询成功", tbInstituteDepartmentInfoService.findForJson(params));
		} catch (Exception e){
			e.printStackTrace();
			logger.error("[TbMajorInfo-del()]：错误原因:" + e.getMessage());
			return  ServerResponse.createServerResponseByFAIL("系别列表查询失败:"+e.getMessage());
		}
	}  
	
	/**
	 * ajax获得所有年限
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_get_major_year")
	public Map<String, Object> ajax_get_major_year(){
		List<TbWords> wordsList = tbWordsService.getMajorYear();
		return ServerResponse.createServerResponseBySUCCESS("数据查询成功", wordsList);
	}

	
	
	
	/**
	 * 由于业务逻辑的相似性，将编辑和新增的数据验证、set参数、逻辑判断等公共操作抽取为私有方法，通过参数type区分是新增还是编辑
	 * 或通过判断参数中有无Id来判断调用者是编辑还是新增，有Id为编辑，反之为新增
	 * @param request
	 * @param TbMajorInfo 专业信息实体类
	 * @param type 类型，新增传入"add",编辑传入"edit"
	 * @return
	 */
	private Map<String, Object> addOrEdit(HttpServletRequest request,TbMajorInfo TbMajorInfo,String type) {
		if (TbMajorInfo == null){
			return ServerResponse.createServerResponseByFAIL("参数非法");
		}
		try {
			//entity对象二次加工程序start
			String strId = request.getParameter("id");
			String strInstituteId = request.getParameter("instituteId");
			String strDepartmentId = request.getParameter("departmentId");
			String strMajorName = request.getParameter("majorName");
			String strMajorYear = request.getParameter("majorYear");
			String strMajorCode = request.getParameter("majorCode");
			// 如果有ID为更新，无ID为新增
			if (strId != null) {
				RegExpValidatorUtils.isNumber(strId+"", 5, "专业ID", false);
				TbMajorInfo.setTmiId(Long.valueOf(strId));
			}
			// 数据验证
			if("edit".equals(type)){
				// 编辑时学院与专业代码可为空
				RegExpValidatorUtils.isNumber(strInstituteId, 5, "学院ID", true);
				RegExpValidatorUtils.isNotContainSpecialCharacters(strMajorCode, 20, "专业代码", true);
			}else{
				// 保存时学院与专业代码不可为空
				RegExpValidatorUtils.isNumber(strInstituteId, 5, "学院ID", false);
				RegExpValidatorUtils.isNotContainSpecialCharacters(strMajorCode, 20, "专业代码", true);
			}
			RegExpValidatorUtils.isNumber(strDepartmentId, 5, "系别ID", false);
			RegExpValidatorUtils.isNotContainSpecialCharacters(strMajorName, 20, "专业名称", false);
			RegExpValidatorUtils.isNumber(strMajorYear, 10,"专业年限", false);
			
			TbMajorInfo.setTmiAddDate(new Timestamp(System.currentTimeMillis()));
			TbMajorInfo.setTmiInstituteId(Long.valueOf(strInstituteId));
			TbMajorInfo.setTmiDepartmentId(Long.valueOf(strDepartmentId));
			TbMajorInfo.setTmiName(strMajorName);
			TbMajorInfo.setTmiMajorYear(Long.valueOf(strMajorYear));
			TbMajorInfo.setTmiCode(strMajorCode);
			
			ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject()
					.getPrincipals().getPrimaryPrincipal();// 获取当前登录用户
			if (loginUser == null) {
				return ServerResponse.createServerResponseByNEED_LOGIN("会话过期，请重新登录");
			}
			TbMajorInfo.setTmiAddPerson(loginUser.getId());
			TbMajorInfo.setTmiStatus(1);
			
			// 查询学院
			TbInstituteInfo tbInstituteInfo = tbInstituteInfoService.findById(Long.valueOf(strInstituteId));
			if (tbInstituteInfo == null) {
				return ServerResponse.createServerResponseByFAIL(101, "您所选择的学院不存在");
			}
			// 查询属系别
			TbInstituteDepartmentInfo tbInstituteDepartmentInfo = tbInstituteDepartmentInfoService.findById(Long.valueOf(strDepartmentId));
			if (tbInstituteDepartmentInfo == null) {
				return ServerResponse.createServerResponseByFAIL(102, "您所选择的系别不存在");
			}
			
			TbMajorInfo tbMajorInfo;
			// 查询专业
			tbMajorInfo = TbMajorInfoService.findByName(strMajorName);
			
			if(strId==null){ // 添加
				if (tbMajorInfo!=null){
					return ServerResponse.createServerResponseByFAIL(103, "您输入的专业名称已经存在，请检查!");
				}
			} else { //更新
				if (tbMajorInfo==null){
					return ServerResponse.createServerResponseByFAIL(103, "您输入的专业名称已经存在，请检查!");
				}
			}
			
			if("add".equals(type)){
				TbMajorInfoService.save(TbMajorInfo);
				return ServerResponse.createServerResponseBySUCCESS("专业添加成功");
			}else if("edit".equals(type)){
				TbMajorInfoService.update(TbMajorInfo);
				return ServerResponse.createServerResponseBySUCCESS("专业更新成功");
			}else{
				return ServerResponse.createServerResponseByFAIL("方法调用异常");
			}
		} catch (ParameterRequisiteException e) {
			e.printStackTrace();
			int code = 101;
			String message = e.getParamName()+"长度过出限制";
			if ("学院ID".equals(e.getParamName())) {
				code = 101;
				message="请选择所属学院";
			} else if ("系别ID".equals(e.getParamName())){
				code = 102;
				message="请选择所属系!";
			} else if ("专业名称".equals(e.getParamName())){
				code = 103;
				message="专业名称不能为空，请检查";
			} else if ("专业年限".equals(e.getParamName())){
				code = 104;
				message="请选择专业年限";
			}  else if ("专业代码".equals(e.getParamName())){
				code = 105;
				message="请输入专业代码";
			}
			logger.error("[TbMajorInfo-add()]：错误原因:" + e.getMessage());
			return ServerResponse.createServerResponseByFAIL(code,message);
		} catch (ParameterLengthException e){
			e.printStackTrace();
			int code = 101;
			String message = e.getParamName()+"长度过出限制";
			if ("专业代码".equals(e.getParamName())) {
				code = 105;
				message="您输入的专业代码长度不能超过20,请检查";
			} 
			return ServerResponse.createServerResponseByFAIL(code,message);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbMajorInfo-add()]：错误原因:" + e.getMessage());
			return ServerResponse.createServerResponseByFAIL("添加失败：" + e.getMessage());
		}
	}
	
}
