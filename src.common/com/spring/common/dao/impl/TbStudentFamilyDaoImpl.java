package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.TbStudentFamilyDao;
import com.spring.common.entity.TbStudentFamily;

@Repository("tbStudentFamilyDao")
public class TbStudentFamilyDaoImpl extends
		BaseDaoMysqlImpl<TbStudentFamily, Long> implements TbStudentFamilyDao {

	public TbStudentFamilyDaoImpl() {
		super(TbStudentFamily.class);
	}

}
