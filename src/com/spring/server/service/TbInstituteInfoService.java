package com.spring.server.service;

import java.util.List;
import java.util.Map;
import com.spring.base.service.BaseService;
import com.spring.server.entity.TbInstituteInfo;

/**
 * 学院信息表业务接口
 *
 * @author 
 * @Date 2019-03-05 14:07:28
 */
 public interface TbInstituteInfoService extends BaseService<TbInstituteInfo, Long>{
	
	public Map<String, Object> findForJson(Map<String, String> params);
	
	/**
	 * 新增前验证学院名是否重复复
	 * 
	 * @param tiiName
	 * @return
	 */
	TbInstituteInfo alax_get_institute_forTiiName(String tiiName);

	/**
	 * 编辑前判断排列序号是否重复
	 * 
	 * @param newOrder
	 */
	TbInstituteInfo alax_get_institute_forOrder(String newOrder);

	/**
	 * 下拉框加载所有学院名和id
	 * 
	 * @return
	 */
	List<Map<String, Object>> ajax_get_instituteIdAndNameList();
}