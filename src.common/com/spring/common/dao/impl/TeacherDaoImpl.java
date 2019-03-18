package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.TeacherDao;
import com.spring.common.entity.Teacher;

@Repository("teacherDao")
public class TeacherDaoImpl extends BaseDaoMysqlImpl<Teacher, Integer> implements TeacherDao {

	public TeacherDaoImpl() {
		super(Teacher.class);
	}
	
}
