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
import com.spring.server.entity.TbCourseInfo;
import com.spring.server.entity.TbMajorInfo;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.server.service.TbCourseInfoService;
import com.spring.server.dao.TbCourseInfoDao;
import com.spring.base.dao.BaseDao;

/**
 * 课程信息表业务类
 *
 * @author hxx
 * @Date 2019-03-08 11:38:49
 */
@Service
public class TbCourseInfoServiceImpl extends BaseServiceImpl<TbCourseInfo, Long> implements TbCourseInfoService {

	@Resource
	TbCourseInfoDao tbcourseInfoDao;

	@Override
	public BaseDao<TbCourseInfo, Long> getGenericDao() {
		return tbcourseInfoDao;
	}

	@Override
	public Map<String, Object> findForJson(Map<String, String> params) {
		Map<String, Object> json = new HashMap<String, Object>();

		String tciName = params.get("tciName"); // 课程名称
		String tsiName = params.get("tsiName"); // 科目名称
		String AddDate = params.get("AddDate");
		String AddDateEnd = params.get("AddDateEnd");

		// System.out.println("AddDate"+AddDate+"AddDateEnd"+AddDateEnd+"课程名称"+tciName+"科目名称"+tsiName);
		int page = params.get("page") == null ? 0 : Integer.parseInt(params.get("page"));
		int pageSize = params.get("rows") == null ? 0 : Integer.parseInt(params.get("rows"));
		String sort = params.get("sort");

		String order = params.get("order");

		List<Object> values = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		// 逻辑判断开始
		sql.append(
				" SELECT tci_id,tci_name,tci_code,tci_subjects_id,DATE_FORMAT(tci_add_time,'%Y-%m-%d %h:%i:%s') dates,tsi_name ");
		sql.append(" FROM  tb_course_info LEFT JOIN tb_subjects_info ON tci_subjects_id = tsi_id WHERE tci_status=1 ");

		// 逻辑判断结束
		if (!StringUtils.isEmpty(tciName)) {
			sql.append(" and  tci_name LIKE " + "'%" + tciName + "%'");
		}
		if (!StringUtils.isEmpty(tsiName)) {
			sql.append(" AND tsi_name LIKE " + "'%" + tsiName + "%'");
		}
		if (!StringUtils.isEmpty(AddDate)) {
			sql.append(" AND DATE_FORMAT(tci_add_time,'%Y-%m-%d') >= DATE_FORMAT('" + AddDate + "','%Y-%m-%d')");
		}
		if (!StringUtils.isEmpty(AddDateEnd)) {
			sql.append(" AND DATE_FORMAT(tci_add_time,'%Y-%m-%d') <= DATE_FORMAT('" + AddDateEnd + "','%Y-%m-%d')");
		}
		if (!StringUtil.isEmptyNull(sort)) {
			sql.append(" order by  " + sort);
		}
		if (!StringUtil.isEmptyNull(order)) {
			sql.append("  " + order);
		}
		if (pageSize == 0) {
			List<Map<String, Object>> list = tbcourseInfoDao.searchForMap(sql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		} else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbcourseInfoDao.searchForMap(sql.toString(), values, pageBean);
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	/**
	 * 二级联动
	 * 
	 * @param tbcourseInfoId
	 * @return
	 */
	@Override
	public List<TbCourseInfo> getFindById(Long tbcourseInfoId) {
		StringBuffer sql = new StringBuffer();
		// 逻辑判断开始
		sql.append(
				"SELECT s.tci_id,s.tci_name FROM tb_course_info s INNER JOIN tb_subjects_info k ON k.tsi_id=s.tci_subjects_id ");

		if (!tbcourseInfoId.toString().isEmpty()) {
			sql.append("WHERE tci_id= " + tbcourseInfoId);
		}
		List<TbCourseInfo> searchp = tbcourseInfoDao.searchp(sql.toString(), null);
		if (searchp.isEmpty()) {
			return null;

		}
		return searchp;
	}

	// 查询一个对象
	public TbCourseInfo findByIds(Long tbcourseInfoId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from tb_course_info where tci_status =1 and tci_id = ");

		// 判断
		if (!tbcourseInfoId.equals(null) && tbcourseInfoId.toString().length() < 0) {
			sql.append(tbcourseInfoId);
		}
		TbCourseInfo tbcourseInfo = tbcourseInfoDao.get(tbcourseInfoId);
		System.out.println("#################################" + tbcourseInfo);
		return tbcourseInfo;
	}

	/**
	 * 删除
	 * 
	 * @param tbcourseInfoId
	 * @return
	 */
	public Integer updateByIds(Long tbcourseInfoId) {
		StringBuffer sql = new StringBuffer();
		sql.append("update tb_course_info set tci_status = 0 where tci_id = ");
		if (!tbcourseInfoId.toString().isEmpty()) {
			sql.append(tbcourseInfoId);
		}
		int updatep = tbcourseInfoDao.updatep(sql.toString(), null);
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$" + updatep);
		return updatep;
	}

	/**
	 * 弹框
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public Map<String, Object> findForJsons(HashMap<String, String> params) {
		Map<String, Object> json = new HashMap<String, Object>();
		String tciName = params.get("tciName"); // 学院名称
		String tsiName = params.get("tsiName"); // 系名称
		String tmiName = params.get("tmiName"); // 专业名称
		String strName = params.get("strName");
		System.out.println("tciName" + tciName + "tsiName" + tsiName + "tmiName" + tmiName);
		// System.out.println("AddDate"+AddDate+"AddDateEnd"+AddDateEnd+"课程名称"+tciName+"科目名称"+tsiName);
		int page = params.get("page") == null ? 0 : Integer.parseInt(params.get("page"));
		int pageSize = params.get("rows") == null ? 0 : Integer.parseInt(params.get("rows"));
		String sort = params.get("sort");
		String order = params.get("order");
		List<Object> values = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		// 逻辑判断开始
		/*
		 * sql.append(
		 * "SELECT u.tii_name,x.tidi_name,z.tmi_name,z.tmi_code FROM tb_institute_info u INNER JOIN  tb_institute_department_info X INNER JOIN  tb_major_info z  ON u.tii_id=x.tidi_institute_id AND u.tii_id=z.tmi_institute_id "
		 * ); sql.append(" WHERE x.tidi_id=z.tmi_department_id ");
		 */
		sql.append(
				"SELECT tmi_id,u.tii_name,x.tidi_name,z.tmi_name,z.tmi_code  FROM tb_institute_info u  INNER JOIN  tb_institute_department_info X ON u.tii_id=x.tidi_institute_id INNER JOIN  tb_major_info z  ON tidi_id = tmi_department_id ");
		sql.append(" WHERE tii_status <> 0 ");

		// 逻辑判断结束
		// 学院名称
		if (!StringUtils.isEmpty(strName)) {
			sql.append(" AND z.tmi_id NOT IN ( " + strName + " )");
		}
		if (!StringUtils.isEmpty(tciName)) {
			sql.append(" AND z.tmi_institute_id =" + tciName);
		}
		// 系名称
		if (!StringUtils.isEmpty(tsiName)) {
			sql.append(" AND z.tmi_department_id = " + tsiName);
		}
		// 专业名称
		if (!StringUtils.isEmpty(tmiName)) {
			sql.append(" AND  tmi_name LIKE " + "'%" + tmiName + "%'");
		}
		if (!StringUtil.isEmptyNull(sort)) {
			sql.append(" order by  " + sort);
		}
		if (!StringUtil.isEmptyNull(order)) {
			sql.append("  " + order);
		}

		if (pageSize == 0) {
			List<Map<String, Object>> list = tbcourseInfoDao.searchForMap(sql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		} else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbcourseInfoDao.searchForMap(sql.toString(), values, pageBean);
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	@Override
	public TbCourseInfo get_course_forSubjectDown(Long tsi_id) {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> json = new HashMap<String, Object>();
		sql.append(" select * from tb_course_info where tci_status=1 AND tci_subjects_id=" + tsi_id);
		TbCourseInfo tbcourseInfo = new TbCourseInfo();
		TbCourseInfo result = null;
		tbcourseInfo.setTciSubjectsId(tsi_id);
		tbcourseInfo.setTciStatus(1);
		result = tbcourseInfoDao.searchOne(tbcourseInfo);
		return result;

	}

	
	/**
	 * wanghao
	 */
	@Override
	public List<TbCourseInfo> findByMajor(TbMajorInfo tbMajorInfo) {
		StringBuffer sql = new StringBuffer("");
		List<Object> values = new ArrayList<>();
		values.add(tbMajorInfo.getTmiId());
		sql.append("select * from tb_course_info where tci_majors_id =?");
		List<TbCourseInfo> listTbCourseInfo = tbcourseInfoDao.search(sql.toString(), values);
		return listTbCourseInfo;
	}
}
