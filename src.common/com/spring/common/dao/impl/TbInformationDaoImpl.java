package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.TbInformationDao;
import com.spring.common.entity.TbInformation;

@Repository("TbInformationDao")
public class TbInformationDaoImpl extends BaseDaoMysqlImpl<TbInformation, Long>
		implements TbInformationDao {

	public TbInformationDaoImpl() {
		super(TbInformation.class);
	}

}
