package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.TbGraderDao;
import com.spring.common.entity.TbGrade;

@Repository("tbGraderDao")
public class TbGradeDaoImpl extends BaseDaoMysqlImpl<TbGrade, Integer> implements TbGraderDao {

	public TbGradeDaoImpl() {
		super(TbGrade.class);
	}
	
}
