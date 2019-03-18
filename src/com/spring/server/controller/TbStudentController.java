package com.spring.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.spring.base.shiro.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import com.spring.base.utils.StringUtil;
import com.spring.server.entity.TbInstituteInfo;
import com.spring.server.entity.TbStudent;
import com.spring.server.entity.TbSubjectsInfo;
import com.spring.server.service.TbInstituteInfoService;
import com.spring.server.service.TbStudentService;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;
import com.spring.base.controller.BaseController;

/**
 * 学生表控制器
 *
 * @author zhouyuan
 * @Date 2019-03-11 11:45:48
 */
@Controller
@RequestMapping("/TbStudent")
public class TbStudentController extends BaseController {

	private String PREFIX = "/TbStudent";

	@Autowired
	private TbStudentService TbStudentService;
	
	@Autowired
	private TbInstituteInfoService tbInstituteInfoService;

	@RequestMapping(value = "/index/{paper}")
	public String view(HttpServletRequest request, @PathVariable String paper, Long TbStudentId) {

		if ("list".equals(paper)) {
			// 返回当前会员对此菜单的按钮权限操作
			request.setAttribute("btnList", saveMenuId(request));
			return PREFIX + "/TbStudent_list";
		}
		if ("add".equals(paper)) {
			return PREFIX + "/TbStudent_add";
		}
		if ("edit".equals(paper)) {
			String ts_id = request.getParameter("ts_id");
			TbStudent entity = TbStudentService.findById(Long.valueOf(ts_id));
			request.setAttribute("entity", entity);
 			return PREFIX + "/TbStudent_edit";
		}

		return PREFIX + "/" + paper;
	}

	/**
	 * 新增学生
	 * 
	 * @param request
	 * @param TbInstituteInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_add_student")
	public Map<String, String> ajax_add_student(HttpServletRequest request, TbStudent tbStudent) {
		Map<String, String> map = new HashMap<>();
		try {
			System.out.println(tbStudent.toString()+"----------------------------");
			
			if (tbStudent.getTsNumber().length() > 20) {
				map.put("code", "102");
				map.put("msg", "您好，学号搜索只能输入20个以内的字母");
				return map;
			}
			if (StringUtil.isEmptyNull(tbStudent.getTsName())) {
				map.put("code", "106");
				map.put("msg", "姓名为空，请填入姓名！");
				return map;
			}
			if (tbStudent.getTsName().length() > 4) {
				map.put("code", "105");
				map.put("msg", "您好，姓名只能输入4个以内的汉字");
				return map;
			}
			if (StringUtil.isEmptyNull(tbStudent.getTsCardNum().length() + "")) {
				map.put("code", "109");
				map.put("msg", "身份证为空，请填入身份证！");
				return map;
			}
			if (tbStudent.getTsCardNum().length() > 18) {
				map.put("code", "109");
				map.put("msg", "您好，身份证号只能输入18个以内的字母和数字！");
				return map;
			}
			if (StringUtil.isEmptyNull(tbStudent.getTsSex())) {
				map.put("code", "110");
				map.put("msg", "您好，请选择性别！");
				return map;
			}

			if(StringUtil.isEmptyNull(tbStudent.getTsClassId()+"")){
			 map.put("code", "111");
			 map.put("msg", "您好，请选择班级！");
			 return map;
			 }

			if (!StringUtil.isNumber(tbStudent.getTsPhone())) {
				map.put("code", "112");
				map.put("msg", "您好，手机号只允许输入数字");
				return map;
			}
			if (tbStudent.getTsPhone().length() > 11) {
				map.put("code", "113");
				map.put("msg", "您好，只能输入11个以内的数字");
				return map;
			}

			if (StringUtil.isEmptyNull(tbStudent.getTsHjProperty() + "")) {
				map.put("code", "115");
				map.put("msg", "您好，请选择户口性质");
				return map;
			}
			if (StringUtil.isEmptyNull(tbStudent.getTsInDegree() + "")) {
				map.put("code", "115");
				map.put("msg", "您好，请选择户口性质");
				return map;
			}

			// 添加不能为空属性
			Timestamp timestamp = new Timestamp(new Date().getTime());
			tbStudent.setTsAddtime(timestamp);
			ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();// 获取当前登录用户
			tbStudent.setTsAdduser(loginUser.getId());
			tbStudent.setTsStatus(1);
			tbStudent.setTsStudentStatus(8);
			// 保存
			TbStudentService.save(tbStudent);
			map.put("code", "100");
			map.put("msg", "添加成功");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbStudent-add()]：错误原因:" + e.getMessage());
			map.put("code", "120");
			map.put("msg", e.getMessage());
			return map;
		}
	}

	/**
	 * 更改学生状态
	 * 
	 * @param request
	 * @param TbSubjectsInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_edit_studentStatus")
	public Map<String, String> ajax_edit_studentStatus(HttpServletRequest request, TbStudent tbStudent) {
		Map<String, String> map = new HashMap<>();
		try {
			if (tbStudent == null) {
				map.put("code", "101");
				map.put("msg", "学生状态不能为空");
				return map;
			}
			if (StringUtil.isEmptyNull(tbStudent.getTsStudentStatus() + "")) {
				map.put("code", "101");
				map.put("msg", "您好，学生状态不能为空，请选择");
				return map;
			}
			if (StringUtil.isEmptyNull(tbStudent.getTsId() + "")) {
				map.put("code", "101");
				map.put("msg", "请选中要更改状态的学生");
				return map;
			}
			TbStudentService.update(tbStudent);
			map.put("code", "100");
			map.put("msg", "更改成功");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbSubjectsInfo-add()]：错误原因:" + e.getMessage());
			map.put("code", "102");
			map.put("msg", e.getMessage());
			return map;
		}
	}

	/**
	 * 得到更改状态弹框弹出时需要回显的id，name和status
	 * 
	 * @param request
	 * @param tsi_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_get_SutdentIdNameAndStatus")
	public TbStudent ajax_get_SutdentIdNameAndStatus(HttpServletRequest request, Long ts_id) {
		return TbStudentService.findById(ts_id);
	}

	/**
	 * 根据id把用户的密码重置为身份证的后六位
	 * 
	 * @param request
	 * @param ts_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_reset_studentPWD")
	public Map<String, String> ajax_reset_studentPWD(HttpServletRequest request, Long ts_id) {
		Map<String, String> map = new HashMap<>();
		TbStudent tbStudent = TbStudentService.findById(ts_id);
		String tsCardNum = tbStudent.getTsCardNum();
		if (StringUtil.isEmptyNull(tsCardNum)) {
			map.put("code", "101");
			map.put("msg", "您的身份证号码有异常，请检查，无法重置");
			return map;
		}
		if (tsCardNum.length() != 18) {
			map.put("code", "101");
			map.put("msg", "您的身份证号码有异常，请检查，无法重置");
			return map;
		}
		String pwd = tsCardNum.trim().substring(12);
		tbStudent.setTsLoginPass(pwd);
		TbStudentService.update(tbStudent);
		map.put("code", "100");
		map.put("msg", "重置成功");
		return map;
	}

	/**
	 * 查询所有学生列表
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_get_studentList")
	public Map<String, Object> ajax_get_studentList(HttpServletRequest request) {
		// params包含所有前端提交的参数
		HashMap<String, String> params = getRequestParams(request);
		return TbStudentService.findForJson(params);
	}

	@ResponseBody
	@RequestMapping(value = "/ajax_del_studentForId")
	public Map<String, String> ajax_del_studentForId(HttpServletRequest request, Long ts_id) {
		Map<String, String> map = new HashMap<>();
		try {
			if (ts_id == null) {
				map.put("code", "101");
				map.put("msg", "请选择要删除的学生");
				return map;
			}
			// 判断id是否存在，如果存在找出这个对象
			TbStudent tbStudent = TbStudentService.findById(ts_id);
			if (StringUtil.isEmptyNull(tbStudent.getTsId() + "")) {
				map.put("code", "102");
				map.put("msg", "您要删除的学院id不存在，请检查!");
				return map;
			}

			// 改变这个学生状态是删除还是正常，而不是在校休学的那个状态
			tbStudent.setTsStatus(0);
			TbStudentService.update(tbStudent);
			map.put("code", "100");
			map.put("msg", "删除成功!");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbInstituteInfo-del()]：错误原因:" + e.getMessage());
			map.put("code", "103");
			map.put("msg", e.getMessage());
			return map;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, TbStudent TbStudent) {
		if (TbStudent == null) {
			return "101";
		}
		// entity对象二次加工程序start

		// entity对象二次加工程序end
		try {
			ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();// 获取当前登录用户
			TbStudentService.save(TbStudent);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbStudent-add()]：错误原因:" + e.getMessage());
			return "110";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request, TbStudent TbStudent) {
		if (TbStudent == null) {
			return "101";
		}
		// entity对象二次加工程序start

		// entity对象二次加工程序end
		try {
			TbStudentService.update(TbStudent);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbStudent-edit()]：错误原因:" + e.getMessage());
			return "110";
		}

	}

	@ResponseBody
	@RequestMapping(value = "/del")
	public String del(HttpServletRequest request, Long TbStudentId) {

		if (TbStudentId == null) {
			return "101";
		}
		try {
			TbStudentService.delete(TbStudentId);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbStudent-del()]：错误原因:" + e.getMessage());
			return "110";
		}
	}
}
