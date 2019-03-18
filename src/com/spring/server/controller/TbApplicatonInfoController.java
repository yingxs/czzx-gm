package com.spring.server.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.spring.server.entity.TbApplicatonInfo;
import com.spring.server.service.TbApplicatonInfoService;

@Controller
@RequestMapping(value = "/tbApplicatonInfo")
public class TbApplicatonInfoController {
	@Resource
	TbApplicatonInfoService  tbApplicatonInfoService;
	
	
	@RequestMapping(value = "/index/{paper}")
	public String view(HttpServletRequest request, @PathVariable String paper)
			/*,@RequestParam(value = "tdId", required = false) Long tdId*/ {
		if (StringUtils.isBlank(paper)) {
			return "";
		}
		/**
		 * 跳转
		 * @param request
		 * @param paper
		 * @param id
		 * @return
		 */
        //跳转到应用管理列表页面
		if ("tbApplicatonInfo".equals(paper)) {
			
			System.out.println("进入应用管理列表页面");
			
			return "ApplicatonInfo/tbApplicatonInfo";
		}
		
	    //跳转到新增应用页面
		if ("tbApplicatonInfoAdd".equals(paper)) {
			
			System.out.println("进入新增应用页面");
			
			return "ApplicatonInfo/tbApplicatonInfoAdd";
		}
        
		
		//跳转到应用管理编辑页面
		if ("tbApplicatonInfoUpdate".equals(paper)) {
			String taiId=request.getParameter("taiId");
			TbApplicatonInfo tbApplicatonInfo = tbApplicatonInfoService.findById(Long.valueOf(taiId));
			if(tbApplicatonInfo!=null){
				request.setAttribute("tbApplicatonInfo", tbApplicatonInfo);
			}
			else{
				request.setAttribute("tbApplicatonInfo", new TbApplicatonInfo());
			}
			System.out.println("进入应用管理编辑页面");
			return "ApplicatonInfo/tbApplicatonInfoUpdate";
		}
		
		
		//跳转到应用管理详情页面
		if ("tbApplicatonInfoDetail".equals(paper)) {
			String taiId=request.getParameter("taiId");
			
			TbApplicatonInfo tbApplicatonInfo = tbApplicatonInfoService.findById(Long.valueOf(taiId));
			System.out.println("进入应用管理详情页面");
			if(tbApplicatonInfo!=null){
				request.setAttribute("tbApplicatonInfo", tbApplicatonInfo);
			}
			else{
				request.setAttribute("tbApplicatonInfo", new TbApplicatonInfo());
			}
			return "ApplicatonInfo/tbApplicatonInfoDetail";
		}
		
		
		
		return "ApplicatonInfo/" + paper;

    }
	
	
	
	
	/**
	 * 
	 * 应用管理列表
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
		params.put("sort", request.getParameter("sort"));
		params.put("order", request.getParameter("order"));
		params.put("taiId", request.getParameter("taiId"));
		params.put("taiName", request.getParameter("taiName"));//应用名称
		params.put("taiIcon", request.getParameter("taiIcon"));//图标
		params.put("taiWebAddr", request.getParameter("taiWebAddr"));//应用地址
		params.put("taiPageCount", request.getParameter("taiPageCount")); //浏览量
		params.put("AddDate", request.getParameter("AddDate"));//创建开始时间
		params.put("AddDateEnd", request.getParameter("AddDateEnd"));//创建结束时间
		
		try {
			return tbApplicatonInfoService.findForJson(params);
		} catch (Exception e) {
		//	log.error("[/tbSpecCategoryAndSpec/list]错误，错误原因："+e.getMessage());
			e.printStackTrace();
			return null;
		}

	}
	
	
	
	/**
	 *  新增应用管理
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request) {
		
		String taiName=request.getParameter("taiName");//应用名称
		String taiWebAddr=request.getParameter("taiWebAddr");//应用地址
		String taiIcon=request.getParameter("taiIcon");//应用小图标
		String taiDesp=request.getParameter("taiDesp");//应用介绍
		
		System.out.println("应用名称:"+taiName+"应用地址:"+taiWebAddr+"应用小图标:"+taiIcon+"应用介绍"+taiDesp);
		//增加时new一个新对象
		TbApplicatonInfo  tbApplicatonInfo = new TbApplicatonInfo();
		tbApplicatonInfo.setTaiName(taiName);//应用名称
		tbApplicatonInfo.setTaiWebAddr(taiWebAddr); //应用地址
		tbApplicatonInfo.setTaiIcon(taiIcon); //应用小图标
		tbApplicatonInfo.setTaiDesp(taiDesp);//应用介绍
		tbApplicatonInfo.setTaiAddDate(new Timestamp(new Date().getTime()));//创建时间
		tbApplicatonInfo.setTaiStatus(1);//状态
		tbApplicatonInfo.setTaiPageCount(0L);//浏览量  //把String类型转换为Long类型
		tbApplicatonInfoService.save(tbApplicatonInfo);
		return "100";
		
	}
	
	
	

	/**
	 * 编辑应用管理
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request) {
		String taiId=request.getParameter("taiId");//应用信息表Id
		String taiName=request.getParameter("taiName");//应用名称
		String taiWebAddr=request.getParameter("taiWebAddr");//应用地址
		String taiIcon=request.getParameter("taiIcon");//应用小图标
		String taiDesp=request.getParameter("taiDesp");//应用介绍
		
		System.out.println("应用名称:"+taiName+"应用地址:"+taiWebAddr+"应用小图标:"+taiIcon+"应用介绍"+taiDesp);
		//更新时，根据findById方法
		TbApplicatonInfo  tbApplicatonInfo =tbApplicatonInfoService.findById(Long.valueOf(taiId));
		tbApplicatonInfo.setTaiName(taiName);//应用名称
		tbApplicatonInfo.setTaiWebAddr(taiWebAddr); //应用地址
		tbApplicatonInfo.setTaiIcon(taiIcon); //应用小图标
		tbApplicatonInfo.setTaiDesp(taiDesp);//应用介绍
		tbApplicatonInfo.setTaiAddDate(new Timestamp(new Date().getTime()));//创建时间
		tbApplicatonInfo.setTaiStatus(1);//状态
		tbApplicatonInfo.setTaiPageCount(0L);//浏览量  //把String类型转换为Long类型
		tbApplicatonInfoService.update(tbApplicatonInfo);
		return "100";
		
	}
	
	
	
	
	/**
	 * 删除
	 *  */
	@ResponseBody
	@RequestMapping(value = "/delete")
	@Transactional
	public String delete(HttpServletRequest request) {
		String taiId=request.getParameter("taiId");//应用信息表Id
		TbApplicatonInfo  tbApplicatonInfo =tbApplicatonInfoService.findById(Long.valueOf(taiId));
		tbApplicatonInfo.setTaiStatus(0);
		tbApplicatonInfoService.update(tbApplicatonInfo);
		return "100";
   }
	
	
}


     




