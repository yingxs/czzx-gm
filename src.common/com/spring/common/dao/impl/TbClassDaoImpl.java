package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.TbClassDao;
import com.spring.common.entity.TbClass;

@Repository("tbClassDao")
public class TbClassDaoImpl extends BaseDaoMysqlImpl<TbClass, Integer> implements TbClassDao {

	public TbClassDaoImpl() {
		super(TbClass.class);
	}
	
}
