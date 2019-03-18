package com.spring.server.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.entity.PageBean;
import com.spring.server.dao.TbDepartmentTeacherDao;
import com.spring.server.entity.TbDepartmentTeacher;
import com.spring.server.service.TbDepartmentTeacherService;
@Service("tbDepartmentTeacherService")
public class TbDepartmentTeacherServiceImpl extends BaseServiceImpl<TbDepartmentTeacher,Long>implements TbDepartmentTeacherService{
  
	@Resource
	TbDepartmentTeacherDao tbDepartmentTeacherDao;
	
	
	@Override
	public BaseDao<TbDepartmentTeacher, Long> getGenericDao() {
		return tbDepartmentTeacherDao;
	}
	
	@Override
	public TbDepartmentTeacher findOne(TbDepartmentTeacher tbDepartmentTeacher) {
		return tbDepartmentTeacherDao.searchOne(tbDepartmentTeacher);
	}

	//查询部门下部门成员的人数
	@Override
	public List<TbDepartmentTeacher> findMemberCount(Long valueOf) {
		System.out.println("进入查询部门下部门成员的人数。。。。");
		String sql="select * from tb_department_teacher where tdt_status=1 and tdt_department_id ="+valueOf;
		return tbDepartmentTeacherDao.search(sql,null);
	}

	//显示部门管理>成员管理中的列表
	@Override
	public HashMap<String, Object> findForJson1(HashMap<String, String> params, Long long1) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
		String order = params.get("order");
		String ttPhoto = params.get("ttPhoto");//照片
		String ttName = params.get("ttName");//姓名
		String ttNumber = params.get("ttNumber");//工号
		String ttPhone = params.get("ttPhone");//手机号码
		String tdtPosition = params.get("tdtPosition");//岗位
		//查询sql
		StringBuffer sbf = new StringBuffer();
		sbf.append("SELECT tdt_id tdtId,tt_id ttId,tt_photo ttPhoto,tt_name ttName,tt_number ttNumber,tt_phone ttPhone,(select tw_name from tb_words where tw_id = tdt_position ) tdtPosition ");//教师表  //照片  //姓名  //工号 //手机号码 //岗位
		sbf.append("FROM tb_department_teacher ");//部门成员表
		sbf.append("left join tb_teacher on tt_id=tdt_teacher_id  ");//教师表
		sbf.append("WHERE tdt_status=1 and tdt_department_id="+long1);
		
		//查询条件
		/*//部门名称 
		if (!StringUtils.isBlank(tdName)) {
			sbf.append(" AND td_name LIKE '%"+tdName+"%' "); 
		}
		
		if (AddDate != null && !AddDate.equalsIgnoreCase("")) {
			sbf.append(" AND DATE_FORMAT(td_addtime,'%Y-%m-%d') >= DATE_FORMAT('"+AddDate+"','%Y-%m-%d') ");
		} 
		
		if (AddDateEnd!= null && !AddDateEnd.equalsIgnoreCase("")) {
			sbf.append("AND DATE_FORMAT(td_addtime,'%Y-%m-%d') <= DATE_FORMAT('"+AddDateEnd+"','%Y-%m-%d') ");
		}
		if (!StringUtils.isBlank(order)) {
			sbf.append("ORDER BY td_addtime "+order+" ");
		} else {
			sbf.append("ORDER BY td_addtime DESC ");
		}
		*/
		
		if (pageSize == 0) {
			List<Map<String, Object>> list = tbDepartmentTeacherDao.searchForMap(sbf.toString(), null);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbDepartmentTeacherDao.searchForMap(sbf.toString(), null, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	
	//显示成员管理中添加成员的列表
	@Override
	public HashMap<String, Object> findForJson2(HashMap<String, String> params, Long long1) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
		String order = params.get("order");
		String ttPhoto = params.get("ttPhoto");//照片
		String ttName = params.get("ttName");//姓名
		String ttNumber = params.get("ttNumber");//工号
		String ttSex = params.get("ttSex");//性别
		
		//查询sql
		StringBuffer sbf = new StringBuffer();
		sbf.append("SELECT tt_id ttId,tt_photo ttPhoto,tt_phone ttPhone,tt_name ttName,tt_number ttNumber,tt_sex ttSex ");//教师表  //照片  //姓名  //工号  //性别
		sbf.append("FROM tb_teacher ");//部门成员表
		sbf.append("WHERE tt_status<>0 and tt_id not in ( select tdt_teacher_id from tb_department_teacher where tdt_department_id = "+long1+"  and tdt_status = 1 ) ");//部门成员表
		//查询条件
		//姓名
		if (!StringUtils.isBlank(ttName)) {
			sbf.append(" AND tt_name LIKE '%"+ttName+"%' "); 
		}
		
		//性别
		if (!StringUtils.isBlank(ttSex)) {
			sbf.append(" AND tt_sex = "+ttSex+" "); 
		}

		if (pageSize == 0) {
			List<Map<String, Object>> list = tbDepartmentTeacherDao.searchForMap(sbf.toString(), null);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbDepartmentTeacherDao.searchForMap(sbf.toString(), null, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}

     }

	//保存多个成员
	@Override
	@Transactional
	public void saveSome(List<TbDepartmentTeacher> tptlist) {
		
		for (TbDepartmentTeacher tbDepartmentTeacher : tptlist) {
			tbDepartmentTeacherDao.save(tbDepartmentTeacher);
		}
	}
}