package com.spring.server.dao.impl;

import org.springframework.stereotype.Repository;
import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.server.dao.TbLeaveMessageDao;
import com.spring.server.entity.TbLeaveMessage;
@Repository
public class TbLeaveMessageDaoImpl extends BaseDaoMysqlImpl<TbLeaveMessage, Long> implements TbLeaveMessageDao{

	public TbLeaveMessageDaoImpl(){
		super(TbLeaveMessage.class);
	}
}
