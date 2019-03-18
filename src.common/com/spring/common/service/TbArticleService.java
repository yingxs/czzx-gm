package com.spring.common.service;

import java.util.HashMap;

import com.spring.base.service.BaseService;
import com.spring.common.dao.TbArticleDao;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbArticle;

public interface TbArticleService extends BaseService<TbArticle, Long> {
	public TbArticleDao getArticleDao();

	public PageBean<TbArticle> findPage(String lastSql, PageBean<TbArticle> page);

	public Integer saveToEntity(TbArticle student);
	
	public void update(TbArticle tbArticle);
	
	public HashMap<String,Object> getList(HashMap<String,String> params);
}
