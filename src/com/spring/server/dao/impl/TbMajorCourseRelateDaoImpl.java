package com.spring.server.dao.impl;

import org.springframework.stereotype.Repository;
import com.spring.server.entity.TbMajorCourseRelate;
import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.server.dao.TbMajorCourseRelateDao;

/**
 * 专业课程关联表业务类
 *
 * @author 
 * @Date 2019-03-12 11:23:00
 */
 
@Repository
public class TbMajorCourseRelateDaoImpl extends BaseDaoMysqlImpl<TbMajorCourseRelate, Long> implements
		TbMajorCourseRelateDao {

	public TbMajorCourseRelateDaoImpl(){
		super(TbMajorCourseRelate.class);
	}


}