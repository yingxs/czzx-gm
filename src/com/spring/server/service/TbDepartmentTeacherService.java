package com.spring.server.service;
import java.util.HashMap;
import java.util.List;

import com.spring.base.service.BaseService;
import com.spring.server.entity.TbDepartmentTeacher;

public interface TbDepartmentTeacherService extends BaseService<TbDepartmentTeacher,Long>{
  
	TbDepartmentTeacher findOne(TbDepartmentTeacher tbDepartmentTeacher);

	List<TbDepartmentTeacher> findMemberCount(Long valueOf);

	HashMap<String, Object> findForJson1(HashMap<String, String> params, Long long1);

	HashMap<String, Object> findForJson2(HashMap<String, String> params, Long long1);

	void saveSome(List<TbDepartmentTeacher> tptlist);

}
