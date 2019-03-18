package com.spring.common.controller;

import java.sql.Timestamp;

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
import com.spring.common.entity.Notice;
import com.spring.common.entity.Teacher;
import com.spring.common.service.NoticeService;
import com.spring.common.service.TeacherService;

@RequestMapping("/admin/notice")
@Controller
public class NoticeController extends BaseController {

	@Resource
	NoticeService noticeService;

	@Resource
	TeacherService teacherService;

	@RequestMapping("/init_page")
	public String initPage(Model model) {
		return "/notice/index";
	}

	@ResponseBody
	@RequestMapping("/list_json")
	public String listJson(HttpServletRequest request, Model model, BaseQueryParam<Notice> pageEntity, String depaName) {
		String lastSql = "";

		if (StringUtils.isNotBlank(pageEntity.getKeywords())) {
			lastSql += " and tni_title like '%" + pageEntity.getKeywords() + "%'";
		}

		if (StringUtils.isNotBlank(pageEntity.getStartDate())) {
			lastSql += " and tni_add_date >= '" + pageEntity.getStartDate() + "'";
		}

		if (StringUtils.isNotBlank(pageEntity.getEndDate())) {
			lastSql += " and tni_add_date <= '" + pageEntity.getEndDate() + "'";
		}

		if (StringUtils.isNotBlank(depaName)) {
			lastSql += " and tni_recv_type = '" + depaName + "'";
		}

		lastSql += " and tni_status = 1 order by tni_add_date desc";

		return pageEntity.resultJson(noticeService.findPage(lastSql, pageEntity));
	}

	@ResponseBody
	@RequestMapping(value = "/th_list_json")
	public String listJsonTh(HttpServletRequest request, Model model, BaseQueryParam<Teacher> pageEntity, @RequestParam(required = false) String logUse) {
		String lastSql = "";

		if (StringUtils.isNotBlank(pageEntity.getKeywords())) {
			lastSql += " and tt_name like '%" + pageEntity.getKeywords() + "%'";
		}

		if (StringUtils.isNotBlank(logUse)) {
			lastSql += " and tt_login_user = '" + logUse + "'";
		}

		lastSql += " and tt_status !=0 order by tt_addtime desc";

		return pageEntity.resultJson(teacherService.findPage(lastSql, pageEntity));
	}

	/** 保存新增 **/
	@ResponseBody
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request, Model model, Notice notice, @RequestParam(value = "fileUrl", required = false) MultipartFile fileUrl, @RequestParam(required = false) String[] ids) {
		String result;
		

		if (notice != null) {
			if (StringUtils.isNotBlank(fileUrl.getOriginalFilename())) {
				System.out.println("GlobalStatic.serverPath->"+GlobalStatic.serverPath);
				String fileName = this.fileUpload(fileUrl, GlobalStatic.serverPath);
				notice.setTniLogo(fileName);
			}
			notice.setTniAddDate(new Timestamp(System.currentTimeMillis()));
			notice.setTniStatus(1);
			String thIds = "";
			for (String string : ids) {
				thIds += string + ",";
			}
			notice.setTniRecvPersons(thIds);
			noticeService.save(notice);
			try {
				result = "1";
			} catch (Exception e) {
				e.printStackTrace();
				result = "0";
			}
		} else {
			result = "0";
		}

		return result;
	}

	/** 保存新增 **/
	@ResponseBody
	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request, Model model, Notice notice, @RequestParam(value = "fileUrl", required = false) MultipartFile fileUrl, @RequestParam(required = false) String[] ids) {
		String result;
		if (notice != null) {
			if (StringUtils.isNotBlank(fileUrl.getOriginalFilename())) {
				System.out.println("GlobalStatic.serverPath->"+GlobalStatic.serverPath);
				String fileName = this.fileUpload(fileUrl, GlobalStatic.serverPath);
				System.out.println("fileName->"+fileName);
				notice.setTniLogo(fileName);
			}
			notice.setTniAddDate(new Timestamp(System.currentTimeMillis()));
			notice.setTniStatus(1);
			String thIds = "";
			for (String string : ids) {
				thIds += string + ",";
			}
			notice.setTniRecvPersons(thIds);
			noticeService.update(notice);
			try {
				result = "1";
			} catch (Exception e) {
				e.printStackTrace();
				result = "0";
			}
		} else {
			result = "0";
		}

		return result;
	}

	/** 修改初始化 **/
	@RequestMapping(value = "/initUpdate/{id}")
	public String initUpdate(HttpServletRequest request, Model model, @PathVariable String id) {
		Notice notice = null;
		if (id != null) {
			try {
				notice = noticeService.findById(Long.parseLong(id));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		model.addAttribute("notice", notice);
		return "/notice/uodate_index";
	}

	/** 根据Id删除 **/
	@ResponseBody
	@RequestMapping(value = "/del")
	public String del(HttpServletRequest request, Model model, @RequestParam String id) {
		String result;
		if (id != null) {
			try {
				Notice nn = new Notice();
				nn.setTniId(Long.parseLong(id));
				nn.setTniStatus(0);
				noticeService.update(nn);
			} catch (Exception e) {
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
			return "notice/add_notice";
		} else if ("edit".equals(paper)) {// 跳转到编辑页面
			
			return "notice/edit_notice";
		}

		return "notice/" + paper;
	}

}
