package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.TbAccountDao;
import com.spring.common.entity.TbAccount;

@Repository
public class TbAccountDaoImpl extends BaseDaoMysqlImpl<TbAccount, Long> implements TbAccountDao {

	public TbAccountDaoImpl() {
		super(TbAccount.class);
	}

}
