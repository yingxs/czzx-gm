package com.spring.common.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.dao.ChinaCountyDao;
import com.spring.common.entity.ChinaCounty;
import com.spring.common.service.ChinaCountyService;


@Service("chinaCountyService")
@Transactional
public class ChinaCountyServiceImpl  extends
BaseServiceImpl<ChinaCounty, Long> implements ChinaCountyService{
	
	@Resource
	ChinaCountyDao chinaCountyDao;

	@Override
	public List<ChinaCounty> findAllCounty() {
		return chinaCountyDao.getAll();
	}

	@Override
	public List<ChinaCounty> findAllCountyByCity(Long cid) {
		String sql="SELECT * FROM china_county o WHERE o.cid="+cid;
		return chinaCountyDao.search(sql, null);
	}

	@Override
	public BaseDao<ChinaCounty, Long> getGenericDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChinaCounty findCountyById(Long id) {
		return chinaCountyDao.get(id);
	}
	
}
