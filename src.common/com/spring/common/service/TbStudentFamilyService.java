package com.spring.common.service;

import com.spring.base.service.BaseService;
import com.spring.common.entity.TbStudentFamily;

public interface TbStudentFamilyService extends
		BaseService<TbStudentFamily, Long> {

	public TbStudentFamily finByTbFamaily(Long studentId);

	public Long saveToEntity(TbStudentFamily family);

}
