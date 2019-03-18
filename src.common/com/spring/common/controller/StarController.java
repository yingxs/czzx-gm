package com.spring.common.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.base.controller.BaseController;
import com.spring.base.utils.GlobalStatic;
import com.spring.common.basequery.BaseQueryParam;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbStar;
import com.spring.common.entity.TbStudent;
import com.spring.common.entity.Userinfo;
import com.spring.common.service.TbStarService;
import com.spring.common.service.TbStudentService;
import com.spring.common.service.TeacherService;

@Controller
@RequestMapping(value = "/admin/star")
public class StarController extends BaseController {
	@Resource
	TbStarService tbStarService;
	@Resource
	TbStudentService tbStudentService;

	@Resource
	TeacherService teacherService;

	@RequestMapping("/init_page")
	public String initPage(Model model) {
		return "/star/list";
	}

	/**
	 * 
	 * @param model
	 * @param pageEntity
	 * @param quereyTitle
	 * @param tsSex
	 * @return
	 */
	@RequestMapping("/list_json")
	@ResponseBody
	public String listJoson(Model model, BaseQueryParam<TbStar> pageEntity, String tsType) {
		String lastSql = "";

		if (StringUtils.isNotBlank(pageEntity.getKeywords())) {
			lastSql += " and ts_user_name like '%" + pageEntity.getKeywords() + "%'";
		}
		if (StringUtils.isNotBlank(pageEntity.getStartDate())) {
			lastSql += " and ts_addtime > '" + pageEntity.getStartDate() + "'";
		}

		if (StringUtils.isNotBlank(pageEntity.getEndDate())) {
			lastSql += " and ts_addtime < '" + pageEntity.getEndDate() + "'";
		}

		if (StringUtils.isNotBlank(tsType)) {
			lastSql += " and ts_type = " + tsType;
		}

		lastSql += " and ts_status != '0' order by  ts_addtime desc";
		PageBean<TbStar> pages = tbStarService.findPage(lastSql, pageEntity);
		return pageEntity.resultJson(pages);

	}

	/**
	 * 
	* @Title: imageUpload 
	* @Description: KindEditor圖片上傳 
	* @param @param req
	* @param @param file
	* @param @return
	* @param @throws IOException    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/imageUpload",method=RequestMethod.POST)
	@ResponseBody
    public String imageUpload(HttpServletRequest req,@RequestParam MultipartFile file) throws IOException{
      //
	   String baseUrl = "http://218.75.33.77";//上线后更改地址
	
       if(!file.getContentType().contains("image")){
           return "{\"error\":1,\"message\":\"plase select imageFile~\"}";
       }
       //String url=PropertiesUtil.getProperty("context_url", "config")+"/upload/img/";
       String url=baseUrl+"/czzx-gm/upload/article/";
       //String realPath = PropertiesUtil.getProperty("upload_path", "config") + "/upload/img";
       String realPath = req.getRealPath("/upload/article/");
       
        //如果文件夹不存在就新建一个文件夹
		File dirPath = new File(realPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
       
       System.out.println("url:"+url);
       System.out.println("realPath:"+realPath);
       // 获取文件名
       String originalFilename = file.getOriginalFilename();
       // 文件名后缀处理---start---
       String suffix = originalFilename.substring(
    		   originalFilename.lastIndexOf("."),
    		   originalFilename.length());
       
       SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
       String fileName = df.format(new Timestamp(System.currentTimeMillis())) + "_"
				+ new Random().nextInt(5) + suffix;
       
       url+=fileName;
       
       File downfile = new File(realPath,fileName);
       if(downfile.exists()){
           return "{\"error\":1,\"message\":\"pricture exists~\"}";
       }
       FileUtils.copyInputStreamToFile(file.getInputStream(), downfile);
       return "{\"error\":0,\"url\":\""+url+"\"}";
    }
	
	/** 保存新增 **/
	@ResponseBody
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, Model model, TbStar tbStar, @RequestParam(value = "fileUrl", required = false) MultipartFile fileUrl,@RequestParam String userName) {
		String result;
//		String path = GlobalStatic.serverPath;
		String path = request.getRealPath("/upload/");//2016-09-30
		if (tbStar != null) {
			if (StringUtils.isNotBlank(fileUrl.getOriginalFilename())) {
				String fileName = this.fileUpload(fileUrl, path);
				tbStar.setTsPhoto(fileName);
			}
			if (StringUtils.isNotBlank(userName)) {
				tbStar.setTsUserName(userName);
			}
			tbStar.setTsAddTime(new Date());
			tbStar.setTsStatus(1);
			tbStarService.save(tbStar);
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
	
	/** 保存新增 **/
	@ResponseBody
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request, TbStar tbStar) {
		Userinfo userInfo = (Userinfo) request.getSession().getAttribute(GlobalStatic.DEFAULT_LOGIN_SESSION_NAME);
		String userId = request.getParameter("userId");
		String userName = "";
		if(userId != null){
			int type = tbStar.getTsType();
			if(type == 1){//学生
				userName = tbStudentService.findById(Long.valueOf(userId)).getTsName();
			}else if(type == 2){//老师
				userName = teacherService.findById(Integer.parseInt(userId)).getName();
			}
		}
		if (tbStar != null) {
			tbStar.setTsUserName(userName);
			tbStar.setTsAddTime(new Date());
			tbStar.setTsStatus(1);
			tbStar.setTsAdduser(userInfo.getId());
			try {
				if(tbStar.getTsId() == null || tbStar.getTsId() == 0){
					tbStarService.save(tbStar);
				}else{
					tbStarService.update(tbStar);
				}
				return "1";
			} catch (Exception e) {
				e.printStackTrace();
				return "0";
			}
		} else {
			return "0";
		}

	}
	
	@ResponseBody
	@RequestMapping(value = "/getTypeAll")
	public List getTypeAll(HttpServletRequest request){
		String type = request.getParameter("type");
		if(type != null && type.trim().length() != 0){
			if(Integer.parseInt(type) == 1){//学生
				return tbStarService.getStudents();
			}else{
				return tbStarService.getTeachers();
			}
		}else{
			return null;
		}
	}

	/** 根据Id删除 **/
	@ResponseBody
	@RequestMapping(value = "/del")
	public String del(HttpServletRequest request, Model model, String id) {
		String result;
		if (id != null) {
			try {
				// teacherService.del(Integer.parseInt(id));
				TbStar tbStar = tbStarService.findById(Long.parseLong(id));
				tbStar.setTsStatus(0);
				tbStarService.update(tbStar);
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

	/** 修改初始化 **/
	@RequestMapping(value = "/initUpdate/{id}")
	public String initUpdate(HttpServletRequest request, Model model, @PathVariable String id) {
		TbStar tbStar = null;
		if (id != null) {
			try {
				tbStar = tbStarService.findById(Long.parseLong(id));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		model.addAttribute("tbStar", tbStar);
		if(tbStar.getTsType()==1){
			model.addAttribute("typeName", "学生");
		}else{
			model.addAttribute("typeName", "老师");
		}
		
		return "/star/update_star";
	}

	/** 修改 **/
	@ResponseBody
	@RequestMapping(value = "/update/{id}")
	public String update(HttpServletRequest request, @PathVariable String id, TbStar tbStar) {
		String result;
		String userId = request.getParameter("userId");
		String userName = "";
		if(userId != null){
			int type = tbStar.getTsType();
			if(type == 1){//学生
				userName = tbStudentService.findById(Long.valueOf(userId)).getTsName();
			}else if(type == 2){//老师
				userName = teacherService.findById(Integer.parseInt(userId)).getName();
			}
		}
		System.out.println("userName:"+userName);
		if (tbStar != null) {
			try {
				TbStar aa = tbStarService.findById(Long.parseLong(id));
				aa.setTsPhoto(tbStar.getTsPhoto());
				aa.setTsType(tbStar.getTsType());
				aa.setTsUserName(userName);
				aa.setTsDesc(tbStar.getTsDesc());
				aa.setTsContent(tbStar.getTsContent());
				tbStarService.update(aa);
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
			return "star/add_star";
		} else if ("edit".equals(paper)) {// 跳转到编辑页面
			
			return "star/edit_star";
		}

		return "star/" + paper;
	}
}