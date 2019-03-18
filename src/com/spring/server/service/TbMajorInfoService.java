package com.spring.server.service;

import java.util.List;
import java.util.Map;
import com.spring.base.service.BaseService;
import com.spring.server.entity.TbMajorInfo;

/**
 * 专业信息表业务接口
 *
 * @author 
 * @Date 2019-03-07 14:54:30
 */
 public interface TbMajorInfoService extends BaseService<TbMajorInfo, Long>{
	
	public Map<String, Object> findForJson(Map<String, String> params);

	public TbMajorInfo seachOne(TbMajorInfo tbMajorInfo);
	/**
	 * wanghao
	 * @param name
	 * @return
	 */
	public TbMajorInfo findByName(String  name);


	/**
	 * hxx
	 * @param tbcourseInfoId
	 * @return
	 */
	public List<Map<String, Object>> tbMajorInfoName(Long tbcourseInfoId);
	
	
	
	
	
}