package com.spring.common.controller;

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
import com.spring.common.dao.TbStudentDao;
import com.spring.common.entity.ChinaCity;
import com.spring.common.entity.ChinaCounty;
import com.spring.common.entity.ChinaProvince;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbAccount;
import com.spring.common.entity.TbClass;
import com.spring.common.entity.TbGrade;
import com.spring.common.entity.TbStudent;
import com.spring.common.entity.TbStudentFamily;
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

@Controller
@RequestMapping("/admin/students")
public class StudentController extends BaseController {

	@Resource
	TbStudentService tbStudentService;
	@Resource
	TbStudentFamilyService tbStudentFamilyService;
	@Resource
	TbClassService tbClassService;
	@Resource
	TbGradeService tbGradeService;
	@Resource
	TeacherService teacherService;
	@Resource 
	TbAccountService tbAccountService;
	@Resource
	ChinaProvinceService chinaProvinceService;
	@Resource
	ChinaCityService chinaCityService;
	@Resource
	ChinaCountyService chinaCountyService;
	
	@RequestMapping(value = "/init_page")
	public String view(HttpServletRequest request) {
		
		return "student/index";
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
			@RequestParam(value = "tsId", required = false) Long tsId) {
		if (StringUtils.isBlank(paper)) {
			return "";
		}

		if ("add".equals(paper)) {// 跳转到添加页面
			return "student/student_add";
		} else if ("edit".equals(paper)) {// 跳转到编辑页面
			TbStudent student = tbStudentService.findById(tsId);
			request.setAttribute("student", student);
			if(student.getTsClassId()!=null){
				TbClass tbClass = tbClassService.findById(student.getTsClassId());
				request.setAttribute("classId", tbClass.getId());
				if(!StringUtil.isEmptyNull(tbClass.getGradeId().toString())){
					TbGrade grade = tbGradeService.findById(Integer.valueOf(tbClass.getGradeId().toString()));
					request.setAttribute("gradeId", grade.getId());
				}
			}
			return "student/student_update";
		} else if ("detail".equals(paper)) {// 跳转到学生详情页面
			TbStudent student = tbStudentService.findById(tsId);
			request.setAttribute("student", student);
			if(student.getTsSex() == 1){
				request.setAttribute("sex", "男");
			}else{
				request.setAttribute("sex", "女");
			}
			if(student.getTsClassId()!=null){
				TbClass tbClass = tbClassService.findById(student.getTsClassId());
				request.setAttribute("class", tbClass.getName());
				if(tbClass.getGradeId()!=null){
					TbGrade grade = tbGradeService.findById(Integer.valueOf(tbClass.getGradeId().toString()));
					request.setAttribute("grade", grade.getName());
				}
			}
			if(student.getTsProvinceId()!=null){
				ChinaProvince province = chinaProvinceService.findProById(student.getTsProvinceId());
				request.setAttribute("pName", province.getPname());
			}
			if(student.getTsCityId()!=null){
				ChinaCity city = chinaCityService.findCityById(student.getTsCityId());
				request.setAttribute("cName", city.getCname());
			}
			if(student.getTsCountyId()!=null){
				ChinaCounty county = chinaCountyService.findCountyById(student.getTsCountyId());
				request.setAttribute("oName", county.getOname());
			}
			return "student/student_detail";
		} else if ("upgrade".equals(paper)) {// 跳转到学生升级页面
			
			return "student/student_upgrade";
		}

		return "student/" + paper;
	}

	/**
	 * 查询所有的学员的信息
	 * 
	 * @param model
	 * @param pageEntity
	 * @param tsNumber
	 * @param tsSex
	 * @return
	 */
	@RequestMapping("/list_json")
	@ResponseBody
	public String listJoson(Model model, BaseQueryParam<TbStudent> pageEntity, String tsNumber, String tsSex) {
		String lastSql = "";

		if (StringUtils.isNotBlank(pageEntity.getKeywords())) {
			lastSql += " and ts_name like '%" + pageEntity.getKeywords() + "%'";
		}

		if (StringUtils.isNotBlank(tsNumber)) {
			lastSql += " and ts_number = '" + tsNumber + "'";
		}

		if (StringUtils.isNotBlank(tsSex)) {
			lastSql += " and ts_sex = '" + tsSex + "'";
		}
		lastSql += " and ts_status != '0'";
		PageBean<TbStudent> pages = tbStudentService.findPage(lastSql, pageEntity);
		List<TbStudent> iList = pages.getList();
		for (TbStudent tbStudent : iList) {
			tbStudent.setTbStudentFamily(tbStudentFamilyService.finByTbFamaily(tbStudent.getTsId()));
			tbStudent.setTbClass(tbClassService.findById(Integer.valueOf(tbStudent.getTbClass().getId().toString())));
			tbStudent.setTbGrade(tbGradeService.findById(Integer.valueOf(tbStudent.getTbClass().getGradeId().toString())));
			tbStudent.setTeacher(teacherService.findById(Integer.valueOf(tbStudent.getTbClass().getTeacherId().toString())));
		}
		return pageEntity.resultJson(pages);

	}

	/**
	 * 修改跳转的页面以及调取数据
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/init_update")
	public String initUpdate(Model model, @RequestParam String id) {

		TbStudent tbStudent = tbStudentService.findById(Long.parseLong(id));
		tbStudent.setTbStudentFamily(tbStudentFamilyService.finByTbFamaily(tbStudent.getTsId()));
		tbStudent.setTbClass(tbClassService.findById(Integer.valueOf(tbStudent.getTbClass().getId().toString())));
		tbStudent.setTbGrade(tbGradeService.findById(Integer.valueOf(tbStudent.getTbClass().getGradeId().toString())));
		tbStudent.setTeacher(teacherService.findById(Integer.valueOf(tbStudent.getTbClass().getTeacherId().toString())));
		model.addAttribute("tbStudent", tbStudent);
		return "/student/update";
	}

	/** 保存新增 **/
	@ResponseBody
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request) {
		Userinfo userinfo = (Userinfo) request.getSession().getAttribute(GlobalStatic.DEFAULT_LOGIN_SESSION_NAME);// 获取当前登录用户
		String tsNumber = request.getParameter("tsNumber");//学号
		String tsName = request.getParameter("tsName");//姓名
		String tsSex = request.getParameter("tsSex");//性别
		String tbClass = request.getParameter("tbClass");//班级
		String tsCardNum = request.getParameter("tsCardNum");//身份证号
		String tsBirthday = request.getParameter("tsBirthday");//出生日期
		String tsPolitical = request.getParameter("tsPolitical");//政治面貌
		String tsNation = request.getParameter("tsNation");//民族
		String tsJiguan = request.getParameter("tsJiguan");//籍贯
		String province = request.getParameter("province");//省
		String city = request.getParameter("city");//市
		String county = request.getParameter("county");//区
		String tsAddress = request.getParameter("tsAddress");//详细地址
		String tsfName = request.getParameter("names");//家长姓名
		String tsfPhone = request.getParameter("phones");//家长电话
		String tsfRelation = request.getParameter("relations");//家长关系
		String tsLoginUser = request.getParameter("tsLoginUser");//登录账号
		String tsLoginPass = request.getParameter("tsLoginPass");//登录密码
		
		try {
			//添加账号信息
			TbAccount account= null;
			if(!StringUtil.isEmptyNull(tsLoginUser)){
				account = new TbAccount();
				account.setTaAccount(tsLoginUser);
				if(!StringUtil.isEmptyNull(tsLoginPass)){
					account.setTaPassword(StringUtil.getMD5Str(tsLoginPass));
				}
				account.setTaType(2);
				account.setTaStatus(1);
				tbAccountService.save(account);
			}
			
			//添加学生信息
			TbStudent student = new TbStudent();
			student.setTsName(tsName);
			student.setTsSex(Integer.valueOf(tsSex));
			student.setTsNumber(tsNumber);
			if(!StringUtil.isEmptyNull(tsCardNum)){
				student.setTsCardNum(tsCardNum);
			}
			if(!StringUtil.isEmptyNull(province)){
				student.setTsProvinceId(Long.valueOf(province));
			}
			if(!StringUtil.isEmptyNull(city)){
				student.setTsCityId(Long.valueOf(city));
			}
			if(!StringUtil.isEmptyNull(county)){
				student.setTsCountyId(Long.valueOf(county));
			}
			if(!StringUtil.isEmptyNull(tsAddress)){
				student.setTsAddress(tsAddress);
			}
			student.setTsAddtime(new Date());
			student.setTsAdduser(userinfo.getId());
			student.setTsStatus(1);
			if(account!=null){
				student.setTsAccountId(account.getTaId());
			}
			if(!StringUtil.isEmptyNull(tsBirthday)){
				student.setTsBirthday(tsBirthday);
			}
			if(!StringUtil.isEmptyNull(tsNation)){
				student.setTsNation(tsNation);
			}
			if(!StringUtil.isEmptyNull(tsPolitical)){
				student.setTsPolitical(tsPolitical);
			}
			if(!StringUtil.isEmptyNull(tsJiguan)){
				student.setTsJiguan(tsJiguan);
			}
			if(!StringUtil.isEmptyNull(tsLoginUser)){
				student.setTsLoginUser(tsLoginUser);
			}
			if(!StringUtil.isEmptyNull(tsLoginPass)){
				student.setTsLoginPass(StringUtil.getMD5Str(tsLoginPass));
			}
			if(!StringUtil.isEmptyNull(tbClass)){
				student.setTsClassId(Integer.valueOf(tbClass));
			}
			tbStudentService.save(student);
			
			//添加家长信息
			if(!StringUtil.isEmptyNull(tsfName)){
				TbStudentFamily studentFamily = new TbStudentFamily();
				String[] strTsfName = tsfName.split(",");
				String[] strTsfPhone = tsfPhone.split(",");
				String[] strTsfRelation = tsfRelation.split(",");
				for (int i = 0; i < strTsfName.length; i++) {
					studentFamily.setTsfStudentId(student.getTsId());
					studentFamily.setTsfName(strTsfName[i]);
					studentFamily.setTsfPhone(strTsfPhone[i]);
					studentFamily.setTsfRelation(strTsfRelation[i]);
					tbStudentFamilyService.save(studentFamily);
				}
			}
			
			return "100";
		} catch (NumberFormatException e) {
			e.getMessage();
			e.printStackTrace();
			return "101";
		}
	}

	/**
	 * 修改学生的信息
	 * 
	 * @param student
	 * @param family
	 * @return
	 */
	@RequestMapping("/update_info")
	@ResponseBody
	public String updateStudentInfo(HttpServletRequest request) {
		String tsNumber = request.getParameter("tsNumber");//学号
		String tsName = request.getParameter("tsName");//姓名
		String tsSex = request.getParameter("tsSex");//性别
		String tbClass = request.getParameter("tbClass");//班级
		String tsCardNum = request.getParameter("tsCardNum");//身份证号
		String tsBirthday = request.getParameter("tsBirthday");//出生日期
		String tsPolitical = request.getParameter("tsPolitical");//政治面貌
		String tsNation = request.getParameter("tsNation");//民族
		String tsJiguan = request.getParameter("tsJiguan");//籍贯
		String province = request.getParameter("province");//省
		String city = request.getParameter("city");//市
		String county = request.getParameter("county");//区
		String tsAddress = request.getParameter("tsAddress");//详细地址
		String tsId = request.getParameter("tsId");//学生id
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa:"+tsJiguan);
		
		try {
			//添加学生信息
			TbStudent student = tbStudentService.findById(Long.valueOf(tsId));
			student.setTsName(tsName);
			student.setTsSex(Integer.valueOf(tsSex));
			student.setTsNumber(tsNumber);
			if(!StringUtil.isEmptyNull(tsCardNum)){
				student.setTsCardNum(tsCardNum);
			}
			if(!StringUtil.isEmptyNull(province)){
				student.setTsProvinceId(Long.valueOf(province));
			}	
			if(!StringUtil.isEmptyNull(city)){
				student.setTsCityId(Long.valueOf(city));
			}
			if(!StringUtil.isEmptyNull(county)){
				student.setTsCountyId(Long.valueOf(county));
			}
			if(!StringUtil.isEmptyNull(tsAddress)){
				student.setTsAddress(tsAddress);
			}
			if(!StringUtil.isEmptyNull(tsBirthday)){
				student.setTsBirthday(tsBirthday);
			}
			if(!StringUtil.isEmptyNull(tsNation)){
				student.setTsNation(tsNation);
			}
			if(!StringUtil.isEmptyNull(tsPolitical)){
				student.setTsPolitical(tsPolitical);
			}
			if(!StringUtil.isEmptyNull(tsJiguan)){
				student.setTsJiguan(tsJiguan);
			}
			if(!StringUtil.isEmptyNull(tbClass)){
				student.setTsClassId(Integer.valueOf(tbClass));
			}
			
			tbStudentService.update(student);
			
			return "100";
		} catch (NumberFormatException e) {
			e.getMessage();
			e.printStackTrace();
			return "101";
		}
	}

	/** 根据Id删除 **/
	@ResponseBody
	@RequestMapping(value = "/del")
	public String del(HttpServletRequest request, Model model, String id) {
		String result;
		if (id != null) {
			try {
				String sql = "UPDATE tb_student SET ts_status = 0 where ts_id =" + id;
//				tbStudentService.getTbStudentDao().updatep(sql);
				tbStudentService.equals(sql);
			} catch (Exception e) {
				e.printStackTrace();
			}
			result = "1";
		} else {
			result = "0";
		}

		return result;
	}

	/**
	 * 获取所有的年级
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/all/grade")
	public String getCity() {
		return JsonUtils.writeValueAsString(tbGradeService.findAll());
	}

	@ResponseBody
	@RequestMapping(value = "/list_check_student")
	public String listCheckStudent(HttpServletRequest request, Model model, BaseQueryParam<TbStudent> pageEntity, String loginUser, String stuNum) {
		String lastSql = "";

		if (StringUtils.isNotBlank(loginUser)) {
			lastSql += " and ts_login_user = '" + loginUser + "'";
		}

		if (StringUtils.isNotBlank(stuNum)) {
			lastSql += " and ts_number = '" + stuNum + "'";
		}

		PageBean<TbStudent> page = tbStudentService.findPage(lastSql, pageEntity);
		String str = String.valueOf(page != null ? page.getList().size() : 0);
		return str;
	}
	@ResponseBody
	@RequestMapping(value = "/selByName")
	public String selByName(HttpServletRequest request, Model model, BaseQueryParam<TbStudent> pageEntity, String name) {
		String lastSql = "";

		if (StringUtils.isNotBlank(name)) {
			lastSql += " and ts_name like '%" + name + "%'";
		}

		lastSql += " and ts_status !=0 ";

		return pageEntity.resultJson(tbStudentService.findPage(lastSql, pageEntity));
	}
	
	/**
	 * 学生管理列表
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
		params.put("tsName", request.getParameter("tsName"));
		params.put("AddDate", request.getParameter("AddDate"));
		params.put("AddDateEnd", request.getParameter("AddDateEnd"));
		try {
			return tbStudentService.findForStudentJson(params);
		} catch (Exception e) {
			logger.error("[brand/list]出错，错误原因："+e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

}
