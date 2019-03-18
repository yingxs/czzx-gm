package com.spring.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spring.base.service.BaseService;
import com.spring.common.dao.TbClassDao;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbClass;
import com.spring.common.entity.TbGrade;
import com.spring.common.entity.Userinfo;

public interface TbClassService extends BaseService<TbClass, Integer> {

	public List<TbClass> list(Map<String, Object> params);

	public List<TbClass> listAll();
	
	public PageBean<TbClass> findPage(String lastSql,PageBean<TbClass> page);
	
	public void save();
	
	public void del(Integer id);
	
	public TbClass findById(Integer id);
	
	public void update(TbClass tc);
	
	/**	
	 * 查询年级下的班级
	 * @param gid
	 * @return
	 */
	public List<TbClass> findAllClassByGrade(Long gid);
	
	public TbClassDao getTbClassDao();

	public List<TbClass> getClassName();

	public HashMap<String, Object> findForClassJson(HashMap<String, String> params);

	public void saveClassAndTeacher(String tbGrades, String tbClassName,String tbTeacher, Userinfo userInfo);

	public long getStudentNumber(String tcId);

	public HashMap<String, Object> findForStudentJson(HashMap<String, String> params);

}
