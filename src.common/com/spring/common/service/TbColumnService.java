package com.spring.common.service;

import java.util.HashMap;
import java.util.List;

import com.spring.base.service.BaseService;
import com.spring.common.dao.TbColumnDao;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbColumn;

public interface TbColumnService extends BaseService<TbColumn, Long> {
	public TbColumnDao getColumnDao();

	public PageBean<TbColumn> findPage(String lastSql, PageBean<TbColumn> page);

	public Integer saveToEntity(TbColumn tbColumn);
	
	public void update(TbColumn tbColumn);
	
	public List<TbColumn> searchp(String sql, Object... params);

	public List<TbColumn> getColumnByType(int type);
	
	public List<TbColumn> getColumnByByParentId(String id);

	public HashMap<String, Object> findForTbColumnJson(HashMap<String, String> params);

	public List<TbColumn> listByParent(long l);

	public List<TbColumn> listByParentP(long l);

	public int searchForIndexAndParentId(String tcParentId, String tcIndex, String tcId);

	public void updateForTbColumn(TbColumn tbColumn);

}
