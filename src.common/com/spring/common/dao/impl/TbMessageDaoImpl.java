package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.TbMessageDao;
import com.spring.common.entity.TbMessage;

@Repository("tbMessageDao")
public class TbMessageDaoImpl extends BaseDaoMysqlImpl<TbMessage, Long> implements
		TbMessageDao {
	
	public TbMessageDaoImpl(){
		super(TbMessage.class);
	}
	
}
