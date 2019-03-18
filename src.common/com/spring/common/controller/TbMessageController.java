package com.spring.common.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.spring.base.utils.GlobalStatic;
import com.spring.common.entity.TbMessage;
import com.spring.common.entity.Userinfo;
import com.spring.common.service.TbMessageService;
import com.spring.common.service.UserinfoService;

/**
 * 
*    
* 项目名称：DdzbBackstage   
* 类名称：MessageController   
* 类描述：   消息管理控制类
* 创建人：limeng  
* 创建时间：2014年12月5日 下午2:15:38   
* @version    
*
 */
@Controller
@RequestMapping(value = "/message")
public class TbMessageController extends BaseController {
	private static Log log = LogFactory
			.getLog(TbMessageController.class);
	@Resource
	TbMessageService tbMessageService;
	@Resource 
	UserinfoService userinfoService;

	/**
	 * @throws IOException 
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
	public String view(HttpServletRequest request,HttpServletResponse response, @PathVariable String paper,
			@RequestParam(value = "tmId", required = false) String tmId,
			@RequestParam(value = "tmNumber", required = false) String tmNumber){
	
		if (StringUtils.isBlank(paper)) {
			return "";
		}

		if ("list".equals(paper)) {// 跳转到主页面
			
			return "baseData/tbMessage_list";
		}
		
		if ("send".equals(paper)) {// 跳转到添加页面
		
			return "baseData/tbMessage_send";
		}
		if ("info".equals(paper)) {// 跳转到详情页面
			HashMap<String, Object> params = new HashMap<String, Object>();
			
			TbMessage tbMessage = tbMessageService.findById(Long.valueOf(tmId));
			params.put("tmTitle", tbMessage.getTmTitle());//消息标题
			params.put("tmContent", tbMessage.getTmContent());//消息内容
			params.put("tmNumber", tbMessage.getTmNumber());//消息内容
			
			request.setAttribute("params", params);
			
			return "baseData/tbMessage_detail";
		}

		return "baseData/" + paper;
	}

	/**
	 * 
	 * @Title: list
	 * @Description: 分页显示消息
	 * @param @param request
	 * @param @return 设定文件
	 * @return HashMap<String,Object> 返回类型
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
		params.put("tmTitle", request.getParameter("tmTitle"));
		params.put("tmAddDate", request.getParameter("tmAddDate"));
		params.put("tmAddDateEnd", request.getParameter("tmAddDateEnd"));
		
		return tbMessageService.findForJson(params);

	}
	/**
	 * 
	* @Title: infoList 
	* @Description: 分页显示消息详情 
	* @param @param request
	* @param @return    设定文件 
	* @return HashMap<String,Object>    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/infoList")
	public HashMap<String, Object> infoList(HttpServletRequest request) {
		/**
		 * 添加分页和查询参数
		 */
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("page", request.getParameter("page"));
		params.put("pageSize", request.getParameter("rows"));
		params.put("tmNumber", request.getParameter("tmNumber"));
		
		return tbMessageService.findForJsonByTitle(params);
		
	}
	

	/**
	 * 
	* @Title: delete 
	* @Description: 删除消息 
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(HttpServletRequest request) {

		String tmNumber = request.getParameter("tmNumber");
		/**
		 * 判定登录用户是否超时
		 */
		ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject()
				.getPrincipals().getPrimaryPrincipal();// 获取当前登录用户

		if (loginUser.getName().equalsIgnoreCase("")) {
			return "101";
		}

		try {
			tbMessageService.updateByNumber(tmNumber);
			
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbMessageController-delete()]：错误原因:" + e.getMessage());
			return null;
		}
	}
	
	/**
	 * 发送消息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpServletRequest request) {
		
		try {
			int code = (int) (Math.random() * 90000000 + 10000000);
			String tmTitle = request.getParameter("tmTitle");
			String tmContent = request.getParameter("tmContent");
			String ids = request.getParameter("ids");
			String types = request.getParameter("types");
			String[] id = ids.split(",");
			String[] type = types.split(",");
			for (int i = 0; i < id.length; i++) {
				TbMessage tbMessage = new TbMessage();
				tbMessage.setTmAddtime(new Timestamp(new Date().getTime()));
				tbMessage.setTmStatus(1);
				tbMessage.setTmTitle(tmTitle);
				tbMessage.setTmContent(tmContent);
				tbMessage.setTmMemberId(Long.valueOf(id[i]));
				tbMessage.setTmType(Integer.valueOf(type[i]));
				tbMessage.setTmNumber(""+code);
				Userinfo userInfo = (Userinfo) request.getSession().getAttribute
						(GlobalStatic.DEFAULT_LOGIN_SESSION_NAME);//获取登录用户
				tbMessage.setTmAddPerson(userInfo.getId());
				tbMessageService.save(tbMessage);
			}
			return "100";
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "101";
		}
	}
	
	/**
	 * 
	* @Title: infoList 
	* @Description: 消息接受者（会员列表）
	* @param @param request
	* @param @return    设定文件 
	* @return HashMap<String,Object>    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/memberList")
	public HashMap<String, Object> memberList(HttpServletRequest request) {
		/**
		 * 添加分页和查询参数
		 */
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("page", request.getParameter("page"));
		params.put("pageSize", request.getParameter("rows"));
		params.put("sort", request.getParameter("sort"));
		params.put("order", request.getParameter("order"));
		params.put("tmType", request.getParameter("tmType"));
		params.put("tmAccount", request.getParameter("tmAccount"));
		
		return tbMessageService.findForMember(params);
	}
}
