package com.spring.server.service.impl;

import org.springframework.stereotype.Service;
import com.spring.base.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import com.spring.common.entity.PageBean;
import org.springframework.transaction.annotation.Transactional;
import com.spring.server.entity.TbMajorInfo;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.server.service.TbMajorInfoService;
import com.spring.server.dao.TbMajorInfoDao;
import com.spring.base.dao.BaseDao;

/**
 * 专业信息表业务类
 *
 * @author wanghao
 * @Date 2019-03-05 14:38:37
 */
@Service
public class TbMajorInfoServiceImpl extends BaseServiceImpl<TbMajorInfo, Long> implements TbMajorInfoService {

	@Resource
	TbMajorInfoDao TbMajorInfoDao;

	@Override
	public BaseDao<TbMajorInfo, Long> getGenericDao() {
		return TbMajorInfoDao;
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
		
		String majorName = params.get("majorName");
		String departmenId = params.get("departmenId");
		List<Object> values = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer();
		
		//逻辑判断开始
		sql.append(" SELECT tmi_id,tii_name,tidi_name,tmi_name,tw_name,tmi_code,tmi_add_date ");
		sql.append(" FROM tb_major_info tmi");
		sql.append(" JOIN tb_institute_department_info tidi");
		sql.append(" JOIN tb_institute_info tii");
		sql.append(" JOIN userinfo u");
		sql.append(" JOIN tb_words ");
		sql.append(" ON   tmi_add_person = id AND  tidi_id = tmi_department_id AND tii_id=tmi_institute_id AND tw_id  = tmi_major_year where  tw_status<>0 and tmi_status<>0 and tii_status<>0");
		
		
		//逻辑判断结束
		
		if(!StringUtils.isEmpty(departmenId)){
			values.add(Integer.valueOf(departmenId));
			sql.append(" and tmi_department_id =? " );
		}
		if(!StringUtils.isEmpty(majorName)){
			sql.append(" and tmi_name like '%"+majorName+"%' " );
		}
		if(!StringUtils.isEmpty(AddDate)){
			sql.append(" AND DATE_FORMAT(tmi_add_date,'%Y-%m-%d') >= DATE_FORMAT('"+AddDate+"','%Y-%m-%d')");
		}
		if(!StringUtils.isEmpty(AddDateEnd)){
			sql.append(" AND DATE_FORMAT(tmi_add_date,'%Y-%m-%d') <= DATE_FORMAT('"+AddDateEnd+"','%Y-%m-%d')");
		}
		if(!StringUtil.isEmptyNull(sort)){
			sql.append(" order by  " + sort);
		}
		if(!StringUtil.isEmptyNull(order)){
			sql.append("  " + order);
		}
		if (pageSize == 0) {
			List<Map<String,Object>> list = TbMajorInfoDao.searchForMap(sql.toString(), values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String,Object>> pageBean = new PageBean<Map<String,Object>>(page, pageSize);
			System.out.println("sql:"+sql.toString());
			System.out.println("values:"+values);
			
			pageBean = TbMajorInfoDao.searchForMap(sql.toString(), values, pageBean);
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	@Override
	public TbMajorInfo findByName(String name) {
		TbMajorInfo paramtbMajorInfo = new TbMajorInfo();
		paramtbMajorInfo.setTmiName(name);
		TbMajorInfo tbMajorInfo = TbMajorInfoDao.searchOne(paramtbMajorInfo);
		return tbMajorInfo;
	}

	@Override
	public List<TbMajorInfo> findAll() {
		return TbMajorInfoDao.search("SELECT tmi_id,tmi_name FROM tb_major_info  WHERE tmi_status<>0 ORDER BY tmi_add_date DESC", null);
	}
	
	
	
	
	
	
	/**
	 * hxx
	 */
	public TbMajorInfo seachOne(TbMajorInfo tbMajorInfo) {
		return TbMajorInfoDao.searchOne(tbMajorInfo);
	}


	/**
	 * hxx
	 * 专业
	 * @param tbcourseInfoId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> tbMajorInfoName(Long tbcourseInfoId) {
		StringBuffer sql = new StringBuffer();
		//逻辑判断开始
		sql.append("SELECT m.tmi_id,m.tmi_name FROM tb_institute_department_info d INNER JOIN tb_major_info m ON d.tidi_id=m.tmi_department_id  ");
		sql.append("WHERE m.tmi_status=1 AND m.tmi_id= "+tbcourseInfoId);

		List<Map<String, Object>> maps = TbMajorInfoDao.searchForMap(sql.toString(), null);
		//逻辑判断结束
		System.out.println("@@@@@@@专业@@@@@@@@@@@@@@@@@"+maps);
		return maps;
	}

	
	
	
}