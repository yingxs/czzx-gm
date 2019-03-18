package com.spring.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.utils.StringUtil;
import com.spring.common.entity.PageBean;
import com.spring.server.dao.TbClassroomInfoDao;
import com.spring.server.entity.ClassRoomInfoVO;
import com.spring.server.entity.TbClassroomInfo;
import com.spring.server.service.TbClassroomInfoService;

/**
 * 教室表业务类
 *
 * @author wanghao
 * @Date 2019-03-08 11:45:36
 */
 @Service
public class TbClassroomInfoServiceImpl extends BaseServiceImpl<TbClassroomInfo, Long> implements TbClassroomInfoService {
	
	@Resource TbClassroomInfoDao TbClassroomInfoDao;

	@Override
	public BaseDao<TbClassroomInfo, Long> getGenericDao() {
		return TbClassroomInfoDao;
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
			List<Map<String,Object>> list = TbClassroomInfoDao.searchForMap(sql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String,Object>> pageBean = new PageBean<Map<String,Object>>(page, pageSize);
			pageBean = TbClassroomInfoDao.searchForMap(sql.toString(), values, pageBean);
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}
	
	@Override
	public List<TbClassroomInfo> findAllByBuildIdAndFloorId(Map<String, String> mapParams) {
		
		StringBuffer sql = new StringBuffer("");
		Long buildId = Long.valueOf(mapParams.get("buildId"));
		Long floorId = Long.valueOf(mapParams.get("floorId"));
		/*
	SELECT  
		tci_id, tci_building_id, tci_floor_id, tci_classname, tci_count, tci_status, tci_use_status, tci_user_claass_id 
	FROM  tb_classroom_info  WHERE  tci_status<>0  AND  tci_building_id=1 
	AND  tci_floor_id=1 
	ORDER BY   
		tci_classname ASC
		 */
		sql.append(" SELECT tci_id, tci_building_id, tci_floor_id, tci_classname, tci_count, tci_status, tci_use_status, tci_user_claass_id ");
		sql.append(" FROM  tb_classroom_info  WHERE  tci_status<>0  AND  tci_building_id= "+buildId);
		sql.append(" AND  tci_floor_id= "+floorId);
		sql.append(" ORDER BY");
		sql.append(" tci_id asc");
		return TbClassroomInfoDao.search(sql.toString(), null);
	}

	@Override
	public List<TbClassroomInfo> findByBuildId(Long buildId) {
		ArrayList<Object> values = new ArrayList<>();
		values.add(buildId);
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT tci_id, tci_building_id, tci_floor_id, tci_classname, tci_count, tci_status, tci_use_status, tci_user_claass_id ");
		sql.append(" FROM  tb_classroom_info  WHERE  tci_status<>0  AND  tci_building_id=? ");
		return TbClassroomInfoDao.search(sql.toString(), values);
	}

	@Override
	public List<ClassRoomInfoVO> findByIds(String[] ids) {
		
		StringBuffer inQuerysql = new StringBuffer(" ( ");
		StringBuffer sql = new StringBuffer("");
		for(int i = 0  ; i < ids.length ;i++){
			if(i == ids.length-1){
				inQuerysql.append(ids[i]+" )");
			}else{
				inQuerysql.append(ids[i]+" ,");
			}
		}
		sql.append(" SELECT tci_id,tbi_name,tci_classname ");
		sql.append(" FROM tb_classroom_info  tci ");
		sql.append(" JOIN tb_building_info tbi ");
		sql.append(" ON tci_building_id=tbi_id  WHERE  tci_status<>0 AND tbi_status<>0 AND tci_use_status=1  AND  tci_id IN ");
		sql.append(inQuerysql.toString());
		return  TbClassroomInfoDao.search(sql.toString(), null,ClassRoomInfoVO.class);
	}

	@Override
	public int batchDel(String[] ids) {
		StringBuffer inQuerysql = new StringBuffer(" ( ");
		StringBuffer sql = new StringBuffer("");
		for(int i = 0  ; i < ids.length ;i++){
			if(i == ids.length-1){
				inQuerysql.append(ids[i]+" )");
			}else{
				inQuerysql.append(ids[i]+" ,");
			}
		}
		 sql.append(" update tb_classroom_info set tci_status=0 where tci_id in "+inQuerysql.toString());
		return TbClassroomInfoDao.update(sql.toString(), null);
	}

	@Override
	public List<ClassRoomInfoVO> findById(String id) {
		StringBuffer sql = new StringBuffer("");
		sql.append(" SELECT tci_id,tbi_name,tci_classname ");
		sql.append(" FROM tb_classroom_info  tci ");
		sql.append(" JOIN tb_building_info tbi ");
		sql.append(" ON tci_id=tbi_id WHERE  tci_status<>0 AND tbi_status<>0 AND tci_use_status='1'  AND  tci_id = "+id);
		return TbClassroomInfoDao.search(sql.toString(), null,ClassRoomInfoVO.class);
	}

	@Override
	public List<TbClassroomInfo> findAll() {
		return TbClassroomInfoDao.search("SELECT * FROM tb_classroom_info WHERE tci_status<>0 ORDER BY tci_building_id ASC ,tci_classname ASC ", null);
	}
	
	
	
	
	
	
}