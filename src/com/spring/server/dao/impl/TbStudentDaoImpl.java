package com.spring.server.dao.impl;

import org.springframework.stereotype.Repository;
import com.spring.server.entity.TbStudent;
import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.server.dao.TbStudentDao;

/**
 * 学生表业务类
 *
 * @author zhouyuan
 * @Date 2019-03-11 11:45:49
 */
 
@Repository
public class TbStudentDaoImpl extends BaseDaoMysqlImpl<TbStudent, Long> implements
		TbStudentDao {

	public TbStudentDaoImpl(){
		super(TbStudent.class);
	}
}