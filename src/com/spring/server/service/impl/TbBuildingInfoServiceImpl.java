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
import com.spring.server.dao.TbBuildingInfoDao;
import com.spring.server.entity.TbBuildingInfo;
import com.spring.server.service.TbBuildingInfoService;

/**
 * 教学楼业务类
 *
 * @author wanghao
 * @Date 2019-03-08 09:49:40
 */
 @Service
public class TbBuildingInfoServiceImpl extends BaseServiceImpl<TbBuildingInfo, Long> implements TbBuildingInfoService {
	
	@Resource TbBuildingInfoDao TbBuildingInfoDao;

	@Override
	public BaseDao<TbBuildingInfo, Long> getGenericDao() {
		return TbBuildingInfoDao;
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
			List<Map<String,Object>> list = TbBuildingInfoDao.searchForMap(sql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String,Object>> pageBean = new PageBean<Map<String,Object>>(page, pageSize);
			pageBean = TbBuildingInfoDao.searchForMap(sql.toString(), values, pageBean);
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	@Override
	public List<TbBuildingInfo> findAllBylikeBuildName(String buildName) {
		if (buildName == null || "".equals(buildName)){
			return TbBuildingInfoDao.search("SELECT tbi_id,tbi_name,tbi_status,tbi_region_id FROM tb_building_info WHERE tbi_status <> 0  order by tbi_id ", null);
		}
		return TbBuildingInfoDao.search("SELECT tbi_id,tbi_name,tbi_status,tbi_region_id FROM tb_building_info WHERE tbi_status <> 0 and tbi_name like '%"+buildName+"%'  order by tbi_id ", null);
	}
	@Override
	public List<TbBuildingInfo> findAllByBuildName(String buildName) {
		return TbBuildingInfoDao.search("SELECT tbi_id,tbi_name,tbi_status,tbi_region_id FROM tb_building_info WHERE tbi_status <> 0 and tbi_name = '"+buildName+"'  order by tbi_id ", null);
	}

	
	@Override
	public List<TbBuildingInfo> findAll() {
		return TbBuildingInfoDao.search("SELECT tbi_id,tbi_name,tbi_status,tbi_region_id FROM tb_building_info WHERE tbi_status <> 0  order by tbi_id ", null);
	}

	
	
	
	@Override
	public List<TbBuildingInfo> findAllBylikeBuildName(String buildName, String buildId) {
		if (buildName == null || "".equals(buildName)){
			return TbBuildingInfoDao.search("SELECT tbi_id,tbi_name,tbi_status,tbi_region_id FROM tb_building_info WHERE tbi_status <> 0  and tbi_id<>"+buildId+"  order by tbi_id ", null);
		}
		return TbBuildingInfoDao.search("SELECT tbi_id,tbi_name,tbi_status,tbi_region_id FROM tb_building_info WHERE tbi_status <> 0 and tbi_name = '"+buildName+"'  and tbi_id<>"+buildId+"  order by tbi_id ", null);
	}

	
	

	
	
	
	
	
}