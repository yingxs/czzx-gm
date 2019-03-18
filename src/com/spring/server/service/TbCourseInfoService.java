package com.spring.server.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.spring.base.service.BaseService;
import com.spring.server.entity.TbCourseInfo;
import com.spring.server.entity.TbMajorInfo;

/**
 * 课程信息表业务接口
 *
 * @author
 * @Date 2019-03-08 11:38:49
 */
public interface TbCourseInfoService extends BaseService<TbCourseInfo, Long> {

	public Map<String, Object> findForJson(Map<String, String> params);

	public List<TbCourseInfo> getFindById(Long tbcourseInfoId);

	// 查询一个对象
	public TbCourseInfo findByIds(Long tbcourseInfoId);

	// 修改状态
	public Integer updateByIds(Long tbcourseInfoId);

	// 弹框
	public Map<String, Object> findForJsons(HashMap<String, String> params);

	public TbCourseInfo get_course_forSubjectDown(Long tsi_id);
	
	
	
	
	/**
	 * wanghao
	 */
	List<TbCourseInfo> findByMajor(TbMajorInfo tbMajorInfo);
	
	
	
	

}