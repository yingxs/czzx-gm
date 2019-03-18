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

import com.spring.server.dao.TbGradeDao;
import com.spring.server.entity.TbGrade;
import com.spring.server.service.TbGradeService;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.dao.BaseDao;

/**
 * 年级表业务类
 *
 * @author zhouyuan
 * @Date 2019-03-15 14:35:02
 */
@Service
public class TbGradeServiceImpl extends BaseServiceImpl<TbGrade, Long> implements TbGradeService {

	@Resource
	TbGradeDao TbGradeDao;

	@Override
	public BaseDao<TbGrade, Long> getGenericDao() {
		return TbGradeDao;
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
			List<Map<String, Object>> list = TbGradeDao.searchForMap(sql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		} else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = TbGradeDao.searchForMap(sql.toString(), values, pageBean);
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	@Override
	public List<Map<String, Object>> ajax_get_gradeList() {
		StringBuffer sql = new StringBuffer();
		sql.append("select tg_id,tg_name from tb_grade where  tg_status=1 ");
		List<Map<String, Object>> searchForMap = TbGradeDao.searchForMap(sql + "", null);
		return searchForMap;
	}
}