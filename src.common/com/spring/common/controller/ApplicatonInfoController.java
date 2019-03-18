package com.spring.common.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.base.controller.BaseController;
import com.spring.base.utils.GlobalStatic;
import com.spring.common.basequery.BaseQueryParam;
import com.spring.common.entity.ApplicatonInfo;
import com.spring.common.service.ApplicatonInfoService;

@Controller
@RequestMapping(value = "/admin/applicaton")
public class ApplicatonInfoController extends BaseController {

	@Resource
	ApplicatonInfoService applicatonInfoService;


	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, Model model) {
		return "app/appManage";
	}

	@ResponseBody
	@RequestMapping(value = "/list_json")
	public String listJson(HttpServletRequest request, Model model, BaseQueryParam<ApplicatonInfo> pageEntity) {
		String lastSql = "";

		if (StringUtils.isNotBlank(pageEntity.getKeywords())) {
			lastSql += " and tai_name like '%" + pageEntity.getKeywords() + "%'";
		}
		if (StringUtils.isNotBlank(pageEntity.getStartDate())) {
			lastSql += " and tai_add_date > '" + pageEntity.getStartDate() + "'";
		}

		if (StringUtils.isNotBlank(pageEntity.getEndDate())) {
			lastSql += " and tai_add_date < '" + pageEntity.getEndDate() + "'";
		}

		lastSql += " and tai_status !=0 order by tai_add_date desc";

		return pageEntity.resultJson(applicatonInfoService.findPage(lastSql, pageEntity));
	}

	/** 保存新增 **/
	@ResponseBody
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request, Model model, ApplicatonInfo applicatonInfo, @RequestParam(value = "fileUrl", required = false) MultipartFile fileUrl) {
		String result;
//		String path = GlobalStatic.serverPath;
		String path = request.getRealPath("/upload/");//2016-09-30
		System.err.println("path:"+path);
		if (applicatonInfo != null) {
			applicatonInfo.setTaiStatus(1);
			applicatonInfo.setTaiAddDate(new Date());
			applicatonInfo.setTaiAddCount(0);
			if (StringUtils.isNotBlank(fileUrl.getOriginalFilename())) {
				String fileName = this.fileUpload(fileUrl, path);
				applicatonInfo.setTaiIcon(fileName);
			}
			applicatonInfoService.save(applicatonInfo);
			try {

				result = "1";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = "0";
			}
		} else {
			result = "0";
		}

		return result;
	}
	/** 修改初始化 **/
	@RequestMapping(value = "/initUpdate")
	public String initUpdate(HttpServletRequest request, Model model, String id) {
		ApplicatonInfo ApplicatonInfo = null;
		if (id != null) {
			try {
				ApplicatonInfo = applicatonInfoService.findById(Long.parseLong(id));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		model.addAttribute("applicatonInfo", ApplicatonInfo);
		return "/app/update_app";
	}
	
	/** 查看下载初始化 **/
	@RequestMapping(value = "/detail")
	public String detail(HttpServletRequest request, Model model, String id) {
		ApplicatonInfo ApplicatonInfo = null;
		if (id != null) {
			try {
				ApplicatonInfo = applicatonInfoService.findById(Long.parseLong(id));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		model.addAttribute("applicatonInfo", ApplicatonInfo);
		return "/app/detail_app";
	}

	/** 修改 **/
	@ResponseBody
	@RequestMapping(value = "/update/{id}")
	public String update(HttpServletRequest request, @PathVariable String id, ApplicatonInfo applicatonInfo, @RequestParam(value = "fileUrl", required = false) MultipartFile fileUrl) {
		String result;
		if (applicatonInfo != null) {
			try {
//				String path = GlobalStatic.serverPath;
				String path = request.getRealPath("/upload/");//2016-09-30
				ApplicatonInfo aa = applicatonInfoService.findById(Long.parseLong(id));
				
				if (StringUtils.isNotBlank(fileUrl.getOriginalFilename())) {
					String fileName = this.fileUpload(fileUrl, path);
					aa.setTaiIcon(fileName);
				}
				aa.setTaiName(applicatonInfo.getTaiName());
				aa.setTaiWebAddr(applicatonInfo.getTaiWebAddr());
				aa.setTaContent(applicatonInfo.getTaContent());
				applicatonInfoService.update(aa);
				result = "1";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = "0";
			}
		} else {
			result = "0";
		}

		return result;
	}
	/** 根据Id删除 **/
	@ResponseBody
	@RequestMapping(value = "/del")
	public String del(HttpServletRequest request, Model model, String id) {
		String result;
		if (id != null) {
			try {
				// teacherService.del(Integer.parseInt(id));
				ApplicatonInfo applicatonInfo = applicatonInfoService.findById(Long.parseLong(id));
				applicatonInfo.setTaiStatus(0);
				applicatonInfoService.update(applicatonInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result = "1";
		} else {
			result = "0";
		}

		return result;
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
			@RequestParam(value = "id", required = false) Long id) {
		if (StringUtils.isBlank(paper)) {
			return "";
		}

		if ("add".equals(paper)) {// 跳转到添加页面
			return "app/add_app";
		} else if ("edit".equals(paper)) {// 跳转到编辑页面
			
			return "app/edit_app";
		}else if("detail".equals(paper)){//跳转到查看下载
			
			return "app/detail_app";
		}

		return "app/" + paper;
	}
}
