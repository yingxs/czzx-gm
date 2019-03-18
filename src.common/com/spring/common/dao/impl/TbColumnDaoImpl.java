package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.TbColumnDao;
import com.spring.common.entity.TbColumn;

@Repository("tbColumnDao")
public class TbColumnDaoImpl extends BaseDaoMysqlImpl<TbColumn, Long>
		implements TbColumnDao {

	public TbColumnDaoImpl() {
		super(TbColumn.class);
	}

}
