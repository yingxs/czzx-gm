package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.TbWordsDao;
import com.spring.common.entity.TbWords;


@Repository("tbWordsDao")
public class TbWordsDaoImpl extends BaseDaoMysqlImpl<TbWords, Long> implements
		TbWordsDao {

	public TbWordsDaoImpl(){
		super(TbWords.class);
	}
}
