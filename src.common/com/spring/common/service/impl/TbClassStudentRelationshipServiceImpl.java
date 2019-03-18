package com.spring.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.dao.TbClassStudentRelationshipDao;
import com.spring.common.entity.TbClassStudentRelationship;
import com.spring.common.service.TbClassStudentRelationshipService;

@Service
public class TbClassStudentRelationshipServiceImpl extends BaseServiceImpl<TbClassStudentRelationship, Long> implements TbClassStudentRelationshipService{

	@Autowired
	TbClassStudentRelationshipDao tbClassStudentRelationshipDao;
	
	@Override
	public BaseDao<TbClassStudentRelationship, Long> getGenericDao() {
		return tbClassStudentRelationshipDao;
	}

	@Override
	public TbClassStudentRelationship findOne(TbClassStudentRelationship relationship) {
		return tbClassStudentRelationshipDao.searchOne(relationship);
	}

	@Override
	public void updateByClassId(String classId, String teacherId,String teacherName) {
		StringBuffer strSql = new StringBuffer();
		strSql.append("UPDATE tb_class_student_relationship SET ");
		strSql.append("tscr_teacher_id = "+teacherId+", ");
		strSql.append("tscr_teacher_name = '"+teacherName+"' ");
		strSql.append("WHERE tcsr_class_id = "+classId+" ");
		tbClassStudentRelationshipDao.update(strSql.toString(), null);
	}

	@Override
	public List<TbClassStudentRelationship> findByClassId(String tcId) {
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("SELECT * from tb_class_student_relationship");
		sbSql.append(" where tcsr_class_id="+tcId);
		return tbClassStudentRelationshipDao.search(sbSql.toString(), null);
	}

}
