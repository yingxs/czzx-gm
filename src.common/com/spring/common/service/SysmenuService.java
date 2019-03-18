package com.spring.common.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.spring.base.service.BaseService;
import com.spring.common.entity.MenuTree;
import com.spring.common.entity.Sysmenu;
import com.spring.common.entity.Userinfo;

public interface SysmenuService extends BaseService<Sysmenu, Integer> {

	public List<MenuTree> findMenuByUser(Userinfo userInfo);
	
	public List<Sysmenu> list(Map<String, Object> params);
	
	public void del(Integer id);
	
	public List<Sysmenu> listByParent(Long parent);
	
	List<Map<String,Object>> findBtnById(HttpServletRequest request,String menuId);
	
}
