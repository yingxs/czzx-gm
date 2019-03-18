package com.spring.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.spring.server.entity.TbClassRoomRelate;
import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.server.dao.TbClassRoomRelateDao;

/**
 * 班级教室关联表业务类
 *
 * @author wanghao
 * @Date 2019-03-08 11:46:40
 */
 
@Repository
public class TbClassRoomRelateDaoImpl extends BaseDaoMysqlImpl<TbClassRoomRelate, Long> implements
		TbClassRoomRelateDao {

	public TbClassRoomRelateDaoImpl(){
		super(TbClassRoomRelate.class);
	}

	/**
	 * 根据教室ID删除绑定信息
	 */
	@Override
	public int delRelieveInfo(String[] ids) {
		StringBuffer inQuerysql = new StringBuffer(" ( ");
		StringBuffer sql = new StringBuffer("");
		List<Object> valueList = new ArrayList<>();
		for(int i = 0  ; i < ids.length ;i++){
			if(i == ids.length-1){
				inQuerysql.append(" ? )");
			}else{
				inQuerysql.append(" ? ,");
			}
			valueList.add(ids[i]);
		}
		sql.append("delete from tb_class_room_relate where tcrr_room_id in ");
		sql.append(inQuerysql.toString());
		return  del(sql.toString(), valueList);
	}

	/**
	 * 根据教室id数组修改状态
	 */
	@Override
	public int updateClassRoomStatus(String[] ids) {
		StringBuffer inQuerysql = new StringBuffer(" ( ");
		StringBuffer sql = new StringBuffer("");
		for(int i = 0  ; i < ids.length ;i++){
			if(i == ids.length-1){
				inQuerysql.append(ids[i]+" )");
			}else{
				inQuerysql.append(ids[i]+" ,");
			}
		}
		sql.append("update tb_classroom_info set  tci_use_status = 0 where tci_id in ");
		sql.append(inQuerysql.toString());
		return update(sql.toString(), null);
	}
	
	
}