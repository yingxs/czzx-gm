package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.TbAdvertiseContentDao;
import com.spring.common.entity.TbAdvertiseContent;

@Repository("tbAdvertiseContentDao")
public class TbAdvertiseContentDaoImpl extends BaseDaoMysqlImpl<TbAdvertiseContent, Long> implements
		TbAdvertiseContentDao {
	
	public TbAdvertiseContentDaoImpl(){
		super(TbAdvertiseContent.class);
	}
	
}
