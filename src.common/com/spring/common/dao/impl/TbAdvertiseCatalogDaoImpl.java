package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.TbAdvertiseCatalogDao;
import com.spring.common.entity.TbAdvertiseCatalog;

@Repository("tbAdvertiseCatalogDao")
public class TbAdvertiseCatalogDaoImpl extends BaseDaoMysqlImpl<TbAdvertiseCatalog, Long> implements
		TbAdvertiseCatalogDao {
	
	public TbAdvertiseCatalogDaoImpl(){
		super(TbAdvertiseCatalog.class);
	}
	
}
