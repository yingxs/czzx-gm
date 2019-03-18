package com.spring.common.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.base.controller.BaseController;
import com.spring.base.shiro.ShiroUser;
import com.spring.common.entity.TbInformation;
import com.spring.common.entity.TbInformationCata;
import com.spring.common.service.TbInformationCataService;
import com.spring.common.service.TbInformationService;


@Controller
@RequestMapping(value="/tbInformation")
public class TbInformationController extends BaseController{

	
	@Resource TbInformationService tbInformationService;
	@Resource TbInformationCataService tbInformationCataService;
	
	@RequestMapping(value="/index/{paper}")
	public String view(HttpServletRequest request, @PathVariable String paper) {
		if (StringUtils.isBlank(paper)) {
			return "";
		}
		 
		if ("list".equals(paper)) {
			return "baseData/tbInformation_list";
		}
		
		if ("add".equals(paper)) {
			return "baseData/tbInformation_add";
		}
		
		
		return "baseData/"+paper;
	}  
	
	
	/**
	 * 内容修改页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update")
	public String update(HttpServletRequest request) {
		TbInformation tbInformation = tbInformationService.findById(Long.valueOf(request.getParameter("id")));
		request.setAttribute("tbInformation", tbInformation);
		return "baseData/tbInformation_update";
	}
	
	
	/**
	 * 删除, 实际为修改状态
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delete")
	public String delete(HttpServletRequest request) {
		TbInformation tbInformation = tbInformationService.findById(Long.valueOf(request.getParameter("id")));
		tbInformation.setTiStatus(0);
		try {
			tbInformationService.update(tbInformation);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			return "101";
		}
	}
	
	/**
	 * 内容详情页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/dials")
	public String dials(HttpServletRequest request) {
		TbInformation tbInformation = tbInformationService.findById(Long.valueOf(request.getParameter("id")));
		TbInformationCata tbInformationCata = tbInformationCataService.findById(tbInformation.getTiCataId());
		
		request.setAttribute("tbInformationCata", tbInformationCata);
		request.setAttribute("tbInformation", tbInformation);
		return "baseData/tbInformation_detail";
	}
	
	/**
	 * 新增内容
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public String add(HttpServletRequest request,TbInformation tbInformation,HttpServletResponse response) {
		
		
		String strContent = request.getParameter("tiContent");
		
		tbInformation.setTiContent(strContent);
		
		//如果 id 为null 则进行添加
		if(tbInformation.getTiId() == null || tbInformation.getTiId().equals("")){ 
			TbInformation tbInformationOne = new TbInformation();
			tbInformationOne.setTiCataId(tbInformation.getTiCataId());
			tbInformationOne.setTiTitle(tbInformation.getTiTitle());
			tbInformationOne.setTiStatus(1);
			tbInformationOne = tbInformationService.findOne(tbInformationOne);
			if(tbInformationOne!=null){
				return "105";//文章已存在
			}
			
			tbInformation.setTiAddDate(new Timestamp(new Date().getTime()));//添加时间
			tbInformation.setTiStatus(1);//初始状态为1
			ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();//当前登录人
			tbInformation.setTiAddPreson(loginUser.getId());
		} else {
			TbInformation findTbInformation = tbInformationService.findById(tbInformation.getTiId());
			tbInformation.setTiAddDate(findTbInformation.getTiAddDate());//添加时间
			tbInformation.setTiStatus(findTbInformation.getTiStatus());//初始状态为1
			tbInformation.setTiAddPreson(findTbInformation.getTiAddPreson());
		}	
		
		try { 
			if(tbInformation.getTiId() == null || tbInformation.getTiId().equals("")){
				tbInformationService.save(tbInformation);
				
			} else {
				tbInformationService.update(tbInformation);
			}
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			return "101";
		}
		
	}
	
	
	/**内容
	 * @param request
	 * @return 
	 */ 
	@ResponseBody
	@RequestMapping(value="/list")
	public HashMap<String, Object> list(HttpServletRequest request) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("page", request.getParameter("page"));
		params.put("pageSize", request.getParameter("rows"));
		
		params.put("tiTitle", request.getParameter("tiTitle"));//标题
		params.put("ticName", request.getParameter("ticName"));//分类名称
		params.put("tiAddDate", request.getParameter("tiAddDate"));//时间段=---开始
		params.put("tiAddDateEnd", request.getParameter("tiAddDateEnd"));//时间段=---结束
		
		
		params.put("sort", request.getParameter("sort"));
		params.put("order", request.getParameter("order"));
		
		return tbInformationService.findByParam(params);
	}
	/**
	 * ajax 返回所有分类
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findByTbInformationCata")
	public List<TbInformationCata> findByTbInformationCata(HttpServletRequest request) {
		
		return tbInformationCataService.findAll();
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
       String url=baseUrl+"/czzx-gm/upload/information/";
       //String realPath = PropertiesUtil.getProperty("upload_path", "config") + "/upload/img";
       String realPath = req.getRealPath("/upload/information/");
       
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
	
}
