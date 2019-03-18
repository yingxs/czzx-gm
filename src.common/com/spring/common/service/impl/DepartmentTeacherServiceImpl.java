/*package com.spring.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.dao.DepartmentTeacherDao;
import com.spring.common.entity.DepartmentTeacher;
import com.spring.common.service.DepartmentTeacherService;

@Service("departmentTeacherService")
@Transactional
public class DepartmentTeacherServiceImpl extends BaseServiceImpl<DepartmentTeacher, Integer> implements DepartmentTeacherService {

	@Resource
	DepartmentTeacherDao departmentTeacherDao;

	@Override
	public BaseDao<DepartmentTeacher, Integer> getGenericDao() {
		return departmentTeacherDao;
	}

	@Override
	public List<DepartmentTeacher> listAll() {
		String sql = "select * from tb_teacher ";
		List<Object> values = new ArrayList<Object>();
		return departmentTeacherDao.search(sql, values);
	}
	
	@Override
	public void save(DepartmentTeacher dtTeacher) {
		 departmentTeacherDao.save(dtTeacher);
	}
	
	@Override
	public void del(Integer id) {
		departmentTeacherDao.del(id);
	}
	
	@Override
	public List<DepartmentTeacher> list(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public DepartmentTeacher findById(Integer id) {
		// TODO Auto-generated method stub
		return departmentTeacherDao.get(id);
	}
	
	@Override
	public void update(DepartmentTeacher dtTeacher ) {
		// TODO Auto-generated method stub
		departmentTeacherDao.update(dtTeacher);
	}

	@Override
	public DepartmentTeacherDao getDepartmentDao() {
		return departmentTeacherDao;
	}

}
*/