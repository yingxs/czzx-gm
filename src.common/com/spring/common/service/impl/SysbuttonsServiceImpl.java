package com.spring.common.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.dao.SysbuttonsDao;
import com.spring.common.entity.Sysbuttons;
import com.spring.common.service.SysbuttonsService;

@Service("sysbuttonsService")
@Transactional
public class SysbuttonsServiceImpl extends BaseServiceImpl<Sysbuttons, Integer> implements
		SysbuttonsService {

	@Resource SysbuttonsDao sysbuttonsDao;
	
	@Override
	public BaseDao<Sysbuttons, Integer> getGenericDao() {
		return sysbuttonsDao;
	}

	

}
