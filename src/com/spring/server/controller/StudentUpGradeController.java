package com.spring.server.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.base.controller.BaseController;
import com.spring.common.entity.TbClass;
import com.spring.common.entity.TbClassStudentRelationship;
import com.spring.common.entity.TbGrade;
import com.spring.common.entity.TbStudent;
import com.spring.common.service.TbClassService;
import com.spring.common.service.TbClassStudentRelationshipService;
import com.spring.common.service.TbGradeService;
import com.spring.common.service.TbStudentService;

@Controller
@RequestMapping("/upgrade/students")
public class StudentUpGradeController extends BaseController {

	@Resource
	TbStudentService tbStudentService;
	@Resource
	TbClassStudentRelationshipService classStudentRelationshipService;
	@Resource
	TbClassService tbClassService;
	@Resource
	TbGradeService tbGradeService;

	@RequestMapping(value = "/init_page")
	public String view(HttpServletRequest request) {

		return "student_upgrade/student_upgrade";
	}

	/**
	 * 获取所有的年级
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/all/grade")
	public List<TbGrade> getCity() {
		List<TbGrade> findAll = tbGradeService.findAll();
		for (int i = 0; i < findAll.size(); i++) {
			if (findAll.get(i).getId() == 4) {
				findAll.remove(findAll.get(i));
			}
		}
		return findAll;
	}

	/**
	 * 获取可升的年级 去掉现在的年级和比现在还低的年级
	 * 
	 * @return
	 */
	@ResponseBody

	@RequestMapping("/get_up_grade")
	public List<TbGrade> getUpGrade(HttpServletRequest request) {
		List<TbGrade> tbGrades = new ArrayList<>();
		String gId = request.getParameter("gId");
		List<TbGrade> findAll = tbGradeService.findAll();
		for (int i = 0; i < findAll.size(); i++) {
			if (findAll.get(i).getId() > Integer.parseInt(gId)) {
				tbGrades.add(findAll.get(i));
			}
		}
		return tbGrades;
	}

	/**
	 * 获取某年级的班级
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/get_class")
	public List<TbClass> getClass(HttpServletRequest request) {
		String gId = request.getParameter("gId");
		return tbClassService.findAllClassByGrade(Long.parseLong(gId));
	}

	/**
	 * 查询该班的学生人数
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/get_stu")
	public HashMap<String, Object> getStu(HttpServletRequest request) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("tbGrade", request.getParameter("tbGrade"));
		params.put("tbClass", request.getParameter("tbClass"));
		return tbStudentService.findForStudentJson(params);
	}

	/**
	 * 学生升级
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/up_grade")
	public void upGrade(HttpServletRequest request) {
		String cId = request.getParameter("cId");//班级ID
		String studentIds = request.getParameter("studentIds");//
		String gId = request.getParameter("gId");
		String[] split = studentIds.split(",");
		if(gId != null && !gId.equals("4")){
			for (String s : split) {
					TbStudent student = tbStudentService.findById(Long.parseLong(s));
					student.setTsClassId(Integer.parseInt(cId));
					tbStudentService.update(student);
					TbClassStudentRelationship tbClassStudentRelationship = new TbClassStudentRelationship();
					tbClassStudentRelationship.setTcsrClassId(Long.parseLong(cId));
					tbClassStudentRelationship.setTcsrAddDate(new Timestamp(new Date().getTime()));
					tbClassStudentRelationship.setTcsrStatus(1);
					tbClassStudentRelationship.setTcsrStudentId(student.getTsId());
					classStudentRelationshipService.save(tbClassStudentRelationship);
			}
		}else{
			for (String s : split) {
				TbStudent student = tbStudentService.findById(Long.parseLong(s));
				student.setTsStatus(3);
				student.setTsClassId(Integer.parseInt(cId));
				tbStudentService.update(student);
				TbClassStudentRelationship tbClassStudentRelationship = new TbClassStudentRelationship();
				tbClassStudentRelationship.setTcsrClassId(Long.parseLong(cId));
				tbClassStudentRelationship.setTcsrAddDate(new Timestamp(new Date().getTime()));
				tbClassStudentRelationship.setTcsrStatus(1);
				tbClassStudentRelationship.setTcsrStudentId(student.getTsId());
				classStudentRelationshipService.save(tbClassStudentRelationship);
		}
		}
	}

	/**
	 * 学生管理列表
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public HashMap<String, Object> list(HttpServletRequest request) {
		/**
		 * 添加分页和查询参数
		 */
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("page", request.getParameter("page"));
		params.put("pageSize", request.getParameter("rows"));
		params.put("order", request.getParameter("order"));
		params.put("tbGrade", request.getParameter("tbGrade"));
		params.put("tbClass", request.getParameter("tbClass"));
		try {
			return tbStudentService.findForStudentJson(params);
		} catch (Exception e) {
			logger.error("[brand/list]出错，错误原因：" + e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

}
