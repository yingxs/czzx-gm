package com.spring.server.dao.impl;

import org.springframework.stereotype.Repository;
import com.spring.server.entity.TbBuildingInfo;
import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.server.dao.TbBuildingInfoDao;

/**
 * 教学楼业务类
 *
 * @author wanghao
 * @Date 2019-03-08 09:49:40
 */
 
@Repository
public class TbBuildingInfoDaoImpl extends BaseDaoMysqlImpl<TbBuildingInfo, Long> implements
		TbBuildingInfoDao {

	public TbBuildingInfoDaoImpl(){
		super(TbBuildingInfo.class);
	}
}