package com.spring.server.service;

import java.util.List;
import java.util.Map;
import com.spring.base.service.BaseService;
import com.spring.server.entity.TbGrade;

/**
 * 年级表业务接口
 *
 * @author zhouyuan
 * @Date 2019-03-15 14:35:02
 */
 public interface TbGradeService extends BaseService<TbGrade, Long>{
	
	public Map<String, Object> findForJson(Map<String, String> params);

	/**
	 * 下拉框加载所有年级
	 * @return
	 */
	 List<Map<String, Object>> ajax_get_gradeList();
}