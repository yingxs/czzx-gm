package com.spring.server.service;

import java.util.List;
import java.util.Map;
import com.spring.base.service.BaseService;
import com.spring.server.entity.TbInstituteDepartmentInfo;

/**
 * 系信息表业务接口
 *
 * @author hxx
 * @Date 2019-03-05 16:46:54
 */
public interface TbInstituteDepartmentInfoService extends BaseService<TbInstituteDepartmentInfo, Long> {

	public Map<String, Object> findForJson(Map<String, String> params);

	/**
	 * 删除学院之前看看下面有没有系
	 * 
	 * @param tii_id
	 */
	public TbInstituteDepartmentInfo ajax_getDepartment_for_tidi_institute_id(Long tii_id);

	/**
	 * 根据学院的id查询下面的系，作级联下拉框用
	 * 
	 * @param tidi_institute_id
	 * @return
	 */
	List<Map<String, Object>> getInstituteDepartmentInfo_bytiiId(long tidi_institute_id);

	public List<Map<String, Object>> tbinstituteDepartmentInfoTidiName(Long tbcourseInfoId);

	/**
	 * 下拉框加载所有系
	 * 
	 * @return listMapstring
	 */
	List<Map<String, Object>> getInstituteDepartmentInfo_list();
}