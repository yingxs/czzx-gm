package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.SysmenuDao;
import com.spring.common.entity.Sysmenu;

@Repository("sysmenuDao")
public class SysmenuDaoImpl extends BaseDaoMysqlImpl<Sysmenu, Integer> implements
		SysmenuDao {

	public SysmenuDaoImpl(){
		super(Sysmenu.class);
	}
}
