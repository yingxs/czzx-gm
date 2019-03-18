package com.spring.server.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.server.dao.TbApplicatonInfoDao;
import com.spring.server.entity.TbApplicatonInfo;

@Repository
public class TbApplicatonInfoDaoImpl extends BaseDaoMysqlImpl<TbApplicatonInfo, Long>  implements TbApplicatonInfoDao {

	 public TbApplicatonInfoDaoImpl(){
		 super(TbApplicatonInfo.class);
	 }
}
