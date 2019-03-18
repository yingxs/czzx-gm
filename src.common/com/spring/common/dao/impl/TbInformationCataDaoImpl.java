package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.TbInformationCataDao;
import com.spring.common.entity.TbInformationCata;

@Repository("TbInformationCataDao")
public class TbInformationCataDaoImpl extends BaseDaoMysqlImpl<TbInformationCata, Long> implements TbInformationCataDao{

	public TbInformationCataDaoImpl(){
		super(TbInformationCata.class);
	}

	
}
