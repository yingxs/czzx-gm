package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.TbClassStudentRelationshipDao;
import com.spring.common.entity.TbClassStudentRelationship;

@Repository
public class TbClassStudentRelationshipDaoImpl extends BaseDaoMysqlImpl<TbClassStudentRelationship, Long> implements TbClassStudentRelationshipDao {

	public TbClassStudentRelationshipDaoImpl() {
		super(TbClassStudentRelationship.class);
	}
}
