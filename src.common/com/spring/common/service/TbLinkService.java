package com.spring.common.service;

import java.util.HashMap;

import com.spring.base.service.BaseService;
import com.spring.common.entity.TbLink;

public interface TbLinkService extends BaseService<TbLink, Long>{

	HashMap<String, Object> findForJson(HashMap<String, String> params);

}
