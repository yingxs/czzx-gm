package com.spring.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.dao.TbLinkDao;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbLink;
import com.spring.common.service.TbLinkService;

@Service("TbLinkService")
@Transactional
public class TbLinkServiceImpl extends
BaseServiceImpl<TbLink, Long> implements TbLinkService{

	@Resource
	TbLinkDao tbLinkDao;
	
	@Override
	public BaseDao<TbLink, Long> getGenericDao() {
		return tbLinkDao;
	}
	
	/**
	 * 获取友情链接列表
	 * @param params
	 * @return
	 */
	@Override
	public HashMap<String,Object> findForJson(HashMap<String,String> params){
		HashMap<String,Object> json = new HashMap<String,Object>();
		
		int page = params.get("page") == null ? 0:Integer.valueOf(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.valueOf(params.get("pageSize"));
		
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("select tl_id,tl_name,tl_category,tl_picture,tl_url,tl_index,tl_adduser, ");
		sbSql.append("DATE_FORMAT(tl_addtime,'%Y-%m-%d %H:%i:%s') as tlAddtime ");
		sbSql.append("from tb_link ");
		sbSql.append("WHERE 1 = 1 ");
		sbSql.append("AND tl_status <> 0 ");//0删除
		
		List<Object> values = new ArrayList<Object>();
		
		if(params.get("tlName")!=null&&!params.get("tlName").equalsIgnoreCase("")){
			sbSql.append("AND tl_name like '%"+params.get("tlName")+"%' ");
		}
		
		if(params.get("tlCategory")!=null&&!params.get("tlCategory").equalsIgnoreCase("")){
			sbSql.append("AND tl_category = "+params.get("tlCategory")+" ");
		}
		
		if (params.get("AddDate") != null && !params.get("AddDate").equalsIgnoreCase("")) {
			sbSql.append("AND DATE_FORMAT(tl_addtime,'%Y-%m-%d') >= DATE_FORMAT('"+params.get("AddDate")+"','%Y-%m-%d') ");
		} 
		
		if (params.get("AddDateEnd")!= null && !params.get("AddDateEnd").equalsIgnoreCase("")) {
			sbSql.append("AND DATE_FORMAT(tl_addtime,'%Y-%m-%d') <= DATE_FORMAT('"+params.get("AddDateEnd")+"','%Y-%m-%d') ");
		}
		
		if(params.get("order") == null||params.get("order").equalsIgnoreCase("asc")){
			sbSql.append(" ORDER BY tl_index,tlAddtime ");
		}else{
			sbSql.append(" ORDER BY tl_index,tlAddtime DESC ");
		}
		
		if (pageSize == 0) {
			List<Map<String,Object>> list = tbLinkDao.searchForMap(sbSql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String,Object>> pageBean = new PageBean<Map<String,Object>>(page, pageSize);
			
			if(params.get("orderBy") != null)
				pageBean.setOrderBy(params.get("orderBy"));
			if(params.get("orderType") != null)
				pageBean.setOrderType(params.get("orderType"));
			
			pageBean = tbLinkDao.searchForMap(sbSql.toString(), values, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}
	
}
