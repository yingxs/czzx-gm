package com.spring.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import com.spring.base.shiro.ShiroUser;
import org.apache.shiro.SecurityUtils;
import com.spring.server.entity.TbMajorCourseRelate;
import com.spring.server.service.TbMajorCourseRelateService;
import com.spring.base.controller.BaseController;

/**
 * 专业课程关联表控制器
 *
 * @author 
 * @Date 2019-03-12 11:23:00
 */
@Controller
@RequestMapping("/Tb_MajorCourseRelate")
public class TbMajorCourseRelateController extends BaseController {

    private String PREFIX = "/Tb_MajorCourseRelate";

    @Autowired
    private TbMajorCourseRelateService Tb_MajorCourseRelateService;

    @RequestMapping(value = "/index/{paper}")
	public String view(HttpServletRequest request, @PathVariable String paper,
			 Long Tb_MajorCourseRelateId) {
		
		if ("list".equals(paper)) {
			//返回当前会员对此菜单的按钮权限操作
			request.setAttribute("btnList", saveMenuId(request));
			return PREFIX+"/Tb_MajorCourseRelate_list";
		}
		if ("add".equals(paper)) {
			return PREFIX+"/Tb_MajorCourseRelate_add";
		}
		if ("edit".equals(paper)) {

			TbMajorCourseRelate entity = Tb_MajorCourseRelateService.findById(Tb_MajorCourseRelateId);
			request.setAttribute("entity", entity);
			return PREFIX+"/Tb_MajorCourseRelate_edit";
		}

		return PREFIX +"/"+ paper;
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public Map<String, Object> list(HttpServletRequest request) {
		//params包含所有前端提交的参数
		HashMap<String, String> params =getRequestParams(request);
		return Tb_MajorCourseRelateService.findForJson(params);
	}

	@ResponseBody
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request,TbMajorCourseRelate Tb_MajorCourseRelate) {
		if (Tb_MajorCourseRelate == null){
            return "101";
        }
		//entity对象二次加工程序start
		
		//entity对象二次加工程序end
		try {
			ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject()
					.getPrincipals().getPrimaryPrincipal();// 获取当前登录用户
			Tb_MajorCourseRelateService.save(Tb_MajorCourseRelate);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[Tb_MajorCourseRelate-add()]：错误原因:" + e.getMessage());
			return "110";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,TbMajorCourseRelate Tb_MajorCourseRelate){
		if (Tb_MajorCourseRelate == null){
            return "101";
        }
		//entity对象二次加工程序start
		
		//entity对象二次加工程序end
		try {
			Tb_MajorCourseRelateService.update(Tb_MajorCourseRelate);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[Tb_MajorCourseRelate-edit()]：错误原因:" + e.getMessage());
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
	public String del(HttpServletRequest request,Long Tb_MajorCourseRelateId) {

		if (Tb_MajorCourseRelateId==null) {
			return "101";
		}
		try {
			Tb_MajorCourseRelateService.delete(Tb_MajorCourseRelateId);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[Tb_MajorCourseRelate-del()]：错误原因:" + e.getMessage());
			return "110";
		}
	}
}
