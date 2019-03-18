package com.spring.server.dao;

import com.spring.base.dao.BaseDao;
import com.spring.server.entity.TbClassRoomRelate;

/**
 * 班级教室关联表业务接口
 *
 * @author wanghao
 * @Date 2019-03-08 11:46:40
 */
public interface TbClassRoomRelateDao extends BaseDao<TbClassRoomRelate, Long> {
	
	//根据教室ID删除绑定信息
	int delRelieveInfo(String[] ids);
	//根据教室id数组修改状态
	int updateClassRoomStatus(String[] ids);
		

}