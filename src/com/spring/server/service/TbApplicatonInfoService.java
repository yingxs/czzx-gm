package com.spring.server.service;

import java.util.HashMap;

import com.spring.base.service.BaseService;
import com.spring.server.entity.TbApplicatonInfo;

public interface TbApplicatonInfoService extends BaseService<TbApplicatonInfo, Long>{
	 
	HashMap<String, Object> findForJson(HashMap<String, String> params);
}
