package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.UsergroupDao;
import com.spring.common.entity.Usergroup;

@Repository("usergroupDao")
public class UsergroupDaoImpl extends BaseDaoMysqlImpl<Usergroup, Integer> implements
		UsergroupDao {

	UsergroupDaoImpl(){
		super(Usergroup.class);
	}
	
}
