package com.spring.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spring.base.service.BaseService;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.Teacher;

public interface TeacherService extends BaseService<Teacher, Integer> {

	public List<Teacher> list(Map<String, Object> params);

	public List<Teacher> listAll();

	public PageBean<Teacher> findPage(String lastSql, PageBean<Teacher> page);

	public Integer save(String sql, List<Object> value, Teacher teacher);

	public void del(Integer id);

	public Teacher findById(Integer id);

	public void update(Teacher t);

	public List<Teacher> findTeacher();

	public HashMap<String, Object> findForTeacherJson(HashMap<String, String> params);

}
