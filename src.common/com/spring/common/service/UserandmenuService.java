package com.spring.common.service;

import java.util.List;
import java.util.Map;

import com.spring.base.service.BaseService;
import com.spring.common.entity.Sysmenu;
import com.spring.common.entity.Userandmenu;
import com.spring.common.entity.Userinfo;

public interface UserandmenuService extends BaseService<Userandmenu, Long> {

	List<Userandmenu> findList(Long userId);
	
	/**
	 * 添加某一用户的菜单、按钮权限配置
	 * @param menuIds
	 * @param btnIds
	 * @param userId
	 */
	void addPerms(String menuIds, String btnIds, String userId);
	
	/**
	 * 通过菜单id删除
	 * @param menuId
	 */
	void delByMenu(Integer menuId);
	
	/**
	 * 查询某一用户的权限配置
	 * @param grouId
	 * @return
	 */
	List<Sysmenu> list(String userId);
 
	boolean findByParams(String url, Long userId);
	
	List<Map<String, Object>> getUserAuth(Userinfo userinfo, Long parent);

	void addPermsEx(String menuIds, String btnIds, String userId);
}
