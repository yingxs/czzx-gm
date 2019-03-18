package com.spring.common.service;


import java.util.List;

import com.spring.base.service.BaseService;
import com.spring.common.entity.ChinaProvince;



public interface ChinaProvinceService extends
	BaseService< ChinaProvince, Long> {
	/**
	 * 根据id查询ChinaProvince
	 */
	public ChinaProvince findProById(Long id);
	/**
	 * 查询所有省份
	 * @author jinlei
	 *	@time: 2015年1月9日 上午10:13:10
	 * @return
	 */
	public List<ChinaProvince> findAllPro();
}
