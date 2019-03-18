package com.spring.common.controller;

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
import com.spring.common.entity.TbArticle;
import com.spring.common.entity.TbColumn;
import com.spring.common.entity.Userinfo;
import com.spring.common.service.TbArticleService;
import com.spring.common.service.TbColumnService;
import com.spring.common.service.UserinfoService;

@Controller
@RequestMapping(value = "/admin/article")
public class ArticleController extends BaseController {
	@Resource
	TbArticleService tbArticleService;

	@Resource
	UserinfoService userinfoService;

	@Resource
	TbColumnService tbColumnService;
	
	@RequestMapping("/init_page")
	public String initPage(Model model) {
		return "/article/article";
	}
	
	@RequestMapping(value="/index/{paper}")
	public String view(HttpServletRequest request,@PathVariable String paper,
			@RequestParam(value = "tsId",required = false)String tsId){
		if("list".equals(paper)){
			System.out.println("进入毕业生管理界面=====>");
			return "article/article_list";
		}
		else if("add".equals(paper)){
			
			return "article/add_article";
			
		}else if("edit".equals(paper)){
			
			return "article/update_article";
			
		}
		
		return "graduated/"+paper;
	}
	
	@ResponseBody
	@RequestMapping(value = "/list")
	public HashMap<String,Object> list(HttpServletRequest request){
		System.out.println("graduated/list");
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("taTitle", request.getParameter("taTitle"));
		params.put("taColumnId", request.getParameter("taColumnId"));
		params.put("page", request.getParameter("page"));
		params.put("pageSize", request.getParameter("rows"));
		params.put("addDate", request.getParameter("addDate"));
		params.put("endDate", request.getParameter("endDate"));
		params.put("sort", request.getParameter("sort"));
		params.put("order", request.getParameter("order"));
		return tbArticleService.getList(params);
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
	   String baseUrl = "http://www.zjqtwl.com/qtwl-gm/";//上线后更改地址
		
       if(!file.getContentType().contains("image")){
           return "{\"error\":1,\"message\":\"plase select imageFile~\"}";
       }
       //String url=PropertiesUtil.getProperty("context_url", "config")+"/upload/img/";
       String url=baseUrl+"/upload/article/";
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
	public String listJoson(Model model, BaseQueryParam<TbArticle> pageEntity, String selColumn) {
		String lastSql = "";
		if (StringUtils.isNotBlank(pageEntity.getKeywords())) {
			lastSql += " and ta_title like '%" + pageEntity.getKeywords() + "%'";
		}

		if (StringUtils.isNotBlank(pageEntity.getStartDate())) {
			lastSql += " and ta_addtime > '" + pageEntity.getStartDate() + "'";
		}

		if (StringUtils.isNotBlank(pageEntity.getEndDate())) {
			lastSql += " and ta_addtime < '" + pageEntity.getEndDate() + "'";
		}
		if (StringUtils.isNotBlank(selColumn)) {
			lastSql += " and ta_column_id = '" + selColumn + "'";
		}

		lastSql += " and ta_status != '0' ";
		lastSql += " ORDER BY ta_addtime DESC ";
		PageBean<TbArticle> pages = tbArticleService.findPage(lastSql, pageEntity);
		for (TbArticle tbArticle : pages.getList()) {
			Userinfo user = userinfoService.findById(Long.parseLong(tbArticle.getTaAdduser()));
			if (user != null) {
				tbArticle.setAddUserName(user.getName());
			} else {
				tbArticle.setAddUserName("无");
			}

			if (tbArticle.getTaColumnId() != null) {
				TbColumn tc = tbColumnService.findById(tbArticle.getTaColumnId().longValue());
				tbArticle.setColumnName(tc.getTcName());
			}

		}
		return pageEntity.resultJson(pages);

	}

	/**
	 * 设置头条
	 * @param model
	 * @param pageEntity
	 * @param quereyTitle
	 * @param tsSex
	 * @return
	 */
	@RequestMapping("/update_set")
	@ResponseBody
	public String updateSet(Model model, String id, String taHeadline, String taTop) {
		TbArticle tbArticle = tbArticleService.findById(Long.parseLong(id));
		String result;
		if (tbArticle != null) {
			if (StringUtils.isNotBlank(taHeadline)) {
				if (tbArticle.getTaHeadline() == 0) {
					tbArticle.setTaHeadline(1);
				} else {
					tbArticle.setTaHeadline(0);
				}

			}

			if (StringUtils.isNotBlank(taTop)) {

				if (tbArticle.getTaTop() == 0) {
					tbArticle.setTaTop(1);
				} else {
					tbArticle.setTaTop(0);
				}
				tbArticle.setTaTopDate(new Date());
			}

			try {
				tbArticleService.update(tbArticle);
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

	/** 保存新增 **/
	@ResponseBody
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request,TbArticle article) {
		Userinfo userInfo = (Userinfo) request.getSession().getAttribute(GlobalStatic.DEFAULT_LOGIN_SESSION_NAME);
		String result;
		System.out.println(" path---->:" + article.getTaPicture() );

		if (article != null) {
			try {
				
				if (article.getTaTop() == null) {
					article.setTaTop(0);
				}else if(article.getTaTop() == 1){
					article.setTaTopDate(new Date());
				}

				if (article.getTaHeadline() == null) {
					article.setTaHeadline(0);
				}
				
				String strAddDate = article.getTaAddtime();
				
			
				article.setTaAddtime(strAddDate);
				article.setTaAdduser(userInfo.getId().toString());
				article.setTaStatus(1);
				article.setTaCount(0);
				if(article.getTaId()== null || article.getTaId()==0)
					tbArticleService.save(article);
				else
					tbArticleService.update(article);
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
	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request,TbArticle article) {
		
		Userinfo userInfo = (Userinfo) request.getSession().getAttribute(GlobalStatic.DEFAULT_LOGIN_SESSION_NAME);
		
		if(userInfo == null )
			return "/login";
		
		TbArticle  tbArticle = tbArticleService.findById(Long.parseLong(article.getTaId() +""));
		
		if(tbArticle==null)
			return "101";
		
		
		tbArticle.setTaTitle(article.getTaTitle());
		tbArticle.setTaColumnId(article.getTaColumnId());
		tbArticle.setTaAddtime(article.getTaAddtime());
		tbArticle.setTaPicture(article.getTaPicture());
		
		tbArticle.setTaContent(article.getTaContent());
		
		if (article.getTaHeadline() == null)
			article.setTaHeadline(0);
		else
			tbArticle.setTaHeadline(article.getTaHeadline());
		if (article.getTaTop() == null) 
			article.setTaTop(0);
		else
			tbArticle.setTaTop(article.getTaTop());

		tbArticleService.update(tbArticle);
		return "100";
	
	}
	

	/** 修改初始化 **/
	@RequestMapping(value = "/initUpdate")
	public String initUpdate(HttpServletRequest request, Model model, String id) {
		TbArticle tbArticle = null;
		if (id != null) {
			try {
				tbArticle = tbArticleService.findById(Long.parseLong(id));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(tbArticle.getTaColumnId()!=null){
			TbColumn tbColumn = tbColumnService.findById(tbArticle.getTaColumnId().longValue());
			model.addAttribute("tbColumn",tbColumn);
		}else{
			model.addAttribute("tbColumn",new HashMap<String,Object>());
		}
		model.addAttribute("tbArticle", tbArticle);
		return "/article/edit_article";
	}
	
	
	/** 文章详情 **/
	@RequestMapping(value = "/detail")
	public String detail(HttpServletRequest request, Model model, String id) {
		TbArticle tbArticle = null;
		if (id != null) {
			try {
				tbArticle = tbArticleService.findById(Long.parseLong(id));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(tbArticle.getTaColumnId()!=null){
			TbColumn tbColumn = tbColumnService.findById(tbArticle.getTaColumnId().longValue());
			model.addAttribute("tbColumn",tbColumn);
		}else{
			model.addAttribute("tbColumn",new HashMap<String,Object>());
		}
		model.addAttribute("tbArticle", tbArticle);
		return "/article/detail_article";
	}

	/** 修改 **/
	@ResponseBody
	@RequestMapping(value = "/update/{id}")
	public String update(HttpServletRequest request, @PathVariable String id, TbArticle article, @RequestParam(value = "fileUrl", required = false) MultipartFile fileUrl) {
		String result;
		if (article != null) {
			try {
				TbArticle newtbArticle = tbArticleService.findById(Long.parseLong(id));
//				String path = System.getProperty("user.dir") + "/WebContent/upload/";
				String path = request.getRealPath("/upload/");
				System.out.println("path:"+path);
				if (StringUtils.isNotBlank(article.getTaTitle())) {
					newtbArticle.setTaTitle(article.getTaTitle());
				}

				if (StringUtils.isNotBlank(article.getTaContent())) {
					newtbArticle.setTaContent(article.getTaContent());
				}

				if (StringUtils.isNotBlank(fileUrl.getOriginalFilename())) {
					String fileName = this.fileUpload(fileUrl, path);
					newtbArticle.setTaPicture(fileName);
				}

				if (article.getTaColumnId() != null) {
					newtbArticle.setTaColumnId(article.getTaColumnId());
				}
				if (article.getTaTop() == null) {
					newtbArticle.setTaTop(0);
				} else {
					System.out.println("taTop:"+article.getTaTop());
					newtbArticle.setTaTop(1);
					newtbArticle.setTaTopDate(new Date());
				}

				if (article.getTaHeadline() == null) {
					newtbArticle.setTaHeadline(0);
				} else {
					newtbArticle.setTaHeadline(1);
				}

				tbArticleService.update(newtbArticle);
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
				TbArticle tbArticle = tbArticleService.findById(Long.parseLong(id));
				tbArticle.setTaStatus(0);
				tbArticleService.update(tbArticle);
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
			return "article/add_article";
		} else if ("edit".equals(paper)) {// 跳转到编辑页面
			
			return "article/edit_article";
		}

		return "article/" + paper;
	}
}