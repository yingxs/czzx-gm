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
import com.spring.server.entity.TbClassInfo;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.server.service.TbClassInfoService;
import com.spring.server.dao.TbClassInfoDao;
import com.spring.base.dao.BaseDao;

/**
 * 班级表业务类
 *
 * @author zhouyuan
 * @Date 2019-03-16 23:40:17
 */
@Service
public class TbClassInfoServiceImpl extends BaseServiceImpl<TbClassInfo, Long> implements TbClassInfoService {

	@Resource
	TbClassInfoDao TbClassInfoDao;

	@Override
	public BaseDao<TbClassInfo, Long> getGenericDao() {
		return TbClassInfoDao;
	}

	@Override
	public Map<String, Object> findForJson(Map<String, String> params) {
		Map<String, Object> json = new HashMap<String, Object>();
		String AddDate = params.get("AddDate");
		String AddDateEnd = params.get("AddDateEnd");
		int page = params.get("page") == null ? 0 : Integer.parseInt(params.get("page"));
		int pageSize = params.get("rows") == null ? 0 : Integer.parseInt(params.get("rows"));
		String sort = params.get("sort");
		String order = params.get("order");
		List<Object> values = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		// 逻辑判断开始

		// 逻辑判断结束
		if (!StringUtils.isEmpty(AddDate)) {
			sql.append(" AND DATE_FORMAT(XXXX,'%Y-%m-%d') >= DATE_FORMAT('" + AddDate + "','%Y-%m-%d')");
		}
		if (!StringUtils.isEmpty(AddDateEnd)) {
			sql.append(" AND DATE_FORMAT(XXXX,'%Y-%m-%d') <= DATE_FORMAT('" + AddDateEnd + "','%Y-%m-%d')");
		}
		if (!StringUtil.isEmptyNull(sort)) {
			sql.append(" order by  " + sort);
		}
		if (!StringUtil.isEmptyNull(order)) {
			sql.append("  " + order);
		}
		if (pageSize == 0) {
			List<Map<String, Object>> list = TbClassInfoDao.searchForMap(sql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		} else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = TbClassInfoDao.searchForMap(sql.toString(), values, pageBean);
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	@Override
	public List<Map<String, Object>> ajax_getClass_byGradeId_AndInstituteId_AndInstituteDepartmentId(
			long tc_institute_id, long tc_institute_department_id, long tc_grade) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tc_id,tc_name from tb_class where  tc_status=1 and tc_institute_id=" + tc_institute_id
				+ " and tc_institute_department_id=" + tc_institute_department_id + " and tc_grade=" + tc_grade);
		List<Map<String, Object>> searchForMap = TbClassInfoDao.searchForMap(sql + "", null);
		return searchForMap;
	}
}