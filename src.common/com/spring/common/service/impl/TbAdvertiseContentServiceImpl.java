package com.spring.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.dao.TbAdvertiseContentDao;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbAdvertiseContent;
import com.spring.common.service.TbAdvertiseContentService;

@Service("tbAdvertiseContentService")
@Transactional
public class TbAdvertiseContentServiceImpl extends BaseServiceImpl<TbAdvertiseContent, Long> implements
		TbAdvertiseContentService {

	@Resource TbAdvertiseContentDao tbAdvertiseContentDao;
	
	@Override
	public BaseDao<TbAdvertiseContent, Long> getGenericDao() {
		return tbAdvertiseContentDao;
	}
	
	/**
	 * 
	* Title: findForJson 
	* Description:  分页查询广告内容
	* @param params
	* @return 
	* @see com.spring.backstage.service.TbAdvertiseContentService#findForJson(java.util.HashMap)
	 */
	@Override
	public HashMap<String, Object> findForJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();

		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
		String order = params.get("order");
		String tacCata = params.get("tacCata");
		String tacContent = params.get("tacContent");
		String AddDateEnd = params.get("AddDateEnd");
		String AddDate = params.get("AddDate");
		
		String sql = "SELECT a.tac_id tacId,a.tac_phone_image tacPhoneImage ,a.tac_web_image tacWebImage,a.tac_content tacContent,"
				+ "(SELECT c.tac_name FROM tb_advertise_catalog c WHERE a.tac_cata_id=c.tac_id) tacCataName,"
				+ "(SELECT u.account FROM userinfo u WHERE a.tac_creater=u.id)  tacAddPerson "
				+ "FROM tb_advertise_content a WHERE 1=1 ";
		List<Object> values = new ArrayList<Object>();
		
		if (!StringUtils.isBlank(tacCata)) {
			sql += " and a.tac_cata_id = ? ";
			values.add(tacCata);
		}
		if (!StringUtils.isBlank(tacContent)) {
			sql += " and a.tac_content like '%"+tacContent+"%' ";
		}
		
		if (AddDate != null && !AddDate.equalsIgnoreCase("")) {
			sql +=" AND DATE_FORMAT(a.tac_add_date,'%Y-%m-%d') >= DATE_FORMAT('"+AddDate+"','%Y-%m-%d')";
		} 
		if (AddDateEnd!= null && !AddDateEnd.equalsIgnoreCase("")) {
			sql += " AND DATE_FORMAT(a.tac_add_date,'%Y-%m-%d') <= DATE_FORMAT('"+AddDateEnd+"','%Y-%m-%d')";
		}
		
		if (!StringUtils.isBlank(order)) {
			sql += " order by a.tac_add_date " + order;
		} else {
			sql += " order by a.tac_add_date desc ";
		}
		if (pageSize == 0) {
			List<Map<String, Object>> list = tbAdvertiseContentDao.searchForMap(sql, values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbAdvertiseContentDao.searchForMap(sql, values, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	/**
	 * 
	* Title: findCatalogName 
	* Description:  查询广告板块名称
	* @return 
	* @see com.spring.backstage.service.TbAdvertiseContentService#findCatalogName()
	 */
	@Override
	public List<Map<String, Object>> findCatalogName() {
		return tbAdvertiseContentDao.searchForMap("select tac_id as tacCataId,tac_name as tacCataName from tb_advertise_catalog where tac_status = 1", null);
	}
	
	@Override
	public List<TbAdvertiseContent> findCatalogId(long tacId) {
		return tbAdvertiseContentDao.searchp("select c.* from tb_advertise_content c where c.tac_status = 1 AND c.tac_cata_id = '"+tacId+"'", null);
	}
	
	/**
	 * 
	* Title: findAdvertiseById 
	* Description:  根据id查询广告内容
	* @param id
	* @return 
	* @see com.spring.backstage.service.TbAdvertiseContentService#findAdvertiseById(java.lang.Long)
	 */
	@Override
	public Map<String, Object> findAdvertiseById(Long id) {
		String sql = "SELECT a.tac_id tacId,a.tac_cata_id tacCataId,a.tac_phone_image tacPhoneImage ,a.tac_web_image tacWebImage,a.tac_content tacContent,a.tac_link tacLink,"
				+ "(SELECT c.tac_name FROM tb_advertise_catalog c WHERE a.tac_cata_id=c.tac_id) as tacCataName,"
				+ "(SELECT u.account FROM userinfo u WHERE a.tac_creater=u.id) as tacAddPerson "
				+ "FROM tb_advertise_content a WHERE 1=1 and a.tac_id =  "+id;
		
		List<Map<String, Object>> list =  tbAdvertiseContentDao.searchForMap(sql, null);
		
		Map<String, Object> content = new HashMap<String, Object>();
		for(int i = 0; i<list.size(); i++){
			content = list.get(i);//将list转换成TbAdvertiseContent对象
		}
		
		return content;
	}
}
