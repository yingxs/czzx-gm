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
import com.spring.server.dao.TbDepartmentManageDao;
import com.spring.server.entity.TbDepartment;
import com.spring.server.service.TbDepartmentManageService;
@Service("tbDepartmentManageService")
public class TbDepartmentManageServiceImpl extends BaseServiceImpl<TbDepartment,Long>implements TbDepartmentManageService{

	@Resource
	TbDepartmentManageDao tbDepartmentManageDao;
	
	@Override
	public BaseDao<TbDepartment, Long> getGenericDao() {
		return tbDepartmentManageDao;
	}


	@Override
	public TbDepartment findOne(TbDepartment tbDepartment) {
		return tbDepartmentManageDao.searchOne(tbDepartment);
	}

    //部门管理
	@Override
	public HashMap<String, Object> findForJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
		String order = params.get("order");
		String tdName = params.get("tdName");//部门名称
		String tdDesp = params.get("tdDesp");//部门介绍
		String tgLogo = params.get("tgLogo");//部门图标
		String AddDate = params.get("AddDate");//开始时间
		String AddDateEnd = params.get("AddDateEnd");//结束时间
		//查询sql
		StringBuffer sbf = new StringBuffer();
		sbf.append("SELECT td_id tdId,td_name tdName,td_desp tdDesp,tg_logo tgLogo,");//部门表  //部门名称  //部门介绍   //部门图标   
		sbf.append("DATE_FORMAT(td_addtime,'%Y-%m-%d %H:%i:%s') tdAddtime ");//创建时间
		sbf.append("FROM tb_department ");
		sbf.append("WHERE td_status<>0 ");
		
		//查询条件
		//部门名称 
		if (!StringUtils.isBlank(tdName)) {
			sbf.append("AND td_name LIKE '%"+tdName+"%' ");
		}
		
		if (AddDate != null && !AddDate.equalsIgnoreCase("")) {
			sbf.append("AND DATE_FORMAT(td_addtime,'%Y-%m-%d') >= DATE_FORMAT('"+AddDate+"','%Y-%m-%d') ");
		} 
		
		if (AddDateEnd!= null && !AddDateEnd.equalsIgnoreCase("")) {
			sbf.append("AND DATE_FORMAT(td_addtime,'%Y-%m-%d') <= DATE_FORMAT('"+AddDateEnd+"','%Y-%m-%d') ");
		}
		if (!StringUtils.isBlank(order)) {
			sbf.append("ORDER BY td_addtime "+order+" ");
		} else {
			sbf.append("ORDER BY td_addtime DESC ");
		}
		
		
		if (pageSize == 0) {
			List<Map<String, Object>> list = tbDepartmentManageDao.searchForMap(sbf.toString(), null);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbDepartmentManageDao.searchForMap(sbf.toString(), null, pageBean);
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}


	@Override
	public List<TbDepartment> findDepartment() {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("select * from tb_department where td_status !=0");
		
		return tbDepartmentManageDao.search(sbSql.toString(), null);
	}

}
