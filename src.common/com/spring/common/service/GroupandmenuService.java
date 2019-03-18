package com.spring.common.service;

import java.util.List;

import com.spring.base.service.BaseService;
import com.spring.common.entity.Groupandmenu;
import com.spring.common.entity.Sysmenu;

public interface GroupandmenuService extends BaseService<Groupandmenu, Long> {

	/**
	 * 添加某一用户组的菜单、按钮权限配置
	 * @param menuIds
	 * @param btnIds
	 * @param userId
	 */
	void addPerms(String menuIds, String btnIds, String grouId);
	
	/**
	 * 查询某一用户组的权限配置
	 * @param grouId
	 * @return
	 */
	List<Sysmenu> list(String grouId);

	void addPermsEx(String menuIds, String btnIds, String userId);
}
