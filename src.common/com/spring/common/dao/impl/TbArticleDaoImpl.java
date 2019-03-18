package com.spring.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.base.dao.impl.BaseDaoMysqlImpl;
import com.spring.common.dao.TbArticleDao;
import com.spring.common.entity.TbArticle;

@Repository("tbArticleDao")
public class TbArticleDaoImpl extends BaseDaoMysqlImpl<TbArticle, Long>
		implements TbArticleDao {

	public TbArticleDaoImpl() {
		super(TbArticle.class);
	}

}
