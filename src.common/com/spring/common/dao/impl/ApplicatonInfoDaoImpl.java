package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.ApplicatonInfoDao;
import com.spring.common.entity.ApplicatonInfo;

@Repository("applicatonInfoDao")
public class ApplicatonInfoDaoImpl extends BaseDaoMysqlImpl<ApplicatonInfo, Long> implements ApplicatonInfoDao {

	public ApplicatonInfoDaoImpl() {
		super(ApplicatonInfo.class);
	}

}
