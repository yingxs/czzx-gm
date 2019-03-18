package com.spring.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.dao.TbAdvertiseCatalogDao;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbAdvertiseCatalog;
import com.spring.common.service.TbAdvertiseCatalogService;

@Service("tbAdvertiseCatalogService")
@Transactional
public class TbAdvertiseCatalogServiceImpl extends BaseServiceImpl<TbAdvertiseCatalog, Long> implements
		TbAdvertiseCatalogService {

	@Resource TbAdvertiseCatalogDao tbAdvertiseCatalogDao;
	
	@Override
	public BaseDao<TbAdvertiseCatalog, Long> getGenericDao() {
		return tbAdvertiseCatalogDao;
	}

	@Override
	public TbAdvertiseCatalog findOne(TbAdvertiseCatalog searchParam) {
		return tbAdvertiseCatalogDao.searchOne(searchParam);
	}
	
	/**
	 * 
	* Title: findForJson 
	* Description:  分页查询广告板块
	* @param params
	* @return 
	* @see com.spring.backstage.service.TbAdvertiseCatalogService#findForJson(java.util.HashMap)
	 */
	@Override
	public HashMap<String, Object> findForJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();

		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
		String sort = params.get("sort");
		String order = params.get("order");
		String tacName = params.get("tacName");
		
		String sql = "select a.*,"
				+ "(SELECT u.account FROM userinfo u WHERE a.Tac_add_person = u.id) as tacAddPersonName "
				+ "from tb_advertise_catalog a where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		
		if (!StringUtils.isBlank(tacName)) {
			sql += " and a.tac_name like ? ";
			values.add("%" + tacName + "%");
		}
		if (!StringUtils.isBlank(sort)) {
			sql += " order by tac_add_date " + order;
		} else {
			sql += " order by tac_id desc ";
		}
		if (pageSize == 0) {
			List<TbAdvertiseCatalog> list = tbAdvertiseCatalogDao.search(sql, values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<TbAdvertiseCatalog> pageBean = new PageBean<TbAdvertiseCatalog>(page, pageSize);
			pageBean = tbAdvertiseCatalogDao.search(sql, values, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}
}
