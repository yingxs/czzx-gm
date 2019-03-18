package com.spring.server.service;

import java.util.HashMap;

import com.spring.base.service.BaseService;
import com.spring.server.entity.TbLeaveMessage;

public interface TbLeaveMessageService extends BaseService<TbLeaveMessage, Long>{

	HashMap<String, Object> findForJson(HashMap<String, String> params);

}
