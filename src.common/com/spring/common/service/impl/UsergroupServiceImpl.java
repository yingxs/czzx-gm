package com.spring.common.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.dao.UsergroupDao;
import com.spring.common.entity.MenuTree;
import com.spring.common.entity.Usergroup;
import com.spring.common.service.UsergroupService;

@Service("usergroupService")
@Transactional
public class UsergroupServiceImpl extends BaseServiceImpl<Usergroup, Integer> implements
		UsergroupService {

	@Resource UsergroupDao usergroupDao;
	
	@Override
	public BaseDao<Usergroup, Integer> getGenericDao() {
		return usergroupDao;
	}
	
	@Override
	public Usergroup findUsergroup(Usergroup searchParams) {
		return usergroupDao.searchOne(searchParams);
	}

	@Override
	public List<MenuTree> findForTree() {
		return usergroupDao.search("select id,parent_id as parendId,name as text from usergroup u WHERE u.`name`<>'老师' AND u.`name`<>'学生' order by create_time asc", null, MenuTree.class);
	}
	
	@Override
	public List<Usergroup> findIdByName(String name) {
		return usergroupDao.searchp("select * from usergroup where name = ?", name);
	}

}
