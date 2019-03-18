package com.spring.common.service.impl;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.shiro.ShiroUser;
import com.spring.base.utils.StringUtil;
import com.spring.common.dao.GroupandmenuDao;
import com.spring.common.dao.SysmenuDao;
import com.spring.common.dao.UserandmenuDao;
import com.spring.common.entity.Sysmenu;
import com.spring.common.entity.Userandmenu;
import com.spring.common.entity.Userinfo;
import com.spring.common.service.UserandmenuService;

@Service("userandmenuService")
@Transactional
public class UserandmenuServiceImpl extends BaseServiceImpl<Userandmenu, Long> implements
		UserandmenuService {

	@Resource UserandmenuDao userandmenuDao;
	@Resource GroupandmenuDao groupandmenuDao;
	@Resource SysmenuDao sysmenuDao;
	
	@Override
	public BaseDao<Userandmenu, Long> getGenericDao() {
		return userandmenuDao;
	}

	@Override
	public List<Userandmenu> findList(Long userId) {
		// TODO Auto-generated method stub
		return userandmenuDao.searchp("select * from userandmenu where user_id = ?", userId);
	}
	
	/**
	 * 
	* Title: addPerms 
	* Description:  添加权限
	* @param menuIds
	* @param btnIds
	* @param userId 
	* @see com.spring.common.service.UserandmenuService#addPerms(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void addPerms(String menuIds, String btnIds, String userId) {
		
		// 获取当前登录用户
		ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject()
				.getPrincipals().getPrimaryPrincipal();
		//查询当前用户的所有权限
		List<Userandmenu> menuIdList = userandmenuDao.searchp("select um.menu_id from userandmenu um where um.user_id = ?  ", Integer.parseInt(userId));
		String menuIdAll = "";
		for(int i= 0;i<menuIdList.size();i++){
			if(menuIdAll == ""){
				menuIdAll +=  menuIdList.get(i).getMenuId();
			 
			}else{
				menuIdAll += ",";
				menuIdAll +=  menuIdList.get(i).getMenuId();
			}
		}
		
		//删除当前用户的所有权限配置
		String menu[] = menuIdAll.split(",");
		for (String menuid : menu) {
			if (!StringUtils.isBlank(menuid)) {
				userandmenuDao.delp("delete from userandmenu where user_id = ? and menu_id = ?", 
						Long.valueOf(userId),Integer.parseInt(menuid));
			}
		}
		
		String menuIdArray[] = menuIds.split(",");
		for (String id : menuIdArray) {
			if (!StringUtils.isBlank(id)) {
				
				//添加菜单权限配置
				Userandmenu userandmenu = new Userandmenu();
				userandmenu.setAddtime(new Timestamp(new Date().getTime()));
				userandmenu.setAddUserId(loginUser.getId());
				userandmenu.setAddUsername(loginUser.getAccount());
				userandmenu.setMenuId(Integer.parseInt(id));
				userandmenu.setType(0);//0表示保存的事菜单配置
				userandmenu.setUserId(Long.parseLong(userId));
				userandmenuDao.save(userandmenu);
				
			}
		}
		
		String btnIdArray[] = menuIds.split(",");
		for (String id : btnIdArray) {
			if (!StringUtils.isBlank(id)) {
				
				//添加按钮权限配置
				Userandmenu userandmenu = new Userandmenu();
				userandmenu.setAddtime(new Timestamp(new Date().getTime()));
				userandmenu.setAddUserId(loginUser.getId());
				userandmenu.setAddUsername(loginUser.getAccount());
				userandmenu.setMenuId(Integer.parseInt(id));
				userandmenu.setType(1);//1表示保存的是按钮配置
				userandmenu.setUserId(Long.parseLong(userId));
				userandmenuDao.save(userandmenu);
				
			}
		}
	}

	@Override
	public void delByMenu(Integer menuId) {
		userandmenuDao.delp("delete from userandmenu where menu_id = ?", menuId);
	}

	@Override
	public List<Sysmenu> list(String userId) {
		List<Sysmenu> list = sysmenuDao.search("select * from sysmenu where parentId = 0 order by sortCode asc", null);
		for (Sysmenu sysmenu : list) {
			List<Sysmenu> childList = sysmenuDao.searchp("select * from sysmenu where parentId = ? order by sortCode asc", sysmenu.getId());
			int count = userandmenuDao.getInt("select count(*) from userandmenu where user_id = ? and menu_id = ?", Integer.parseInt(userId),sysmenu.getId());
			if(count>0) sysmenu.setChecked(true);
			if (childList!=null && childList.size()>0) {
				for (Sysmenu child : childList) {
					int childCount = userandmenuDao.getInt("select count(*) from userandmenu where user_id = ? and menu_id = ?", Integer.parseInt(userId),child.getId());
					if(childCount>0) child.setChecked(true);
					
					List<Sysmenu> btnList = sysmenuDao.searchp("select * from sysmenu where parentId = ? order by sortCode asc", child.getId());
					if (btnList!=null && btnList.size()>0) {
						for (Sysmenu btn : btnList) {
							int btnCount = userandmenuDao.getInt("select count(*) from userandmenu where  user_id = ? and menu_id = ?", Integer.parseInt(userId),btn.getId());
							if(btnCount>0) btn.setChecked(true);
						}
						child.setChildren(btnList);
					}
				}
				sysmenu.setChildren(childList);
				
			}
			
		}
		return list;
	}
	/**
	 * 
	* Title: findByParams 
	* Description:  根据URL判断用户是否有权限
	* @param url
	* @param userId
	* @return 
	* @see com.spring.common.service.UserandmenuService#findByParams(java.lang.String, java.lang.Long)
	 */
	@Override
	public boolean findByParams(String url, Long userId) {
		if(StringUtil.isEmptyNull(url) || null == userId){
			return false;
		}
		String sql = "select count(*) from userandmenu where user_id = ? and menu_id IN(select m.id from sysmenu m where m.url = ?)";
		List<Object> values = new ArrayList<Object>();
		values.add(userId);
		values.add(url);
		return userandmenuDao.getInt(sql, values) == 0 ? false : true;
	}
	
	@Override
	public List<Map<String, Object>> getUserAuth(Userinfo userinfo, Long parent) {
		//先获取全部菜单，后期再限制
		String sql = "SELECT * FROM sysmenu m WHERE m.status=1 AND m.parentId=? ORDER BY m.sortCode ASC";
		List<Object> values = new ArrayList<Object>();
		values.add(parent);
		return userandmenuDao.searchForMap(sql, values);
	}
	
	/*  add by paul 2018-09-11 重新处理用户的权限设置功能  上面的效率太低，优化下*/
	@Override
	public void addPermsEx(String menuIds, String btnIds, String userId) {
		
		/*
		 * 1. 删除当前用户的所有权限信息
		 * 2. 重新添加用户的权限信息
		 */
		System.out.println("Menu setting -> userId:" + userId);
		System.out.println("Menu setting -> menuIds:" + menuIds);
		System.out.println("Menu setting -> btnIds:" + btnIds);
		
		ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject()
				.getPrincipals().getPrimaryPrincipal();
		
		//删除当前用户的所有权限信息
		StringBuffer sbDelMenu = new StringBuffer();
		sbDelMenu.append(" delete from userandmenu ");
		sbDelMenu.append(" where user_id = " +userId);
		userandmenuDao.del(sbDelMenu.toString(), null);
		
		//开始批量添加用户的菜单
		String[] alMenuIds = menuIds.split(",");  
		
		if(alMenuIds.length <=0 )
			return;
		//查询自己所属组的权限	
		List<Userandmenu> alAddmenu = new ArrayList();
		for(int i=0;i<alMenuIds.length;i++) {
			
			if(alMenuIds[i].equals(""))
				continue;
			Userandmenu userandmenu = new Userandmenu();
			userandmenu.setAddtime(new Timestamp(new Date().getTime()));
			userandmenu.setAddUserId(loginUser.getId());
			userandmenu.setAddUsername(loginUser.getAccount());
			userandmenu.setMenuId(Integer.parseInt(alMenuIds[i]));
			userandmenu.setType(0);//0表示保存的事菜单配置
			userandmenu.setUserId(Long.parseLong(userId));
			userandmenu.setGroupType(1);
			alAddmenu.add(userandmenu);
			
		}
		userandmenuDao.batchSave(alAddmenu);
	}

}
