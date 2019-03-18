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
import com.spring.server.entity.TbStudent;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.server.service.TbStudentService;
import com.spring.server.dao.TbStudentDao;
import com.spring.base.dao.BaseDao;

/**
 * 学生表业务类
 *
 * @author zhouyuan
 * @Date 2019-03-11 11:45:49
 */
@Service
public class TbStudentServiceImpl extends BaseServiceImpl<TbStudent, Long> implements TbStudentService {

	@Resource
	TbStudentDao TbStudentDao;

	@Override
	public BaseDao<TbStudent, Long> getGenericDao() {
		return TbStudentDao;
	}

	@Override
	public Map<String, Object> findForJson(Map<String, String> params) {
		Map<String, Object> json = new HashMap<String, Object>();
		// 学号
		String stuNumber1 = params.get("stuNumber");
		String stuNumber = null;
		if (stuNumber1 != null) {
			stuNumber = stuNumber1.trim();
		}
		// 姓名
		String likeName1 = params.get("likeName");
		String likeName = null;
		if (likeName1 != null) {
			likeName = likeName1.trim();
		}
		// 性别
		String sexLike1 = params.get("sexLike");
		String sexLike = null;
		if (sexLike1 != null) {
			sexLike = sexLike1.trim();
		}

		// 班级
		String likeClass1 = params.get("likeClass");
		String likeClass = null;
		if (likeClass1 != null) {
			likeClass = likeClass1.trim();
		}

		// 身份证号码
		String idCard1 = params.get("idCard");
		String idCard = null;
		if (idCard1 != null) {
			idCard = idCard1.trim();
		}
		// 学生状态
		String studentStatus1 = params.get("studentStatus");
		String studentStatus = null;
		if (studentStatus1 != null) {
			studentStatus = studentStatus1.trim();
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
		String prefix = "02";
		// 逻辑判断开始
		sql.append(" SELECT ts_id,ts_name,ts_number,ts_card_num,ts_addtime,ts_student_status,ts_class_id,"
				+ "ts_sex,ts_phone,ts_status,tc_id,tc_name,tw_id,tw_name,tw_code FROM  tb_student  LEFT JOIN "
				+ "tb_class ON ts_class_id = tc_id LEFT JOIN tb_words  on ts_student_status = tw_id where ts_status=1");
		// 拼接模糊查询
		if (!StringUtil.isEmptyNull(stuNumber)) {
			sql.append(" AND ts_number LIKE '%" + stuNumber + "%' ");
		}
		if (!StringUtil.isEmptyNull(likeName)) {
			sql.append(" AND ts_name LIKE '%" + likeName + "%' ");
		}
		if (!StringUtil.isEmptyNull(sexLike)) {
			sql.append(" AND ts_sex LIKE '%" + sexLike + "%' ");
		}
		// 班级，关联字典表
		if (!StringUtil.isEmptyNull(likeClass)) {
			sql.append(" AND ts_class_id LIKE '%" + likeClass + "%' ");
		}
		if (!StringUtil.isEmptyNull(idCard)) {
			sql.append(" AND ts_card_num LIKE '%" + idCard + "%' ");
		}
		// 学生状态
		if (!StringUtil.isEmptyNull(studentStatus)) {
			sql.append(" AND ts_student_status LIKE '%" + studentStatus + "%' ");
		}
		// 逻辑判断结束
		if (!StringUtils.isEmpty(AddDate)) {
			sql.append(" AND DATE_FORMAT(ts_addtime,'%Y-%m-%d') >= DATE_FORMAT('" + AddDate + "','%Y-%m-%d')");
		}
		if (!StringUtils.isEmpty(AddDateEnd)) {
			sql.append(" AND DATE_FORMAT(ts_addtime,'%Y-%m-%d') <= DATE_FORMAT('" + AddDateEnd + "','%Y-%m-%d')");
		}
		if (!StringUtil.isEmptyNull(sort)) {
			sql.append(" order by  " + sort);
		}
		if (!StringUtil.isEmptyNull(order)) {
			sql.append("  " + order);
		}
		if (pageSize == 0) {
			List<Map<String, Object>> list = TbStudentDao.searchForMap(sql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		} else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = TbStudentDao.searchForMap(sql.toString(), values, pageBean);
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}
}