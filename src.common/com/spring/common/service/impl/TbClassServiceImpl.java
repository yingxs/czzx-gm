package com.spring.common.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.utils.StringUtil;
import com.spring.common.dao.TbClassDao;
import com.spring.common.dao.TbClassStudentRelationshipDao;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbClass;
import com.spring.common.entity.TbClassStudentRelationship;
import com.spring.common.entity.TbGrade;
import com.spring.common.entity.Teacher;
import com.spring.common.entity.Userinfo;
import com.spring.common.service.TbClassService;
import com.spring.common.service.TbGradeService;
import com.spring.common.service.TeacherService;

@Service("tbClassService")
@Transactional
public class TbClassServiceImpl extends BaseServiceImpl<TbClass, Integer> implements TbClassService {

	@Resource
	TbClassDao tbClassDao;
	@Resource
	TbClassStudentRelationshipDao tbClassStudentRelationshipDao;
	@Resource
	TeacherService teacherService;

	@Override
	public BaseDao<TbClass, Integer> getGenericDao() {
		return tbClassDao;
	}

	@Override
	public List<TbClass> listAll() {
		String sql = "select * from tb_teacher ";
		List<Object> values = new ArrayList<Object>();
		return tbClassDao.search(sql, values);
	}
	@Override
	public PageBean<TbClass> findPage(String lastSql,PageBean<TbClass> page) {
		return tbClassDao.searchBySql(lastSql, page);
	}
	
	@Override
	public void save() {
		TbClass tbClass = new TbClass();
		tbClassDao.save(tbClass);
	}
	
	@Override
	public void del(Integer id) {
		tbClassDao.del(id);
	}
	
	@Override
	public List<TbClass> list(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public TbClass findById(Integer id) {
		// TODO Auto-generated method stub
		return tbClassDao.get(id);
	}
	
	@Override
	public void update(TbClass tbClass ) {
		// TODO Auto-generated method stub
		tbClassDao.update(tbClass);
	}

	@Override
	public List<TbClass> findAllClassByGrade(Long gid) {
		String sql="SELECT * FROM tb_classs tc WHERE tc.tc_grade_id="+gid+" and tc_status=1";
		return tbClassDao.search(sql, null);
	}

	@Override
	public TbClassDao getTbClassDao() {
		return tbClassDao;
	}
	
	
	// 查班级
	public List<TbClass> getClassName() {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT tc_id,tc_name FROM tb_classs ");
		sbSql.append("WHERE tc_status <> 0  ");
		return tbClassDao.search(sbSql.toString(), null);
	}

	@Override
	public HashMap<String, Object> findForClassJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
		String order = params.get("order");
		String tbGrade = params.get("tbGrade");
		//查询sql
		StringBuffer strSql = new StringBuffer();
		strSql.append("SELECT tc_id tcId,tc_name tcName,tg_name tgName,tg_id tgId,tt_id ttId, ");
		strSql.append("tscr_teacher_name tscrTeacherName,tt_phone ttPhone ");
		strSql.append("FROM tb_classs ");
		strSql.append("LEFT JOIN tb_grade ON tc_grade_id = tg_id ");
		strSql.append("LEFT JOIN ( SELECT min(tcsr_id) tcsr_id,tscr_teacher_name,tcsr_class_id,tscr_teacher_id FROM	tb_class_student_relationship GROUP BY tcsr_class_id) tcsr ON  tc_id=tcsr.tcsr_class_id ");
		strSql.append("LEFT JOIN tb_teacher ON tt_id = tcsr.tscr_teacher_id ");
		strSql.append("WHERE tc_status<>0 ");
		
		//查询条件
		if (!StringUtils.isBlank(tbGrade)) {
			strSql.append("AND tc_grade_id = "+tbGrade+" ");
		}
		if (!StringUtils.isBlank(order)) {
			strSql.append("ORDER BY tc_addtime "+order+" ");
		} else {
			strSql.append("ORDER BY tc_addtime DESC ");
		}
		if (pageSize == 0) {
			List<Map<String, Object>> list = tbClassDao.searchForMap(strSql.toString(), null);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbClassDao.searchForMap(strSql.toString(), null, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	@Override
	public void saveClassAndTeacher(String tbGrades, String tbClassName,String tbTeacher, Userinfo userInfo) {
		TbClass tbClass = new TbClass();
		tbClass.setGradeId(Long.valueOf(tbGrades));
		tbClass.setName(tbClassName);
		tbClass.setAddtime(new Timestamp(new Date().getTime()));
		tbClass.setAdduser(userInfo.getId());
		tbClass.setStatus(1);
		tbClassDao.save(tbClass);
		
		TbClassStudentRelationship relationship = new TbClassStudentRelationship();
		if(!StringUtil.isEmptyNull(tbTeacher)){
			relationship.setTcsrClassId(tbClass.getId());
			relationship.setTcsrAddDate(new Timestamp(new Date().getTime()));
			relationship.setTcsrStatus(1);
			relationship.setTscrTeacherId(Long.valueOf(tbTeacher));
			Teacher teacher = teacherService.findById(Integer.valueOf(tbTeacher));
			relationship.setTscrTeacherName(teacher.getName());
		}else{
			relationship.setTcsrClassId(tbClass.getId());
			relationship.setTcsrAddDate(new Timestamp(new Date().getTime()));
			relationship.setTcsrStatus(1);
		}
		tbClassStudentRelationshipDao.save(relationship);
		
	}

	@Override
	public long getStudentNumber(String tcId) {
		String sql = "SELECT COUNT(*) FROM tb_student WHERE ts_class_id = "+tcId+" AND ts_status<>0";
		return tbClassDao.getLong(sql, null);
	}

	@Override
	public HashMap<String, Object> findForStudentJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
		String tcId = params.get("tcId");
		//查询sql
		StringBuffer strSql = new StringBuffer();
		strSql.append("SELECT ts_id tsId,ts_name tsName,ts_number tsNumber, ");
		strSql.append("ts_sex tsSex,tg_name tgName,tc_name tcName,ts_status tsStatus, ");
		strSql.append("tsf_name tsfName,tsf_phone tsfPhone,ts_address taAddress, ");
		strSql.append("(SELECT pname FROM china_province WHERE pid = ts_province_id) pName, ");
		strSql.append("(SELECT cname FROM china_city WHERE cid = ts_city_id) cName, ");
		strSql.append("(SELECT oname FROM china_county WHERE oid = ts_county_id) oName ");
		strSql.append("FROM tb_student ");
		strSql.append("LEFT JOIN tb_classs ON ts_class_id = tc_id ");
		strSql.append("LEFT JOIN tb_grade ON tg_id = tc_grade_id ");
		strSql.append("LEFT JOIN tb_student_family ON ts_id = tsf_student_id ");
		strSql.append("WHERE ts_class_id = "+tcId+" ");
		strSql.append("AND ts_status<>0 ");
		
		if (pageSize == 0) {
			List<Map<String, Object>> list = tbClassDao.searchForMap(strSql.toString(), null);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbClassDao.searchForMap(strSql.toString(), null, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

}
