package com.spring.server.dao.impl;

import org.springframework.stereotype.Repository;
import com.spring.server.entity.TbFloorInfo;
import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.server.dao.TbFloorInfoDao;

/**
 * 楼层表业务类
 *
 * @author wanghao
 * @Date 2019-03-08 11:44:38
 */
 
@Repository
public class TbFloorInfoDaoImpl extends BaseDaoMysqlImpl<TbFloorInfo, Long> implements
		TbFloorInfoDao {

	public TbFloorInfoDaoImpl(){
		super(TbFloorInfo.class);
	}
}