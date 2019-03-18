package com.spring.common.service;

import java.util.HashMap;
import java.util.List;

import com.spring.base.service.BaseService;
import com.spring.common.entity.Notice;
import com.spring.common.entity.PageBean;

public interface NoticeService extends BaseService<Notice, Long> {

	public PageBean<Notice> findPage(String lastSql, PageBean<Notice> page);

	HashMap<String, Object> findForJson(HashMap<String, String> params);

	HashMap<String, Object> findForrecvPersons(HashMap<String, String> params);

	HashMap<String, Object> findForrecvThacher(HashMap<String, String> params);

	HashMap<String, Object> RecvThacher(HashMap<String, String> params);

	HashMap<String, Object> recvList(HashMap<String, String> params);

	public List<Notice> findByTitle(String tniTitle);

}
