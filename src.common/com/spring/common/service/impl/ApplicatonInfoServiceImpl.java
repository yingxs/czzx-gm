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
import com.spring.common.dao.ApplicatonInfoDao;
import com.spring.common.entity.ApplicatonInfo;
import com.spring.common.entity.PageBean;
import com.spring.common.service.ApplicatonInfoService;

@Service("applicatonInfoService")
@Transactional
public class ApplicatonInfoServiceImpl extends BaseServiceImpl<ApplicatonInfo, Long> implements ApplicatonInfoService {

	@Resource
	ApplicatonInfoDao applicatonInfoDao;

	@Override
	public BaseDao<ApplicatonInfo, Long> getGenericDao() {
		return applicatonInfoDao;
	}

	@Override
	public PageBean<ApplicatonInfo> findPage(String lastSql, PageBean<ApplicatonInfo> page) {
		return applicatonInfoDao.searchBySql(lastSql, page);
	}

	@Override
	public void update(ApplicatonInfo ApplicatonInfo) {
		// TODO Auto-generated method stub
		applicatonInfoDao.update(ApplicatonInfo);
	}

	@Override
	public Integer saveToEntity(ApplicatonInfo applicatonInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ApplicatonInfo> searchp(String sql, Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * 资源站列表
	 */
	@Override
	public HashMap<String,Object> findForJson(HashMap<String,String> params){
		HashMap<String,Object> json = new HashMap<String,Object>();
		
		int page = params.get("page") == null ? 0:Integer.valueOf(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.valueOf(params.get("pageSize"));
		
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT *, DATE_FORMAT(tai_add_date,'%Y-%m-%d %H:%i:%s') as taiAddDate ");
		sbSql.append("FROM tb_applicaton_info ");
		sbSql.append("LEFT JOIN tb_application_user ON tau_app_id = tai_id ");
		sbSql.append("LEFT JOIN tb_teacher ON tt_id = tau_user_id ");
		sbSql.append("WHERE 1 = 1 ");
		sbSql.append("AND tai_status <> 0 ");
		
	
		
		List<Object> values = new ArrayList<Object>();
		
		if(params.get("ttName")!=null&&!params.get("ttName").equalsIgnoreCase("")){
			sbSql.append("AND tt_name like '%"+params.get("ttName")+"%' ");
		}
		
		if (params.get("AddDate") != null && !params.get("AddDate").equalsIgnoreCase("")) {
			sbSql.append("AND DATE_FORMAT(tai_add_date,'%Y-%m-%d') >= DATE_FORMAT('"+params.get("AddDate")+"','%Y-%m-%d') ");
		} 
		
		if (params.get("AddDateEnd")!= null && !params.get("AddDateEnd").equalsIgnoreCase("")) {
			sbSql.append("AND DATE_FORMAT(tai_add_date,'%Y-%m-%d') <= DATE_FORMAT('"+params.get("AddDateEnd")+"','%Y-%m-%d') ");
		}
		
		
		sbSql.append("GROUP BY tau_id ");
		if (params.get("order") == null && !params.get("order").equalsIgnoreCase("")) {
			sbSql.append(" ORDER BY tai_add_date DESC ");
		} else {
			sbSql.append(" ORDER BY tai_add_date ASC ");
		}
		
		if (pageSize == 0) {
			List<Map<String,Object>> list = applicatonInfoDao.searchForMap(sbSql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String,Object>> pageBean = new PageBean<Map<String,Object>>(page, pageSize);
			
			if(params.get("orderBy") != null)
				pageBean.setOrderBy(params.get("orderBy"));
			if(params.get("orderType") != null)
				pageBean.setOrderType(params.get("orderType"));
			
			pageBean = applicatonInfoDao.searchForMap(sbSql.toString(), values, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}
	
}
