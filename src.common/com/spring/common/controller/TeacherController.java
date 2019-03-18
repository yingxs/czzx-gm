package com.spring.common.controller;

import java.net.UnknownHostException;
import java.util.ArrayList;
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
import com.spring.common.basequery.BaseQueryParam;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbAccount;
import com.spring.common.entity.TbClass;
import com.spring.common.entity.TbStudent;
import com.spring.common.entity.Teacher;
import com.spring.common.entity.Userinfo;
import com.spring.common.service.ChinaCityService;
import com.spring.common.service.ChinaCountyService;
import com.spring.common.service.ChinaProvinceService;
import com.spring.common.service.TbAccountService;
import com.spring.common.service.TbClassService;
import com.spring.common.service.TbGradeService;
import com.spring.common.service.TbStudentFamilyService;
import com.spring.common.service.TbStudentService;
import com.spring.common.service.TeacherService;
import com.spring.server.entity.TbDepartment;
import com.spring.server.service.TbDepartmentManageService;
import com.spring.server.service.TbDepartmentTeacherService;

@Controller
@RequestMapping(value = "/admin/teacher")
public class TeacherController extends BaseController {

	@Resource
	TeacherService teacherService;
	@Resource
	ChinaProvinceService chinaProvinceService;
	@Resource
	ChinaCityService chinaCityService;
	@Resource
	ChinaCountyService chinaCountyService;
	@Resource
	TbClassService tbClassService;
	@Resource
	TbStudentService tbStudentService;
	@Resource
	TbStudentFamilyService tbStudentFamilyService;
	@Resource
	TbGradeService tbGradeService;
	@Resource
	TbDepartmentManageService tbDepartmentManageService;
	@Resource
	TbDepartmentTeacherService tbDepartmentTeacherService;
	@Resource
	TbAccountService tbAccountService;

	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, Model model) {
		return "j_s/teacher";
	}

	/**
	 * 教师列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/teacherList")
	public HashMap<String, Object> list(HttpServletRequest request) {

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("page", request.getParameter("page"));
		params.put("pageSize", request.getParameter("rows"));
		params.put("sort", request.getParameter("sort"));
		params.put("order", request.getParameter("order"));
		params.put("ttName", request.getParameter("ttName"));
		params.put("ttSex", request.getParameter("ttSex"));
		params.put("department", request.getParameter("department"));
		try {
			return teacherService.findForTeacherJson(params);
		} catch (Exception e) {
			logger.error("[/admin/teacher/teacherList]错误，错误原因："+e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	//** 根据Id删除 **//*
	@ResponseBody
	@RequestMapping(value = "/del")
	public String del(HttpServletRequest request) {
		String ttId = request.getParameter("ttId");
		
		try {
			Teacher teacher = teacherService.findById(Integer.valueOf(ttId));
			if(teacher == null){
				return "102";
			}
			
			teacher.setStatus(0);
			teacherService.update(teacher);
			
			return "100";
		} catch (Exception e) {
			logger.error("[/admin/teacher/del]错误，错误原因："+e.getMessage());
			e.printStackTrace();
			return "101";
		}
	}
	
	//**重置密码**//*
	@ResponseBody
	@RequestMapping(value = "/updatePwd")
	public String updatePwd(HttpServletRequest request) {
		String ttId = request.getParameter("ttId");
		
		try {
			Teacher teacher = teacherService.findById(Integer.valueOf(ttId));
			if(teacher == null){
				return "102";
			}
			
			teacher.setLoginPass(StringUtil.getMD5Str("123456"));
			teacherService.update(teacher);
			
			return "100";
		} catch (Exception e) {
			logger.error("[/admin/teacher/updatePwd]错误，错误原因："+e.getMessage());
			e.printStackTrace();
			return "101";
		}
	}

	//** 修改 **//*
	@ResponseBody
	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request,Teacher teacher) {
		
		try {
			Teacher newTeacher = teacherService.findById(teacher.getId());
			if(newTeacher == null){
				return "102";
			}
			//对以下三条进行验重
			String strTeacherNum = teacher.getTeacherNum();//教师证号
			String strCardNum = teacher.getCardNum();//身份证号
			String strNumber = teacher.getNumber();//职工编号
			List<Teacher> allTeacher = teacherService.findAll();
			if(!strTeacherNum.equalsIgnoreCase(newTeacher.getTeacherNum())){//如果输入的不等于他自己
				for (Teacher tcnTeacher : allTeacher) {
					if(strTeacherNum.equalsIgnoreCase(tcnTeacher.getTeacherNum())){
						return "105";
					}
				}
			}
			if(!strCardNum.equalsIgnoreCase(newTeacher.getCardNum())){
				for (Teacher tcnTeacher : allTeacher) {
					if(strCardNum.equalsIgnoreCase(tcnTeacher.getCardNum())){
						return "106";
					}
				}
			}
			if(!strNumber.equalsIgnoreCase(newTeacher.getNumber())){
				for (Teacher tcnTeacher : allTeacher) {
					if(strNumber.equalsIgnoreCase(tcnTeacher.getNumber())){
						return "107";
					}
				}
			}
			
			
			
			newTeacher.setName(teacher.getName());
			newTeacher.setSex(teacher.getSex());
			newTeacher.setNumber(teacher.getNumber());
			newTeacher.setBirth(teacher.getBirth());
			newTeacher.setPhone(teacher.getPhone());
			newTeacher.setMail(teacher.getMail());
			newTeacher.setProvinceId(teacher.getProvinceId());
			newTeacher.setCityId(teacher.getCityId());
			newTeacher.setRegionId(teacher.getRegionId());
			newTeacher.setAddress(teacher.getAddress());
			newTeacher.setInDate(teacher.getInDate());
			newTeacher.setMarriage(teacher.getMarriage());
			newTeacher.setPolitical(teacher.getPolitical());
			newTeacher.setTeacherNum(teacher.getTeacherNum());
			newTeacher.setCardNum(teacher.getCardNum());
			newTeacher.setContactPerson(teacher.getContactPerson());
			newTeacher.setContactPhone(teacher.getContactPhone());
			//realPath.append(teacher.getPhoto().substring(7));//图片路径
			newTeacher.setPhoto(teacher.getPhoto());
			teacherService.update(newTeacher);
			
			return "100";
		} catch (Exception e) {
			logger.error("[/admin/teacher/update]错误，错误原因："+e.getMessage());
			e.printStackTrace();
			return "101";
		}
	}

	//** 获取修改信息 **//*
	@RequestMapping(value = "/initUpdate/{id}")
	public String initUpdate(HttpServletRequest request, Model model, @PathVariable String id) {
		Teacher teacher = null;
		if (id != null) {
			try {
				teacher = teacherService.findById(Integer.parseInt(id));
				//Integer deId = tbDepartmentTeacherService.getDepartmentDao().getInt("SELECT tt.tdt_department_id FROM tb_department_teacher tt WHERE tt.tdt_teacher_id = ?", id);
				try {
					//model.addAttribute("dd", tbDepartmentManageService.findById(deId));
					model.addAttribute("cp", chinaProvinceService.findById((long)teacher.getProvinceId()));
					model.addAttribute("ccs", chinaCityService.findById((long)teacher.getCityId()));
					model.addAttribute("ccr", chinaCountyService.findById((long)teacher.getRegionId()));
				} catch (Exception e) {
				}
				//DepartmentTeacher departmentTeacher = departmentTeacherService.getDepartmentDao().search("SELECT * FROM tb_department_teacher tb WHERE tb.tdt_teacher_id=? ", Integer.parseInt(id));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("teacher", teacher);
		return "j_s/update_teacher";
	}

	//** 保存新增 **//*
	@ResponseBody
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request,Teacher teacher) {
		Userinfo userInfo = (Userinfo) request.getSession().getAttribute(GlobalStatic.DEFAULT_LOGIN_SESSION_NAME);
		teacher.setAddtime(new Date());
		teacher.setAdduser(userInfo.getId());
		teacher.setStatus(1);
		teacher.setLoginPass(StringUtil.getMD5Str(teacher.getLoginPass()));
		
		
		try {
			//对登录账号,教师证号,身份证验重
			String strLoinTeacher=teacher.getLoginUser();//登录账号
			String strTeacherNum = teacher.getTeacherNum();//教师证号
			String strCardNum = teacher.getCardNum();//身份证号
			String strNumber = teacher.getNumber();//职工编号
			List<Teacher> allTeacher = teacherService.findAll();
			for (Teacher loginTeacher : allTeacher) {
				if(strLoinTeacher.equalsIgnoreCase(loginTeacher.getLoginUser())){
					return "102";
				}
				if(strTeacherNum.equalsIgnoreCase(loginTeacher.getTeacherNum())){
					return "105";
				}
				if(strCardNum.equalsIgnoreCase(loginTeacher.getCardNum())){
					return "106";
				}
				if(strNumber.equalsIgnoreCase(loginTeacher.getNumber())){
					return "107";
				}
			}
			List<TbStudent> allStudent = tbStudentService.findAll();
			for (TbStudent loginStudent : allStudent) {
				if(strLoinTeacher.equalsIgnoreCase(loginStudent.getTsLoginUser())){
					return "103";
				}
			}
			List<TbAccount> allAccount = tbAccountService.findAll();
			for (TbAccount tbAccount : allAccount) {
				if(strLoinTeacher.equalsIgnoreCase(tbAccount.getTaAccount())){
					return "104";
				}
			}
			
//			StringBuffer realPath = new StringBuffer();
//			try {
//				realPath.append("http://");
//				realPath.append(java.net.InetAddress.getLocalHost().getHostAddress());
//				realPath.append(":");
//				realPath.append(request.getServerPort());
//			} catch (UnknownHostException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			realPath.append(teacher.getPhoto().substring(7));//图片路径
			teacher.setPhoto(teacher.getPhoto());
			teacherService.save(teacher);
			
			return "100";
		} catch (Exception e) {
			logger.error("[/admin/teacher/save]错误，错误原因："+e.getMessage());
			e.printStackTrace();
			return "101";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/selByName")
	public String selByName(HttpServletRequest request, Model model, BaseQueryParam<Teacher> pageEntity, String name) {
		String lastSql = "";

		if (StringUtils.isNotBlank(name)) {
			lastSql += " and tt_name like '%" + name + "%'";
		}

		lastSql += " and tt_status !=0 ";

		return pageEntity.resultJson(teacherService.findPage(lastSql, pageEntity));
	}

	//*** 地区联动 **//*
	@ResponseBody
	@RequestMapping("/pro")
	public String getProvince() {
		return JsonUtils.writeValueAsString(chinaProvinceService.findAllPro());
	}

	@ResponseBody
	@RequestMapping("/city/{proId}")
	public String getCity(@PathVariable String proId) {
		return JsonUtils.writeValueAsString(chinaCityService.findAllCityByPro(Long.parseLong(proId)));
	}

	@ResponseBody
	@RequestMapping("/county/{id}")
	public String getCounty(@PathVariable String id) {
		return JsonUtils.writeValueAsString(chinaCountyService.findAllCountyByCity(Long.parseLong(id)));
	}

	//** 班级查询 **//*
	@ResponseBody
	@RequestMapping("/getClass/{id}")
	public String getTbCalss(@PathVariable String id) {
		System.out.println("id->"+id);
		if(StringUtil.isEmptyNull(id)){
			id = "0";
		}
		List<TbClass> lTc = tbClassService.findAllClassByGrade(Long.parseLong(id));
		List<TbClass> lTcNew = new ArrayList<TbClass>();
		for (TbClass tbClass : lTc) {
			 if(tbClass.getStatus().intValue()!=0){
				 lTcNew.add(tbClass);
			 }
		}
		return JsonUtils.writeValueAsString(lTcNew);
	}

	@ResponseBody
	@RequestMapping(value = "/list_check_user")
	public String listCheckUser(HttpServletRequest request, Model model, BaseQueryParam<Teacher> pageEntity, @RequestParam(required=false) String login_user) {
		String lastSql = "";

		if (StringUtils.isNotBlank(login_user)) {
			lastSql += " and tt_login_user = '" + login_user + "'";
		}

		lastSql += " order by tt_addtime desc";

		PageBean<Teacher> page = teacherService.findPage(lastSql, pageEntity);
        String str = String.valueOf(page != null ? page.getList().size() : 0);
		return str;
	}
	
	//** 查询所有教师 **//*
	@ResponseBody
	@RequestMapping("/get_all_teacher")
	public String getTbCalss() {
		List<Teacher> lT = teacherService.findAll();
		return JsonUtils.writeValueAsString(lT);
	}
	
	//** 查询部门**//*
	@ResponseBody
	@RequestMapping("/getDepartment")
	public List<TbDepartment> getDepartment() {
		return tbDepartmentManageService.findDepartment();
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
			return "j_s/add_teacher";
		} else if ("update".equals(paper)) {// 跳转到编辑页面
			Teacher teacher = teacherService.findById(Integer.valueOf(id.toString()));
			request.setAttribute("teacher", teacher);
			
			return "j_s/update_teacher";
		}

		return "j_s/" + paper;
	}
}
