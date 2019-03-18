package com.spring.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.dao.TbArticleDao;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbArticle;
import com.spring.common.service.TbArticleService;

@Service("tbArticleService")
@Transactional
public class TbArticleServiceImpl extends BaseServiceImpl<TbArticle, Long>
		implements TbArticleService {

	@Resource
	TbArticleDao tbArticleDao;

	@Override
	public BaseDao<TbArticle, Long> getGenericDao() {
		return tbArticleDao;
	}

	@Override
	public PageBean<TbArticle> findPage(String lastSql, PageBean<TbArticle> page) {
		return tbArticleDao.searchBySql(lastSql, page);
	}

	@Override
	public Integer saveToEntity(TbArticle student) {
		return Integer.parseInt(tbArticleDao.saveId(student).toString());
	}

	@Override
	public TbArticleDao getArticleDao() {
		return tbArticleDao;
	}
	
	@Override
	public HashMap<String,Object> getList(HashMap<String,String> params){
		HashMap<String,Object> json = new HashMap<String,Object>();
		
		
		String strArticle = params.get("taTitle") == null? "":params.get("taTitle");
		
		String strColumnId = params.get("taColumnId") == null? "":params.get("taColumnId");
		
		String strAddDate = params.get("addDate") == null? "":params.get("addDate");
		
		String strEndDate =  params.get("endDate") == null? "":params.get("endDate");
		
		int page = params.get("page") == null ? 0:Integer.valueOf(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.valueOf(params.get("pageSize"));
		
		StringBuffer sbSql = new StringBuffer();
		
		sbSql.append("  select *,(select tc_name from tb_column where tc_id = ta_column_id ) as column_name,DATE_FORMAT(ta_addtime,'%Y-%m-%d') as 'adddate' from tb_article  where ta_status = 1 ");
		
		if(!strArticle.equals(""))
			sbSql.append(" and ta_title like '%" + strArticle + "%'");
		
		if(!strColumnId.equals(""))
			sbSql.append(" and ta_column_id =" + strColumnId );
		
		
		
		if (!strAddDate.equals("")) {
			sbSql.append(" and DATE_FORMAT(ta_addtime,'%Y-%m-%d')>='" + strAddDate +"' ");
	
		}
		if (!strEndDate.equals("")) {
			sbSql.append(" and DATE_FORMAT(ta_addtime,'%Y-%m-%d')<='" + strEndDate +" ");
		}
		
		
		List<Object> values = new ArrayList<Object>();
		
		
		sbSql.append(" ORDER BY ta_addtime DESC ");
		
		if (pageSize == 0) {
			List<Map<String,Object>> list = tbArticleDao.searchForMap(sbSql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String,Object>> pageBean = new PageBean<Map<String,Object>>(page, pageSize);
			
			if(params.get("sort") != null)
				pageBean.setOrderBy(params.get("orderBy"));
			if(params.get("order") != null)
				pageBean.setOrderType(params.get("orderType"));
			
			pageBean = tbArticleDao.searchForMap(sbSql.toString(), values, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

}
