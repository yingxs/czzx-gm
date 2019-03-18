package com.spring.server.dao.impl;

import org.springframework.stereotype.Repository;
import com.spring.server.entity.TbInstituteInfo;
import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.server.dao.TbInstituteInfoDao;

/**
 * 学院信息表业务类
 *
 * @author 
 * @Date 2019-03-05 14:07:28
 */
 
@Repository
public class TbInstituteInfoDaoImpl extends BaseDaoMysqlImpl<TbInstituteInfo, Long> implements
		TbInstituteInfoDao {

	public TbInstituteInfoDaoImpl(){
		super(TbInstituteInfo.class);
	}
}