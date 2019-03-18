package com.spring.server.service;
import java.util.HashMap;
import java.util.List;

import com.spring.base.service.BaseService;
import com.spring.server.entity.TbDepartment;

public interface TbDepartmentManageService extends BaseService<TbDepartment,Long>{
  
	TbDepartment findOne(TbDepartment tbDepartment);

	HashMap<String, Object> findForJson(HashMap<String, String> params);

	List<TbDepartment> findDepartment();

}
