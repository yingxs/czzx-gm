package com.spring.server.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.entity.PageBean;
import com.spring.server.dao.TbApplicatonInfoDao;
import com.spring.server.entity.TbApplicatonInfo;
import com.spring.server.service.TbApplicatonInfoService;

@Service("tbApplicatonInfoService")
public class TbApplicatonInfoServiceImpl extends BaseServiceImpl<TbApplicatonInfo, Long> implements TbApplicatonInfoService{

	@Resource
	TbApplicatonInfoDao tbApplicatonInfoDao;
	
	@Override
	public BaseDao<TbApplicatonInfo, Long> getGenericDao() {
		return tbApplicatonInfoDao;
	}
	
	//应用管理
	@Override
	public HashMap<String, Object> findForJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
		String order = params.get("order");
		String taiName = params.get("taiName");//应用名称
		String taiIcon = params.get("taiIcon");//图标
		String taiWebAddr = params.get("taiWebAddr");//应用地址
		String taiPageCount = params.get("taiPageCount");//浏览量
		String taiDesp = params.get("taiDesp");//应用介绍
		String AddDate = params.get("AddDate");//创建开始时间
		String AddDateEnd = params.get("AddDateEnd");//创建结束时间
		//查询sql
		StringBuffer sbf = new StringBuffer();
		sbf.append("SELECT tai_id taiId,tai_name taiName,tai_icon taiIcon,tai_web_addr taiWebAddr,tai_page_count taiPageCount,tai_desp taiDesp,");//应用信息表  //应用名称  //图标  //应用地址  //浏览量  //应用介绍
		sbf.append("DATE_FORMAT(tai_add_date,'%Y-%m-%d %H:%i:%s') taiAddDate ");//创建时间
		sbf.append("FROM tb_applicaton_info ");
		sbf.append("WHERE tai_status<>0 ");
		
		//查询条件
		//根据应用名称查询 
		if (!StringUtils.isBlank(taiName)) {
			sbf.append("AND tai_name LIKE '%"+taiName+"%' ");
		}
		//根据时间查询
		if (AddDate != null && !AddDate.equalsIgnoreCase("")) {
			sbf.append("AND DATE_FORMAT(tai_add_date,'%Y-%m-%d') >= DATE_FORMAT('"+AddDate+"','%Y-%m-%d') ");
		} 
		
		if (AddDateEnd!= null && !AddDateEnd.equalsIgnoreCase("")) {
			sbf.append("AND DATE_FORMAT(tai_add_date,'%Y-%m-%d') <= DATE_FORMAT('"+AddDateEnd+"','%Y-%m-%d') ");
		}
		if (!StringUtils.isBlank(order)) {
			sbf.append("ORDER BY tai_add_date "+order+" ");
		} else {
			sbf.append("ORDER BY tai_add_date DESC ");
		}
		
		
		if (pageSize == 0) {
			List<Map<String, Object>> list = tbApplicatonInfoDao.searchForMap(sbf.toString(), null);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbApplicatonInfoDao.searchForMap(sbf.toString(), null, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}



}
