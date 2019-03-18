package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.ChinaCountyDao;
import com.spring.common.entity.ChinaCounty;

@Repository("chinaCountyDao")
public class ChinaCountyDaoImpl extends 
	BaseDaoMysqlImpl<ChinaCounty, Long> implements ChinaCountyDao{
	public ChinaCountyDaoImpl(){
		super(ChinaCounty.class);
	}
}
