package com.spring.common.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.dao.TbStudentFamilyDao;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbStudentFamily;
import com.spring.common.service.TbStudentFamilyService;

@Service("tbStudentFamilyService")
@Transactional
public class TbStudentFamilyServiceImpl extends
		BaseServiceImpl<TbStudentFamily, Long> implements
		TbStudentFamilyService {

	@Resource
	TbStudentFamilyDao tbStudentFamilyDao;

	@Override
	public BaseDao<TbStudentFamily, Long> getGenericDao() {
		return tbStudentFamilyDao;
	}

	@Override
	public TbStudentFamily finByTbFamaily(Long studentId) {
		String lastSql = " and tsf_student_id = " + studentId;
		List<TbStudentFamily> iList = this.tbStudentFamilyDao.searchBySql(lastSql, new PageBean<TbStudentFamily>()).getList();
		return iList.isEmpty() ? null : iList.get(0);
	}

	@Override
	public Long saveToEntity(TbStudentFamily family) {
		return Long.parseLong(tbStudentFamilyDao.saveId(family).toString());
	}

}
