package com.spring.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.utils.GlobalStatic;
import com.spring.common.dao.GroupandmenuDao;
import com.spring.common.dao.SysmenuDao;
import com.spring.common.dao.UserandmenuDao;
import com.spring.common.entity.Menu;
import com.spring.common.entity.MenuTree;
import com.spring.common.entity.Sysmenu;
import com.spring.common.entity.Userinfo;
import com.spring.common.service.SysmenuService;

@Service("sysmenuService")
@Transactional
public class SysmenuServiceImpl extends BaseServiceImpl<Sysmenu, Integer> implements
		SysmenuService {

	@Resource SysmenuDao sysmenuDao;
	@Resource UserandmenuDao userandmenuDao;
	@Resource GroupandmenuDao groupandmenuDao;
	
	@Override
	public BaseDao<Sysmenu, Integer> getGenericDao() {
		return sysmenuDao;
	}
	
	@Override
	public List<Sysmenu> listByParent(Long parent) {
		
		String sql = "select * from sysmenu where status = 1 and parentId = ? ORDER BY sortCode asc ";
		List<Object> values = new ArrayList<Object>();
		values.add(parent);
		return sysmenuDao.search(sql, values);
		
	}

	public List<MenuTree> convertTrees(List<Sysmenu> menus) {
		
		List<MenuTree> list = new ArrayList<MenuTree>();
		
		if (menus!=null && menus.size()>0) {
			for (Sysmenu menu : menus) {
				MenuTree tree = new MenuTree();
				tree.setId(menu.getId());
				tree.setText(menu.getName());
				tree.setState("open");
				tree.setChecked(false);
				tree.setParendId(menu.getParentId());
				HashMap<String, String> attr = new HashMap<String, String>();
				attr.put("url", menu.getUrl());
				tree.setAttributes(attr);
				list.add(tree);
			}
		}
		
		return list;
	}

	@Override
	public List<MenuTree> findMenuByUser(Userinfo userInfo) {
		List<MenuTree> menus = new ArrayList<MenuTree>();
		if (userInfo.getCode()!=null && "admin".equals(userInfo.getCode())) {
			List<Sysmenu> list = sysmenuDao.search("select * from sysmenu where parentId = 0 order by sortCode asc", null);
			menus = convertTrees(list);
			if (menus!=null && menus.size()>0) {
				for (MenuTree tree : menus) {
					List<Sysmenu> childList = sysmenuDao.searchp("select * from sysmenu where parentId = ? order by sortCode asc", tree.getId());
					if (childList!=null && childList.size()>0) {
						tree.setChildren(convertTrees(childList));
					}
				}
			}
		}else {
			//根据登录用户判断已加权限的menu ID
			List<Sysmenu> list = sysmenuDao.searchp("select * from sysmenu m where status = 1 and parentId = 0 and m.id in(select um.menu_id from userandmenu um where um.user_id=?) ORDER BY sortCode asc", userInfo.getId());
			menus = convertTrees(list);
			if (menus!=null && menus.size()>0) {
				for (MenuTree tree : menus) {
					List<Sysmenu> childList = sysmenuDao.searchp("select * from sysmenu m where parentId = ? and m.id in(select um.menu_id from userandmenu um where um.user_id=?) order by sortCode asc", tree.getId(), userInfo.getId());
					if (childList!=null && childList.size()>0) {
						tree.setChildren(convertTrees(childList));
					}
				}
			}
		}
		return menus;
	}

	@Override
	public List<Sysmenu> list(Map<String, Object> params) {
		
		String sql = "select * from sysmenu where 1 = 1";
		List<Object> values = new ArrayList<Object>();
		
		Object parentId = params.get("parentId");
		
		if (parentId !=null && parentId.equals("-1")) {
			
			List<Sysmenu> list = sysmenuDao.search("select * from sysmenu where parentId = 0 order by sortCode asc",null);
			if (list!=null && list.size()>0) {
				for (Sysmenu sysmenu : list) {
					List<Sysmenu> childList = sysmenuDao.searchp("select * from sysmenu where parentId = ? order by sortCode asc", sysmenu.getId());
					sysmenu.setChildren(childList);
				}
			}
			
			return list;
		}else {
			sql += " and parentId = ?";
			values.add(parentId);
			sql += " order by sortCode asc";
			
			List<Sysmenu> list = sysmenuDao.search(sql,values);
			return list;
		}
		
	}

	@Override
	public void del(Integer id) {
		userandmenuDao.delp("delete from userandmenu where menu_id = ?", id);
		groupandmenuDao.delp("delete from groupandmenu where menuId = ?", id);
		sysmenuDao.del(id);
	}

	/**
	 * 根据会员id和菜单id查找按钮操作
	 */
	@Override
	public List<Map<String,Object>> findBtnById(HttpServletRequest request,String menuId) {
		Userinfo userInfo = (Userinfo) request.getSession().getAttribute
				(GlobalStatic.DEFAULT_LOGIN_SESSION_NAME);//获取登录用户
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT * ");
		sql.append(" FROM sysmenu WHERE parentId= "+menuId);
		//获取菜单
		List<Sysmenu> sysmenuList=sysmenuDao.search(sql.toString(), null);
		String menuList="-1";
		if(sysmenuList.size()>0){
			menuList="";
			for(int i=0;i<sysmenuList.size();){
				menuList+=sysmenuList.get(i).getId();
				i++;
				if(i<sysmenuList.size()){
					menuList+=",";
				}
			}
		}
		StringBuffer sqlS=new StringBuffer();
		//返回会员按钮
		sqlS.append(" SELECT b.parentId AS menuId,b.name AS name,b.url AS url FROM userandmenu a JOIN sysmenu b ON a.menu_id=b.id WHERE  a.menu_id IN("+menuList+") AND a.user_id="+userInfo.getId()+" GROUP BY b.NAME   ORDER BY sortCode ");
		return sysmenuDao.searchForMap(sqlS.toString(), null);
	}
	
	
}
