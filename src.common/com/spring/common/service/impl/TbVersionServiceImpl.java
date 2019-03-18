/**    
* @{#} TbInformationCataServiceImpl.java Create on 2015��8��17�� ����4:04:44    
* Copyright (c) 2015.    
*/
package com.spring.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.dao.TbVersionDao;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbVersion;
import com.spring.common.service.TbVersionService;

/**
 * @author <a href="mailto:liwei.flyf@gmail.com">author</a>
 * @version 1.0
 * @description
 */
@Service("tbVersionService")
public class TbVersionServiceImpl extends BaseServiceImpl<TbVersion, Integer>implements TbVersionService {

	@Resource
	TbVersionDao tbVersionDao;

	@Override
	public BaseDao<TbVersion, Integer> getGenericDao() {
		// TODO Auto-generated method stub
		return tbVersionDao;
	}

	@Override
	public PageBean<TbVersion> findByPage(TbVersion params, PageBean<TbVersion> pageBean) {
		// TODO Auto-generated method stub
		return tbVersionDao.search(params, pageBean);
	}

	@Override
	public TbVersion findUserinfo(TbVersion searchParams) {
		// TODO Auto-generated method stub
		return tbVersionDao.searchOne(searchParams);
	}

	@Override
	public List<TbVersion> findAll(TbVersion searchParams) {
		// TODO Auto-generated method stub
		return tbVersionDao.search(searchParams);
	}

	@Override
	public HashMap<String, Object> findForJson(HashMap<String, String> params) {
		// TODO Auto-generated method stub
		HashMap<String, Object> json = new HashMap<String, Object>();

		int page = params.get("page") == null ? 1 : Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0 : Integer.parseInt(params.get("pageSize"));
		String sort = params.get("sort");
		String order = params.get("order");
		String tvName = params.get("tvName");
		String start_time = params.get("start_time");
		String end_time = params.get("end_time");

		StringBuilder sql = new StringBuilder("select * from tb_version where tv_status != 0 ");
		List<Object> values = new ArrayList<Object>();
		if (!StringUtils.isBlank(tvName)) {
			sql.append(" and tv_name like ?");
			values.add("%" + tvName + "%");
		}
		if (!StringUtils.isBlank(start_time)) {
			sql.append(" and tv_add_date > DATE_FORMAT(?, '%Y-%c-%d %H:%i:%s')");
			values.add(start_time);
		}
		if (!StringUtils.isBlank(end_time)) {
			sql.append(" and tv_add_date < DATE_FORMAT(?, '%Y-%c-%d %H:%i:%s')");
			values.add(end_time);
		}
		if (!StringUtils.isBlank(sort)) {
			sql.append(" order by tv_add_date " + order);
		} else {
			sql.append(" order by tv_add_date desc");
		}
		if (pageSize == 0) {
			List<TbVersion> list = tbVersionDao.search(sql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		} else {
			PageBean<TbVersion> pageBean = new PageBean<TbVersion>(page, pageSize);
			pageBean = tbVersionDao.search(sql.toString(), values, pageBean);

			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	@Override
	public List<TbVersion> findAllUser() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder("");
		sb.append("select * from tb_information_cata where tic_status = 1");
		List<TbVersion> list = tbVersionDao.search(sb.toString(), new ArrayList<Object>());
		return list;
	}

	@Override
	public Long getIdByName(int type) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder("");
		sb.append("select * from tb_version where tv_type = ?");
		List<Object> values = new ArrayList<Object>();
		values.add(type);
		List<TbVersion> list = tbVersionDao.search(sb.toString(), values);
		if (list != null && list.size() > 0) {
			return list.get(0).getTvId();
		} else {
			return 0l;
		}
	}
}
