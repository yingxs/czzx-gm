package com.spring.server.controller;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.base.utils.GlobalStatic;
import com.spring.common.entity.Userinfo;
import com.spring.server.entity.TbDepartment;
import com.spring.server.entity.TbDepartmentTeacher;
import com.spring.server.service.TbDepartmentManageService;
import com.spring.server.service.TbDepartmentTeacherService;
import com.spring.server.service.TbTeacherInfoService;

@Controller
@RequestMapping(value = "/departmentManage")
public class TbDepartmentManageController {
	@Resource
	TbDepartmentManageService tbDepartmentManageService;
	@Resource
	TbDepartmentTeacherService tbDepartmentTeacherService;
	@Resource
	TbTeacherInfoService tbTeacherService;
	
	@RequestMapping(value = "/index/{paper}")
	public String view(HttpServletRequest request, @PathVariable String paper)
			/*,@RequestParam(value = "tdId", required = false) Long tdId*/ {
		if (StringUtils.isBlank(paper)) {
			return "";
		}
		/**
		 * 跳转
		 * @param request
		 * @param paper
		 * @param id
		 * @return
		 */
        //跳转到部门管理列表页面
		if ("tbDepartmentManage".equals(paper)) {
			
			System.out.println("进入部门管理列表页面");
			
			return "Department/tbDepartmentManage";
		}
		
		//跳转到部门管理新增页面
		if ("tbDepartmentManageAdd".equals(paper)) {
			
			System.out.println("进入部门管理新增页面");
			
			return "Department/tbDepartmentManageAdd";
		}
		
		//跳转到部门管理编辑页面
		if ("tbDepartmentManageUpdate".equals(paper)) {
			String tdId=request.getParameter("tdId");
			TbDepartment tbDepartment=tbDepartmentManageService.findById(Long.valueOf(tdId));
			if(tbDepartment!=null){
				request.setAttribute("tbDepartment", tbDepartment);
			}
			else{
				request.setAttribute("tbDepartment", new TbDepartment());
			}
			System.out.println("进入部门管理编辑页面");
			return "Department/tbDepartmentManageUpdate";
		}

		
	    //跳转到部门管理>成员管理列表页面
		if ("tbDepartmentManageCYGL".equals(paper)) {
			String tdId=request.getParameter("tdId");
			TbDepartment tbDepartment=tbDepartmentManageService.findById(Long.valueOf(tdId));
			System.out.println("进入成员管理"+tbDepartment.getTdName());
			if(tbDepartment!=null){
				request.setAttribute("tbDepartment", tbDepartment);
			}
			else{
				request.setAttribute("tbDepartment", new TbDepartment());
		 	}
			System.out.println("进入部门管理成员管理列表页面");
			
			return "Department/tbDepartmentManageCYGL";
		}
		
		
		//跳转到添加成员页面
		if ("tbDepartmentManageCYGLAdd".equals(paper)) {
			
			System.out.println("进入成员管理添加成员页面");
			
			String tdId=request.getParameter("tdId");
			TbDepartment tbDepartment=tbDepartmentManageService.findById(Long.valueOf(tdId));
			System.out.println("进入成员管理"+tdId);
			if(tbDepartment!=null){
				request.setAttribute("tbDepartment", tbDepartment);
			}else{
				request.setAttribute("tbDepartment", new TbDepartment());
		 	}
			
			return "Department/tbDepartmentManageCYGLAdd";
		}
		
		
		
		return "Department/" + paper;

    }
	
	
	/**
	 * 
	 * 部门管理列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public HashMap<String, Object> list(HttpServletRequest request) {
		/**
		 * 添加分页和查询参数
		 */
	//	System.out.println("进入SPEC list方法");
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("page", request.getParameter("page"));
		params.put("pageSize", request.getParameter("rows"));
		params.put("sort", request.getParameter("sort"));
		params.put("order", request.getParameter("order"));
		params.put("tdId", request.getParameter("tdId"));
		params.put("tdName", request.getParameter("tdName"));//部门名称
		params.put("tdDesp", request.getParameter("tdDesp"));//部门介绍
		params.put("tgLogo", request.getParameter("tgLogo"));//图像
		params.put("AddDate", request.getParameter("AddDate"));//创建时间
		params.put("AddDateEnd", request.getParameter("AddDateEnd"));
		
		try {
			return tbDepartmentManageService.findForJson(params);
		} catch (Exception e) {
		//	log.error("[/tbSpecCategoryAndSpec/list]错误，错误原因："+e.getMessage());
			e.printStackTrace();
			return null;
		}

	}
	
	
	
	/**
	 *  新增部门管理
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request) {
		String tdName=request.getParameter("tdName");//部门名称
		String tgLogo=request.getParameter("tgLogo");//头像
		String tdDesp=request.getParameter("tdDesp");//部门介绍
		String s=request.getParameter("s");//部门权限
		System.out.println("部门名称:"+tdName+"头像:"+tgLogo+"部门介绍:"+tdDesp+"S:"+s);
		
		s=s.substring(0, s.length()-1);
		String arr[]=s.split(",");
		
		
		TbDepartment tbDepartment=new TbDepartment();
		tbDepartment.setTgIfManager(0);//是否是校长信箱 1:是  0:否
		tbDepartment.setTgIfPama(0);//是否是家校留言   1:是  0:否
		tbDepartment.setTgIfInner(0);//是否是心理咨询  1:是  0:否
		
		for(int i=0;i<arr.length;i++){
			if(Integer.valueOf(arr[i])==1){
				tbDepartment.setTgIfManager(1);
			}
			if(Integer.valueOf(arr[i])==2){
				tbDepartment.setTgIfPama(1);			
			}
			if(Integer.valueOf(arr[i])==3){
				tbDepartment.setTgIfInner(1);
			}
		}
		
		
		tbDepartment.setTdName(tdName);//部门名称
		tbDepartment.setTgLogo(tgLogo);//图像
		tbDepartment.setTdDesp(tdDesp);//部门介绍
		tbDepartment.setTdAddtime(new Timestamp(new Date().getTime()));//创建时间
		Userinfo userInfo = (Userinfo) request.getSession().getAttribute
				(GlobalStatic.DEFAULT_LOGIN_SESSION_NAME);//获取登录用户
		tbDepartment.setTdAdduser(userInfo.getId());
		tbDepartment.setTdStatus(1);
		tbDepartmentManageService.save(tbDepartment);
		return "100";
		
	}
	
	
	
	
	/**
	 *  编辑部门管理
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request) {
		String tdId= request.getParameter("tdId");
		String tdName=request.getParameter("tdName");//部门名称
		String tgLogo=request.getParameter("tgLogo");//头像
		String tdDesp=request.getParameter("tdDesp");//部门介绍
		String s=request.getParameter("s");//部门权限
		System.out.println("部门名称:"+tdName+"头像:"+tgLogo+"部门介绍:"+tdDesp+"S:"+s);
		
		s=s.substring(0, s.length()-1);
		String arr[]=s.split(",");
		
		
		TbDepartment tbDepartment=tbDepartmentManageService.findById(Long.valueOf(tdId));
		tbDepartment.setTgIfManager(0);//是否是校长信箱 1:是  0:否
		tbDepartment.setTgIfPama(0);//是否是家校留言   1:是  0:否
		tbDepartment.setTgIfInner(0);//是否是心理咨询  1:是  0:否
		
		for(int i=0;i<arr.length;i++){
			if(Integer.valueOf(arr[i])==1){
				tbDepartment.setTgIfManager(1);
			}
			if(Integer.valueOf(arr[i])==2){
				tbDepartment.setTgIfPama(1);			
			}
			if(Integer.valueOf(arr[i])==3){
				tbDepartment.setTgIfInner(1);
			}
		}
		
		
		
		tbDepartment.setTdName(tdName);//部门名称
		tbDepartment.setTgLogo(tgLogo);//图像
		tbDepartment.setTdDesp(tdDesp);//部门介绍
		tbDepartment.setTdAddtime(new Timestamp(new Date().getTime()));//创建时间
		Userinfo userInfo = (Userinfo) request.getSession().getAttribute
				(GlobalStatic.DEFAULT_LOGIN_SESSION_NAME);//获取登录用户
		tbDepartment.setTdAdduser(userInfo.getId());
		tbDepartment.setTdStatus(1);
		tbDepartmentManageService.update(tbDepartment);
		return "100";
		
	}
	
	
	/**
	 * 删除
	 *  */
	@ResponseBody
	@RequestMapping(value = "/delete")
	@Transactional
	public String delete(HttpServletRequest request) {
		String tdId=request.getParameter("tdId");
		TbDepartment tbDepartment=tbDepartmentManageService.findById(Long.valueOf(tdId));
		tbDepartment.setTdStatus(0);
	    tbDepartmentManageService.update(tbDepartment);
		return "100";
   }
	
	
	
	/**
	 * 运营管理 > 部门管理 > 成员管理   操作中的删除
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteTwo")
	@Transactional
	public String deleteTwo(HttpServletRequest request) {
		String tdtId=request.getParameter("tdtId");
		TbDepartmentTeacher tbDepartmentTeacher=tbDepartmentTeacherService.findById(Long.valueOf(tdtId));
		tbDepartmentTeacher.setTdtStatus(0);
		tbDepartmentTeacherService.update(tbDepartmentTeacher);
		return "100";
   }
	
	
	
	
	/**
	 * 查询部门下部门成员的人数
	 */
	@ResponseBody
	@RequestMapping(value = "/findMemberCount")
	@Transactional
	public String findMemberCount(HttpServletRequest request) {
		String tdId = request.getParameter("tdId");
		List<TbDepartmentTeacher> tdmlist=tbDepartmentTeacherService.findMemberCount(Long.valueOf(tdId));
		if(tdmlist.size()==0){
			return "100";
		}else{
			return "101";
		}
		
   }
	
	
	
	
	
	/**
	 * 成员管理中的>添加成员>选择列表中的添加方法
	 */
	@ResponseBody
	@RequestMapping(value = "/addMore")
	@Transactional
	public String addMore(HttpServletRequest request) {
		String tdId=request.getParameter("tdId");
		String ids=request.getParameter("ids");
		String []id=ids.split(",");
		
		System.out.println("进入添加多个成员方法,ids="+ids);
	    
	    List<TbDepartmentTeacher> tptlist=new ArrayList<>();
	    for (String ttId : id) {
	    	TbDepartmentTeacher tpTeacher=new TbDepartmentTeacher();
	    	
	    	tpTeacher.setTdtDepartmentId(Long.valueOf(tdId));//部门id
	    	tpTeacher.setTdtTeacherId(Long.valueOf(ttId));//教师id
	    	tpTeacher.setTdtAddDate(new Timestamp(new Date().getTime()));//创建时间
	    	tpTeacher.setTdtPosition(73L);//岗位
	    	tpTeacher.setTdtStatus(1);//状态 1:正常 0:删除
	    	tptlist.add(tpTeacher);
		}
        tbDepartmentTeacherService.saveSome(tptlist);
	    return "100";
		
   }
	
	
	
	
	/**
	 * 编辑简历方法
	 */
	 
	@ResponseBody
	@RequestMapping(value = "/updateResume")
	public String updateResume(HttpServletRequest request) {
		String tdtId = request.getParameter("tdtId");
		String tdtTeacherDesp = request.getParameter("tdtTeacherDesp");//教师简介
		TbDepartmentTeacher tbDepartmentTeacher=tbDepartmentTeacherService.findById(Long.valueOf(tdtId));
		tbDepartmentTeacher.setTdtTeacherDesp(tdtTeacherDesp);
		tbDepartmentTeacherService.update(tbDepartmentTeacher);
		return "100";
		
   }
	
	
	
	
	/**
	 * 
	 * 成员管理列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list2")
	public HashMap<String, Object> list2(HttpServletRequest request) {
		/**
		 * 添加分页和查询参数
		 */
		System.out.println("进入成员管理list2方法");
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("page", request.getParameter("page"));
		params.put("pageSize", request.getParameter("rows"));
		params.put("sort", request.getParameter("sort"));
		params.put("order", request.getParameter("order"));
		params.put("ttId", request.getParameter("ttId"));
		params.put("ttPhoto", request.getParameter("ttPhoto"));//图片
		params.put("ttName", request.getParameter("ttName"));//姓名
		params.put("ttNumber", request.getParameter("ttNumber"));//工号
		params.put("ttPhone", request.getParameter("ttPhone"));//手机号
		params.put("tdtPosition", request.getParameter("tdtPosition"));//岗位
		
		String tdId = request.getParameter("tdId");
		System.out.println("tdId="+tdId);
		try {
			return tbDepartmentTeacherService.findForJson1(params,Long.valueOf(tdId));
		} catch (Exception e) {
		//	log.error("[/tbSpecCategoryAndSpec/list]错误，错误原因："+e.getMessage());
			e.printStackTrace();
			return null;
		}

	}
	
	
	
	
	
	/**
	 * 
	 * 成员管理>添加成员
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list3")
	public HashMap<String, Object> list3(HttpServletRequest request) {
		/**
		 * 添加分页和查询参数
		 */
		System.out.println("进入成员管理>添加成员list3方法");
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("page", request.getParameter("page"));
		params.put("pageSize", request.getParameter("rows"));
		params.put("sort", request.getParameter("sort"));
		params.put("order", request.getParameter("order"));
		params.put("ttId", request.getParameter("ttId"));
		params.put("ttPhoto", request.getParameter("ttPhoto"));//图片
		params.put("ttName", request.getParameter("ttName"));//姓名
		params.put("ttNumber", request.getParameter("ttNumber"));//工号
		params.put("ttPhone", request.getParameter("ttPhone"));//手机号
		params.put("ttSex", request.getParameter("ttSex"));//性别		
		String tdId = request.getParameter("tdId");
		System.out.println("tdId="+tdId);
		try {
			return tbDepartmentTeacherService.findForJson2(params,Long.valueOf(tdId));
		} catch (Exception e) {
		//	log.error("[/tbSpecCategoryAndSpec/list]错误，错误原因："+e.getMessage());
			e.printStackTrace();
			return null;
		}

	}
}