package com.spring.common.service;

import com.spring.common.dao.TbStudentDao;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbStudent;

import java.util.HashMap;

import com.spring.base.service.BaseService;

public interface TbStudentService extends BaseService<TbStudent, Long> {

	public PageBean<TbStudent> findPage(String lastSql, PageBean<TbStudent> page);

	public Integer saveToEntity(TbStudent student);

//	public TbStudentDao getTbStudentDao();

	HashMap<String, Object> findForJson(HashMap<String, String> params);

	HashMap<String, Object> findForStudentJson(HashMap<String, String> params);
}
