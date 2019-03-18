package com.spring.common.service;

import java.util.List;

import com.spring.base.service.BaseService;
import com.spring.common.dao.TbStarDao;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbStar;
import com.spring.common.entity.TbStudent;
import com.spring.server.entity.TbTeacherInfo;

public interface TbStarService extends BaseService<TbStar, Long> {

	public PageBean<TbStar> findPage(String lastSql, PageBean<TbStar> page);

	public TbStarDao getTbStarDao();

	public List<TbStudent> getStudents();

	public List<TbTeacherInfo> getTeachers();
}
