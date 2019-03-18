package com.spring.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.dao.TbGraderDao;
import com.spring.common.entity.MenuTree;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbGrade;
import com.spring.common.service.TbGradeService;

@Service("tbGradeService")
@Transactional
public class TbGradeServiceImpl extends BaseServiceImpl<TbGrade, Integer> implements TbGradeService {

	@Resource
	TbGraderDao TbGradeDao;

	@Override
	public BaseDao<TbGrade, Integer> getGenericDao() {
		return TbGradeDao;
	}

	@Override
	public List<TbGrade> listAll() {
		String sql = "select * from tb_teacher ";
		List<Object> values = new ArrayList<Object>();
		return TbGradeDao.search(sql, values);
	}
	@Override
	public PageBean<TbGrade> findPage(String lastSql,PageBean<TbGrade> page) {
		return TbGradeDao.searchBySql(lastSql, page);
	}
	
	@Override
	public void save() {
		TbGrade TbGrade = new TbGrade();
		TbGradeDao.save(TbGrade);
	}
	
	@Override
	public void del(Integer id) {
		TbGradeDao.del(id);
	}
	
	@Override
	public List<TbGrade> list(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public TbGrade findById(Integer id) {
		// TODO Auto-generated method stub
		return TbGradeDao.get(id);
	}
	
	@Override
	public void update(TbGrade TbGrade ) {
		// TODO Auto-generated method stub
		TbGradeDao.update(TbGrade);
	}

	@Override
	public List<TbGrade> findGrade() {
		String sql = "SELECT * FROM tb_grade WHERE tg_status<>0 ";
		return TbGradeDao.search(sql, null);
	}

	//AND tg_name NOT LIKE '%毕业班%' 
	@Override
	public List<MenuTree> getAllTree() {
		List<MenuTree> menus = new ArrayList<MenuTree>();
		String sql = "SELECT * FROM tb_grade WHERE tg_status<>0 ";
		List<TbGrade> list = TbGradeDao.search(sql, null);
		menus = convertTrees(list);
		return menus;
	}

	//菜单树
	private List<MenuTree> convertTrees(List<TbGrade> menus) {
		List<MenuTree> list = new ArrayList<MenuTree>();
		if (menus!=null && menus.size()>0) {
			for (TbGrade menu : menus) {
				MenuTree tree = new MenuTree();
				tree.setId(Integer.valueOf(String.valueOf(menu.getId())));
				tree.setText(menu.getName());
				tree.setState("open");
				tree.setChecked(false);
				tree.setAttributes(null);
				list.add(tree);
			}
		}
		return list;
	}

}
