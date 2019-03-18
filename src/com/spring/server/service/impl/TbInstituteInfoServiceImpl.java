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
import com.spring.server.entity.TbInstituteInfo;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.server.service.TbInstituteInfoService;
import com.spring.server.dao.TbInstituteInfoDao;
import com.spring.base.dao.BaseDao;

/**
 * 学院信息表业务类
 *
 * @author hxx
 * @Date 2019-03-05 14:07:28
 */
@Service
public class TbInstituteInfoServiceImpl extends BaseServiceImpl<TbInstituteInfo, Long>
		implements TbInstituteInfoService {

	@Resource
	TbInstituteInfoDao tbInstituteInfoDao;

	@Override
	public BaseDao<TbInstituteInfo, Long> getGenericDao() {
		return tbInstituteInfoDao;
	}

	@Override
	public Map<String, Object> findForJson(Map<String, String> params) {
		Map<String, Object> json = new HashMap<String, Object>();
		String InstituteNameLike1 = params.get("InstituteNameLike");
		String InstituteNameLike = null;
		if (StringUtil.isEmptyNull(InstituteNameLike1)) {
			InstituteNameLike = InstituteNameLike1.trim();
		}
		// 开始时间
		String AddDate = params.get("AddDate");
		// 结束时间
		String AddDateEnd = params.get("AddDateEnd");
		int page = params.get("page") == null ? 0 : Integer.parseInt(params.get("page"));
		int pageSize = params.get("rows") == null ? 0 : Integer.parseInt(params.get("rows"));
		String sort = params.get("sort");
		String order = params.get("order");
		List<Object> values = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		// 逻辑判断开始
		sql.append("select * from tb_institute_info where tii_status=1 ");

		if (!StringUtils.isEmpty(InstituteNameLike)) {
			sql.append(" AND tii_name Like '%" + InstituteNameLike + "%' ");
		}

		// 逻辑判断结束
		if (!StringUtils.isEmpty(AddDate)) {
			sql.append(" AND DATE_FORMAT(tii_add_date,'%Y-%m-%d') >= DATE_FORMAT('" + AddDate + "','%Y-%m-%d')");
		}
		if (!StringUtils.isEmpty(AddDateEnd)) {
			sql.append(" AND DATE_FORMAT(tii_add_date,'%Y-%m-%d') <= DATE_FORMAT('" + AddDateEnd + "','%Y-%m-%d')");
		}
		if (!StringUtil.isEmptyNull(sort)) {
			sql.append(" order by  " + sort);
		}
		if (!StringUtil.isEmptyNull(order)) {
			sql.append("  " + order);
		}
		if (pageSize == 0) {
			List<Map<String, Object>> list = tbInstituteInfoDao.searchForMap(sql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		} else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbInstituteInfoDao.searchForMap(sql.toString(), values, pageBean);
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	@Override
	public TbInstituteInfo alax_get_institute_forTiiName(String tiiName) {
		TbInstituteInfo tbInstituteInfo = new TbInstituteInfo();
		tbInstituteInfo.setTiiName(tiiName);
		tbInstituteInfo.setTiiStatus(1);
		TbInstituteInfo result = null;
		result = tbInstituteInfoDao.searchOne(tbInstituteInfo);
		return result;
	}

	@Override
	public TbInstituteInfo alax_get_institute_forOrder(String newOrder) {
		TbInstituteInfo tbInstituteInfo = new TbInstituteInfo();
		tbInstituteInfo.setTiiOrder(Integer.valueOf(newOrder));
		tbInstituteInfo.setTiiStatus(1);
		TbInstituteInfo result = null;
		result = tbInstituteInfoDao.searchOne(tbInstituteInfo);
		return result;
	}

	@Override
	public List<Map<String, Object>> ajax_get_instituteIdAndNameList() {
		StringBuffer sql = new StringBuffer();
		sql.append("select tii_id,tii_name from tb_institute_info where  tii_status=1 ");
		List<Map<String, Object>> searchForMap = tbInstituteInfoDao.searchForMap(sql + "", null);
		return searchForMap;
	}
}