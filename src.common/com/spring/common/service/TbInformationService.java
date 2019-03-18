package com.spring.common.service;

import java.util.HashMap;
import java.util.List;

import com.spring.base.service.BaseService;
import com.spring.common.entity.TbInformation;

public interface TbInformationService extends BaseService<TbInformation, Long>{
	
	public HashMap<String, Object> findByParam(HashMap<String, String> params);
	
	List<TbInformation> findByCataId(long cataId);
	
	TbInformation findOne(TbInformation information);
}
