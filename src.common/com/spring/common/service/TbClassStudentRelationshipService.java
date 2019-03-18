package com.spring.common.service;

import java.util.List;

import com.spring.base.service.BaseService;
import com.spring.common.entity.TbClassStudentRelationship;

public interface TbClassStudentRelationshipService extends BaseService<TbClassStudentRelationship, Long> {

	TbClassStudentRelationship findOne(TbClassStudentRelationship relationship);

	void updateByClassId(String classId, String teacherId, String teacherName);

	List<TbClassStudentRelationship> findByClassId(String tcId);

}
