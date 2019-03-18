package com.spring.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.dao.TbInformationCataDao;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbInformationCata;
import com.spring.common.service.TbInformationCataService;

@Service("TbInformationCataService")
@Transactional
public class TbInformationCataServiceImpl extends
		BaseServiceImpl<TbInformationCata, Long> implements
		TbInformationCataService {

	@Resource
	TbInformationCataDao tbInformationCataDao;

	@Override
	public BaseDao<TbInformationCata, Long> getGenericDao() {
		return tbInformationCataDao;
	}

	@Override
	public TbInformationCata findOne(TbInformationCata searchParam) {
		return tbInformationCataDao.searchOne(searchParam);
	}
	
	@Override
	public HashMap<String, Object> findByParam(HashMap<String, String> params) {

		HashMap<String, Object> json = new HashMap<String, Object>();

		int page = params.get("page") == null ? 0 : Integer.parseInt(params
				.get("page"));
		int pageSize = params.get("pageSize") == null ? 0 : Integer
				.parseInt(params.get("pageSize"));
		String ticName = params.get("ticName");
		String tmAddDate = params.get("tmAddDate");
		String tmAddDateEnd = params.get("tmAddDateEnd");

		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT ");
		sbSql.append("tic_id ticId,");//ID
		sbSql.append("tic_name ticName,");//分类名称
		sbSql.append("tic_mean ticMean,");//分类描述
		sbSql.append("(SELECT `name` FROM userinfo WHERE id=tic_add_preson) ticAddPreson,");//创建人
		sbSql.append("DATE_FORMAT(tic_add_date,'%Y-%m-%d %H:%i') as ticAddDate ");//创建时间
		sbSql.append("FROM tb_information_cata ");//查询表
		sbSql.append("WHERE tic_status != 0");//状态不为0

		//查询条件
		if (!StringUtils.isBlank(ticName)) {
			sbSql.append(" and tic_name like '%"+ticName+"%'");
		}
		if (tmAddDate != null && !tmAddDate.equalsIgnoreCase("")) {
			sbSql.append(" AND DATE_FORMAT(tic_add_date,'%Y-%m-%d') >= DATE_FORMAT('"+tmAddDate+"','%Y-%m-%d')");
		} 
		if (tmAddDateEnd!= null && !tmAddDateEnd.equalsIgnoreCase("")) {
			sbSql.append(" AND DATE_FORMAT(tic_add_date,'%Y-%m-%d') <= DATE_FORMAT('"+tmAddDateEnd+"','%Y-%m-%d')");
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
			List<Map<String, Object>> list = tbInformationCataDao.searchForMap(sbSql.toString(), null);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbInformationCataDao.searchForMap(sbSql.toString(), null, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}

	}

	@Override
	public List<TbInformationCata> findByParentId(long parentId) {
		return tbInformationCataDao.searchp("SELECT c.* FROM tb_information_cata c WHERE c.tic_status = 1 AND c.tic_parent_id = '"+parentId+"'", null);
	}
	
	@Override
	public List<TbInformationCata> findByName(String cord) {
		List<Object> listValue = new ArrayList<Object>();
		String sql = "select * from tb_information_cata where tic_name = ?";
		listValue.add(cord);
		return tbInformationCataDao.search(sql, listValue);
	}

	@Override
	public List<TbInformationCata> findByTbInformationCata() {
		String sql = "select * from tb_information_cata where 1=1 and tic_status != 0  and tic_parent_id = 0";

		return tbInformationCataDao.search(sql, null);
	}

	/* 获取所有的二级分类 */
	@Override
	public List<TbInformationCata> findChildCata() {

		String sql = "select * from tb_information_cata where 1=1 and tic_status != 0  and tic_parent_id != 0";

		return tbInformationCataDao.search(sql, null);

	}
	
	@Override
	public TbInformationCata findTbInformationCataById(long ticId) {
		String sql = "select c.*,(select t.tic_name from tb_information_cata t where t.tic_id = c.tic_parent_id) as ticParentName from tb_information_cata c where c.tic_status != 0  and c.tic_id = '"+ticId+"'";
		return tbInformationCataDao.search(sql, null).get(0);
	}
}
