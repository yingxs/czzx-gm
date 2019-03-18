package com.spring.common.service;


import java.util.List;

import com.spring.base.service.BaseService;
import com.spring.common.entity.ChinaCity;



public interface ChinaCityService extends
	BaseService< ChinaCity, Long> {
	/**
	 * 根据id查询ChinaCity
	 */
	public ChinaCity findCityById(Long id);
	/**
	 * 查询所有城市
	 * @author jinlei
	 *	@time: 2015年1月9日 上午10:12:09
	 * @return
	 */
	public List<ChinaCity> findAllCity();
	/**
	 * 查询所有省份下的城市
	 * @author jinlei
	 *	@time: 2015年1月9日 上午10:12:34
	 * @param pid
	 * @return
	 */
	public List<ChinaCity> findAllCityByPro(Long pid);
}
