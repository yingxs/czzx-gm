package com.spring.common.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.base.controller.BaseController;
import com.spring.base.shiro.ShiroUser;
import com.spring.common.entity.TbAdvertiseContent;
import com.spring.common.service.TbAdvertiseContentService;

/**
 * 
*    
* 项目名称：  
* 类名称：TbAdvertiseContentController   
* 类描述：  广告管理
* 创建人：lm 
* 创建时间：2015年3月19日 上午8:44:55   
* @version    
*
 */
@Controller
@RequestMapping(value = "/advertise")
public class TbAdvertiseContentController extends BaseController {
	private Log log = LogFactory.getLog(TbAdvertiseContentController.class);

	@Resource
	TbAdvertiseContentService tbAdvertiseContentService;
	
	/**
	 * 
	 * @Title: view
	 * @Description: 页面跳转
	 * @param @param request
	 * @param @param paper
	 * @param @param id
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/index/{paper}")
	public String view(HttpServletRequest request, @PathVariable String paper,
			@RequestParam(value = "tacId", required = false) Long tacId) {
		log.info("操作对象的ID：" + tacId);
		if (StringUtils.isBlank(paper)) {
			return "";
		}

		if ("list".equals(paper)) {// 跳转到主页面
			
			return "baseData/tbAdvertiseContent_list";
		}
		if ("add".equals(paper)) {// 跳转到添加页面

			return "baseData/tbAdvertiseContent_add";
		}
		if("edit".equals(paper)){
			Map<String, Object> advertise = tbAdvertiseContentService.findAdvertiseById(tacId);
			request.setAttribute("advertise", advertise);
			
			return "baseData/tbAdvertiseContent_edit";
		}
		if("info".equals(paper)){
			Map<String, Object> advertise = tbAdvertiseContentService.findAdvertiseById(tacId);
			request.setAttribute("advertise", advertise);
			
			return "baseData/tbAdvertiseContent_info";
		}
		
		return "baseData/" + paper;
	}
	
	/**
	 * 
	* @Title: list 
	* @Description:  分页显示广告
	* @param @param request
	* @param @return    设定文件 
	* @return HashMap<String,Object>    返回类型 
	* @throws
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
		params.put("sort", request.getParameter("sort"));
		params.put("order", request.getParameter("order"));
		params.put("tacCata", request.getParameter("tacCata"));
		params.put("tacContent", request.getParameter("tacContent"));
		params.put("AddDateEnd", request.getParameter("AddDateEnd"));
		params.put("AddDate", request.getParameter("AddDate"));

		return tbAdvertiseContentService.findForJson(params);

	}

	/**
	 * 
	 * @Title: add
	 * @Description: 新增广告
	 * @param @param
	 * @param @return
	 * @return String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request,TbAdvertiseContent entity) {

		TbAdvertiseContent advertise = new TbAdvertiseContent();
		if(entity.getTacCataId().equals("")){
			return "101";
		}
		
		try {
			advertise.setTacContent(entity.getTacContent());
			advertise.setTacCataId(entity.getTacCataId());
			advertise.setTacPhoneImage(entity.getTacPhoneImage());
			advertise.setTacWebImage(entity.getTacWebImage());
			advertise.setTacLink(entity.getTacLink());
			advertise.setTacAddDate(new Timestamp(new Date().getTime()));
			// 获取当前登录用户
			ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject()
					.getPrincipals().getPrimaryPrincipal();
			advertise.setTacCreater(loginUser.getId());
			advertise.setTacStatus(1);
			
			tbAdvertiseContentService.save(advertise);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[TbAdvertiseContentController-add()]：错误原因:" + e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	* @Title: edit 
	* @Description:  修改广告内容
	* @param @param request
	* @param @param entity
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,TbAdvertiseContent entity) {
		
		if(entity.getTacCataId().equals("")){
			return "101";
		}
		TbAdvertiseContent advertise = tbAdvertiseContentService.findById(entity.getTacId());
		
		try {
			advertise.setTacCataId(entity.getTacCataId());
			advertise.setTacContent(entity.getTacContent());
			advertise.setTacPhoneImage(entity.getTacPhoneImage());
			advertise.setTacWebImage(entity.getTacWebImage());
			advertise.setTacLink(entity.getTacLink());
			
			tbAdvertiseContentService.update(advertise);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[TbAdvertiseContentController-edit()]：错误原因:" + e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 * @Title: delete
	 * @Description: 删除广告
	 * @param @param request
	 * @param @return
	 * @return String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(HttpServletRequest request) {
		String tacId = request.getParameter("tacId");
		
		try {
			tbAdvertiseContentService.delete(Long.valueOf(tacId));
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[TbAdvertiseContentController-delete()]：错误原因:" + e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	* @Title: findCatalogName 
	* @Description:  查询广告板块
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/findCatalogName", method = RequestMethod.POST)
	public List<Map<String, Object>> findCatalogName() {
		
//		StringBuilder json = new StringBuilder();
		List<Map<String, Object>> list = tbAdvertiseContentService.findCatalogName();
		/**
		 * 将list对象转换成json格式
		 */
//		json.append("[");
//		
//		if (list != null && list.size() != 0) {
//			for (int i = 0; i < list.size(); i++) {
//				TbAdvertiseContent advertiseContent = list.get(i);
//				json.append("{");
//				json.append("\"tacCataId\":\"" + advertiseContent.getTacCataId() + "\"");
//				if (i == list.size() - 1) {
//					json.append("}");
//				} else {
//					json.append("},");
//				}
//			}
//		}
//		json.append("]");
//		System.out.println("类别:" + json.toString());
//		log.info("类别:" + json.toString());
		return list;
	}
	
}
