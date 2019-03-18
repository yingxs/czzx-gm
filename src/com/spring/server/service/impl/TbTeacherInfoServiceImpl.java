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
import com.spring.server.entity.TbTeacherInfo;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.server.service.TbTeacherInfoService;
import com.spring.server.dao.TbTeacherInfoDao;
import com.spring.base.dao.BaseDao;

/**
 * 教师表业务类
 *
 * @author hxx
 * @Date 2019-03-13 16:55:07
 */
@Service
public class TbTeacherInfoServiceImpl extends BaseServiceImpl<TbTeacherInfo, Long> implements TbTeacherInfoService {

	// 教师表
	@Resource
	TbTeacherInfoDao TbTeacherInfoDao;

	@Override
	public BaseDao<TbTeacherInfo, Long> getGenericDao() {
		return TbTeacherInfoDao;
	}

	@Override
	public Map<String, Object> findForJson(Map<String, String> params) {
		Map<String, Object> json = new HashMap<String, Object>();
		String ttiName = params.get("ttiName");
		String ttiSex = params.get("ttiSex");
		String ttiPhone = params.get("ttiPhone");
		String AddDate = params.get("AddDate");
		String AddDateEnd = params.get("AddDateEnd");
		int page = params.get("page") == null ? 0 : Integer.parseInt(params.get("page"));
		int pageSize = params.get("rows") == null ? 0 : Integer.parseInt(params.get("rows"));
		String sort = params.get("sort");
		String order = params.get("order");
		List<Object> values = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		// 逻辑判断开始

		sql.append(
				"SELECT j.tti_id,j.tti_number,j.tti_name,j.tti_sex,j.tti_phone,j.tti_score,DATE_FORMAT(j.tti_addtime,'%Y-%m-%d %h:%i:%s') dates FROM tb_teacher_info j ");
		sql.append(" where j.tti_status= 1");

		// 模糊教师姓名
		if (!StringUtils.isEmpty(ttiName)) {
			sql.append(" AND j.tti_name LIKE " + "'%" + ttiName + "%'");
		}
		// 性别条件搜索

		if (!StringUtils.isEmpty(ttiSex)) {
			sql.append(" AND j.tti_sex= " + ttiSex);
		}
		// 电话
		if (!StringUtils.isEmpty(ttiPhone)) {
			sql.append(" AND j.tti_phone LIKE " + "'%" + ttiPhone + "%'");
		}

		if(!StringUtils.isEmpty(AddDate)){
			sql.append(" AND DATE_FORMAT(j.tti_addtime,'%Y-%m-%d') >= DATE_FORMAT('"+AddDate+"','%Y-%m-%d')");
		}
		if(!StringUtils.isEmpty(AddDateEnd)){
			sql.append(" AND DATE_FORMAT(j.tti_addtime,'%Y-%m-%d') <= DATE_FORMAT('"+AddDateEnd+"','%Y-%m-%d')");
		}

		if (!StringUtil.isEmptyNull(sort)) {
			sql.append(" order by  " + sort);
		}
		if (!StringUtil.isEmptyNull(order)) {
			sql.append("  " + order);
		}

		if (pageSize == 0) {
			List<Map<String, Object>> list = TbTeacherInfoDao.searchForMap(sql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		} else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = TbTeacherInfoDao.searchForMap(sql.toString(), values, pageBean);
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}
}