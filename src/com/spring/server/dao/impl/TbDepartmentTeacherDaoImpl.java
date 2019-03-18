package com.spring.server.dao.impl;
import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.server.dao.TbDepartmentTeacherDao;
import com.spring.server.entity.TbDepartmentTeacher;
@Repository
public class TbDepartmentTeacherDaoImpl extends BaseDaoMysqlImpl<TbDepartmentTeacher,Long> implements TbDepartmentTeacherDao{

	public TbDepartmentTeacherDaoImpl(){
		super(TbDepartmentTeacher.class);
	}
}
