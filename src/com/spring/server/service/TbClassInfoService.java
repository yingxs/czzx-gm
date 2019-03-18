package com.spring.server.service;

import java.util.List;
import java.util.Map;
import com.spring.base.service.BaseService;
import com.spring.server.entity.TbClassInfo;

/**
 * 班级表业务接口
 *
 * @author zhouyuan
 * @Date 2019-03-16 23:40:17
 */
public interface TbClassInfoService extends BaseService<TbClassInfo, Long> {

	public Map<String, Object> findForJson(Map<String, String> params);

	/**
	 * 根据学院id，系别id，年级id下拉框加载班级
	 * 
	 * @param tc_institute_id
	 * @param tc_institute_department_id
	 * @param tc_grade
	 * @return
	 */
	List<Map<String, Object>> ajax_getClass_byGradeId_AndInstituteId_AndInstituteDepartmentId(long tc_institute_id,
			long tc_institute_department_id, long tc_grade);
}