package com.spring.server.service;

import java.util.Map;
import com.spring.base.service.BaseService;
import com.spring.server.entity.TbStudent;

/**
 * 学生表业务接口
 *
 * @author zhouyuan
 * @Date 2019-03-11 11:45:48
 */
 public interface TbStudentService extends BaseService<TbStudent, Long>{
	
	public Map<String, Object> findForJson(Map<String, String> params);
}