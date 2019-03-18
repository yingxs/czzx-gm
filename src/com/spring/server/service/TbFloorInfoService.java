package com.spring.server.service;

import java.util.List;
import java.util.Map;

import com.spring.base.service.BaseService;
import com.spring.server.entity.TbFloorInfo;

/**
 * 楼层表业务接口
 *
 * @author wanghao
 * @Date 2019-03-08 11:44:38
 */
 public interface TbFloorInfoService extends BaseService<TbFloorInfo, Long>{
	
	public Map<String, Object> findForJson(Map<String, String> params);

	public List<TbFloorInfo> findAllByBuildId(Map<String, String> mapParams);
	
	public List<TbFloorInfo> finAllByName(String floorName,String buildId);
	
}