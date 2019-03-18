package com.spring.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.utils.StringUtil;
import com.spring.common.entity.PageBean;
import com.spring.server.dao.TbClassRoomRelateDao;
import com.spring.server.entity.ClassRoomInfoVO;
import com.spring.server.entity.TbClassRoomRelate;
import com.spring.server.service.TbClassRoomRelateService;

/**
 * 班级教室关联表业务类
 *
 * @author wanghao
 * @Date 2019-03-08 11:46:40
 */
 @Service
public class TbClassRoomRelateServiceImpl extends BaseServiceImpl<TbClassRoomRelate, Long> implements TbClassRoomRelateService {
	
	@Resource TbClassRoomRelateDao TbClassRoomRelateDao;

	@Override
	public BaseDao<TbClassRoomRelate, Long> getGenericDao() {
		return TbClassRoomRelateDao;
	}
	
	
	/**
	 * 根据id数组解绑教室
	 */
	@Override
	@Transactional
	public int relieveClassRoome(String[] ids) {
		try {
			int delCount = TbClassRoomRelateDao.delRelieveInfo(ids);
			int updateCount = TbClassRoomRelateDao.updateClassRoomStatus(ids);
			if (delCount == updateCount && delCount > 0 ) {
				return updateCount;
			}
			throw new Exception("执行错误，未知异常");
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚
			return -1;
		}
		
	}
	
	
	
	
	@Override
	public Map<String, Object> findForJson(Map<String, String> params) {
		Map<String, Object> json = new HashMap<String, Object>();
		String AddDate = params.get("AddDate");
		String AddDateEnd = params.get("AddDateEnd");
		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("rows") == null ? 0:Integer.parseInt(params.get("rows"));
		String sort = params.get("sort");
		String order = params.get("order");
		List<Object> values = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		//逻辑判断开始
		
		
		//逻辑判断结束
		if(!StringUtils.isEmpty(AddDate)){
			sql.append(" AND DATE_FORMAT(XXXX,'%Y-%m-%d') >= DATE_FORMAT('"+AddDate+"','%Y-%m-%d')");
		}
		if(!StringUtils.isEmpty(AddDateEnd)){
			sql.append(" AND DATE_FORMAT(XXXX,'%Y-%m-%d') <= DATE_FORMAT('"+AddDateEnd+"','%Y-%m-%d')");
		}
		if(!StringUtil.isEmptyNull(sort)){
			sql.append(" order by  " + sort);
		}
		if(!StringUtil.isEmptyNull(order)){
			sql.append("  " + order);
		}
		if (pageSize == 0) {
			List<Map<String,Object>> list = TbClassRoomRelateDao.searchForMap(sql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String,Object>> pageBean = new PageBean<Map<String,Object>>(page, pageSize);
			pageBean = TbClassRoomRelateDao.searchForMap(sql.toString(), values, pageBean);
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	// 查询教室id数组中没有被占用的教室并返回
	@Override
	public List<ClassRoomInfoVO> findNotUse(String[] ids) {
		StringBuffer inQuerysql = new StringBuffer(" ( ");
		StringBuffer sql = new StringBuffer("");
		for(int i = 0  ; i < ids.length ;i++){
			if(i == ids.length-1){
				inQuerysql.append(ids[i]+" )");
			}else{
				inQuerysql.append(ids[i]+" ,");
			}
		}
		
		sql.append("  SELECT  a.tci_id,a.tci_classname ");
		sql.append("  FROM   tb_classroom_info a  ");
		sql.append("  LEFT JOIN  tb_class_room_relate b  ");
		sql.append(" ON 	 a.tci_id = b.tcrr_room_id 	 ");
		sql.append(" WHERE  b.tcrr_room_id IS NULL AND a.tci_id IN	 ");
		sql.append(inQuerysql.toString());
		
		return TbClassRoomRelateDao.search(sql.toString(),null,ClassRoomInfoVO.class);
	}

	@Override
	public  List<ClassRoomInfoVO> findNotUseById(String id) {
		StringBuffer sql = new StringBuffer("");
		sql.append("  SELECT  tci_id, tbi_name, tci_classname ");
		sql.append("  FROM  tb_class_room_relate ");
		sql.append("  JOIN tb_building_info ");
		sql.append(" JOIN tb_classroom_info	 ");
		sql.append(" ON tbi_id = tci_building_id	 ");
		sql.append(" AND tcrr_room_id = tci_id AND tci_status<>0 AND tbi_status<>0 tci_id="+id);
		
		return TbClassRoomRelateDao.search(sql.toString(), null, ClassRoomInfoVO.class);
	}

	
	
	
	
	

	
}