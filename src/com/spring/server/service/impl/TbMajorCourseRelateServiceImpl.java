package com.spring.server.service.impl;

import com.spring.server.dao.TbCourseInfoDao;
import com.spring.server.entity.TbCourseInfo;
import org.springframework.stereotype.Service;
import com.spring.base.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import javax.annotation.Resource;
import com.spring.common.entity.PageBean;
import com.spring.server.entity.TbMajorCourseRelate;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.util.DateUtil;
import com.spring.server.service.TbMajorCourseRelateService;
import com.spring.server.dao.TbMajorCourseRelateDao;
import com.spring.base.dao.BaseDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;


/**
 * 专业课程关联表业务类
 *
 * @author hxx
 * @Date 2019-03-12 11:23:00
 */
 @Service
public class TbMajorCourseRelateServiceImpl extends BaseServiceImpl<TbMajorCourseRelate, Long> implements TbMajorCourseRelateService {
	
	@Resource
	TbMajorCourseRelateDao Tb_MajorCourseRelateDao;

	@Resource
	TbCourseInfoDao tbcourseInfoDao;

	@Resource
	TbMajorCourseRelateDao tbMajorCourseRelateDao;
	@Override
	public BaseDao<TbMajorCourseRelate, Long> getGenericDao() {
		return Tb_MajorCourseRelateDao;
	}
	
	@Override
	public Map<String, Object> findForJson(Map<String, String> params) {
		Map<String, Object> json = new HashMap<String, Object>();
		String AddDate = params.get("AddDate");
		String AddDateEnd = params.get("AddDateEnd");
		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("rows") == null ? 0:Integer.parseInt(params.get("rows"));
		String sort = params.get("sort");
		String order = params.get("order");
		List<Object> values = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		//逻辑判断开始
		
		
		//逻辑判断结束
		if(!StringUtils.isEmpty(AddDate)){
			sql.append(" AND DATE_FORMAT(XXXX,'%Y-%m-%d') >= DATE_FORMAT('"+AddDate+"','%Y-%m-%d')");
		}
		if(!StringUtils.isEmpty(AddDateEnd)){
			sql.append(" AND DATE_FORMAT(XXXX,'%Y-%m-%d') <= DATE_FORMAT('"+AddDateEnd+"','%Y-%m-%d')");
		}
		if(!StringUtil.isEmptyNull(sort)){
			sql.append(" order by  " + sort);
		}
		if(!StringUtil.isEmptyNull(order)){
			sql.append("  " + order);
		}
		if (pageSize == 0) {
			List<Map<String,Object>> list = Tb_MajorCourseRelateDao.searchForMap(sql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String,Object>> pageBean = new PageBean<Map<String,Object>>(page, pageSize);
			pageBean = Tb_MajorCourseRelateDao.searchForMap(sql.toString(), values, pageBean);
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	/**
	 * 添加
	 * @return
	 */
	public void InsertInTo(String id) {
		StringBuffer sb=new StringBuffer();
		sb.append("INSERT INTO  tb_major_course_relate('tcrr_major_id') ");
		if(!id.isEmpty()){
			sb.append("VALUES("+id+")");
		}

		System.out.println("service#########################################"+sb);
	}

	@Override
	@Transactional
	public void saveTbcourseInfo(TbCourseInfo tbcourseInfo, String[] testName, Long loginId) throws Exception{
		try {
			tbcourseInfo.setTciAddTime(DateUtil.getTimestamp());
			tbcourseInfo.setTciAddPerson(loginId);
			tbcourseInfo.setTciStatus(1);
			Object o = tbcourseInfoDao.saveId(tbcourseInfo);
			Long ttrId = Long.valueOf(o.toString()) ;
			TbMajorCourseRelate tbMajorCourseRelate = new  TbMajorCourseRelate();
			for(int i = 0; i<testName.length;i++){
				tbMajorCourseRelate.setTcrrMajorId(Long.valueOf(testName[i]));
				tbMajorCourseRelate.setTcrrCourseId(ttrId);
				tbMajorCourseRelate.setTcrrAddPerson(Integer.valueOf(loginId.toString()));
				tbMajorCourseRelate.setTcrrAddTime(DateUtil.getTimestamp());
				tbMajorCourseRelateDao.save(tbMajorCourseRelate);
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚
		}
	}
}