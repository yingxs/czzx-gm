package com.spring.common.service;


import java.util.List;

import com.spring.base.service.BaseService;
import com.spring.common.entity.ChinaCounty;



public interface ChinaCountyService extends
	BaseService< ChinaCounty, Long> {
	/**
	 * 根据id查询ChinaCounty
	 */
	public ChinaCounty findCountyById(Long id);
	/**
	 * 查询所有区县
	 * @author jinlei
	 *	@time: 2015年1月9日 上午10:10:38
	 * @return
	 */
	public List<ChinaCounty> findAllCounty();
	/**
	 * 查询市区下的区县
	 * @author jinlei
	 *	@time: 2015年1月9日 上午10:10:38
	 * @return
	 */
	
	public List<ChinaCounty> findAllCountyByCity(Long cid);

}
