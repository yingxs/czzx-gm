package com.spring.server.service;

import java.util.Map;
import com.spring.base.service.BaseService;
import com.spring.server.entity.TbMajorCourseRelate;
import com.spring.server.entity.TbCourseInfo;

/**
 * 专业课程关联表业务接口
 *
 * @author
 * @Date 2019-03-12 11:23:00
 */
 public interface TbMajorCourseRelateService extends BaseService<TbMajorCourseRelate, Long>{
	
	public Map<String, Object> findForJson(Map<String, String> params);
    //添加
    public void InsertInTo(String id);

   void saveTbcourseInfo(TbCourseInfo tbcourseInfo, String[] testName, Long loginId) throws Exception;
}