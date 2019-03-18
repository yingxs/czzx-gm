package com.spring.common.service;

import java.util.HashMap;
import java.util.List;

import com.spring.base.service.BaseService;
import com.spring.common.entity.ApplicatonInfo;
import com.spring.common.entity.PageBean;

public interface ApplicatonInfoService extends BaseService<ApplicatonInfo, Long> {

	public PageBean<ApplicatonInfo> findPage(String lastSql, PageBean<ApplicatonInfo> page);

	public Integer saveToEntity(ApplicatonInfo applicatonInfo);

	public void update(ApplicatonInfo applicatonInfo);

	public List<ApplicatonInfo> searchp(String sql, Object... params);

	HashMap<String, Object> findForJson(HashMap<String, String> params);
}
