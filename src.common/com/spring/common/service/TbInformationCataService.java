package com.spring.common.service;

import java.util.HashMap;
import java.util.List;

import com.spring.base.service.BaseService;
import com.spring.common.entity.TbInformationCata;

public interface TbInformationCataService extends BaseService<TbInformationCata, Long>{
	public HashMap<String, Object> findByParam(HashMap<String, String> params);
	
	/**
	 * 根据分类名称过滤
	 * @param cord
	 * @return
	 */
	public List<TbInformationCata> findByName(String cord);
	
	/**
	 * 获取所有第一级分类
	 * @return
	 */
	public List<TbInformationCata> findByTbInformationCata();
	
	List<TbInformationCata> findByParentId(long parentId);
	
	public List<TbInformationCata> findChildCata();
	
	TbInformationCata findTbInformationCataById(long ticId);
	
	TbInformationCata findOne(TbInformationCata searchParam);
}
