package com.spring.common.service;

import java.util.HashMap;

import com.spring.base.service.BaseService;
import com.spring.common.entity.TbAdvertiseCatalog;

public interface TbAdvertiseCatalogService extends BaseService<TbAdvertiseCatalog, Long> {

	HashMap<String, Object> findForJson(HashMap<String, String> params);
	
	TbAdvertiseCatalog findOne(TbAdvertiseCatalog searchParam);
}
