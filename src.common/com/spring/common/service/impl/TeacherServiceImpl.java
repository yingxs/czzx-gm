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
import com.spring.common.dao.TeacherDao;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.Teacher;
import com.spring.common.service.TeacherService;

@Service("teacherService")
@Transactional
public class TeacherServiceImpl extends BaseServiceImpl<Teacher, Integer> implements TeacherService {

	@Resource
	TeacherDao teacherDao;

	@Override
	public BaseDao<Teacher, Integer> getGenericDao() {
		return teacherDao;
	}

	@Override
	public List<Teacher> listAll() {
		String sql = "select * from tb_teacher ";
		List<Object> values = new ArrayList<Object>();
		return teacherDao.search(sql, values);
	}
	@Override
	public PageBean<Teacher> findPage(String lastSql,PageBean<Teacher> page) {
		return teacherDao.searchBySql(lastSql, page);
	}
	
	@Override
	public Integer save(String sql,List<Object> value,Teacher teacher) {
		 return teacherDao.addReturnId(sql, value);
		 //return teacherDao.save(teacher).getId();
	}
	
	@Override
	public void del(Integer id) {
		teacherDao.del(id);
	}
	
	@Override
	public List<Teacher> list(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Teacher findById(Integer id) {
		// TODO Auto-generated method stub
		return teacherDao.get(id);
	}
	
	@Override
	public void update(Teacher teacher ) {
		// TODO Auto-generated method stub
		teacherDao.update(teacher);
	}

	@Override
	public List<Teacher> findTeacher() {
		String sql = "SELECT * FROM tb_teacher WHERE tt_status<>0 ";
		return teacherDao.search(sql, null);
	}

	@Override
	public HashMap<String, Object> findForTeacherJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
		String order = params.get("order");
		String ttName = params.get("ttName");
		String ttSex = params.get("ttSex");
		String department = params.get("department");
		//查询sql
		StringBuffer strSql = new StringBuffer();
		strSql.append("SELECT distinct tt_id ttId,tt_name ttName,tt_number ttNumber,tt_photo ttPhoto, ");
		strSql.append("tt_sex ttSex,tt_phone ttPhone,tt_mail ttMail, ");
		strSql.append("tt_political ttPolitical,tt_marriage ttMarriage, ");
		strSql.append("DATE_FORMAT(tt_birth,'%Y-%m-%d') ttBirth ");
		strSql.append("FROM tb_teacher ");
		strSql.append("LEFT JOIN tb_department_teacher ON tt_id = tdt_teacher_id AND tdt_status<>0 ");
		strSql.append("WHERE tt_status<>0 ");
		
		//查询条件
		if (!StringUtils.isBlank(ttName)) {
			strSql.append("AND tt_name LIKE '%"+ttName+"%' ");
		}
		if (!StringUtils.isBlank(ttSex)) {
			strSql.append("AND tt_sex = "+ttSex+" ");
		}
		if (!StringUtils.isBlank(department)) {
			strSql.append("AND tdt_department_id = "+department+" ");
		}
		if (!StringUtils.isBlank(order)) {
			strSql.append("ORDER BY tt_addtime "+order+" ");
		} else {
			strSql.append("ORDER BY tt_addtime DESC ");
		}
		if (pageSize == 0) {
			List<Map<String, Object>> list = teacherDao.searchForMap(strSql.toString(), null);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = teacherDao.searchForMap(strSql.toString(), null, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

}
