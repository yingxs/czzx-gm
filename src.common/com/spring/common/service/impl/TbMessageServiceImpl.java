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
import com.spring.common.dao.TbMessageDao;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbMessage;
import com.spring.common.service.TbMessageService;

@Service("tbMessageService")
@Transactional
public class TbMessageServiceImpl extends BaseServiceImpl<TbMessage, Long>
		implements TbMessageService {

	@Resource
	TbMessageDao tbMessageDao;

	@Override
	public BaseDao<TbMessage, Long> getGenericDao() {
		return tbMessageDao;
	}

	@Override
	public TbMessage findTbMessage(TbMessage searchParams) {
		return tbMessageDao.searchOne(searchParams);
	}

	@Override
	public PageBean<TbMessage> findByPage(TbMessage params,
			PageBean<TbMessage> pageBean) {
		return tbMessageDao.search(params, pageBean);
	}

	/**
	 * 
	 * @Title: findForJson
	 * @Description: 分页消息
	 * @param @param params
	 * @param @return
	 * @return
	 * @see com.spring.backstage.service.SalaryService#findForJson(java.util.HashMap)
	 */
	@Override
	public HashMap<String, Object> findForJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();

		int page = params.get("page") == null ? 0 : Integer.parseInt(params
				.get("page"));
		int pageSize = params.get("pageSize") == null ? 0 : Integer
				.parseInt(params.get("pageSize"));
		String order = params.get("order");
		String tmTitle = params.get("tmTitle");
		String tmAddDate = params.get("tmAddDate");
		String tmAddDateEnd = params.get("tmAddDateEnd");
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("tm_id tmId,");//消息id
		sql.append("tm_number tmNumber,");//消息id
		sql.append("tm_title tmTitle,");//消息标题
		sql.append("(SELECT account FROM userinfo WHERE tm_addperson=id) tmAddperson,");//创建人
		sql.append("DATE_FORMAT(tm_addtime,'%Y-%m-%d %H:%i:%s') tmAddtime ");//创建时间
		sql.append("FROM tb_message ");//查询表
		sql.append("WHERE tm_status<>0 ");

		if (!StringUtils.isBlank(tmTitle)) {
			sql.append("AND tm_title like '%" + tmTitle + "%'");
		}
		if (tmAddDate != null && !tmAddDate.equalsIgnoreCase("")) {
			sql.append(" AND DATE_FORMAT(tm_addtime,'%Y-%m-%d') >= DATE_FORMAT('"+tmAddDate+"','%Y-%m-%d')");
		} 
		if (tmAddDateEnd!= null && !tmAddDateEnd.equalsIgnoreCase("")) {
			sql.append(" AND DATE_FORMAT(tm_addtime,'%Y-%m-%d') <= DATE_FORMAT('"+tmAddDateEnd+"','%Y-%m-%d')");
		}
		sql.append("GROUP BY tm_number ");
		if (!StringUtils.isBlank(order)) {
			sql.append(" order by tm_addtime " + order);
		} else {
			sql.append(" order by tm_addtime desc");
		}

		if (pageSize == 0) {
			List<Map<String, Object>> list = tbMessageDao.searchForMap(sql.toString(), null);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbMessageDao.searchForMap(sql.toString(), null, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	/**
	 * 
	 * Title: findForJsonByTitle Description: 根据消息标题分页显示消息详情
	 * 
	 * @param params
	 * @return
	 * @see com.spring.backstage.service.MessageService#findForJsonByTitle(java.util.HashMap)
	 */
	@Override
	public HashMap<String, Object> findForJsonByTitle(
			HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();

		int page = params.get("page") == null ? 0 : Integer.parseInt(params
				.get("page"));
		int pageSize = params.get("pageSize") == null ? 0 : Integer
				.parseInt(params.get("pageSize"));
		
		String tmNumber = params.get("tmNumber");
		
		String sql = "SELECT tmName,tmAccount,tmPhone,tmType,tmStatus "
				+ "FROM (SELECT tm_name tmName,tm_account tmAccount,tm_phone tmPhone,type tmType,tm_status tmStatus "
				+ "FROM tb_member WHERE tm_id in (SELECT tm_member_id FROM tb_message WHERE tm_type=1 and tm_number = '"+tmNumber+"') "
						+ "UNION ALL "
						+ "SELECT ts_name tmName,ts_account tmAccount,ts_contac_mobile tmPhone,type tmType,ts_status tmStatus "
						+ "FROM tb_sales WHERE ts_id in (SELECT tm_member_id FROM tb_message WHERE tm_type=2 and tm_number = '"+tmNumber+"') "
						+ "UNION ALL "
						+ "SELECT tw_name tmName,tw_account tmAccount,tw_phone tmPhone,type tmType,tw_status tmStatus "
						+ "FROM tb_worker WHERE tw_id in (SELECT tm_member_id FROM tb_message WHERE tm_type=3 and tm_number = '"+tmNumber+"')) AS tm WHERE tmStatus = 1";
		List<Object> values = new ArrayList<Object>();

		if (pageSize == 0) {
			List<Map<String, Object>> list = tbMessageDao.searchForMap(sql, values);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		} else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page,
					pageSize);
			pageBean = tbMessageDao.searchForMap(sql, values, pageBean);

			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

	@Override
	public int updateByNumber(String tmNumber) {
		String sql = "UPDATE tb_message SET tm_status = 0 WHERE tm_number = '"+tmNumber+"'";
		return tbMessageDao.update(sql, null);
	}
	
	/**
	 * 
	 * @Title: findForJson
	 * @Description: 会员列表
	 * @param @param params
	 * @param @return
	 * @return
	 * @see com.spring.backstage.service.SalaryService#findForJson(java.util.HashMap)
	 */
	@Override
	public HashMap<String, Object> findForMember(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();

		int page = params.get("page") == null ? 0 : Integer.parseInt(params
				.get("page"));
		int pageSize = params.get("pageSize") == null ? 0 : Integer
				.parseInt(params.get("pageSize"));
		String order = params.get("order");
		String tmType = params.get("tmType");
		String tmAccount = params.get("tmAccount");
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT tmId,");
		sql.append("tmName,");//姓名
		sql.append("tmAccount,");//登录账号
		sql.append("tmPhone,");//手机号码
		sql.append("tmType,");//会员类型
		sql.append("tmStatus ");//状态
		sql.append("FROM ");
		sql.append("(SELECT tm_id tmId, tm_name tmName,tm_account tmAccount,tm_phone tmPhone,type tmType,tm_status tmStatus FROM tb_member ");
		sql.append("UNION ALL ");
		sql.append("SELECT ts_id tsId, ts_name tmName,ts_account tmAccount,ts_contac_mobile tmPhone,type tmType,ts_status tmStatus FROM tb_sales ");
		sql.append("UNION ALL ");
		sql.append("SELECT tw_id twId, tw_name tmName,tw_account tmAccount,tw_phone tmPhone,type tmType,tw_status tmStatus FROM tb_worker)");
		sql.append("AS tm ");
		sql.append("WHERE tmStatus = 1 ");

		if (!StringUtils.isBlank(tmType)) {
			sql.append("AND tmType = '"+tmType+"' ");
		}
		if (!StringUtils.isBlank(tmAccount)) {
			sql.append("AND tmAccount like '%"+tmAccount+"%'");
		}

		if (pageSize == 0) {
			List<Map<String, Object>> list = tbMessageDao.searchForMap(sql.toString(), null);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbMessageDao.searchForMap(sql.toString(), null, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}

}
