package com.spring.server.dao.impl;

import org.springframework.stereotype.Repository;
import com.spring.server.entity.TbMajorInfo;
import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.server.dao.TbMajorInfoDao;

/**
 * 专业信息表业务类
 *
 * @author 
 * @Date 2019-03-07 14:54:30
 */
 
@Repository
public class TbMajorInfoDaoImpl extends BaseDaoMysqlImpl<TbMajorInfo, Long> implements
		TbMajorInfoDao {

	public TbMajorInfoDaoImpl(){
		super(TbMajorInfo.class);
	}
}