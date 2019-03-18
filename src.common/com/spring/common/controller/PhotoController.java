package com.spring.common.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.spring.base.utils.StringUtil;
import com.spring.common.entity.TbColumn;
import com.spring.common.entity.TbPhoto;
import com.spring.common.entity.TbPhotoAlbum;
import com.spring.common.entity.Userinfo;
import com.spring.common.service.TbColumnService;
import com.spring.common.service.TbPhotoAlbumService;
import com.spring.common.service.TbPhotoService;

@Controller
@RequestMapping("/admin/photo")
public class PhotoController extends BaseController {

	@Resource
	TbPhotoAlbumService tbPhotoAlbumService;
	@Resource
	TbPhotoService tbPhotoService;
	@Resource
	TbColumnService tbColumnService;

	/**
	 * list 页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/init_page")
	public String initPage(Model model) {
		List<TbPhotoAlbum> tbList = tbPhotoAlbumService.getTbPhotoAlbumDao().searchp("SELECT * FROM tb_photo_album  WHERE tpa_status != 0");
		;
		for (TbPhotoAlbum tbPhotoAlbum : tbList) {
			List<TbPhoto> iList = tbPhotoService.getTbPhotoDao().searchp("SELECT * FROM tb_photo  WHERE tp_album_id = ? and tp_status != 0 ORDER BY tp_id DESC LIMIT 0,1", tbPhotoAlbum.getTpaId());
			if (!iList.isEmpty() && iList.size() != 0) {
				TbPhoto tb = iList.get(0);
				//tbPhotoAlbum.setTbPhoto(tb);
			}
		}
		model.addAttribute("tbList", tbList);
		return "/photo/index";
	}

	/**
	 * 详情
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/xq")
	public String xq(HttpServletRequest request,Model model) {
		String strAlbumId = request.getParameter("id");
		System.out.println("id:"+strAlbumId);
		List<TbPhoto> iList = tbPhotoService.findByAlbumId(strAlbumId);
		System.out.println(iList.size());
		model.addAttribute("tbList", iList);
		model.addAttribute("photoId", strAlbumId);
		return "/photo/xq";
	}

	/**
	 * 保存
	 * 
	 * @param model
	 * @param photo
	 * @return
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request,Model model, TbPhotoAlbum photo) {
		Userinfo userInfo = (Userinfo) request.getSession().getAttribute(GlobalStatic.DEFAULT_LOGIN_SESSION_NAME);
		photo.setTpaStatus(1);
		photo.setTpaAddime(new Timestamp(new Date().getTime()));
		photo.setTpaAdduser(userInfo.getId());
		tbPhotoAlbumService.save(photo);
		List<TbPhotoAlbum> tbList = tbPhotoAlbumService.getTbPhotoAlbumDao().searchp("SELECT * FROM tb_photo_album  WHERE tpa_status != 0 ");
		/*for (TbPhotoAlbum tbPhotoAlbum : tbList) {
			List<TbPhoto> iList = tbPhotoService.getTbPhotoDao().searchp("SELECT * FROM tb_photo  WHERE tp_album_id = ? and tp_status != 0 ORDER BY tp_id DESC LIMIT 0,1", tbPhotoAlbum.getTpaId());
			if (!iList.isEmpty() && iList.size() != 0) {
				TbPhoto tb = iList.get(0);
				//tbPhotoAlbum.setTbPhoto(tb);
			}
		}*/
		model.addAttribute("tbList", tbList);
		return "/photo/album_list";
	}

	/**
	 * 上传图片
	 * 
	 * @param model
	 * @param photoId
	 * @param fileUrl
	 * @param photoName
	 * @param request
	 * @param type
	 * @return
	 */
	@RequestMapping("/save_photo")
	public String savePhoto(Model model, String tpaColumnId_photo, @RequestParam(value = "fileUrl", required = false) MultipartFile[] fileUrl, String[] photoName, HttpServletRequest request, @RequestParam(required = false) String type) {
		try {
			Userinfo userInfo = (Userinfo) request.getSession().getAttribute(GlobalStatic.DEFAULT_LOGIN_SESSION_NAME);
			for (int i = 0; i < fileUrl.length; i++) {
				TbPhoto tb = new TbPhoto();
				String requestPath = request.getRealPath("/upload/");
//				String finame = this.fileUpload(fileUrl[i], GlobalStatic.serverPath);
				String finame = this.fileUpload(fileUrl[i], requestPath);
				System.out.println("finame->"+finame);
				if (StringUtils.isNotBlank(finame)) {
					tb.setTpUrl(finame);
				}
				if(photoName.length!=0){
					tb.setTpName(photoName[i]);
				}else{
					tb.setTpName("");
				}
				tb.setTpAlbumId(Long.parseLong(tpaColumnId_photo));
				tb.setTpCoverPhoto(0);
				tb.setTpStatus(1);
				tb.setTpAdduser(userInfo.getId());
				tb.setTpAddtime(new Timestamp(System.currentTimeMillis()));
				tbPhotoService.save(tb);
			}

			model.addAttribute("msg", "添加成功!");
			return returnViews(type, model, tpaColumnId_photo);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 删除
	 * 
	 * @param model
	 * @param ids
	 * @param type
	 * @param photoId
	 * @return
	 */
	@RequestMapping("/update")
	public String update( HttpServletRequest request,Model model, @RequestParam(required = false) String[] ids, @RequestParam(required = false) String type, @RequestParam(required = false) String photoId) {
		for (String id : ids) {
			if (type != null) {
				TbPhoto tb = new TbPhoto();
				tb.setTpStatus(0);
				tb.setTpId(Long.parseLong(id));
				tbPhotoService.update(tb);
			} else {
				TbPhotoAlbum tbs = new TbPhotoAlbum();
				tbs.setTpaStatus(0);
				tbs.setTpaId(Long.parseLong(id));
				tbPhotoAlbumService.update(tbs);	
			}
		}
		List<TbPhoto> iList = tbPhotoService.findByAlbumId(photoId);
		System.out.println(iList.size());
		model.addAttribute("tbList", iList);
		model.addAttribute("photoId", photoId);
		model.addAttribute("msg", "删除成功!");
		return "/photo/xq";
	}
	
	/**
	 * 设置封面图片
	 * 
	 * @param model
	 * @param ids
	 * @param photoId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/setCoverPhoto")
	public Map<String,Object> setCoverPhoto(String ids, String photoId) {
		Map<String,Object> resultMap = new HashMap<>();
		try {
			List<TbPhoto> photoList = tbPhotoService.findByAlbumId(photoId);
			if(StringUtil.isEmptyNull(ids)){
				resultMap.put("code", 101);
				resultMap.put("message", "请选中需要设置为封面的图片!");
				return resultMap;
			}
			/*if(ids.length <= 0){
				resultMap.put("code", 102);
				resultMap.put("message", "请选中需要设置为封面的图片!");
				return resultMap;
			}*/
			
			tbPhotoService.setCoverPhoto(photoId,ids);
			resultMap.put("code", 100);
			resultMap.put("message", "封面图片设置成功!");
			return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbInstituteInfo-add()]：错误原因:" + e.getMessage());
			resultMap.put("code", -101);
			resultMap.put("message", "数据库操作失败,请联系开发人员!");
			return resultMap;
		}
	}

	/**
	 * 公用
	 * 
	 * @param type
	 * @param model
	 * @param photoId
	 * @return
	 */
	public String returnViews(String type, Model model, String photoId) {
		if (type != null) {
			List<TbPhoto> iList = tbPhotoService.getTbPhotoDao().searchp("SELECT * FROM tb_photo  WHERE tp_album_id = ? and tp_status != 0 ORDER BY tp_id DESC", photoId);
			model.addAttribute("tbList", iList);
			return "/photo/xq";
		} else {
			List<TbPhotoAlbum> tbList = tbPhotoAlbumService.getTbPhotoAlbumDao().searchp("SELECT * FROM tb_photo_album  WHERE tpa_status != 0");
			for (TbPhotoAlbum tbPhotoAlbum : tbList) {
				List<TbPhoto> iList = tbPhotoService.getTbPhotoDao().searchp("SELECT * FROM tb_photo  WHERE tp_album_id = ? and tp_status != 0 ORDER BY tp_id DESC LIMIT 0,1", tbPhotoAlbum.getTpaId());
				if (!iList.isEmpty() && iList.size() != 0) {
					TbPhoto tb = iList.get(0);
					//tbPhotoAlbum.setTbPhoto(tb);
				}
			}
			model.addAttribute("tbList", tbList);
			return "/photo/album_list";
		}
	}
	
	/**
	 * 删除相册
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/removeAlbums")
	public String removeAlbums(HttpServletRequest request){
		//获取传过来的栏目编号和栏目类型
		String strChkValue = request.getParameter("chk_value");
		
		if(!StringUtils.isBlank(strChkValue)){
			String strAlbumIds[] = strChkValue.split(",");
			for (String strAlbumId : strAlbumIds) {
				List<TbPhoto> photoList = tbPhotoService.findByAlbumId(strAlbumId);//拿到该相册下的所有图片
				if(photoList.size()!=0){
					return "112";
				}else{
					TbPhotoAlbum tbPhotoAlbum = tbPhotoAlbumService.findById(Long.valueOf(strAlbumId));
					tbPhotoAlbum.setTpaStatus(0);
					tbPhotoAlbumService.update(tbPhotoAlbum);
				}
			}
			return "0";
		}
		return "";
		
	}
	
	@ResponseBody
	@RequestMapping("/checks")
	public String checks(Model model,String photoName) {
		  int count = 0;
		  if(StringUtils.isNotBlank(photoName)){
			  count = tbPhotoAlbumService.getTbPhotoAlbumDao().getInt("SELECT count(*) FROM tb_photo_album WHERE tpa_name = ? and tpa_status = 1 ", photoName);
		  }
		return String.valueOf(count);
	}
	
	/**
	 * 查询栏目
	 * 
	 * */
	@ResponseBody
	@RequestMapping("/getColumn")
	public List<TbColumn> getgetColumn(HttpServletRequest request){
		String tcType = request.getParameter("tcType");
		return tbColumnService.getColumnByType(Integer.parseInt(tcType));
	}
	
	/**
	 * 查询相册
	 * 
	 * */
	@ResponseBody
	@RequestMapping("/getAlbumAll")
	public List<TbPhotoAlbum> getAlbumAll(HttpServletRequest request,String type){
		return tbPhotoAlbumService.getAlbumAll(type);
	}
}
