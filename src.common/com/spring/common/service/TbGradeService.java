package com.spring.common.service;

import java.util.List;
import java.util.Map;

import com.spring.base.service.BaseService;
import com.spring.common.entity.MenuTree;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbGrade;

public interface TbGradeService extends BaseService<TbGrade, Integer> {

	public List<TbGrade> list(Map<String, Object> params);

	public List<TbGrade> listAll();
	
	public PageBean<TbGrade> findPage(String lastSql,PageBean<TbGrade> page);
	
	public void save();
	
	public void del(Integer id);
	
	public TbGrade findById(Integer id);
	
	public void update(TbGrade tc);

	public List<TbGrade> findGrade();

	public List<MenuTree> getAllTree();
	
}
