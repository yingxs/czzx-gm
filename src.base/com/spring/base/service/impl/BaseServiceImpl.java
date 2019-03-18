package com.spring.base.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.BaseService;
import com.spring.common.entity.PageBean;

@Transactional
public abstract class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T,ID> {


	public abstract BaseDao<T,ID> getGenericDao();
	
	public void save(T entity) {
		getGenericDao().save(entity);
	}

	public void delete(ID id) {
		getGenericDao().del(id);
	}

	public void update(T entity) {
		getGenericDao().update(entity);
	}

	public T findById(ID id) {
		return getGenericDao().get(id);
	}

	public List<T> findAll() {
		return getGenericDao().getAll();
	}
	
	public PageBean<T> getPage(T t, PageBean<T> pageBean){
		return getGenericDao().search(t, pageBean);
	}
	
	public PageBean<T> getPageByParams(Map<String, Object> map, PageBean<T> pageBean){
		return getGenericDao().search(map, pageBean);
	}

}
