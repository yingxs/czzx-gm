package com.spring.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.dao.TbInformationDao;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbInformation;
import com.spring.common.service.TbInformationService;

@Service("TbInformationService")
@Transactional
public class TbInformationServiceImpl extends
		BaseServiceImpl<TbInformation, Long> implements TbInformationService {

	@Resource
	TbInformationDao tbInformationDao;

	@Override
	public BaseDao<TbInformation, Long> getGenericDao() {
		return tbInformationDao;
	}

	@Override
	public HashMap<String, Object> findByParam(HashMap<String, String> params) {

		HashMap<String, Object> json = new HashMap<String, Object>();

		int page = params.get("page") == null ? 0 : Integer.parseInt(params
				.get("page"));
		int pageSize = params.get("pageSize") == null ? 0 : Integer
				.parseInt(params.get("pageSize"));

		List<Object> values = new ArrayList<Object>();
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT tice.*,DATE_FORMAT(ti_add_date,'%Y-%m-%d %H:%i') as Tbu_addDate,");
		sbSql.append("(select account from userinfo where id = ti_add_preson) as tmName,");
		sbSql.append("(select tic_name from tb_information_cata where tic_id = ti_cata_id) as ticName ");
		sbSql.append(" from tb_information tice where 1=1 ");

		// 等于0 删除不显示
		sbSql.append(" and ti_status != 0 ");

		// 标题
		if (params.get("tiTitle") != null
				&& !params.get("tiTitle").equalsIgnoreCase("")) {
			sbSql.append(" and ti_title like '%" + params.get("tiTitle")
					+ "%' ");
		}

		// 分类名称
		if (params.get("ticName") != null
				&& !params.get("ticName").equalsIgnoreCase("")) {
			sbSql.append(" and ti_cata_id =" + params.get("ticName"));
		}

		// 根据时间段查询
		if (params.get("tiAddDate") != null
				&& !params.get("tiAddDate").equalsIgnoreCase("")) {
			sbSql.append(" and ti_add_date >= '" + params.get("tiAddDate")
					+ "'");
		}
		// /根据时间段查询
		if (params.get("tiAddDateEnd") != null
				&& !params.get("tiAddDateEnd").equalsIgnoreCase("")) {
			sbSql.append(" and ti_add_date <= '" + params.get("tiAddDateEnd")
					+ "'");
		}

		// 根据时间段查询
		if (params.get("tiAddDate") != null
				&& !params.get("tiAddDate").equalsIgnoreCase("")) {
			sbSql.append(" AND DATE_FORMAT(ti_add_date, '%Y-%m-%d') >= DATE_FORMAT('"
					+ params.get("tiAddDate") + "', '%Y-%m-%d') ");
		}
		if (params.get("tiAddDateEnd") != null
				&& !params.get("tiAddDateEnd").equalsIgnoreCase("")) {
			sbSql.append(" AND DATE_FORMAT(ti_add_date, '%Y-%m-%d') <= DATE_FORMAT('"
					+ params.get("tiAddDateEnd") + "', '%Y-%m-%d') ");
		}

		if (params.get("sort") != null
				&& !params.get("sort").equalsIgnoreCase("")) {
			sbSql.append(" order by  " + params.get("sort"));
		}

		if (params.get("order") != null
				&& !params.get("order").equalsIgnoreCase("")) {
			sbSql.append(" " + params.get("order"));
		}
		if (pageSize == 0) {
			List<Map<String, Object>> list = tbInformationDao.searchForMap(
					sbSql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		} else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(
					page, pageSize);

			pageBean = tbInformationDao.searchForMap(sbSql.toString(), values,
					pageBean);

			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}

	}

	
	@Override
	public List<TbInformation> findByCataId(long cataId) {
		return tbInformationDao.searchp("SELECT i.* FROM tb_information i WHERE i.ti_status = 1 AND i.ti_cata_id = '"+cataId+"'", null);
	}

	@Override
	public TbInformation findOne(TbInformation information) {
		return tbInformationDao.searchOne(information);
	}
}
