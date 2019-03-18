package com.spring.server.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.server.entity.TbGrade;
import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.server.dao.TbGradeDao;

/**
 * 年级表业务类
 *
 * @author zhouyuan
 * @Date 2019-03-15 14:35:02
 */
 
@Repository
public class TbGradeDaoImpl extends BaseDaoMysqlImpl<TbGrade, Long> implements
		TbGradeDao {

	public TbGradeDaoImpl(){
		super(TbGrade.class);
	}
}