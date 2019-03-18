package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.TbLinkDao;
import com.spring.common.entity.TbLink;

@Repository("TbLinkDao")
public class TbLinkDaoImpl extends BaseDaoMysqlImpl<TbLink, Long>
implements TbLinkDao{
	
	public TbLinkDaoImpl() {
		super(TbLink.class);
	}

}
