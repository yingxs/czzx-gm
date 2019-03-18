package com.spring.server.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.common.entity.PageBean;
import com.spring.server.dao.TbLeaveMessageDao;
import com.spring.server.entity.TbLeaveMessage;
import com.spring.server.service.TbLeaveMessageService;

@Service("tbLeaveMessage")
public class TbLeaveMessageServiceImpl extends BaseServiceImpl<TbLeaveMessage, Long>  implements TbLeaveMessageService{

	@Resource
	TbLeaveMessageDao tbLeaveMessageDao;
	
	@Override
	public BaseDao<TbLeaveMessage, Long> getGenericDao() {
		return tbLeaveMessageDao;
	}

	//查询留言列表
	@Override
	public HashMap<String, Object> findForJson(HashMap<String, String> params) {
		HashMap<String, Object> json = new HashMap<String, Object>();
		int page = params.get("page") == null ? 0:Integer.parseInt(params.get("page"));
		int pageSize = params.get("pageSize") == null ? 0:Integer.parseInt(params.get("pageSize"));
		String order = params.get("order");
		
		String tlmAskAnswerId = params.get("tlmAskAnswerId");//留言人
		String ltmName = params.get("ltmName");//联系人
		String tlmPhone = params.get("tlmPhone");//联系电话
		String tlmMail = params.get("tlmMail");//邮箱
		String tlmType = params.get("tlmType");//留言栏目
		System.out.println("留言栏目:"+tlmType);
		String tlmStatus = params.get("tlmStatus");//状态
		String AddDate = params.get("AddDate");//创建开始时间
		String AddDateEnd = params.get("AddDateEnd");//创建结束时间
		//查询sql
		StringBuffer sbf = new StringBuffer();
		sbf.append("SELECT tlm_id tlmId,tlm_ask_answer_id tlmAskAnswerId,ltm_name ltmName,tlm_phone tlmPhone,tlm_mail tlmMail,tlm_type tlmType,tlm_content tlmContent,tlm_status tlmStatus,tlm_if_secret tlmIfSecret, "); //tlmIfSecret  是否单独回复，1：是0：否
		sbf.append("(if(tlm_ask_type=1,(select ts_name from tb_student where ts_id=tlm_ask_answer_id ),(select tt_name from tb_teacher where tt_id=tlm_ask_answer_id ))) answerName, ");//留言人
		sbf.append("DATE_FORMAT(tlm_add_date,'%Y-%m-%d %H:%i:%s') tlmAddDate ");//创建时间
		sbf.append("FROM tb_leave_message ");
		sbf.append("WHERE tlm_status<>0 ");
		
		//查询条件
		//根据留言人查询 
		if (!StringUtils.isBlank(tlmAskAnswerId)) {
			
			sbf.append("AND (if(tlm_ask_type=1,(select ts_name from tb_student where ts_id=tlm_ask_answer_id ),(select tt_name from tb_teacher where tt_id=tlm_ask_answer_id ))) LIKE '%"+tlmAskAnswerId+"%' ");
			
			if("游".equals(tlmAskAnswerId)||"客".equals(tlmAskAnswerId)||"游客".equals(tlmAskAnswerId)){
				sbf.append("or tlm_ask_answer_id =0 ");
			}
	
		}
		//根据姓名查询 
		if (!StringUtils.isBlank(ltmName)) {
			sbf.append("AND ltm_name LIKE '%"+ltmName+"%' ");
		}
		//根据留言栏目查询
		if (!StringUtils.isBlank(tlmType)) {
			sbf.append(" AND tlm_type = "+tlmType+" "); 
		}
		//根据创建时间查询
		if (AddDate != null && !AddDate.equalsIgnoreCase("")) {
			sbf.append("AND DATE_FORMAT(tlm_add_date,'%Y-%m-%d') >= DATE_FORMAT('"+AddDate+"','%Y-%m-%d') ");
		} 
		
		if (AddDateEnd!= null && !AddDateEnd.equalsIgnoreCase("")) {
			sbf.append("AND DATE_FORMAT(tlm_add_date,'%Y-%m-%d') <= DATE_FORMAT('"+AddDateEnd+"','%Y-%m-%d') ");
		}
		if (!StringUtils.isBlank(order)) {
			sbf.append("ORDER BY tlm_add_date "+order+" ");
		} else {
			sbf.append("ORDER BY tlm_add_date DESC ");
		}
		
		
		if (pageSize == 0) {
			List<Map<String, Object>> list = tbLeaveMessageDao.searchForMap(sbf.toString(), null);
			json.put("total", list.size());
			json.put("rows", list);
			return json;
		}else {
			PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>(page, pageSize);
			pageBean = tbLeaveMessageDao.searchForMap(sbf.toString(), null, pageBean);
			
			json.put("total", pageBean.getRowCount());
			json.put("rows", pageBean.getList());
			return json;
		}
	}



	
}
