package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.ChinaCityDao;
import com.spring.common.entity.ChinaCity;



@Repository("chinaCityDao")
public class ChinaCityDaoImpl extends 
	BaseDaoMysqlImpl<ChinaCity, Long> implements ChinaCityDao{
	public ChinaCityDaoImpl(){
		super(ChinaCity.class);
	}
}
