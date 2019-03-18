package com.spring.common.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.dao.ChinaProvinceDao;
import com.spring.common.entity.ChinaProvince;
import com.spring.common.service.ChinaProvinceService;



@Service("chinaProvinceService")
@Transactional
public class ChinaProvinceServiceImpl  extends
BaseServiceImpl<ChinaProvince, Long> implements ChinaProvinceService{
	
	@Resource
	ChinaProvinceDao chinaProvinceDao;

	@Override
	public List<ChinaProvince> findAllPro() {
		return chinaProvinceDao.getAll();
	}

	@Override
	public ChinaProvince findProById(Long id) {
		return chinaProvinceDao.get(id);
	}

	@Override
	public BaseDao<ChinaProvince, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
