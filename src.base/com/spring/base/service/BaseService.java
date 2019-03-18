package com.spring.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.spring.common.entity.PageBean;

public interface BaseService<T, ID extends Serializable> {

	void save(T entity);

	void delete(ID id);

	void update(T entity);

	T findById(ID id);

	List<T> findAll();
	
	PageBean<T> getPage(T t, PageBean<T> pageBean);
	
	PageBean<T> getPageByParams(Map<String, Object> map, PageBean<T> pageBean);

}
