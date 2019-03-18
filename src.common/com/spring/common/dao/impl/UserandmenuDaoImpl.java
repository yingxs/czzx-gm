package com.spring.common.dao.impl;


import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.UserandmenuDao;
import com.spring.common.entity.Userandmenu;

@Repository("userandmenuDao")
public class UserandmenuDaoImpl extends BaseDaoMysqlImpl<Userandmenu, Long> implements
		UserandmenuDao {

	UserandmenuDaoImpl(){
		super(Userandmenu.class);
	}

}
