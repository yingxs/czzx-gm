package com.spring.server.controller;

import java.util.ArrayList;
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

import com.spring.base.controller.BaseController;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbPhoto;
import com.spring.common.entity.TbPhotoAlbum;
import com.spring.common.service.TbColumnService;
import com.spring.common.service.TbPhotoAlbumService;
import com.spring.common.service.TbPhotoService;

@Controller
@RequestMapping("/photo")
public class TbPhotoController extends BaseController {

	@Resource
	TbPhotoAlbumService tbPhotoAlbumService;
	@Resource
	TbPhotoService tbPhotoService;
	@Resource
	TbColumnService tbColumnService;

	/**
	 * 
	 * @Title: view @Description: 页面跳转 @param @param request @param @param
	 *         paper @param @param id @param @return @return String @throws
	 */
	@RequestMapping(value = "/index/{paper}")
	public String view(HttpServletRequest request, @PathVariable String paper,
			@RequestParam(value = "tacId", required = false) Long tacId) {
		if (StringUtils.isBlank(paper)) {
			return "";
		}

		if ("list".equals(paper)) {// 跳转到主页面

			return "photo/album_list";
		}

		if ("info".equals(paper)) {
//			Map<String, Object> advertise = tbAdvertiseContentService.findAdvertiseById(tacId);
//			request.setAttribute("advertise", advertise);

			return "photo/album_info";
		}

		return "photo/" + paper;
	}
	
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
			}
		}
		model.addAttribute("tbList", tbList);
		return "/photo/album_list";
	}

/*	@ResponseBody
	@RequestMapping(value = "/list")
	public HashMap<String, Object> list(HttpServletRequest request) {
		*//**
		 * 添加分页和查询参数
		 *//*
		HashMap<String, Object> json = new HashMap<String, Object>();
		int page = request.getParameter("page") == null ? 0 : Integer.parseInt(request.getParameter("page"));
		int pageSize = request.getParameter("rows") == null ? 0 : Integer.parseInt(request.getParameter("rows"));
		String tcName = request.getParameter("tcName");
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT tpa.tpa_id tpa_id,tc.tc_name,tpa.tpa_name,(SELECT tp_url from tb_photo tp WHERE tpa_id=tp.tp_album_id AND tp.tp_status!=0 limit 1) tp_url");
		sbSql.append(" from tb_photo_album tpa,tb_column tc");
		sbSql.append(" where tpa_status!=0 AND tc.tc_id=tpa.tpa_column_id");
		List<Object> values = new ArrayList<Object>();

		if (!StringUtils.isBlank(tcName)) {
			sbSql.append("and tc_name like '%" + tcName + "%' ");
			values.add(tcName);
		}

		if (pageSize == 0) {
			List<Map<String, Object>> list = tbPhotoAlbumService.getTbPhotoAlbumDao().searchForMap(sbSql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		} else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbPhotoAlbumService.getTbPhotoAlbumDao().searchForMap(sbSql.toString(), values, pageBean);
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}

	}*/
	
	
	@ResponseBody
	@RequestMapping(value = "/list")
	public HashMap<String, Object> list(HttpServletRequest request) {
		/**
		 * 添加分页和查询参数
		 */
		HashMap<String, Object> json = new HashMap<String, Object>();
		int page = request.getParameter("page") == null ? 0 : Integer.parseInt(request.getParameter("page"));
		int pageSize = request.getParameter("rows") == null ? 0 : Integer.parseInt(request.getParameter("rows"));
		String tcName = request.getParameter("tcName");
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT t.* , tp_url ");
		sbSql.append(" from tb_photo_album t ");
		sbSql.append(" LEFT JOIN tb_photo ON tp_album_id = tpa_id and tp_coverPhoto = 1 ");
		sbSql.append(" where tpa_status!=0 ");
		sbSql.append(" ORDER BY tpa_addime desc ");
		List<Object> values = new ArrayList<Object>();

		if (pageSize == 0) {
			List<Map<String, Object>> list = tbPhotoAlbumService.getTbPhotoAlbumDao().searchForMap(sbSql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		} else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbPhotoAlbumService.getTbPhotoAlbumDao().searchForMap(sbSql.toString(), values, pageBean);
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}

	}
	
	@ResponseBody
	@RequestMapping(value = "/info")
	public HashMap<String, Object> info(HttpServletRequest request) {
		/**
		 * 添加分页和查询参数
		 */
		HashMap<String, Object> json = new HashMap<String, Object>();
		List<Object> values = new ArrayList<Object>();
		int page = request.getParameter("page") == null ? 0 : Integer.parseInt(request.getParameter("page"));
		int pageSize = request.getParameter("rows") == null ? 0 : Integer.parseInt(request.getParameter("rows"));
		String tpaId = request.getParameter("tpaId");
		TbPhotoAlbum tbPhotoAlbum=new TbPhotoAlbum();
		tbPhotoAlbum.setTpaId(Long.parseLong(tpaId));
		Object id = tbPhotoAlbumService.getTbPhotoAlbumDao().saveId(tbPhotoAlbum);
		
		
		String sql = "SELECT * FROM tb_photo ";
		sql += "LEFT JOIN tb_photo_album ON tpa_column_id = tp_column ";
		sql += "WHERE tp_status != 0 AND  tpa_id="+tpaId;

		if (pageSize == 0) {
			List<Map<String, Object>> list = tbPhotoService.getTbPhotoDao().searchForMap(sql, values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		} else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbPhotoService.getTbPhotoDao().searchForMap(sql, values, pageBean);
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}

	}

}
