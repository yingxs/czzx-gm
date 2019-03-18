package com.spring.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.dao.TbAccountDao;
import com.spring.common.entity.TbAccount;
import com.spring.common.service.TbAccountService;

@Service
public class TbAccountServiceImpl extends BaseServiceImpl<TbAccount, Long> implements TbAccountService {

	@Autowired
	TbAccountDao tbAccountDao;
	
	@Override
	public BaseDao<TbAccount, Long> getGenericDao() {
		return tbAccountDao;
	}

}
