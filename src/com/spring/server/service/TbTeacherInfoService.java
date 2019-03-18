package com.spring.server.service;

import java.util.Map;
import com.spring.base.service.BaseService;
import com.spring.server.entity.TbTeacherInfo;

/**
 * 教师表业务接口
 *
 * @author 
 * @Date 2019-03-13 16:55:07
 */
 public interface TbTeacherInfoService extends BaseService<TbTeacherInfo, Long>{
	
	public Map<String, Object> findForJson(Map<String, String> params);
}