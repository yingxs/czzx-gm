package com.spring.server.dao.impl;

import org.springframework.stereotype.Repository;
import com.spring.server.entity.TbTeacherInfo;
import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.server.dao.TbTeacherInfoDao;

/**
 * 教师表业务类
 *
 * @author
 * @Date 2019-03-13 16:55:07
 */
 
@Repository
public class TbTeacherInfoDaoImpl extends BaseDaoMysqlImpl<TbTeacherInfo, Long> implements
		TbTeacherInfoDao {

	public TbTeacherInfoDaoImpl(){
		super(TbTeacherInfo.class);
	}
}