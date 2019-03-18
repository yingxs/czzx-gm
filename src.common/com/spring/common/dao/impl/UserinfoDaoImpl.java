package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.UserinfoDao;
import com.spring.common.entity.Userinfo;

@Repository("userinfoDao")
public class UserinfoDaoImpl extends BaseDaoMysqlImpl<Userinfo, Long> implements
		UserinfoDao {
	
	public UserinfoDaoImpl(){
		super(Userinfo.class);
	}
	
}
