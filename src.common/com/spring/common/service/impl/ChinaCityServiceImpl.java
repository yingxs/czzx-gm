package com.spring.common.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.dao.ChinaCityDao;
import com.spring.common.entity.ChinaCity;
import com.spring.common.service.ChinaCityService;


@Service("chinaCityService")
@Transactional
public class ChinaCityServiceImpl  extends
BaseServiceImpl<ChinaCity, Long> implements ChinaCityService{
	
	@Resource
	ChinaCityDao chinaCityDao;

	@Override
	public List<ChinaCity> findAllCity() {
		return chinaCityDao.getAll();
	}

	@Override
	public List<ChinaCity> findAllCityByPro(Long pid) {
		String sql=" SELECT * FROM china_city c WHERE c.pid = "+pid;
		return chinaCityDao.search(sql, null);
	}

	@Override
	public BaseDao<ChinaCity, Long> getGenericDao() {
		return null;
	}

	@Override
	public ChinaCity findCityById(Long id) {
		return chinaCityDao.get(id);
		
	}

}
