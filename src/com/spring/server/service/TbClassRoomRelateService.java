package com.spring.server.service;

import java.util.List;
import java.util.Map;

import com.spring.base.service.BaseService;
import com.spring.server.entity.ClassRoomInfoVO;
import com.spring.server.entity.TbClassRoomRelate;

/**
 * 班级教室关联表业务接口
 *
 * @author wanghao
 * @Date 2019-03-08 11:46:40
 */
 public interface TbClassRoomRelateService extends BaseService<TbClassRoomRelate, Long>{
	
	public Map<String, Object> findForJson(Map<String, String> params);
	
	//查询教室id数组中没有被占用的教室并返回
	List<ClassRoomInfoVO> findNotUse(String[] ids);
	
	//查询教室id数组中没有被占用的教室并返回
	List<ClassRoomInfoVO> findNotUseById(String id) ;
	
	//解除绑定
	int relieveClassRoome(String[] ids);
	
	
	
}