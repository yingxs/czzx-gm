package com.spring.server.dao.impl;
import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.server.dao.TbDepartmentManageDao;
import com.spring.server.entity.TbDepartment;
@Repository
public class TbDepartmentManageDaoImpl extends BaseDaoMysqlImpl<TbDepartment,Long> implements TbDepartmentManageDao{

	public TbDepartmentManageDaoImpl(){
		super(TbDepartment.class);
	}
}
