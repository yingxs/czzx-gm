package com.spring.server.dao.impl;

import org.springframework.stereotype.Repository;
import com.spring.server.entity.TbSubjectsInfo;
import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.server.dao.TbSubjectsInfoDao;

/**
 * 科目表业务类
 *
 * @author
 * @Date 2019-03-08 16:14:27
 */
 
@Repository
public class TbSubjectsInfoDaoImpl extends BaseDaoMysqlImpl<TbSubjectsInfo, Long> implements
		TbSubjectsInfoDao {

	public TbSubjectsInfoDaoImpl(){
		super(TbSubjectsInfo.class);
	}
}