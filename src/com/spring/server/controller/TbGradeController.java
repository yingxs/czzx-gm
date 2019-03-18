package com.spring.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.spring.base.shiro.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import com.spring.base.utils.StringUtil;
import com.spring.server.entity.TbGrade;
import com.spring.server.service.TbGradeService;
import com.spring.base.controller.BaseController;

/**
 * 年级表控制器
 *
 * @author zhouyuan
 * @Date 2019-03-15 14:35:02
 */
@Controller
@RequestMapping("/tbGrade")
public class TbGradeController extends BaseController {

    private String PREFIX = "/tbGrade";

    @Autowired
    private TbGradeService tbGradeService;

    @RequestMapping(value = "/index/{paper}")
	public String view(HttpServletRequest request, @PathVariable String paper,
			 Long tbGradeId) {
		
		if ("list".equals(paper)) {
			//返回当前会员对此菜单的按钮权限操作
			request.setAttribute("btnList", saveMenuId(request));
			return PREFIX+"/tbGrade_list";
		}
		if ("add".equals(paper)) {
			return PREFIX+"/tbGrade_add";
		}
		if ("edit".equals(paper)) {

			TbGrade entity = tbGradeService.findById(tbGradeId);
			request.setAttribute("entity", entity);
			return PREFIX+"/tbGrade_edit";
		}

		return PREFIX +"/"+ paper;
	}

    
    /*
	 * 下拉框加载选择年级列表
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_get_gradeList")
	public List<Map<String, Object>> ajax_get_gradeList(HttpServletRequest request) {
		return tbGradeService.ajax_get_gradeList();
	}
    
	@ResponseBody
	@RequestMapping(value = "/list")
	public Map<String, Object> list(HttpServletRequest request) {
		//params包含所有前端提交的参数
		HashMap<String, String> params =getRequestParams(request);
		return tbGradeService.findForJson(params);
	}

	@ResponseBody
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request,TbGrade tbGrade) {
		if (tbGrade == null){
            return "101";
        }
		//entity对象二次加工程序start
		
		//entity对象二次加工程序end
		try {
			ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject()
					.getPrincipals().getPrimaryPrincipal();// 获取当前登录用户
			tbGradeService.save(tbGrade);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[tbGrade-add()]：错误原因:" + e.getMessage());
			return "110";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,TbGrade tbGrade){
		if (tbGrade == null){
            return "101";
        }
		//entity对象二次加工程序start
		
		//entity对象二次加工程序end
		try {
			tbGradeService.update(tbGrade);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[tbGrade-edit()]：错误原因:" + e.getMessage());
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
	public String del(HttpServletRequest request,Long tbGradeId) {

		if (tbGradeId==null) {
			return "101";
		}
		try {
			tbGradeService.delete(tbGradeId);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[tbGrade-del()]：错误原因:" + e.getMessage());
			return "110";
		}
	}
}
