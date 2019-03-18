package com.spring.server.service.impl;

import org.springframework.stereotype.Service;
import com.spring.base.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import com.spring.common.entity.PageBean;
import org.springframework.transaction.annotation.Transactional;
import com.spring.server.entity.TbFloorInfo;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.server.service.TbFloorInfoService;
import com.spring.server.dao.TbFloorInfoDao;
import com.spring.base.dao.BaseDao;

/**
 * 楼层表业务类
 *
 * @author wanghao
 * @Date 2019-03-08 11:44:38
 */
 @Service
public class TbFloorInfoServiceImpl extends BaseServiceImpl<TbFloorInfo, Long> implements TbFloorInfoService {
	
	@Resource TbFloorInfoDao TbFloorInfoDao;

	@Override
	public BaseDao<TbFloorInfo, Long> getGenericDao() {
		return TbFloorInfoDao;
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
			List<Map<String,Object>> list = TbFloorInfoDao.searchForMap(sql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String,Object>> pageBean = new PageBean<Map<String,Object>>(page, pageSize);
			pageBean = TbFloorInfoDao.searchForMap(sql.toString(), values, pageBean);
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	@Override
	public List<TbFloorInfo> findAllByBuildId(Map<String, String> mapParams) {
		List<Object> values = new ArrayList<>();
		Long buildId = Long.valueOf(mapParams.get("buildId"));
		String order = mapParams.get("order");
		return TbFloorInfoDao.search(" SELECT  tfi_id, tfi_name, tfi_status, tfi_building_id "
				+ " FROM tb_floor_info WHERE tfi_status <> 0 "
				+ " AND tfi_building_id = "+buildId
				+ " ORDER BY tfi_id ", null);
	}

	@Override
	public List<TbFloorInfo> finAllByName(String floorName,String buildId) {
		List<Object> values = new ArrayList<>();
		values.add(floorName);
		values.add(buildId);
		return TbFloorInfoDao.search("select tfi_id, tfi_name, tfi_status, tfi_building_id "
				+ "FROM tb_floor_info WHERE tfi_status <> 0 "
				+ "and tfi_name=? and tfi_building_id =?  ORDER BY tfi_id ", values);
	}

	
}