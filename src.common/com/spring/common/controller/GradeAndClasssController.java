package com.spring.common.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.base.controller.BaseController;
import com.spring.base.utils.GlobalStatic;
import com.spring.base.utils.JsonUtils;
import com.spring.base.utils.StringUtil;
import com.spring.common.entity.MenuTree;
import com.spring.common.entity.TbClass;
import com.spring.common.entity.TbClassStudentRelationship;
import com.spring.common.entity.TbGrade;
import com.spring.common.entity.Teacher;
import com.spring.common.entity.Userinfo;
import com.spring.common.service.TbClassService;
import com.spring.common.service.TbClassStudentRelationshipService;
import com.spring.common.service.TbGradeService;
import com.spring.common.service.TeacherService;

@Controller
@RequestMapping(value = "/admin/gac")
public class GradeAndClasssController extends BaseController {

	@Resource
	TbClassService tbClassService;
	@Resource
	TbGradeService tbGradeService;
	@Resource
	TeacherService teacherService;
	@Resource
	TbClassStudentRelationshipService tbClassStudentRelationshipService;

	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, Model model) {
		return "classs/index";
	}
	
	@RequestMapping(value = "/studentList")
	public String studentList(HttpServletRequest request, Model model) {
		String tcId = request.getParameter("tcId");
		TbClass tbClass = tbClassService.findById(Integer.valueOf(tcId));
		TbClassStudentRelationship relationship = new TbClassStudentRelationship();
		relationship.setTcsrClassId(Long.valueOf(tcId));
		relationship = tbClassStudentRelationshipService.findOne(relationship);
		
		long studentNumber = tbClassService.getStudentNumber(tcId);
		
		request.setAttribute("tcId", tcId);
		request.setAttribute("className", tbClass.getName());
		if(relationship != null){
			request.setAttribute("teacherName", relationship.getTscrTeacherName());
		}else{
			request.setAttribute("teacherName", "");
		}
		request.setAttribute("studentNumber", studentNumber);
		
		return "classs/detail";
	}

	/**
	 * 班级列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/classList")
	public HashMap<String, Object> list(HttpServletRequest request) {

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("page", request.getParameter("page"));
		params.put("pageSize", request.getParameter("rows"));
		params.put("sort", request.getParameter("sort"));
		params.put("order", request.getParameter("order"));
		params.put("tbGrade", request.getParameter("tbGrade"));
		try {
			return tbClassService.findForClassJson(params);
		} catch (Exception e) {
			logger.error("[/admin/gac/classList]错误，错误原因："+e.getMessage());
			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * 学生列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/onStudentList")
	public HashMap<String, Object> studentList(HttpServletRequest request) {
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("page", request.getParameter("page"));
		params.put("pageSize", request.getParameter("rows"));
		params.put("sort", request.getParameter("sort"));
		params.put("tcId", request.getParameter("tcId"));
		try {
			return tbClassService.findForStudentJson(params);
		} catch (Exception e) {
			logger.error("[/admin/gac/studentList]错误，错误原因："+e.getMessage());
			e.printStackTrace();
			return null;
		}
		
	}
	
	/** 拿到年级名称 **/
	@ResponseBody
	@RequestMapping(value = "/getGradeName")
	public String getGradeNameById(HttpServletRequest request){
		String tgId = request.getParameter("tgId");
		if(StringUtils.isNotBlank(tgId)){
			return tbGradeService.findById(Integer.parseInt(tgId)).getName();
		}
		return null;
	}

	/** 根据Id删除 **/
	@ResponseBody
	@RequestMapping(value = "/del")
	public String del(HttpServletRequest request) {
		String tcId = request.getParameter("tcId");
		
		try {
			TbClass tbClass = tbClassService.findById(Integer.valueOf(tcId));
			if(tbClass == null){
				return "102";
			}
			long studentNumber = tbClassService.getStudentNumber(tcId);
			if(studentNumber > 0){
				return "103";
			}
			tbClass.setStatus(0);
			tbClassService.update(tbClass);
			
			return "100";
		} catch (Exception e) {
			logger.error("[/admin/gac/del]错误，错误原因："+e.getMessage());
			e.printStackTrace();
			return "101";
		}
	}

	/** 修改 **/
	@ResponseBody
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request) {
		String tcId = request.getParameter("tcId");//班级ID
		String tbGrade = request.getParameter("tbGrade");//年级ID
		String tbClassName = request.getParameter("tbClassName");//班级名字
		String tbTeacher = request.getParameter("tbTeacher");//老师ID
		List<TbClassStudentRelationship> tbClassStudentRelationshipList = tbClassStudentRelationshipService.findByClassId(tcId);
		if(tbClassStudentRelationshipList.size() == 0){
			TbClassStudentRelationship relationship = new TbClassStudentRelationship();
			if(!StringUtil.isEmptyNull(tbTeacher)){
				relationship.setTcsrClassId(Long.valueOf(tcId));
				relationship.setTcsrAddDate(new Timestamp(new Date().getTime()));
				relationship.setTcsrStatus(1);
				relationship.setTscrTeacherId(Long.valueOf(tbTeacher));
				Teacher teacher = teacherService.findById(Integer.valueOf(tbTeacher));
				relationship.setTscrTeacherName(teacher.getName());
			}else{
				relationship.setTcsrClassId(Long.valueOf(tcId));
				relationship.setTcsrAddDate(new Timestamp(new Date().getTime()));
				relationship.setTcsrStatus(1);
			}
			tbClassStudentRelationshipService.save(relationship);
		}
		try {
			TbClass tbClass = tbClassService.findById(Integer.valueOf(tcId));
			if(tbClass == null){
				return "102";
			}
			
			tbClass.setGradeId(Long.valueOf(tbGrade));
			tbClass.setName(tbClassName);
			tbClassService.update(tbClass);
			
			if(!StringUtil.isEmptyNull(tbTeacher)){
				Teacher teacher = teacherService.findById(Integer.valueOf(tbTeacher));
				tbClassStudentRelationshipService.updateByClassId(tcId+"",tbTeacher,teacher.getName());
				
			}
			
			return "100";
		} catch (Exception e) {
			logger.error("[/admin/gac/add]错误，错误原因："+e.getMessage());
			e.printStackTrace();
			return "101";
		}
	}
	
	/** 设置**/
	@ResponseBody
	@RequestMapping(value = "/set")
	public String set(HttpServletRequest request) {
		String tcId = request.getParameter("tcId");
		String tbGrade = request.getParameter("tbGrade");
		String tbClassName = request.getParameter("tbClassName");
		String tbTeacher = request.getParameter("tbTeacher");
		List<TbClassStudentRelationship> tbClassStudentRelationshipList = tbClassStudentRelationshipService.findByClassId(tcId);
		if(tbClassStudentRelationshipList.size() == 0){
			TbClassStudentRelationship relationship = new TbClassStudentRelationship();
			if(!StringUtil.isEmptyNull(tbTeacher)){
				relationship.setTcsrClassId(Long.valueOf(tcId));
				relationship.setTcsrAddDate(new Timestamp(new Date().getTime()));
				relationship.setTcsrStatus(1);
				relationship.setTscrTeacherId(Long.valueOf(tbTeacher));
				Teacher teacher = teacherService.findById(Integer.valueOf(tbTeacher));
				relationship.setTscrTeacherName(teacher.getName());
			}else{
				relationship.setTcsrClassId(Long.valueOf(tcId));
				relationship.setTcsrAddDate(new Timestamp(new Date().getTime()));
				relationship.setTcsrStatus(1);
			}
			tbClassStudentRelationshipService.save(relationship);
		}
		try {
			TbClass tbClass = tbClassService.findById(Integer.valueOf(tcId));
			if(tbClass == null){
				return "102";
			}
			
			tbClass.setGradeId(Long.valueOf(tbGrade));
			tbClass.setName(tbClassName);
			tbClassService.update(tbClass);
			
			if(!StringUtil.isEmptyNull(tbTeacher)){
				Teacher teacher = teacherService.findById(Integer.valueOf(tbTeacher));
					tbClassStudentRelationshipService.updateByClassId(tcId+"",tbTeacher,teacher.getName());
			}
			
			return "100";
		} catch (Exception e) {
			logger.error("[/admin/gac/add]错误，错误原因："+e.getMessage());
			e.printStackTrace();
			return "101";
		}
		
	}


	/** 获取修改信息 **/
	@RequestMapping(value = "/initUpdate")
	@ResponseBody
	public String initUpdate(HttpServletRequest request, Model model, String id) {
		TbClass classs = null;
		if (id != null) {
			try {
				classs = tbClassService.findById(Integer.parseInt(id));
				TbGrade grade = tbGradeService.findById(Integer.parseInt(classs.getGradeId().toString()));
				if(grade!=null)
				classs.setGradeName(grade.getName());
				Teacher tt = teacherService.findById(Integer.parseInt(classs.getTeacherId().toString()));
				if(tt!=null)
					classs.setTeacherName(tt.getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return JsonUtils.writeValueAsString(classs);
	}

	/** 保存新增 **/
	@ResponseBody
	@RequestMapping(value = "/add")
	public String save(HttpServletRequest request) {
		Userinfo userInfo = (Userinfo) request.getSession().getAttribute(GlobalStatic.DEFAULT_LOGIN_SESSION_NAME);
		String tbGrades = request.getParameter("tbGrades");
		String tbClassName = request.getParameter("tbClassName");
		String tbTeacher = request.getParameter("tbTeacher");
		
		try {
			
			tbClassService.saveClassAndTeacher(tbGrades,tbClassName,tbTeacher,userInfo);
			
			return "100";
		} catch (Exception e) {
			logger.error("[/admin/gac/add]错误，错误原因："+e.getMessage());
			e.printStackTrace();
			return "101";
		}
	}
	
	/*年级树*/
	@ResponseBody
	@RequestMapping(value = "/gradeTree")
	public List<MenuTree> grageTree(HttpServletRequest request) {
		try {
			return tbGradeService.getAllTree();
		} catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
		
	}

	/** 查询年级 **/
	@ResponseBody
	@RequestMapping("/get_all_grade")
	public List<TbGrade> getTbCalss() {
		return tbGradeService.findGrade();
	}
	
	/** 查询教师 **/
	@ResponseBody
	@RequestMapping("/get_all_teacher")
	public List<Teacher> getTbTeacher() {
		return teacherService.findTeacher();
	}
	
	@ResponseBody
	@RequestMapping("/checks")
	public String checks(String addClassName,String addGrade,@RequestParam(required=false) String id){
		if(StringUtils.isNotBlank(id)){
			TbClass clas =  tbClassService.findById(Integer.parseInt(id));
			if(clas!=null){
				 if(clas.getName().equals(addClassName)){
					 return "0";
				 }
			}
		 }
		  int count =  tbClassService.getTbClassDao().getInt("SELECT  count(*) FROM tb_classs WHERE tc_name = ? AND tc_grade_id = ?", addClassName,addGrade);
		  return String.valueOf(count);
	}
	/**
	 * 新增跳转页面
	 * @param request
	 * @param paper
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/paper/{paper}")
	public String view(HttpServletRequest request, @PathVariable String paper,
			@RequestParam(value = "id", required = false) Long id) {
		if (StringUtils.isBlank(paper)) {
			return "";
		}

		if ("add".equals(paper)) {// 跳转到添加页面
			return "classs/add_classs";
		} else if ("edit".equals(paper)) {// 跳转到编辑页面
			
			return "classs/edit_classs";
		}

		return "classs/" + paper;
	}
}
