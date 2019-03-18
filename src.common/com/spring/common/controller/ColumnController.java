package com.spring.common.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.base.controller.BaseController;
import com.spring.base.utils.GlobalStatic;
import com.spring.base.utils.JsonUtils;
import com.spring.base.utils.StringUtil;
import com.spring.common.basequery.BaseQueryParam;
import com.spring.common.basequery.TreeObj;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbColumn;
import com.spring.common.entity.Userinfo;
import com.spring.common.service.TbArticleService;
import com.spring.common.service.TbColumnService;

@Controller
@RequestMapping(value = "/admin/column")
public class ColumnController extends BaseController {
	@Resource
	TbColumnService tbColumnService;

	@Resource
	TbArticleService tbArticleService;

	@RequestMapping("/init_page")
	public String initPage(Model model) {
		return "/column/column";
	}

	
	@ResponseBody
	@RequestMapping(value = "/columnList", method=RequestMethod.POST)
	public List<TbColumn> list() {
		//获取最上级
		List<TbColumn> listTbColumn = tbColumnService.listByParentP(0L);
		if(null != listTbColumn && listTbColumn.size() > 0){
			//然后获取子集
			for (TbColumn tbColumn : listTbColumn) {
				tbColumn.setChildren(this.tree(Long.valueOf(tbColumn.getTcId())));
			}
		}
		return listTbColumn;
	}	
	
	/**
	 * 子集
	 * @authhor ylj
	 * 2014年10月3日下午1:55:22
	 * @param parentId
	 * @return
	 */
	protected List<TbColumn> tree(Long parentId){
		List<TbColumn> list = tbColumnService.listByParent(parentId);
		if(null != list && list.size() > 0){
			for (TbColumn menu : list) {
				menu.setChildren(this.tree(Long.valueOf(menu.getTcId())));
			}
		}
		return list;
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
	public String listJoson(Model model, BaseQueryParam<TbColumn> pageEntity) {
		String lastSql = "";

		if (StringUtils.isNotBlank(pageEntity.getKeywords())) {
			lastSql += " and tc_name like '%" + pageEntity.getKeywords() + "%'";
		}

		lastSql += " and tc_status != '0' order by tc_parent_id desc";
		PageBean<TbColumn> pages = tbColumnService.findPage(lastSql, pageEntity);
		for (TbColumn t : pages.getList()) {
			if (t.getTcParentId() != 0) {
				TbColumn tt = tbColumnService.findById(t.getTcParentId().longValue());
				if (tt != null) {
					t.setParentName(tt.getTcName());
				}
			} else {
				t.setParentName("基本栏目");
			}
		}
		return pageEntity.resultJson(pages);

	}

	/** 查询所有栏目 **/
	@ResponseBody
	@RequestMapping("/get_all_column")
	public String getTbCalss(BaseQueryParam<TbColumn> pageEntity) {
		if (StringUtils.isBlank(pageEntity.getKeywords())) {
			pageEntity.setKeywords("3");
		}
		List<TbColumn> lT = tbColumnService.getColumnDao().searchp("SELECT * FROM tb_column WHERE tc_type = ? and tc_parent_id = 0", pageEntity.getKeywords());
		List<TreeObj> newList = new ArrayList<TreeObj>();
		for (TbColumn tc : lT) {
			TreeObj tt = new TreeObj();
			tt.setId(tc.getTcId().toString());
			tt.setText(tc.getTcName());

			if (tc.getTcParentId() == 0) {
				List<TbColumn> iList = tbColumnService.searchp("SELECT * FROM tb_column tb WHERE tb.tc_parent_id = ? and tc_type = ?", tc.getTcId(), pageEntity.getKeywords());
				List<TreeObj> iLists = new ArrayList<TreeObj>();
				for (TbColumn tbColumn : iList) {
					TreeObj tts = new TreeObj();
					tts.setId(tbColumn.getTcId().toString());
					tts.setText(tbColumn.getTcName());
					iLists.add(tts);
				}
				tt.setChildren(iLists);
				newList.add(tt);
			}
		}
		return JsonUtils.writeValueAsString(newList);
	}

	/** 查询文章栏目 **/
	@ResponseBody
	@RequestMapping("/get_column")
	public String getCalss(BaseQueryParam<TbColumn> pageEntity) {
		if (StringUtils.isBlank(pageEntity.getKeywords())) {
			pageEntity.setKeywords("3");
		}
		List<TbColumn> lT = tbColumnService.getColumnDao().searchp("SELECT * FROM tb_column WHERE tc_type IN (2,3) and tc_parent_id <> 0");
		List<TreeObj> newList = new ArrayList<TreeObj>();
		for (TbColumn tc : lT){
			TreeObj tt = new TreeObj();
			tt.setId(tc.getTcId().toString());
			tt.setText(tc.getTcName());
			newList.add(tt);
		}
		return JsonUtils.writeValueAsString(newList);
	}
	
	/** 修改初始化 **/
	@RequestMapping(value = "/initUpdate")
	public String initUpdate(HttpServletRequest request, Model model, String id) {
		TbColumn tbColumn = null;
		if (id != null) {
			try {
				tbColumn = tbColumnService.findById(Long.parseLong(id));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (tbColumn.getTcParentId() != 0) {
			TbColumn tt = tbColumnService.findById(tbColumn.getTcParentId().longValue());
			if (tt != null) {
				model.addAttribute("pareName",tt.getTcName());
				model.addAttribute("pareId",tt.getTcId());
			}
		} else {
			model.addAttribute("pareName","基本栏目");
		}
		model.addAttribute("tbColumn", tbColumn);

		return "/column/updateColumn";
	}

	/** 保存新增 **/
	@ResponseBody
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request, Model model, TbColumn tbColumn) {
		Userinfo userInfo = (Userinfo) request.getSession().getAttribute(GlobalStatic.DEFAULT_LOGIN_SESSION_NAME);
		String strResult = "";
		try {
			if(tbColumn == null){
				strResult = "101";
				return strResult;
			}
			if(!StringUtil.isNumber(tbColumn.getTcIndex())){
				strResult = "102";
				return strResult;
			}
			int countInt = tbColumnService.searchForIndexAndParentId(tbColumn.getTcParentId().toString(), tbColumn.getTcIndex().toString(), null);
			if(countInt > 0){
				strResult = "103";
				return strResult;
			}
			tbColumn.setTcStatus(1);
			tbColumn.setTcAddtime(new Date());
			tbColumn.setTcAdduser(userInfo.getId().intValue());
			tbColumnService.saveToEntity(tbColumn);
			strResult = "100";
			return strResult;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			strResult = "-101";
			return strResult;
		}
	}

	/** 修改 **/
	@ResponseBody
	@RequestMapping(value = "/update")
	public String update(HttpServletRequest request, Model model, TbColumn tbColumn) {
		String result;

		if (tbColumn != null) {
			tbColumnService.update(tbColumn);
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

	/** 查询所有栏目 **/
	@ResponseBody
	@RequestMapping("/get_first_column")
	public String getFirstColumn() {
		List<TbColumn> iList = tbColumnService.searchp("SELECT * FROM tb_column tb WHERE tb.tc_type =  ? and tb.tc_status !=0;", 1);
		return JsonUtils.writeValueAsString(iList);
	}
	
	/**
	 * 查询文章分类
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getColumn")
	public String getColumn(){
		List<TbColumn> list = tbColumnService.getColumnDao().searchp("SELECT * FROM tb_column WHERE tc_type IN (2,3) and tc_parent_id <> 0");
		return JsonUtils.writeValueAsString(list);
	}
	
	
	@ResponseBody
	@RequestMapping("/getColumnForTree")
	public String getColumnForTree(){
	//	List<TbColumn> list = tbColumnService.getColumnDao().searchp("SELECT * FROM tb_column WHERE tc_type IN (2,3) and tc_parent_id <> 0");
		
		StringBuffer sbSql = new StringBuffer();
		
		List<TbColumn> alRootColumn = tbColumnService.getColumnByByParentId("0");
		
		sbSql.append("[");
		
		for(int i=0;i<alRootColumn.size();i++){
			
			TbColumn tbColumn = (TbColumn)alRootColumn.get(i);
			
			sbSql.append("{");
			
			sbSql.append("\"id\":\"" + tbColumn.getTcId() +"\",");
			sbSql.append("\"text\":\"" + tbColumn.getTcName() +"\",");
			
			List<TbColumn> alChild =  tbColumnService.getColumnByByParentId(tbColumn.getTcId() +"");
	
			
			sbSql.append("\"children\": [");	
				for(int j=0;j<alChild.size();j++){
					TbColumn tbColumnChild = (TbColumn)alChild.get(j);
					sbSql.append("{");
						sbSql.append("\"id\":\"" + tbColumnChild.getTcId() +"\",");
						sbSql.append("\"text\":\"" + tbColumnChild.getTcName() +"\"");
						if(j == alChild.size() - 1)
							sbSql.append("}");
						else
							sbSql.append("},");
				}
			 sbSql.append("]");
			 if(i == alRootColumn.size() - 1)
					sbSql.append("}");
				else
					sbSql.append("},");
		}
		sbSql.append("]");
		System.out.println("jons->" + sbSql.toString());
		return sbSql.toString();
		
	}
	
	
	
	
	
	

	/** 查询所有子数据 **/
	@ResponseBody
	@RequestMapping("/del_check")
	public String delCheck(String id) {
		int count = 0;
		TbColumn tbColumn = null;
		if (StringUtils.isNotBlank(id)) {
			tbColumn = tbColumnService.findById(Long.parseLong(id));
			System.err.println("id:"+id);
			System.err.println("type"+tbColumn.getTcType());
			// 文章关联表数据
			//select * FROM tb_article ta LEFT JOIN tb_column tc ON tc.tc_id = ta.ta_column_id WHERE  tc.tc_type = ?
			count = tbArticleService.getArticleDao().getInt("SELECT count(*) FROM tb_article ta LEFT JOIN tb_column tc ON tc.tc_id = ta.ta_column_id WHERE  tc.tc_parent_id = ?", Long.valueOf(id));
			if (count != 0) {
				count = 3;
			}
		}
		if (count == 0) {
			try {
				tbColumn.setTcStatus(0);
				tbColumnService.update(tbColumn);
				count = 1;
			} catch (Exception e) {
				count = 2;
			}
		}
		return String.valueOf(count);
	}

	/** 根据Id删除 **/
	@ResponseBody
	@RequestMapping(value = "/del")
	public String del(HttpServletRequest request, Model model, String id) {
		String result;
		if (id != null) {
			try {
				TbColumn tbColumn = tbColumnService.findById(Long.parseLong(id));
				tbColumn.setTcStatus(0);
				tbColumnService.update(tbColumn);
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
	
	/** 修改 **/
	@ResponseBody
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request) {
		String tcId = request.getParameter("tcId");
		String tcName = request.getParameter("tcName");
		String tcParentId = request.getParameter("tcParentIds");
		String tcType = request.getParameter("tcType");
		String tcIndex = request.getParameter("tcIndex");
		String tcContent = request.getParameter("tcContent");

		if(tcId!=null){
			//查询该栏目是否是栏目组，若是栏目组，则需要判断下面是否还有内容
			TbColumn tbColumnForType = tbColumnService.findById(Long.valueOf(tcId));
			Integer iColumnType = Integer.parseInt(tbColumnForType.getTcType());
			if(iColumnType == 1 && iColumnType != Integer.parseInt(tcType)){
				List<TbColumn> columnByByParent = tbColumnService.getColumnByByParentId(tcId);
				if(columnByByParent.size()!=0){
					return "102";
				}
			}
			if(!StringUtil.isNumber(tcIndex)){
				return "103";
			}
			//排序去重
			int iCount = tbColumnService.searchForIndexAndParentId(tcParentId,tcIndex,tcId);
			if(iCount > 0){
				return "104";
			}
			TbColumn tbColumn = new TbColumn();
			tbColumn.setTcId(Integer.parseInt(tcId));
			tbColumn.setTcName(tcName);
			tbColumn.setTcParentId(Integer.parseInt(tcParentId));
			tbColumn.setTcType(tcType);
			tbColumn.setTcIndex(tcIndex);
			tbColumn.setTcContent(tcContent);
			tbColumnService.updateForTbColumn(tbColumn);
			return "100";
		}else{
			return "101";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/removeColumns")
	public String removeColumns(HttpServletRequest request){
		//获取传过来的栏目编号和栏目类型
		String strTcIds = request.getParameter("tcIds");
		
		if(!StringUtils.isBlank(strTcIds)){
			String columns[] = strTcIds.split(",");
			for (String columnId : columns) {
				TbColumn tbColumnForType = tbColumnService.findById(Long.valueOf(columnId));
				Integer iColumnType = Integer.parseInt(tbColumnForType.getTcType());
				if(iColumnType == 1){
					List<TbColumn> columnByByParent = tbColumnService.getColumnByByParentId(columnId);
					if(columnByByParent.size() == 0){
						tbColumnForType.setTcStatus(0);
						tbColumnService.update(tbColumnForType);
					}else{
						return "112";
					}
				}else{
					tbColumnForType.setTcStatus(0);
					tbColumnService.update(tbColumnForType);
				}
			}
			return "0";
		}
		return "";
		
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
			return "column/add_column";
		} else if ("edit".equals(paper)) {// 跳转到编辑页面
			
			return "column/edit_column";
		}

		return "column/" + paper;
	}
}