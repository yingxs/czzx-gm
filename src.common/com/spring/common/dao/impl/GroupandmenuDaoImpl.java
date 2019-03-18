package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.GroupandmenuDao;
import com.spring.common.entity.Groupandmenu;

@Repository("groupandmenuDao")
public class GroupandmenuDaoImpl extends BaseDaoMysqlImpl<Groupandmenu, Long> implements
		GroupandmenuDao {

	GroupandmenuDaoImpl(){
		super(Groupandmenu.class);
	}

}
