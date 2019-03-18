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
import com.spring.server.entity.TbInstituteDepartmentInfo;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.server.service.TbInstituteDepartmentInfoService;
import com.spring.server.dao.TbInstituteDepartmentInfoDao;
import com.spring.base.dao.BaseDao;

/**
 * 系信息表业务类
 *
 * @author hxx
 * @Date 2019-03-05 16:46:54
 */
@Service
public class TbInstituteDepartmentInfoServiceImpl extends BaseServiceImpl<TbInstituteDepartmentInfo, Long>
		implements TbInstituteDepartmentInfoService {

	@Resource
	TbInstituteDepartmentInfoDao tbinstituteDepartmentInfoDao;

	@Override
	public BaseDao<TbInstituteDepartmentInfo, Long> getGenericDao() {
		return tbinstituteDepartmentInfoDao;
	}

	@Override
	public Map<String, Object> findForJson(Map<String, String> params) {
		Map<String, Object> json = new HashMap<String, Object>();
		// 系名称
		String tidiName = params.get("tidiName");
		// 学院名称
		String tiiName = params.get("tiiName");
		// 开始时间
		String AddDate = params.get("AddDate");
		// 结束时间
		String AddDateEnd = params.get("AddDateEnd");

		System.out.println("系名称" + tidiName + "学院名称" + tiiName + "开始时间|" + AddDate + "结束时间" + AddDateEnd);

		int page = params.get("page") == null ? 0 : Integer.parseInt(params.get("page"));
		int pageSize = params.get("rows") == null ? 0 : Integer.parseInt(params.get("rows"));
		String sort = params.get("sort");
		String order = params.get("order");
		List<Object> values = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		// 逻辑判断开始

		sql.append(
				"SELECT  f.tidi_id,f.tidi_name, f.tidi_institute_id, f.tidi_icon, f.tidi_order, DATE_FORMAT( f.tidi_add_date, '%Y-%m-%d %H:%i:%s' ) dates, x.tii_name  FROM ");
		sql.append("tb_institute_department_info f  INNER JOIN tb_institute_info X ON f.tidi_institute_id = x.tii_id ");
		sql.append("WHERE f.tidi_status <> 0 and f.tidi_name LIKE ").append("'%" + tidiName + "%'");

		if (!StringUtils.isEmpty(tiiName)) {
			sql.append(" AND tidi_institute_id =").append(tiiName);
		}

		if (!StringUtils.isEmpty(AddDate)) {
			sql.append(" AND f.tidi_add_date>= DATE_FORMAT( '" + AddDate + "', '%Y-%m-%d' ) ");
		}

		if (!StringUtils.isEmpty(AddDateEnd)) {
			sql.append(" AND f.tidi_add_date>= DATE_FORMAT( '" + AddDateEnd + "', '%Y-%m-%d' ) ");
		}

		System.out.println(sql);
		// 逻辑判断结束
		if (pageSize == 0) {
			List<Map<String, Object>> list = tbinstituteDepartmentInfoDao.searchForMap(sql.toString(), values);
			System.out.println("###############" + list);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		} else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbinstituteDepartmentInfoDao.searchForMap(sql.toString(), values, pageBean);
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	@Override
	public TbInstituteDepartmentInfo ajax_getDepartment_for_tidi_institute_id(Long tii_id) {
		TbInstituteDepartmentInfo tbInstituteDepartmentInfo = new TbInstituteDepartmentInfo();
		tbInstituteDepartmentInfo.setTidiInstituteId(tii_id);
		TbInstituteDepartmentInfo result = null;
		result = tbinstituteDepartmentInfoDao.searchOne(tbInstituteDepartmentInfo);
		return result;

	}

	@Override
	public List<Map<String, Object>> getInstituteDepartmentInfo_bytiiId(long tidi_institute_id) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select tidi_id,tidi_name,tidi_institute_id from tb_institute_department_info where  tidi_status=1 and tidi_institute_id="
						+ tidi_institute_id);
		List<Map<String, Object>> searchForMap = tbinstituteDepartmentInfoDao.searchForMap(sql + "", null);
		return searchForMap;
	}

	@Override
	public List<Map<String, Object>> tbinstituteDepartmentInfoTidiName(Long tbcourseInfoId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getInstituteDepartmentInfo_list() {
		StringBuffer sql = new StringBuffer();
		sql.append("select tidi_id,tidi_name from tb_institute_department_info where  tidi_status=1 ");
		List<Map<String, Object>> searchForMap = tbinstituteDepartmentInfoDao.searchForMap(sql + "", null);
		return searchForMap;
	}
}