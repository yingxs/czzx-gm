package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.TbStarDao;
import com.spring.common.entity.TbStar;

@Repository("tbStarDao")
public class TbStarDaoImpl extends BaseDaoMysqlImpl<TbStar, Long> implements TbStarDao {

	public TbStarDaoImpl() {
		super(TbStar.class);
	}

}
