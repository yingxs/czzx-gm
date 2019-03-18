package com.spring.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.spring.base.shiro.ShiroUser;
import org.apache.shiro.SecurityUtils;
import com.spring.base.utils.StringUtil;
import com.spring.server.entity.TbInstituteDepartmentInfo;
import com.spring.server.entity.TbInstituteInfo;
import com.spring.server.service.TbInstituteDepartmentInfoService;
import com.spring.server.service.TbInstituteInfoService;
import com.spring.base.controller.BaseController;

/**
 * 学院信息表控制器
 *
 * @author
 * @Date 2019-03-05 14:07:27
 */
@Controller
@RequestMapping("/tbInstituteInfo")
public class TbInstituteInfoController extends BaseController {

	private String PREFIX = "/tbInstituteInfo";

	// 系
	@Autowired
	private TbInstituteDepartmentInfoService tbInstituteDepartmentInfoService;

	@Autowired
	private TbInstituteInfoService tbInstituteInfoService;

	@RequestMapping(value = "/index/{paper}")
	public String view(HttpServletRequest request, @PathVariable String paper, Long tbInstituteInfoId) {

		if ("list".equals(paper)) {
			// 返回当前会员对此菜单的按钮权限操作
			request.setAttribute("btnList", saveMenuId(request));
			return PREFIX + "/tbInstituteInfo_list";
		}
		if ("add".equals(paper)) {
			return PREFIX + "/tbInstituteInfo_add";
		}
		if ("edit".equals(paper)) {
			String tii_id = request.getParameter("tii_id");
			TbInstituteInfo entity = tbInstituteInfoService.findById(Long.valueOf(tii_id));
			request.setAttribute("entity", entity);
			return PREFIX + "/tbInstituteInfo_edit";
		}

		return PREFIX + "/" + paper;
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public Map<String, Object> list(HttpServletRequest request) {
		// params包含所有前端提交的参数
		HashMap<String, String> params = getRequestParams(request);
		System.out.println("##############################" + params);
		return tbInstituteInfoService.findForJson(params);
	}

	@ResponseBody
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, TbInstituteInfo tbInstituteInfo) {
		if (tbInstituteInfo == null) {
			return "101";
		}
		// entity对象二次加工程序start

		// entity对象二次加工程序end
		try {
			ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();// 获取当前登录用户
			tbInstituteInfoService.save(tbInstituteInfo);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbInstituteInfo-add()]：错误原因:" + e.getMessage());
			return "110";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request, TbInstituteInfo tbInstituteInfo) {
		if (tbInstituteInfo == null) {
			return "101";
		}
		// entity对象二次加工程序start

		// entity对象二次加工程序end
		try {
			tbInstituteInfoService.update(tbInstituteInfo);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbInstituteInfo-edit()]：错误原因:" + e.getMessage());
			return "110";
		}

	}

	/**
	 * 
	 * @Title: del @Description: 删除 @param @param tcId @param @return @return
	 *         String @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/del")
	public String del(HttpServletRequest request, Long tbInstituteInfoId) {

		if (tbInstituteInfoId == null) {
			return "101";
		}
		try {
			tbInstituteInfoService.delete(tbInstituteInfoId);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbInstituteInfo-del()]：错误原因:" + e.getMessage());
			return "110";
		}
	}

	public static void main(String[] args) {
		String name = "张三";
		StringBuffer bu = new StringBuffer();
		bu.append("select * from a like uname ");
		bu.append("'%" + name + "%'");
		bu.append("AND tidi_institute_id '%" + name + "%'");

		System.out.println(bu);
	}

	/**
	 * ajax加载全部学院信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_get_instituteList")
	public Map<String, Object> ajax_get_instituteList(HttpServletRequest request) {
		// params包含所有前端提交的参数
		HashMap<String, String> params = getRequestParams(request);
		return tbInstituteInfoService.findForJson(params);
	}

	/**
	 * 下拉框加载学院列表
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_get_instituteIdAndNameList")
	public List<Map<String, Object>> ajax_get_instituteIdAndNameList(HttpServletRequest request) {
		return tbInstituteInfoService.ajax_get_instituteIdAndNameList();
	}

	/**
	 * 新增学院
	 * 
	 * @param request
	 * @param TbInstituteInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_add_tbInstituteInfo")
	public Map<String, String> ajax_add_tbInstituteInfo(HttpServletRequest request, TbInstituteInfo TbInstituteInfo) {
		Map<String, String> map = new HashMap<>();
		try {
			if (TbInstituteInfo == null) {
				map.put("code", "101");
				map.put("msg", "新增数据不完整，请检查");
				return map;
			}
			// 验证名称为空和长度
			String tiiName = TbInstituteInfo.getTiiName();
			if (StringUtil.isEmptyNull(tiiName)) {
				map.put("code", "101");
				map.put("msg", "学院名称不能为空，请检查");
				return map;
			}
			if (tiiName.length() > 20) {
				map.put("code", "101");
				map.put("msg", "您输入的学院名称长度超过数据库设计长度，请检查");
				return map;
			}

			// 验证名称是否重复
			TbInstituteInfo result = tbInstituteInfoService.alax_get_institute_forTiiName(tiiName);
			if (result != null) {
				map.put("code", "101");
				map.put("msg", "您输入的学院名称已经存在，请检查");
				return map;
			}

			String tiiIcon = TbInstituteInfo.getTiiIcon();
			// 判断学院logo为空和长度
			if (!StringUtil.isEmptyNull(tiiIcon)) {
				if (tiiIcon.length() > 256) {
					map.put("code", "102");
					map.put("msg", "您传入的logo路径超长，请检查");
					return map;
				}
			}
			// 判断排序号为空和数字
			String tiiOrder = TbInstituteInfo.getTiiOrder() + "";
			if (StringUtil.isEmptyNull(tiiOrder)) {
				map.put("code", "102");
				map.put("msg", "排列序号不能为空");
				return map;
			}
			if (!StringUtil.isNumber(tiiOrder)) {
				map.put("code", "102");
				map.put("msg", "排列序号只能是数字");
				return map;
			}
			// 判断排列序号不能重复
			TbInstituteInfo tbInstituteInfo = tbInstituteInfoService
					.alax_get_institute_forOrder(TbInstituteInfo.getTiiOrder() + "");
			if (tbInstituteInfo != null) {
				map.put("code", "103");
				map.put("msg", "排列序号不能重复");
				return map;
			}
			String tiiCode = TbInstituteInfo.getTiiCode();
			// 判断学院编码为空和长度
			if (!StringUtil.isEmptyNull(tiiCode)) {
				if (tiiCode.length() > 30) {
					map.put("code", "102");
					map.put("msg", "您输入的学院编码超长，请输入长度不超过20的学院编码");
					return map;
				}
			}
			// entity对象二次加工程序start
			TbInstituteInfo.setTiiStatus(1);
			Timestamp timestamp = new Timestamp(new Date().getTime());
			TbInstituteInfo.setTiiAddDate(timestamp);
			// entity对象二次加工程序end
			ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();// 获取当前登录用户
			TbInstituteInfo.setTiiAddPerson(loginUser.getId());
			// 保存
			tbInstituteInfoService.save(TbInstituteInfo);
			map.put("code", "100");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbInstituteInfo-add()]：错误原因:" + e.getMessage());
			map.put("code", "103");
			map.put("msg", e.getMessage());
			return map;
		}
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param tii_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_del_instituteForId")
	public Map<String, String> ajax_del_instituteForId(HttpServletRequest request, Long tii_id) {
		Map<String, String> map = new HashMap<>();
		try {
			if (tii_id == null) {
				map.put("code", "101");
				map.put("msg", "学院id不能为空");
				return map;
			}
			// 判断id是否存在，如果存在找出这个对象
			TbInstituteInfo tbInstituteInfo = tbInstituteInfoService.findById(tii_id);
			if (StringUtil.isEmptyNull(tbInstituteInfo.getTiiId() + "")) {
				map.put("code", "102");
				map.put("msg", "您要删除的学院id不存在，请检查!");
				return map;
			}
			// 查询学院下是否有系
			TbInstituteDepartmentInfo result = tbInstituteDepartmentInfoService
					.ajax_getDepartment_for_tidi_institute_id(tii_id);
			if (result != null) {
				map.put("code", "103");
				map.put("msg", "你要删除的学院下有系存在，不能删除!");
				return map;
			}

			tbInstituteInfo.setTiiStatus(0);
			tbInstituteInfoService.update(tbInstituteInfo);
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

	/**
	 * 编辑学院
	 * 
	 * @param request
	 * @param TbInstituteInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_edit_tbInstituteInfo")
	public Map<String, String> ajax_edit_tbInstituteInfo(HttpServletRequest request, TbInstituteInfo TbInstituteInfo) {
		Map<String, String> map = new HashMap<>();
		if (TbInstituteInfo == null) {
			map.put("code", "101");
			map.put("msg", "新增数据不完整，请检查");
			return map;
		}

		// 验证名称为空和长度
		String tiiName = TbInstituteInfo.getTiiName();
		if (StringUtil.isEmptyNull(tiiName)) {
			map.put("code", "101");
			map.put("msg", "学院名称不能为空，请检查");
			return map;
		}
		if (tiiName.length() > 30) {
			map.put("code", "101");
			map.put("msg", "您输入的学院名称长度超过数据库设计长度，请检查");
			return map;
		}

		String oldName = request.getParameter("oldName");
		// 验证名称是否重复
		TbInstituteInfo result = tbInstituteInfoService.alax_get_institute_forTiiName(tiiName);
		if (result != null) {
			// 老的跟新的不一样
			if (!TbInstituteInfo.getTiiName().equals(oldName)) {
				// 再拿新的去数据库查询有没有重复
				TbInstituteInfo newResult = tbInstituteInfoService
						.alax_get_institute_forTiiName(TbInstituteInfo.getTiiName());
				if (newResult != null) {
					map.put("code", "101");
					map.put("msg", "您输入的学院名称已经存在，请检查");
					return map;
				}
			}
		}

		String tiiIcon = TbInstituteInfo.getTiiIcon();
		// 判断学院logo为空和长度
		if (!StringUtil.isEmptyNull(tiiIcon)) {
			if (tiiIcon.length() > 256) {
				map.put("code", "102");
				map.put("msg", "您传入的logo路径超长，请检查");
				return map;
			}
		}

		String tiiCode = TbInstituteInfo.getTiiCode();
		// 判断学院编码为空和长度
		if (!StringUtil.isEmptyNull(tiiCode)) {
			if (tiiCode.length() > 20) {
				map.put("code", "102");
				map.put("msg", "您输入的学院编码超长，请输入长度不超过20的学院编码");
				return map;
			}
		}

		String oldOrder = request.getParameter("oldOrder");
		String newOrder = TbInstituteInfo.getTiiOrder() + "";

		// 判断排序号为空
		if (StringUtil.isEmptyNull(newOrder)) {
			map.put("code", "102");
			map.put("msg", "排列序号不能为空");
			return map;
		}
		if (!StringUtil.isNumber(newOrder)) {
			map.put("code", "102");
			map.put("msg", "排列序号只能是数字");
			return map;
		}
		// 判断排列序号是否重复
		// 新的不等于老的
		if (!oldOrder.equals(newOrder)) {
			// 判断新的在数据库是否已经存在
			TbInstituteInfo searchResult = tbInstituteInfoService.alax_get_institute_forOrder(newOrder);
			if (searchResult != null) {
				map.put("code", "102");
				map.put("msg", "排列序号不能重复");
				return map;
			}
		}

		try {
			tbInstituteInfoService.update(TbInstituteInfo);
			// 修改成功
			map.put("code", "100");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbInstituteInfo-add()]：错误原因:" + e.getMessage());
			map.put("code", "103");
			map.put("msg", e.getMessage());
			return map;
		}
	}

}
