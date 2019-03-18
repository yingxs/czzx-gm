package com.spring.server.service;

import java.util.List;
import java.util.Map;
import com.spring.base.service.BaseService;
import com.spring.server.entity.TbCourseInfo;
import com.spring.server.entity.TbSubjectsInfo;

/**
 * 科目表业务接口
 *
 * @author 
 * @Date 2019-03-08 16:14:27
 */
 public interface TbSubjectsInfoService extends BaseService<TbSubjectsInfo, Long>{
	
	public Map<String, Object> findForJson(Map<String, String> params);

    List<TbSubjectsInfo> Ajax_Get_TbcourseInfo_TciId(Long pids);
    
    /**
	 * 添加时判断科目名是否已存在
	 * @param tsiName
	 * @return
	 */
	public TbSubjectsInfo get_Subject_By_tsiName(String tsiName);
}