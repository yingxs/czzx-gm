/**    
* @{#} TbInformationCataController.java Create on 2015年8月26日 下午4:11:42    
* Copyright (c) 2015.    
*/
package com.spring.common.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.base.controller.BaseController;
import com.spring.common.entity.TbVersion;
import com.spring.common.service.TbVersionService;

/**
 * @author <a href="mailto:liwei.fly@gmail.com">author</a>
 * @version 1.0
 * @description
 */
@Controller
@RequestMapping(value = "/tbversion")
public class TbVersionController extends BaseController {

	@Resource
	TbVersionService tbVersionService;
	
	@Value("#{config['imgPath']}")
	public String imgPath="";

//	static {
//		PREFIX_MEET = "juzi";
//	}

	@RequestMapping(value = "/index/{paper}")
	public String view(HttpServletRequest request, @PathVariable String paper,
			@RequestParam(value = "tvId", required = false) String tvId) {

		if (StringUtils.isBlank(paper)) {
			return "";
		}
		if ("list".equals(paper)) {
			long ran = new Date().getTime();
			request.setAttribute("ran", ran);
			return "version/tbversion";
		}
		if ("add".equals(paper)) {
			long ran = new Date().getTime();
			request.setAttribute("ran", ran);
			return "version/add_version";
		} else if ("edit".equals(paper)) {
			TbVersion tbVersion = tbVersionService.findById(Integer.valueOf(tvId));
			request.setAttribute("obj", tbVersion);
			return "version/edit_version";
		}
		return "version/" + paper;
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public HashMap<String, Object> list(HttpServletRequest request) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("page", request.getParameter("page"));
		params.put("pageSize", request.getParameter("rows"));
		params.put("sort", request.getParameter("sort"));
		params.put("order", request.getParameter("order"));
		String tvName = request.getParameter("tvName");
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");
		params.put("tvName", tvName);
		params.put("start_time", start_time);
		params.put("end_time", end_time);
		return tbVersionService.findForJson(params);
	}

	@ResponseBody
	@RequestMapping(value = "/change")
	public String change(HttpServletRequest request, @RequestParam(value = "tvId", required = false) String tvId) {
		TbVersion tbVersion = tbVersionService.findById(Integer.valueOf(tvId));
		String status = request.getParameter("status");
		tbVersion.setTvStatus(Integer.valueOf(status));
		tbVersionService.update(tbVersion);
		return "0";
	}

	@ResponseBody
	@RequestMapping(value = "/add")
	public String add(TbVersion tbVersion) {
//		Long id = tbVersionService.getIdByName(tbVersion.getTvType());
//		if (id == 0) {
//			tbVersion.setTvAddDate(new Timestamp(System.currentTimeMillis()));
//			tbVersion.setTvStatus(1);
//			tbVersionService.save(tbVersion);
//		} else {
//			tbVersion.setTvId(id);
//			tbVersion.setTvStatus(1);
//			tbVersion.setTvAddDate(new Timestamp(System.currentTimeMillis()));
//			tbVersionService.update(tbVersion);
//		}
		tbVersion.setTvAddDate(new Timestamp(new Date().getTime()));
		tbVersion.setTvStatus(1);
		tbVersionService.save(tbVersion);
		return "0";
	}

	@ResponseBody
	@RequestMapping(value = "/update")
	public String update(TbVersion tbVersion) {
		tbVersion.setTvAddDate(new Timestamp(System.currentTimeMillis()));
		tbVersionService.update(tbVersion);
		return "0";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/upload")
	@ResponseBody
	public String imageUpload(HttpServletRequest request, @RequestParam("file_upload") MultipartFile file_upload) {

	//	String uploadDir = PropertiesUtil.getProperty("upload_path", "config") + "/appver/";
		String uploadDir = request.getRealPath("/upload/update/appver/");
		
		System.out.println("uploadDir:" + uploadDir);
		// String uploadDir = request.getRealPath("/PocketMoney/upload/ad/");
		File dirPath = new File(uploadDir);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
		// 获取从前台传来的文件名
		String oriName = file_upload.getOriginalFilename();
		// 文件名后缀处理---start---
		String suffix = oriName.substring(oriName.lastIndexOf("."), oriName.length());
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Timestamp(System.currentTimeMillis())) + "_" + new Random().nextInt(5)
				+ suffix;
		File file = new File(uploadDir + "/" + newFileName);
		BufferedImage bi;
		int srcWidth = 0, srcHeight = 0;

		try {
			// addfileimage.transferTo(new File(uploadDir + "/" + newFileName));
			FileUtils.copyInputStreamToFile(file_upload.getInputStream(), file);
			System.out.println("路径:" + uploadDir + "/" + newFileName);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringBuffer json = new StringBuffer("{\"path\":\"" + imgPath + "/upload/update/appver/");
		json.append(newFileName + "\",").append("\"filename\":").append("\"" + oriName + "\"}");
		System.err.println(json.toString());
		return json.toString();
	}
}
