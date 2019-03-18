package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.ChinaProvinceDao;
import com.spring.common.entity.ChinaProvince;


@Repository("chinaProvinceDao")
public class ChinaProvinceDaoImpl extends 
	BaseDaoMysqlImpl<ChinaProvince, Long> implements ChinaProvinceDao{
	public ChinaProvinceDaoImpl(){
		super(ChinaProvince.class);
	}
}
