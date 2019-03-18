package com.spring.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.dao.TbStudentDao;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbStudent;
import com.spring.common.service.TbStudentService;

@Service("tbStudentService")
@Transactional
public class TbStudentServiceImpl extends BaseServiceImpl<TbStudent, Long>
		implements TbStudentService {

	@Autowired
	TbStudentDao tbStudentDao;

	@Override
	public BaseDao<TbStudent, Long> getGenericDao() {
		return tbStudentDao;
	}

	@Override
	public PageBean<TbStudent> findPage(String lastSql, PageBean<TbStudent> page) {
		return tbStudentDao.searchBySql(lastSql, page);
	}

	@Override
	public Integer saveToEntity(TbStudent student) {
		return Integer.parseInt(tbStudentDao.saveId(student).toString());
	}
//
//	@Override
//	public TbStudentDao getTbStudentDao() {
//		return tbStudentDao;
//	}
	
	/**
	 * 毕业生列表
	 */
	@Override
	public HashMap<String,Object> findForJson(HashMap<String,String> params){
		HashMap<String,Object> json = new HashMap<String,Object>();
		
		int page = params.get("page") == null ? 0:Integer.valueOf(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.valueOf(params.get("pageSize"));
		
		StringBuffer sbSql = new StringBuffer();
		
		sbSql.append("SELECT *,DATE_FORMAT(tc_addtime,'%Y') as tcAddtime ");
		sbSql.append("FROM tb_student ");
		sbSql.append("LEFT JOIN tb_classs ON tc_id = ts_class_id ");
		sbSql.append("LEFT JOIN tb_grade ON tg_id = tc_grade_id ");
		sbSql.append("LEFT JOIN tb_student_family ON tsf_student_id = ts_id ");
		sbSql.append("LEFT JOIN tb_class_student_relationship ON tcsr_class_id = tc_id ");
		sbSql.append("WHERE 1 = 1 ");
		sbSql.append("AND ts_status <> 0 ");
		sbSql.append("AND tg_id = 4 ");//毕业班级
		
		
		List<Object> values = new ArrayList<Object>();
		
		if(params.get("tsName")!=null&&!params.get("tsName").equalsIgnoreCase("")){
			sbSql.append("AND ts_name like '%"+params.get("tsName")+"%' ");
		}
		
		if (params.get("AddDate") != null && !params.get("AddDate").equalsIgnoreCase("")) {
			sbSql.append("AND DATE_FORMAT(tc_addtime,'%Y') = "+params.get("AddDate")+" ");
		} 
		
		sbSql.append(" ORDER BY tc_addtime DESC ");
		
		if (pageSize == 0) {
			List<Map<String,Object>> list = tbStudentDao.searchForMap(sbSql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String,Object>> pageBean = new PageBean<Map<String,Object>>(page, pageSize);
			
			if(params.get("orderBy") != null)
				pageBean.setOrderBy(params.get("orderBy"));
			if(params.get("orderType") != null)
				pageBean.setOrderType(params.get("orderType"));
			
			pageBean = tbStudentDao.searchForMap(sbSql.toString(), values, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	@Override
	public HashMap<String, Object> findForStudentJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
		String order = params.get("order");
		String tbGrade = params.get("tbGrade");
		String tbClass = params.get("tbClass");
		String tsName = params.get("tsName");
		String addDate = params.get("AddDate");
		String addDateEnd = params.get("AddDateEnd");

		StringBuffer strSql = new StringBuffer();
		strSql.append("SELECT ts_id tsId,ts_number tsNumber, ts_name tsName, ts_sex tsSex, tc_name tcName, tg_name tgName, tsf.tsf_name tsfName,tsf.tsf_phone tsfPhone,ts_status tsStatus, pname pName,cname cName,oname oName,ts_address tsAddress ");
		strSql.append(" FROM tb_student");
		strSql.append(" LEFT JOIN tb_classs ON ts_class_id = tc_id ");
		strSql.append(" LEFT JOIN tb_grade ON tc_grade_id = tg_id ");
		strSql.append(" LEFT JOIN (SELECT min(tsf_id) as tsf_id,tsf_student_id, tsf_name,tsf_phone,tsf_relation");
		strSql.append(" from tb_student_family GROUP BY tsf_student_id) tsf");
		strSql.append(" ON ts_id = tsf.tsf_student_id ");
		strSql.append(" LEFT JOIN china_province ON ts_province_id = pid ");
		strSql.append(" LEFT JOIN china_city ON ts_city_id = cid ");
		strSql.append(" LEFT JOIN china_county ON ts_county_id = oid ");
		strSql.append(" WHERE ts_status=1 ");
		
		if (!StringUtils.isBlank(tbGrade)) {
			strSql.append("AND tg_id = "+tbGrade+" ");
		}
		if (!StringUtils.isBlank(tbClass)) {
			strSql.append("AND ts_class_id = "+tbClass+" ");
		}
		if (!StringUtils.isBlank(tsName)) {
			strSql.append("AND ts_name like '%"+tsName+"%' ");
		}
		if (params.get("AddDate") != null && !params.get("AddDate").equalsIgnoreCase("")) {
			strSql.append("AND DATE_FORMAT(ts_addtime,'%Y-%m-%d') >= DATE_FORMAT('"+addDate+"','%Y-%m-%d') ");
		} 
		
		if (params.get("AddDateEnd")!= null && !params.get("AddDateEnd").equalsIgnoreCase("")) {
			strSql.append("AND DATE_FORMAT(ts_addtime,'%Y-%m-%d') <= DATE_FORMAT('"+addDateEnd+"','%Y-%m-%d') ");
		}
		if (!StringUtils.isBlank(order)) {
			strSql.append("ORDER BY ts_addtime "+order+" ");
		} else {
			strSql.append("ORDER BY ts_addtime DESC ");
		}
		if (pageSize == 0) {
			List<Map<String, Object>> list = tbStudentDao.searchForMap(strSql.toString(), null);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbStudentDao.searchForMap(strSql.toString(), null, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

}
