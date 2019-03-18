package com.spring.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spring.base.service.BaseService;
import com.spring.common.entity.TbAdvertiseContent;

public interface TbAdvertiseContentService extends BaseService<TbAdvertiseContent, Long> {

	HashMap<String, Object> findForJson(HashMap<String, String> params);

	List<Map<String, Object>> findCatalogName();
	
	List<TbAdvertiseContent> findCatalogId(long tacId);
	
	Map<String, Object> findAdvertiseById(Long id);
}
