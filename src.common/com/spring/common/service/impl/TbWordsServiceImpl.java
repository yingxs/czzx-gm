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
import com.spring.common.dao.TbWordsDao;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbWords;
import com.spring.common.service.TbWordsService;

@Service("tbWordsService")
@Transactional
public class TbWordsServiceImpl extends BaseServiceImpl<TbWords, Long> implements TbWordsService {

	@Resource
	TbWordsDao tbWordsDao;

	@Override
	public BaseDao<TbWords, Long> getGenericDao() {
		return tbWordsDao;
	}

	@Override
	public TbWords searchOne(TbWords t) {
		return tbWordsDao.searchOne(t);
	}

	@Override
	public HashMap<String, Object> findForJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();

		int page = params.get("page") == null ? 0 : Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0 : Integer.parseInt(params.get("pageSize"));

		String sql = "select tw_id,tw_name,tw_code,DATE_FORMAT(tw_add_date,'%Y-%m-%d %H:%i:%s') as tw_add_date,tw_status,tw_value from tb_words w where tw_status = 1";
		List<Object> values = new ArrayList<Object>();

		if (params.get("twCode") != null && !params.get("twCode").equalsIgnoreCase("")) {
			sql += " and tw_code like '%" + params.get("twCode") + "%'";
		}

		if (params.get("twName") != null && !params.get("twName").equalsIgnoreCase("")) {
			sql += " and tw_name like '%" + params.get("twName") + "%'";
		}

		if (params.get("sort") != null && !params.get("sort").equalsIgnoreCase("")) {
			sql += " order by  " + params.get("sort");
		}

		if (params.get("order") != null && !params.get("order").equalsIgnoreCase("")) {
			sql += " " + params.get("order");
		}
		System.out.println("sql-->" + sql);

		if (pageSize == 0) {
			List<Map<String, Object>> list = tbWordsDao.searchForMap(sql, values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		} else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);

			if (params.get("orderBy") != null)
				pageBean.setOrderBy(params.get("orderBy"));
			if (params.get("orderType") != null)
				pageBean.setOrderType(params.get("orderType"));

			pageBean = tbWordsDao.searchForMap(sql, values, pageBean);

			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	public List<TbWords> getRootWords() {

		StringBuffer sbSql = new StringBuffer();
		sbSql.append(" select tw_id,tw_code,tw_name  ");
		sbSql.append("  from tb_words ");
		sbSql.append(" where LENGTH(tw_code) =2 and tw_status = 1");
		return tbWordsDao.search(sbSql.toString(), null);

	}

	@Override
	public String findSuperCodeById(Long id) {
		String sql = "select tw_code from tb_words where tw_id = " + id + " ";
		String code = tbWordsDao.getDouble(sql);
		return code;
	}

	@Override
	public String findIdByCode(String code) {
		String sql = "select tw_name from tb_words where tw_code = '" + code + "'";
		return tbWordsDao.getDouble(sql);
	}

	public List<TbWords> selectList(String code) {

		// String strCode = params.get("code");
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(" select tw_id,tw_code,tw_name  ");
		sbSql.append("  from tb_words ");
		sbSql.append(" where tw_code like '" + code + "%' and LENGTH(tw_code) > 2");
		return tbWordsDao.search(sbSql.toString(), null);
	}

	public List<TbWords> selectListEx(String code) {

		// String strCode = params.get("code");
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(" select tw_id,tw_code,tw_name from tb_words where tw_code like '21%' and length(tw_code) =6 ");
		return tbWordsDao.search(sbSql.toString(), null);
	}

	public List<TbWords> findAllGangWei(String code) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(" select tw_id,tw_code,tw_name from tb_words where tw_code like '01%'");
		return tbWordsDao.search(sbSql.toString(), null);
	}

	@Override
	public List<TbWords> findAllPro() {
		return tbWordsDao.getAll();
	}

	@Override
	public List<TbWords> getEngineerWords(Long id) {
		String sql = "select wor.* from tb_words wor,tb_engineer_words ew where wor.tw_id = ew.tb_words_id and tb_engineer_id = "
				+ id;
		return tbWordsDao.search(sql, null);
	}

	@Override
	public List<TbWords> allParentById() {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(" select tw_id,tw_code,tw_name from tb_words where tw_code like '04%' and length(tw_code) = 4");
		return tbWordsDao.search(sbSql.toString(), null);
	}

	@Override
	public List<TbWords> allParentByWelfare() {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(" select tw_id,tw_code,tw_name from tb_words where tw_code like '05%' and length(tw_code) = 4");
		return tbWordsDao.search(sbSql.toString(), null);
	}

	@Override
	public List<TbWords> selectWordsList(String strCode) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append(" select *  ");
		sbSql.append("  from tb_words ");
		sbSql.append(" where tw_code like '" + strCode + "%' and tw_status=1 and  length(tw_code) = 4");
		return tbWordsDao.search(sbSql.toString(), null);
	}
	// 民族

	@Override
	public List<TbWords> ajax_get_ttination_findall() {

		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT tw_id,tw_name FROM tb_words WHERE tw_code LIKE '%04__'");

		System.out.println("################" + sbSql);

		List<TbWords> search = tbWordsDao.search(sbSql.toString(), null);

		System.out.println("################" + search.toString());
		return search;
	}

	// 政治面貌
	@Override
	public List<TbWords> ajaxGetTtipoliticalFindAll() {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT tw_id,tw_name FROM tb_words WHERE tw_code LIKE '%09__'");

		System.out.println("################" + sbSql);

		List<TbWords> search = tbWordsDao.search(sbSql.toString(), null);
		System.out.println("################" + search.toString());
		return search;
	}

	// 学历层次
	@Override
	public List<TbWords> ajaxGetttittiInDegreeFindAll() {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT tw_id,tw_name FROM tb_words WHERE tw_code LIKE '%06__'");

		System.out.println("################" + sbSql);

		List<TbWords> search = tbWordsDao.search(sbSql.toString(), null);
		System.out.println("################" + search.toString());
		return search;
	}

	// 职务职称
	@Override
	public List<TbWords> ajaxGetttiPositionFindAll() {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT tw_id,tw_name FROM tb_words WHERE tw_code LIKE '%14__'");

		System.out.println("################" + sbSql);

		List<TbWords> search = tbWordsDao.search(sbSql.toString(), null);
		System.out.println("################" + search.toString());
		return search;
	}

	// 教职工类别
	@Override
	public List<TbWords> ajaxGetttiPositionTypeFindAll() {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT tw_id,tw_name FROM tb_words WHERE tw_code LIKE '%15__'");

		System.out.println("################" + sbSql);

		List<TbWords> search = tbWordsDao.search(sbSql.toString(), null);
		System.out.println("################" + search.toString());
		return search;
	}

	// 现任工作
	@Override
	public List<TbWords> ajaxGetttiPostFindAll() {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT tw_id,tw_name FROM tb_words WHERE tw_code LIKE '%13__'");

		System.out.println("################" + sbSql);

		List<TbWords> search = tbWordsDao.search(sbSql.toString(), null);
		System.out.println("################" + search.toString());
		return search;
	}

	@Override
	public List<Map<String, Object>> ajax_get_statusList() {
		String prefix = "02";
		StringBuffer sql = new StringBuffer();
		sql.append("select tw_id,tw_name from tb_words where  tw_status=1 and tw_code like '" + prefix + "%__' ");
		List<Map<String, Object>> searchForMap = tbWordsDao.searchForMap(sql + "", null);
		return searchForMap;
	}

	@Override
	public List<Map<String, Object>> ajax_get_tsNationList() {
		String prefix = "04";
		StringBuffer sql = new StringBuffer();
		sql.append("select tw_id,tw_name from tb_words where  tw_status=1 and tw_code like '" + prefix + "%__' ");
		List<Map<String, Object>> searchForMap = tbWordsDao.searchForMap(sql + "", null);
		return searchForMap;
	}

	@Override
	public List<Map<String, Object>> ajax_get_tsPoliticalList() {
		String prefix = "09";
		StringBuffer sql = new StringBuffer();
		sql.append("select tw_id,tw_name from tb_words where  tw_status=1 and tw_code like '" + prefix + "%__' ");
		List<Map<String, Object>> searchForMap = tbWordsDao.searchForMap(sql + "", null);
		return searchForMap;
	}

	@Override
	public List<Map<String, Object>> ajax_get_tsHjPropertyList() {
		String prefix = "07";
		StringBuffer sql = new StringBuffer();
		sql.append("select tw_id,tw_name from tb_words where  tw_status=1 and tw_code like '" + prefix + "%__' ");
		List<Map<String, Object>> searchForMap = tbWordsDao.searchForMap(sql + "", null);
		return searchForMap;
	}

	@Override
	public List<Map<String, Object>> ajax_get_tsInDegreeList() {
		String prefix = "06";
		StringBuffer sql = new StringBuffer();
		sql.append("select tw_id,tw_name from tb_words where  tw_status=1 and tw_code like '" + prefix + "%__' ");
		List<Map<String, Object>> searchForMap = tbWordsDao.searchForMap(sql + "", null);
		return searchForMap;
	}

	@Override
	public List<Map<String, Object>> ajax_get_tsFamilyIncomeList() {
		String prefix = "10";
		StringBuffer sql = new StringBuffer();
		sql.append("select tw_id,tw_name from tb_words where  tw_status=1 and tw_code like '" + prefix + "%__' ");
		List<Map<String, Object>> searchForMap = tbWordsDao.searchForMap(sql + "", null);
		return searchForMap;
	}

	/**
	 * 根据多个ID获取多个数据字典值
	 * @author wanghao 2019/3/6 17:22
	 * @param list
	 * @return
	 */
	@Override
	public List<TbWords> getMajorYear() {
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT  tw_id,tw_name FROM tb_words WHERE tw_code LIKE '12%' AND tw_code != '12' and tw_status<>0 ");
		List<TbWords> listTbWords = tbWordsDao.search(sql.toString(),null);
		return listTbWords;
	}
}
