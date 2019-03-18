package com.spring.server.controller;

import com.spring.server.entity.*;
import com.spring.server.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.spring.base.shiro.ShiroUser;
import org.apache.shiro.SecurityUtils;
import com.spring.base.controller.BaseController;

/**
 * 课程信息表控制器
 * @Date 2019-03-08 11:38:49
 */
@Controller
@RequestMapping("/tbcourseInfo")
public class TbCourseInfoController extends BaseController {
    private String PREFIX = "/tbcourseInfo";
    //课程信息服务
    @Autowired
    private TbCourseInfoService tbcourseInfoService;
	//科目信息服务
	@Autowired
	private TbSubjectsInfoService TbsubjectsInfoService;

	//专业表
	@Autowired
	private TbMajorInfoService tbMajorInfoService;

	//学院表
	@Autowired
	private TbInstituteInfoService tbInstituteInfoService;

	//系表
	@Autowired
	private TbInstituteDepartmentInfoService tbinstituteDepartmentInfoService;

	//专业关联表
	@Autowired
	private TbMajorCourseRelateService tbMajorCourseRelateService;


    /**
     * 页面请求路径
     * @param request
     * @param paper
     * @param tbcourseInfoId
     * @return
     */
    @RequestMapping(value = "/index/{paper}")
	public String view(HttpServletRequest request, @PathVariable String paper,
			 Long tbcourseInfoId) {
        //列表
		if ("list".equals(paper)) {
			request.setAttribute("btnList", saveMenuId(request));
			return PREFIX+"/tbcourseInfo_list";
		}
        //添加
		if ("add".equals(paper)) {
			return PREFIX+"/tbcourseInfo_add";
		}
        //修改
		if ("edit".equals(paper)) {

			TbCourseInfo entity = tbcourseInfoService.findById(tbcourseInfoId);
			request.setAttribute("entity", entity);
			return PREFIX+"/tbcourseInfo_edit";
		}
		return PREFIX +"/"+ paper;
	}
	/**
	 * 课程管理的列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Ajax_Get_TbinstituteDepartmentInfo_List")
	public Map<String, Object> Ajax_Get_TbinstituteDepartmentInfo_List(HttpServletRequest request) {
        //params包含所有前端提交的参数
		HashMap<String, String> params =getRequestParams(request);
        Map<String, Object> forJson = tbcourseInfoService.findForJson(params);
        if(forJson.isEmpty() && forJson.size()<0 ){
            forJson.put("code","101");
            forJson.put("message","数据为空!");
            return forJson;
        }
        forJson.put("code","100");
        forJson.put("message","success");
		return forJson;
	}

	/**
	 * 添加
	 * @param request
	 * @param tbcourseInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_get_tbcourseInfo_add")
	public Map<String, Object> ajax_get_tbcourseInfo_add(HttpServletRequest request,TbCourseInfo tbcourseInfo,String[]  testName) {
System.out.println("tbcourseInfo"+tbcourseInfo+"testName"+testName);
		Map map =new HashMap<String, Object>();
		try {
			if(testName == null || testName.length < 0){
				map.put("code","101");
				map.put("message","所属专业id不可为空！");
				return map;
			}
				//全局参数
			if (tbcourseInfo == null){
				map.put("code","101");
				map.put("message","暂时没有参数");
				return map;
				//所属科目
			}  else if(tbcourseInfo.getTciSubjectsId().equals(null)){
				map.put("code","101");
				map.put("message","所属科目不能为空！");
				return map;
				//课程名称
			}else if(tbcourseInfo.getTciName().isEmpty()){
				map.put("code","101");
				map.put("message","课程名称不能为空！");
				return map;
				//课程代码
			}else if(tbcourseInfo.getTciCode().length()>=20){
				map.put("code","101");
				map.put("message","课程代码长度不允许超过20个！");
				return map;
				//学分
			}else if(tbcourseInfo.getTciScore().toString().length()>=5 ){
				map.put("code","101");
				map.put("message","学分长度不能超过5！");
				return map;

				//课程目标
			}else if(tbcourseInfo.getTciTaget().toString().length()>=50){
				map.put("code","101");
				map.put("message","课程目标长度不能超过50！");
				return map;

				//课程内容
			}else if(tbcourseInfo.getTciContent().toString().length()>=100){
				map.put("code","101");
				map.put("message","课程内容长度不能超过100！");
				return map;

				//课程计划
			}else if(tbcourseInfo.getTciPlan().toString().length()>=50){
				map.put("code","101");
				map.put("message","课程计划长度不能超过50！");
				return map;

				//课程要求
			}else if( tbcourseInfo.getTciContent().toString().length()>=50){
				map.put("code","101");
				map.put("message","课程要求长度不能超过50！");
				return map;
			}
			ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject() .getPrincipals().getPrimaryPrincipal();// 获取当前登录用户
			tbMajorCourseRelateService.saveTbcourseInfo(tbcourseInfo, testName,loginUser.getId());
			//1、
			//2、
			//3、
			//TbcourseInfo tbcoursein = new TbcourseInfo(); //课程表
			//TbMajorInfo majorInfo = new TbMajorInfo();  //专业表

			//tbcourseInfo.setTciMajorsId(loginUser.getId());
			//tbcourseInfo.setTciStatus(1);
			//tbcourseInfoService.save(tbcourseInfo);


			map.put("code","100");
			map.put("message","新增成功");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbcourseInfo-add()]：错误原因:" + e.getMessage());
			map.put("code","110");
			map.put("message","数据异常");
			return map;
		}
	}


	/**
	 * 修改
	 * @param request
	 * @param tbcourseInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_get_tbcourseInfo_edit")
	public Map<String, Object> ajax_get_tbcourseInfo_edit(HttpServletRequest request,TbCourseInfo tbcourseInfo){
		Map map =new HashMap<String, Object>();
		try {
			//全局参数
			if (tbcourseInfo == null){
				map.put("code","101");
				map.put("message","暂时没有参数");
				return map;
				//所属科目
			}  else if(tbcourseInfo.getTciSubjectsId().equals(null)){
				map.put("code","101");
				map.put("message","所属科目不能为空！");
				return map;
				//课程名称
			}else if(tbcourseInfo.getTciName().isEmpty()){
				map.put("code","101");
				map.put("message","课程名称不能为空！");
				return map;
				//课程代码
			}else if(tbcourseInfo.getTciCode().length()>=20){
				map.put("code","101");
				map.put("message","课程代码长度不允许超过20个！");
				return map;
				//学分
			}else if(tbcourseInfo.getTciScore().toString().length()>=5 ){
				map.put("code","101");
				map.put("message","学分长度不能超过5！");
				return map;

				//课程目标
			}else if(tbcourseInfo.getTciTaget().toString().length()>=50){
				map.put("code","101");
				map.put("message","课程目标长度不能超过50！");
				return map;

				//课程内容
			}else if(tbcourseInfo.getTciContent().toString().length()>=100){
				map.put("code","101");
				map.put("message","课程内容长度不能超过100！");
				return map;

				//课程计划
			}else if(tbcourseInfo.getTciPlan().toString().length()>=50){
				map.put("code","101");
				map.put("message","课程计划长度不能超过50！");
				return map;

				//课程要求
			}else if( tbcourseInfo.getTciContent().toString().length()>=50){
				map.put("code","101");
				map.put("message","课程要求长度不能超过50！");
				return map;
			}
			ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject() .getPrincipals().getPrimaryPrincipal();// 获取当前登录用户

			//1、
			//2、
			//3、
			//TbcourseInfo tbcoursein = new TbcourseInfo(); //课程表
			//TbMajorInfo majorInfo = new TbMajorInfo();  //专业表


			tbcourseInfo.setTciStatus(1);
			tbcourseInfoService.update(tbcourseInfo);
			map.put("code","100");
			map.put("message","修改成功");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbcourseInfo-add()]：错误原因:" + e.getMessage());
			map.put("code","-110");
			map.put("message","修改数据异常");
			return map;
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
	@RequestMapping(value = "/ajax_get_tbcourseInfo_del")
	public Map<String, Object> ajax_get_tbcourseInfo_del(HttpServletRequest request,Long tbcourseInfoId) {
		Map map =new HashMap<String, Object>();


		try {
			//判断传入的课程id是否为空，如果为空，则 code=101,msg ='课程id不能为空' .
			if (tbcourseInfoId==null) {
				map.put("code","101");
				map.put("message","课程id不能为空");
				return map;
			}

			//判断传入的课程id是否存在，如果不存在，则code=102,msg ='您要删除的课程id不存在，请检查!'
			TbCourseInfo tbcourseinfo = tbcourseInfoService.findByIds(tbcourseInfoId);

			if(tbcourseinfo.getTciId() != tbcourseInfoId){
				map.put("code","102");
				map.put("message","您要删除的课程id不存在，请检查!");
				return map;
			}



			//验证通过后，做如下数据操作：
			
			Integer integer = tbcourseInfoService.updateByIds(tbcourseInfoId);

			if(integer<0){
				map.put("code","103");
				map.put("message","删除失败");
				return map;
			}

			map.put("code","100");
			map.put("message","删除成功");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbcourseInfo-del()]：错误原因:" + e.getMessage());
			map.put("code","-101");
			map.put("message","数据库异常！");
			return map;
		}
	}



	/**
     * 科目名称  第一级
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Ajax_Get_TbsubjectsInfo_tsiId")
	public List<TbSubjectsInfo> Ajax_Get_TbsubjectsInfo_tsiId(){
		
		System.out.println("###################################");
        List<TbSubjectsInfo> tbsubjectsInfoList = TbsubjectsInfoService.findAll();
		return tbsubjectsInfoList;
	}

	/**
	 * 课程名称 ---------第二级联动
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "/Ajax_Get_TbcourseInfo_TciName")
	public List<TbCourseInfo> ajaxGetTbcourseInfoTciName(Long tbcourseInfoId){
		List<TbCourseInfo> tbcourseInfo=tbcourseInfoService.getFindById(tbcourseInfoId);
		return tbcourseInfo;

	}





	/**
	 * 学院名称 ----->>>>系名称-----》》》专业名称
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "/Ajax_Get_TbcourseInfo_TiiName")
	public List<TbInstituteInfo> Ajax_Get_TbcourseInfo_TiiName(){

		List<TbInstituteInfo> tbInstituteInfo = tbInstituteInfoService.findAll();
		logger.info("学院名称"+tbInstituteInfo);
		return tbInstituteInfo;

	}

	/**
	 * 系名称 ---------
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Ajax_Get_tb_institute_department_Info_tidi_Name")
	public List<Map<String, Object>>  ajax_Get_tb_institute_department_Info_tidi_Name(Long tbcourseInfoId){
		List<Map<String, Object>> tidiName = tbinstituteDepartmentInfoService
				.tbinstituteDepartmentInfoTidiName(tbcourseInfoId);
		return tidiName;

	}


	/**
	 * 专业名称 ---------
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Ajax_Get_Tb_Major_Info_tidi_Name")
	public List<Map<String, Object>>  ajax_Get_Tb_Major_Info_tidi_Name(Long tbcourseInfoId){
		List<Map<String, Object>> maps = tbMajorInfoService.tbMajorInfoName(tbcourseInfoId);
		logger.info("专业名称 ---------"+maps);
		return maps;
	}

	/**
	 * 弹框
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Ajax_Get_TbinstituteDepartmentInfo_Lists")
	public Map<String, Object> Ajax_Get_TbinstituteDepartmentInfo_Lists(HttpServletRequest request,String strName) {
		//params包含所有前端提交的参数
		HashMap<String, String> params =getRequestParams(request);

		if (strName.length()>0){
			System.out.println(strName+"-------------"+strName.substring(0,strName.length()-1));
			params.put("strName",strName.substring(0,strName.length()-1));
		}else {
			params.put("strName","0");
		}
		Map<String, Object> forJson = tbcourseInfoService.findForJsons(params);
		if(forJson.isEmpty() && forJson.size()<0 ){
			forJson.put("code","101");
			forJson.put("message","数据为空!");
			return forJson;
		}
		forJson.put("code","100");
		forJson.put("message","success");
		return forJson;
	}



	/**
	 * 课程名称 --
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Ajax_Get_TbcourseInfo_TciNames")
	public List<TbCourseInfo> ajaxGetTbcourseInfoTciNames(){
		List<TbCourseInfo> all = tbcourseInfoService.findAll();
		return all;
	}




}
