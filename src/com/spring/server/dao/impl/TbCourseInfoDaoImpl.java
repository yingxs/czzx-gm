package com.spring.server.dao.impl;

import org.springframework.stereotype.Repository;
import com.spring.server.entity.TbCourseInfo;
import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.server.dao.TbCourseInfoDao;

/**
 * 课程信息表业务类
 *
 * @author
 * @Date 2019-03-08 11:38:49
 */
 
@Repository
public class TbCourseInfoDaoImpl extends BaseDaoMysqlImpl<TbCourseInfo, Long> implements
		TbCourseInfoDao {

	public TbCourseInfoDaoImpl(){
		super(TbCourseInfo.class);
	}
}