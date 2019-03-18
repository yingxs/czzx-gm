package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.TbStudentDao;
import com.spring.common.entity.TbStudent;

@Repository("tbStudentDao")
public class TbStudentDaoImpl extends BaseDaoMysqlImpl<TbStudent, Long>
		implements TbStudentDao {

	public TbStudentDaoImpl() {
		super(TbStudent.class);
	}
}
