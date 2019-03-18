package com.spring.server.service.impl;

import com.spring.server.entity.TbCourseInfo;
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
import com.spring.server.entity.TbSubjectsInfo;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.server.service.TbSubjectsInfoService;
import com.spring.server.dao.TbSubjectsInfoDao;
import com.spring.base.dao.BaseDao;

/**
 * 科目表业务类
 *
 * @author hxx
 * @Date 2019-03-08 16:14:27
 */
 @Service
public class TbSubjectsInfoServiceImpl extends BaseServiceImpl<TbSubjectsInfo, Long> implements TbSubjectsInfoService {
	
	@Resource TbSubjectsInfoDao TbsubjectsInfoDao;

	@Override
	public BaseDao<TbSubjectsInfo, Long> getGenericDao() {
		return TbsubjectsInfoDao;
	}
	
	@Override
	public Map<String, Object> findForJson(Map<String, String> params) {
		Map<String, Object> json = new HashMap<String, Object>();

		String subjectNameLike1 = params.get("subjectNameLike");
		String subjectNameLike=subjectNameLike1.trim();
		if(StringUtil.isEmptyNull(subjectNameLike1)){
			subjectNameLike=subjectNameLike1.trim();
		}

		String AddDate = params.get("AddDate");
		String AddDateEnd = params.get("AddDateEnd");
		int page = params.get("page") == null ? 0 : Integer.parseInt(params.get("page"));
		int pageSize = params.get("rows") == null ? 0 : Integer.parseInt(params.get("rows"));
		String sort = params.get("sort");
		String order = params.get("order");
		List<Object> values = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		// 逻辑判断开始
		sql.append("select * from tb_subjects_info where tsi__status=1 ");
		if (!StringUtils.isEmpty(subjectNameLike)) {
			sql.append(" AND tsi_name LIKE '%" + subjectNameLike + "%' ");
		}
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
			List<Map<String, Object>> list = TbsubjectsInfoDao.searchForMap(sql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		} else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = TbsubjectsInfoDao.searchForMap(sql.toString(), values, pageBean);
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	@Override
	public TbSubjectsInfo get_Subject_By_tsiName(String tsiName) {
		TbSubjectsInfo tbSubjectsInfo = new TbSubjectsInfo();
		tbSubjectsInfo.setTsiStatus(1);
		tbSubjectsInfo.setTsiName(tsiName);
		TbSubjectsInfo result = null;
		result = TbsubjectsInfoDao.searchOne(tbSubjectsInfo);
		return result;
	}
	
	@Override
	public List<TbSubjectsInfo> Ajax_Get_TbcourseInfo_TciId(Long pids) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT tsi_id, tsi_name FROM tb_subjects_info WHERE tsi_id = "+pids);
		List<TbSubjectsInfo> search = TbsubjectsInfoDao.search(sbSql.toString(), null);
		return search;
	}
}