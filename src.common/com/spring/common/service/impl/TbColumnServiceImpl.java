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
import com.spring.common.dao.TbColumnDao;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbColumn;
import com.spring.common.service.TbColumnService;

@Service("tbColumnService")
@Transactional
public class TbColumnServiceImpl extends BaseServiceImpl<TbColumn, Long> implements TbColumnService {

	@Resource
	TbColumnDao tbColumnDao;

	@Override
	public BaseDao<TbColumn, Long> getGenericDao() {
		return tbColumnDao;
	}

	@Override
	public PageBean<TbColumn> findPage(String lastSql, PageBean<TbColumn> page) {
		return tbColumnDao.searchBySql(lastSql, page);
	}

	@Override
	public Integer saveToEntity(TbColumn tbColumn) {
		return Integer.parseInt(tbColumnDao.saveId(tbColumn).toString());
	}

	@Override
	public List<TbColumn> searchp(String sql, Object... params) {
		// TODO Auto-generated method stub
		return tbColumnDao.searchp(sql, params);
	}

	@Override
	public TbColumnDao getColumnDao() {
		return tbColumnDao;
	}

	@Override
	public List<TbColumn> getColumnByType(int type) {
		String sql = "SELECT * FROM tb_column WHERE tc_type = "+type+" AND tc_status<>0 ";
		return tbColumnDao.search(sql, null);
	}
	
	@Override
	public List<TbColumn> getColumnByByParentId(String id) {
		String sql = "SELECT * FROM tb_column where tc_parent_id =" + id  + " and tc_status!=0 order by tc_id asc ";
		return tbColumnDao.search(sql, null);
	}

	@Override
	public HashMap<String, Object> findForTbColumnJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
		
		StringBuffer sbSql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		sbSql.append("select * from tb_column where tc_status!=0");
		
//		if(params.get("sort") != null && !params.get("sort").equalsIgnoreCase("")){
//			sbSql.append(" order by  " + params.get("sort"));
//		}
//		if(params.get("order") != null && !params.get("order").equalsIgnoreCase("")){
//			sbSql.append(" " + params.get("order"));
//		}
		System.out.println("sql-->" + sbSql);
		if (pageSize == 0) {
			List<Map<String, Object>> list = tbColumnDao.searchForMap(sbSql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
//			if(params.get("orderBy") != null)
//				pageBean.setOrderBy(params.get("orderBy"));
//			if(params.get("orderType") != null)
//				pageBean.setOrderType(params.get("orderType"));
			pageBean = tbColumnDao.searchForMap(sbSql.toString(), values, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
		
	}

	@Override
	public List<TbColumn> listByParent(long l) {
		String sql = "select * from tb_column where tc_status = 1 and tc_parent_id = ? ORDER BY tc_index asc ";
		List<Object> values = new ArrayList<Object>();
		values.add(l);
		return tbColumnDao.search(sql, values);
	}

	@Override
	public List<TbColumn> listByParentP(long l) {
		String sql = "select * from tb_column where tc_status = 1 and tc_parent_id = ? ORDER BY tc_id asc ";
		List<Object> values = new ArrayList<Object>();
		values.add(l);
		return tbColumnDao.search(sql, values);
	}

	@Override
	public int searchForIndexAndParentId(String tcParentId, String tcIndex, String tcId) {
		StringBuffer sbSql = new StringBuffer();
		List<Object> valueList = new ArrayList<>();
		sbSql.append(" SELECT tc_id ");
		sbSql.append(" FROM tb_column ");
		sbSql.append(" WHERE  tc_parent_id = ? AND tc_status = 1 AND tc_index = ? ");
		if(tcId != null){
			sbSql.append(" and tc_id <> " + tcId);
		}
		valueList.add(tcParentId);
		valueList.add(tcIndex);
		return tbColumnDao.getCount(sbSql.toString(), valueList);
	}

	@Override
	public void updateForTbColumn(TbColumn tbColumn) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(" UPDATE tb_column SET tc_parent_id = "+tbColumn.getTcParentId()+", tc_name = '"+tbColumn.getTcName()+"' , tc_type= "+tbColumn.getTcType()+", tc_index = "+tbColumn.getTcIndex()+", tc_content = '"+tbColumn.getTcContent()+"' WHERE tc_id = "+tbColumn.getTcId());
		tbColumnDao.execute(sbSql.toString());
	}

}
