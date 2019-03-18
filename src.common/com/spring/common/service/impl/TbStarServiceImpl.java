package com.spring.common.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.dao.TbStarDao;
import com.spring.common.dao.TbStudentDao;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbStar;
import com.spring.common.entity.TbStudent;
import com.spring.common.service.TbStarService;
import com.spring.server.dao.TbTeacherInfoDao;
import com.spring.server.entity.TbTeacherInfo;

@Service("tbStarService")
@Transactional
public class TbStarServiceImpl extends BaseServiceImpl<TbStar, Long>
		implements TbStarService {

	@Resource
	TbStarDao tbStarDao;
	@Resource TbStudentDao tbStudentDao;
	@Resource TbTeacherInfoDao tbTeacherDao;

	@Override
	public BaseDao<TbStar, Long> getGenericDao() {
		return tbStarDao;
	}

	@Override
	public PageBean<TbStar> findPage(String lastSql, PageBean<TbStar> page) {
		return tbStarDao.searchBySql(lastSql, page);
	}

	@Override
	public TbStarDao getTbStarDao() {
		// TODO Auto-generated method stub
		return tbStarDao;
	}

	@Override
	public List<TbStudent> getStudents() {
		String strSql = "select * from tb_student where ts_status!=0";
		return tbStudentDao.search(strSql, null);
	}

	@Override
	public List<TbTeacherInfo> getTeachers() {
		String strSql = "select tt_id,tt_name from tb_teacher where tt_status!=0";
		return tbTeacherDao.search(strSql, null);
	}

}
