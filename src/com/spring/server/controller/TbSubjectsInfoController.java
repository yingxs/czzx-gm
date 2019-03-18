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
import java.util.Map;
import com.spring.base.shiro.ShiroUser;
import com.spring.base.utils.StringUtil;

import org.apache.shiro.SecurityUtils;

import com.spring.server.entity.TbCourseInfo;
import com.spring.server.entity.TbSubjectsInfo;
import com.spring.server.service.TbCourseInfoService;
import com.spring.server.service.TbSubjectsInfoService;
import com.spring.base.controller.BaseController;

/**
 * 科目表控制器
 *
 * @author 
 * @Date 2019-03-08 16:14:27
 */
@Controller
@RequestMapping("/TbsubjectsInfo")
public class TbSubjectsInfoController extends BaseController {

    private String PREFIX = "/TbsubjectsInfo";

    @Autowired
    private TbSubjectsInfoService TbsubjectsInfoService;
    
    @Autowired
	private TbCourseInfoService tbcourseInfoService;

    @RequestMapping(value = "/index/{paper}")
	public String view(HttpServletRequest request, @PathVariable String paper,
			 Long TbsubjectsInfoId) {
		
		if ("list".equals(paper)) {
			//返回当前会员对此菜单的按钮权限操作
			request.setAttribute("btnList", saveMenuId(request));
			return PREFIX+"/TbsubjectsInfo_list";
		}
		if ("add".equals(paper)) {
			return PREFIX+"/TbsubjectsInfo_add";
		}
		if ("edit".equals(paper)) {

			TbSubjectsInfo entity = TbsubjectsInfoService.findById(TbsubjectsInfoId);
			request.setAttribute("entity", entity);
			return PREFIX+"/TbsubjectsInfo_edit";
		}

		return PREFIX +"/"+ paper;
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public Map<String, Object> list(HttpServletRequest request) {
		//params包含所有前端提交的参数
		HashMap<String, String> params =getRequestParams(request);
		return TbsubjectsInfoService.findForJson(params);
	}

	@ResponseBody
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request,TbSubjectsInfo TbsubjectsInfo) {
		if (TbsubjectsInfo == null){
            return "101";
        }
		//entity对象二次加工程序start
		
		//entity对象二次加工程序end
		try {
			ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject()
					.getPrincipals().getPrimaryPrincipal();// 获取当前登录用户
			TbsubjectsInfoService.save(TbsubjectsInfo);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbsubjectsInfo-add()]：错误原因:" + e.getMessage());
			return "110";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,TbSubjectsInfo TbsubjectsInfo){
		if (TbsubjectsInfo == null){
            return "101";
        }
		//entity对象二次加工程序start
		
		//entity对象二次加工程序end
		try {
			TbsubjectsInfoService.update(TbsubjectsInfo);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbsubjectsInfo-edit()]：错误原因:" + e.getMessage());
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
	public String del(HttpServletRequest request,Long TbsubjectsInfoId) {

		if (TbsubjectsInfoId==null) {
			return "101";
		}
		try {
			TbsubjectsInfoService.delete(TbsubjectsInfoId);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbsubjectsInfo-del()]：错误原因:" + e.getMessage());
			return "110";
		}
	}
	
	/**
	 * 查询所有科目
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_get_subjectList")
	public Map<String, Object> ajax_get_subjectList(HttpServletRequest request) {
		// params包含所有前端提交的参数
		HashMap<String, String> params = getRequestParams(request);
		return TbsubjectsInfoService.findForJson(params);
	}

	/**
	 * 删除指定科目 状态改为0
	 * 
	 * @param request
	 * @param TbSubjectsInfoId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_del_subjectForId")
	public Map<String, String> ajax_del_subjectForId(HttpServletRequest request, Long tsi_id) {
		Map<String, String> map = new HashMap<>();
		try {
			// 判空
			if (tsi_id == null) {
				map.put("code", "101");
				map.put("msg", "请选中要删除的科目");
				return map;
			}

			// 判断要删除的科目是否还存在
			TbSubjectsInfo subjectsInfo = TbsubjectsInfoService.findById(tsi_id);
			if (subjectsInfo == null || subjectsInfo.getTsiStatus() == 0) {
				map.put("code", "101");
				map.put("msg", "您要删除的科目已经不存在");
				return map;
			}

			// 判断要删除掉的科目下有没有课程
			TbCourseInfo result = tbcourseInfoService.get_course_forSubjectDown(tsi_id);
			if (result != null) {
				map.put("code", "101");
				map.put("msg", "您要删除的科目下有有课程不能删除");
				return map;
			}

			subjectsInfo.setTsiStatus(0);
			TbsubjectsInfoService.update(subjectsInfo);
			map.put("code", "100");
			map.put("msg", "删除成功");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbSubjectsInfo-del()]：错误原因:" + e.getMessage());
			map.put("code", "110");
			map.put("msg", e.getMessage());
			return map;
		}
	}

	/**
	 * 添加科目
	 * 
	 * @param request
	 * @param TbSubjectsInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_add_TbSubjectsInfo")
	public Map<String, String> ajax_add_TbSubjectsInfo(HttpServletRequest request, TbSubjectsInfo TbSubjectsInfo) {
		Map<String, String> map = new HashMap<>();
		try {
			if (TbSubjectsInfo == null) {
				map.put("code", "101");
				map.put("msg", "科目名称不能为空");
				return map;
			}
			if (StringUtil.isEmptyNull(TbSubjectsInfo.getTsiName())) {
				map.put("code", "101");
				map.put("msg", "科目名称不能为空");
				return map;
			}
			// 判断科目名称是否已存在
			TbSubjectsInfo result = TbsubjectsInfoService.get_Subject_By_tsiName(TbSubjectsInfo.getTsiName());
			if(result!=null){
				map.put("code", "102");
				map.put("msg", "您输入的科目名称已存在");
				return map;
			}
			// entity对象二次加工程序start
			Timestamp timestamp = new Timestamp(new Date().getTime());
			TbSubjectsInfo.setTsiAddDate(timestamp);
			TbSubjectsInfo.setTsiStatus(1);
			// entity对象二次加工程序end
			ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();// 获取当前登录用户
			TbSubjectsInfo.setTsiAddPerson(loginUser.getId() + "");
			TbsubjectsInfoService.save(TbSubjectsInfo);
			map.put("code", "100");
			map.put("msg", "添加成功");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbSubjectsInfo-add()]：错误原因:" + e.getMessage());
			map.put("code", "102");
			map.put("msg", e.getMessage());
			return map;
		}
	}

	// 编辑前的回显
	@ResponseBody
	@RequestMapping(value = "/ajax_get_subjectName")
	public TbSubjectsInfo ajax_get_subjectName(HttpServletRequest request, Long tsi_id) {
		return TbsubjectsInfoService.findById(tsi_id);
	}

	/**
	 * 编辑科目
	 * 
	 * @param request
	 * @param TbSubjectsInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_edit_TbSubjectsInfo")
	public Map<String, String> ajax_edit_TbSubjectsInfo(HttpServletRequest request, TbSubjectsInfo TbSubjectsInfo) {
		Map<String, String> map = new HashMap<>();
		try {
			if (TbSubjectsInfo == null) {
				map.put("code", "101");
				map.put("msg", "添加科目不能为空");
				return map;
			}
			if (StringUtil.isEmptyNull(TbSubjectsInfo.getTsiName())) {
				map.put("code", "101");
				map.put("msg", "添加科目不能为空");
				return map;
			}
			if (StringUtil.isEmptyNull(TbSubjectsInfo.getTsiId() + "")) {
				map.put("code", "101");
				map.put("msg", "请选中要编辑的科目");
				return map;
			}
			//判断科目名不能重复
			TbSubjectsInfo result = TbsubjectsInfoService.get_Subject_By_tsiName(TbSubjectsInfo.getTsiName());
			if(result!=null){
				map.put("code", "102");
				map.put("msg", "您好，科目名称不能重复，请检查");
				return map;
			}
			
			TbsubjectsInfoService.update(TbSubjectsInfo);
			map.put("code", "100");
			map.put("msg", "编辑成功");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbSubjectsInfo-add()]：错误原因:" + e.getMessage());
			map.put("code", "102");
			map.put("msg", e.getMessage());
			return map;
		}
	}

	
}
