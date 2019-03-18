package com.spring.server.service;

import java.util.List;
import java.util.Map;
import com.spring.base.service.BaseService;
import com.spring.server.entity.ClassRoomInfoVO;
import com.spring.server.entity.TbClassroomInfo;
import com.spring.server.entity.TbFloorInfo;

/**
 * 教室表业务接口
 *
 * @author wanghao
 * @Date 2019-03-08 11:45:36
 */
 public interface TbClassroomInfoService extends BaseService<TbClassroomInfo, Long>{
	
	public Map<String, Object> findForJson(Map<String, String> params);
	
	//根据楼宇ID和楼层ID查询教室
	List<TbClassroomInfo> findAllByBuildIdAndFloorId(Map<String, String> mapParams);
	
	// 根据楼宇ID查询教室
	List<TbClassroomInfo> findByBuildId(Long buildId);
	
	//根据多个教室ID查询多个教室
	List<ClassRoomInfoVO> findByIds(String[] ids);
	
	//批量删除教室
	int batchDel(String[] ids);
	
	//根据ID查询教室
	List<ClassRoomInfoVO> findById(String id);
	
	
	
	
}