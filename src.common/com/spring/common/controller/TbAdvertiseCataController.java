package com.spring.common.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import com.spring.common.entity.TbAdvertiseCatalog;
import com.spring.common.entity.TbAdvertiseContent;
import com.spring.common.service.TbAdvertiseCatalogService;
import com.spring.common.service.TbAdvertiseContentService;

/**
 * 
*    
* 项目名称： 
* 类名称：TbadvertiseCataController   
* 类描述：   板块管理
* 创建人：lm 
* 创建时间：2015年3月19日 下午4:04:39   
* @version    
*
 */
@Controller
@RequestMapping(value = "/advertiseCata")
public class TbAdvertiseCataController extends BaseController {
	private Log log = LogFactory.getLog(TbAdvertiseCataController.class);

	@Resource
	TbAdvertiseCatalogService tbAdvertiseCatalogService;
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

		if("list".equals(paper)){
			return "baseData/tbAdvertiseCatalog_list";
		}
		
		if ("add".equals(paper)) {// 跳转到添加页面
			return "baseData/tbAdvertiseCatalog_add";
		}
		if ("edit".equals(paper)) {// 跳转到添加页面
			TbAdvertiseCatalog catalog = tbAdvertiseCatalogService.findById(Long.valueOf(tacId));
			request.setAttribute("catalog", catalog);
			return "baseData/tbAdvertiseCatalog_edit";
		}
		
		return "baseData/" + paper;
	}
	
	/**
	 * 
	* @Title: list 
	* @Description:  分页显示广告板块
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
		params.put("tacName", request.getParameter("tacName"));

		return tbAdvertiseCatalogService.findForJson(params);

	}
	
	/**
	 * 
	 * @Title: add
	 * @Description: 新增广告板块
	 * @param @param
	 * @param @return
	 * @return String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request,TbAdvertiseCatalog entity) {
		
		if(entity.getTacName().equals("")){
			return "500";
		}
		
		TbAdvertiseCatalog advertiseCatalog = new TbAdvertiseCatalog();
		advertiseCatalog.setTacName(entity.getTacName());
		advertiseCatalog.setTacStatus(1);
		TbAdvertiseCatalog catalog = tbAdvertiseCatalogService.findOne(advertiseCatalog);
		if(null != catalog){
			return "101";
		}
		
		try {
			advertiseCatalog.setTacDesp(entity.getTacDesp());
			advertiseCatalog.setTacAddDate(new Timestamp(new Date().getTime()));
			// 获取当前登录用户
			ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject()
					.getPrincipals().getPrimaryPrincipal();
			advertiseCatalog.setTacAddPerson(loginUser.getId());
			
			tbAdvertiseCatalogService.save(advertiseCatalog);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[TbAdvertiseCatalogController-add()]：错误原因:" + e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	* @Title: edit 
	* @Description:  编辑广告板块
	* @param @param request
	* @param @param entity
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,TbAdvertiseCatalog entity) {
		
		if(entity.getTacName().equals("")){
			return "500";
		}
		
		try {
			TbAdvertiseCatalog oldAdvertiseCatalog = tbAdvertiseCatalogService.findById(entity.getTacId());
			if(!oldAdvertiseCatalog.getTacName().equals(entity.getTacName())){
				TbAdvertiseCatalog advertiseCatalog = new TbAdvertiseCatalog();
				advertiseCatalog.setTacName(entity.getTacName());
				advertiseCatalog.setTacStatus(1);
				TbAdvertiseCatalog catalog = tbAdvertiseCatalogService.findOne(advertiseCatalog);
				if(null != catalog){
					return "101";
				}
			}
			
			oldAdvertiseCatalog.setTacName(entity.getTacName());
			oldAdvertiseCatalog.setTacDesp(entity.getTacDesp());

			tbAdvertiseCatalogService.update(oldAdvertiseCatalog);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[TbAdvertiseCatalogController-edit()]：错误原因:" + e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 * @Title: delete
	 * @Description: 删除广告板块
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
			//判断该板块下面是否存在广告
			List<TbAdvertiseContent> list = tbAdvertiseContentService.findCatalogId(Long.valueOf(tacId));
			if(list.size()>0){
				return "101";
			}
			
			tbAdvertiseCatalogService.delete(Long.valueOf(tacId));
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			log.error("[TbAdvertiseCatalogController-delete()]：错误原因:" + e.getMessage());
			return null;
		}
	}
}
