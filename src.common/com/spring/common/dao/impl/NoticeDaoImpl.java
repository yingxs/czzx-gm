package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.NoticeDao;
import com.spring.common.entity.Notice;

@Repository("noticeDaoImpl")
public class NoticeDaoImpl extends BaseDaoMysqlImpl<Notice, Long> implements NoticeDao {
	
	public NoticeDaoImpl(){
		 super(Notice.class);
	}

}
