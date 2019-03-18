package com.spring.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.dao.NoticeDao;
import com.spring.common.entity.Notice;
import com.spring.common.entity.PageBean;
import com.spring.common.service.NoticeService;

@Service("noticeService")
@Transactional
public class NoticeServiceImpl extends BaseServiceImpl<Notice, Long> implements NoticeService {

	@Resource
	NoticeDao noticeDao;

	@Override
	public BaseDao<Notice, Long> getGenericDao() {
		return noticeDao;
	}

	@Override
	public PageBean<Notice> findPage(String lastSql, PageBean<Notice> page) {
		return noticeDao.searchBySql(lastSql, page);
	}


	/**
	 * 公告列表
	 * @param params
	 * @return
	 */
	@Override
	public HashMap<String,Object> findForJson(HashMap<String,String> params){
		HashMap<String,Object> json = new HashMap<String,Object>();
		
		int page = params.get("page") == null ? 0:Integer.valueOf(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.valueOf(params.get("pageSize"));
		
		StringBuffer sbSql = new StringBuffer();
		
		sbSql.append("SELECT n.*,name,DATE_FORMAT(tni_add_date_str,'%Y-%m-%d %H:%i:%s') as tniAddDate  ");
		sbSql.append("FROM tb_notice_infomation n ");
		sbSql.append("LEFT JOIN  userinfo ON id = tni_add_user ");
		sbSql.append("WHERE 1 = 1 ");
		sbSql.append("AND tni_status <> 0 ");
		
		
		List<Object> values = new ArrayList<Object>();
		
		if(params.get("tniTitle")!=null&&!params.get("tniTitle").equalsIgnoreCase("")){
			sbSql.append("AND tni_title like '%"+params.get("tniTitle")+"%' ");
		}
		
		if(params.get("tniType")!=null&&!params.get("tniType").equalsIgnoreCase("")){
			sbSql.append("AND tni_type = "+params.get("tniType")+" ");
		}
		
		if (params.get("AddDate") != null && !params.get("AddDate").equalsIgnoreCase("")) {
			sbSql.append("AND DATE_FORMAT(tni_add_date_str,'%Y-%m-%d') >= DATE_FORMAT('"+params.get("AddDate")+"','%Y-%m-%d') ");
		} 
		
		if (params.get("AddDateEnd")!= null && !params.get("AddDateEnd").equalsIgnoreCase("")) {
			sbSql.append("AND DATE_FORMAT(tni_add_date_str,'%Y-%m-%d') <= DATE_FORMAT('"+params.get("AddDateEnd")+"','%Y-%m-%d') ");
		}
		
		sbSql.append(" GROUP BY tni_code ");
		sbSql.append(" ORDER BY tni_add_date DESC ");
		
		if (pageSize == 0) {
			List<Map<String,Object>> list = noticeDao.searchForMap(sbSql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String,Object>> pageBean = new PageBean<Map<String,Object>>(page, pageSize);
			
			if(params.get("orderBy") != null)
				pageBean.setOrderBy(params.get("orderBy"));
			if(params.get("orderType") != null)
				pageBean.setOrderType(params.get("orderType"));
			
			pageBean = noticeDao.searchForMap(sbSql.toString(), values, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}
	
	
	/**
	 * 接收人列表
	 * @param params
	 * @return
	 */
	@Override
	public HashMap<String,Object> findForrecvPersons(HashMap<String,String> params){
		HashMap<String,Object> json = new HashMap<String,Object>();
		
		int page = params.get("page") == null ? 0:Integer.valueOf(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.valueOf(params.get("pageSize"));
		
		StringBuffer sbSql = new StringBuffer();
		
		//家长
		sbSql.append("SELECT * FROM tb_student_family ");
		sbSql.append("LEFT JOIN tb_student ON ts_id = tsf_student_id ");
		sbSql.append("LEFT JOIN tb_classs ON  tc_id = ts_class_id ");
		sbSql.append("LEFT JOIN tb_grade ON tg_id = tc_grade_id ");
		sbSql.append("WHERE 1 = 1 ");
		sbSql.append("AND ts_status <> 0 ");
		sbSql.append("AND tg_id <> 4 ");//不要毕业班
		
		List<Object> values = new ArrayList<Object>();
		
		//家长姓名
		if(params.get("tsfName")!=null&&!params.get("tsfName").equalsIgnoreCase("")){
			sbSql.append("AND tsf_name like '%"+params.get("tsfName")+"%' ");
		}
		//年级
		if(params.get("tgId")!=null&&!params.get("tgId").equalsIgnoreCase("")){
			sbSql.append("AND tg_id = "+params.get("tgId")+" ");
		}
		//班级
		if(params.get("tcId")!=null&&!params.get("tcId").equalsIgnoreCase("")){
			sbSql.append("AND tc_id = "+params.get("tcId")+" ");
		}
		
		sbSql.append(" ORDER BY ts_addtime DESC ");
		
		if (pageSize == 0) {
			List<Map<String,Object>> list = noticeDao.searchForMap(sbSql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String,Object>> pageBean = new PageBean<Map<String,Object>>(page, pageSize);
			
			if(params.get("orderBy") != null)
				pageBean.setOrderBy(params.get("orderBy"));
			if(params.get("orderType") != null)
				pageBean.setOrderType(params.get("orderType"));
			
			pageBean = noticeDao.searchForMap(sbSql.toString(), values, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	
	/**
	 * 教师列表
	 * @param params
	 * @return
	 */
	@Override
	public HashMap<String,Object> findForrecvThacher(HashMap<String,String> params){
		HashMap<String,Object> json = new HashMap<String,Object>();
		
		int page = params.get("page") == null ? 0:Integer.valueOf(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.valueOf(params.get("pageSize"));
		
		StringBuffer sbSql = new StringBuffer();
		
		sbSql.append("SELECT * FROM tb_teacher ");
		sbSql.append("LEFT JOIN tb_department_teacher ON tdt_teacher_id = tt_id ");
		sbSql.append("LEFT JOIN tb_department ON td_id = tdt_department_id ");
		sbSql.append("WHERE 1 = 1 ");
		sbSql.append("AND tt_status <> 0  ");
		
		List<Object> values = new ArrayList<Object>();
		
		
		sbSql.append(" ORDER BY tt_addtime DESC ");
		
		if (pageSize == 0) {
			List<Map<String,Object>> list = noticeDao.searchForMap(sbSql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String,Object>> pageBean = new PageBean<Map<String,Object>>(page, pageSize);
			
			if(params.get("orderBy") != null)
				pageBean.setOrderBy(params.get("orderBy"));
			if(params.get("orderType") != null)
				pageBean.setOrderType(params.get("orderType"));
			
			pageBean = noticeDao.searchForMap(sbSql.toString(), values, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}
	
	
	/**
	 * 接收人教师列表
	 * @param params
	 * @return
	 */
	@Override
	public HashMap<String,Object> RecvThacher(HashMap<String,String> params){
		HashMap<String,Object> json = new HashMap<String,Object>();
		
		int page = params.get("page") == null ? 0:Integer.valueOf(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.valueOf(params.get("pageSize"));
		
		StringBuffer sbSql = new StringBuffer();
		
		sbSql.append("select * FROM tb_notice_infomation  ");
		sbSql.append("LEFT JOIN tb_teacher ON tt_id = tni_recv_persons ");
		sbSql.append("WHERE tni_code =  "+params.get("tniCode"));
		
		List<Object> values = new ArrayList<Object>();
		
		sbSql.append(" ORDER BY tt_addtime DESC ");
		
		if (pageSize == 0) {
			List<Map<String,Object>> list = noticeDao.searchForMap(sbSql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String,Object>> pageBean = new PageBean<Map<String,Object>>(page, pageSize);
			
			if(params.get("orderBy") != null)
				pageBean.setOrderBy(params.get("orderBy"));
			if(params.get("orderType") != null)
				pageBean.setOrderType(params.get("orderType"));
			
			pageBean = noticeDao.searchForMap(sbSql.toString(), values, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}
	
	
	/**
	 * 接收人教师列表
	 * @param params
	 * @return
	 */
	@Override
	public HashMap<String,Object> recvList(HashMap<String,String> params){
		HashMap<String,Object> json = new HashMap<String,Object>();
		
		int page = params.get("page") == null ? 0:Integer.valueOf(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.valueOf(params.get("pageSize"));
		
		StringBuffer sbSql = new StringBuffer();
		
		sbSql.append("select * FROM tb_notice_infomation  ");
		sbSql.append("LEFT JOIN tb_student_family ON tsf_id = tni_recv_persons ");
		sbSql.append("WHERE tni_code =  "+params.get("tniCode"));
		
		
		List<Object> values = new ArrayList<Object>();
		
		sbSql.append(" ORDER BY tsf_id DESC ");
		
		if (pageSize == 0) {
			List<Map<String,Object>> list = noticeDao.searchForMap(sbSql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String,Object>> pageBean = new PageBean<Map<String,Object>>(page, pageSize);
			
			if(params.get("orderBy") != null)
				pageBean.setOrderBy(params.get("orderBy"));
			if(params.get("orderType") != null)
				pageBean.setOrderType(params.get("orderType"));
			
			pageBean = noticeDao.searchForMap(sbSql.toString(), values, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	@Override
	public List<Notice> findByTitle(String tniTitle) {
		
		if(tniTitle != null){
			StringBuffer sbSql = new StringBuffer();
			sbSql.append("select * from tb_notice_infomation where tni_title='"+tniTitle+"' and tni_status<>0");
			return noticeDao.search(sbSql.toString(), null);
		}
		return null;
	}
}
