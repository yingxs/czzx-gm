package com.spring.common.dao.impl;


import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.SysbuttonsDao;
import com.spring.common.entity.Sysbuttons;

@Repository("sysbuttonsDao")
public class SysbuttonsDaoImpl extends BaseDaoMysqlImpl<Sysbuttons, Integer> implements
		SysbuttonsDao {
	
	public SysbuttonsDaoImpl(){
		super(Sysbuttons.class);
	}
	
}
