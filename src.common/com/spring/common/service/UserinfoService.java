package com.spring.common.service;

import java.util.HashMap;
import java.util.List;

import com.spring.base.service.BaseService;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.Userinfo;

public interface UserinfoService extends BaseService<Userinfo, Long> {

	/**
	 * 分页查询
	 * @param params 条件参数
	 * @param pageBean 
	 * @return
	 */
	PageBean<Userinfo> findByPage(Userinfo params,PageBean<Userinfo> pageBean);
	
	Userinfo findUserinfo(Userinfo searchParams);
	
	List<Userinfo> findAll(Userinfo searchParams);
	
	HashMap<String, Object> findForJson(HashMap<String, String> params);
	HashMap<String, Object> findForJson1(HashMap<String, String> params);
	
	List<Userinfo> findUserGroup();
	
	Userinfo findUserinfoById(Long id);
	
	Userinfo findUserByName(String account);

	Userinfo findOne(Userinfo userinfo);
}
