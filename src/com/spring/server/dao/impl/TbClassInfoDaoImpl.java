package com.spring.server.dao.impl;

import org.springframework.stereotype.Repository;
import com.spring.server.entity.TbClassInfo;
import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.server.dao.TbClassInfoDao;

/**
 * 班级表业务类
 *
 * @author zhouyuan
 * @Date 2019-03-16 23:40:17
 */
 
@Repository
public class TbClassInfoDaoImpl extends BaseDaoMysqlImpl<TbClassInfo, Long> implements
		TbClassInfoDao {

	public TbClassInfoDaoImpl(){
		super(TbClassInfo.class);
	}
}