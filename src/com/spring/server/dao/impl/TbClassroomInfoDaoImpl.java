package com.spring.server.dao.impl;

import org.springframework.stereotype.Repository;
import com.spring.server.entity.TbClassroomInfo;
import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.server.dao.TbClassroomInfoDao;

/**
 * 教室表业务类
 *
 * @author wanghao
 * @Date 2019-03-08 11:45:36
 */
 
@Repository
public class TbClassroomInfoDaoImpl extends BaseDaoMysqlImpl<TbClassroomInfo, Long> implements
		TbClassroomInfoDao {

	public TbClassroomInfoDaoImpl(){
		super(TbClassroomInfo.class);
	}
}