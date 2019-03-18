package com.spring.server.dao.impl;

import org.springframework.stereotype.Repository;
import com.spring.server.entity.TbInstituteDepartmentInfo;
import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.server.dao.TbInstituteDepartmentInfoDao;

/**
 * 系信息表业务类
 *
 * @author hxx
 * @Date 2019-03-05 16:46:54
 */
 
@Repository
public class TbInstituteDepartmentInfoDaoImpl extends BaseDaoMysqlImpl<TbInstituteDepartmentInfo, Long> implements
		TbInstituteDepartmentInfoDao {

	public TbInstituteDepartmentInfoDaoImpl(){
		super(TbInstituteDepartmentInfo.class);
	}
	
	
}