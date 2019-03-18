package com.spring.server.service;

import java.util.List;
import java.util.Map;
import com.spring.base.service.BaseService;
import com.spring.server.entity.TbBuildingInfo;

/**
 * 教学楼业务接口
 *
 * @author wanghao
 * @Date 2019-03-08 09:49:40
 */
 public interface TbBuildingInfoService extends BaseService<TbBuildingInfo, Long>{
	
	public Map<String, Object> findForJson(Map<String, String> params);
	
	
	public List<TbBuildingInfo> findAllBylikeBuildName(String buildName);
	/**
	 *	 楼宇名称模糊查询，除去buildId标识的楼宇
	 * @param buildName
	 * @return
	 */
	public List<TbBuildingInfo> findAllBylikeBuildName(String buildName,String buildId);
	
	public List<TbBuildingInfo> findAllByBuildName(String buildName);
	
	
	
}