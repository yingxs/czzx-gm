package com.spring.common.service;

import java.util.HashMap;
import java.util.List;

import com.spring.base.service.BaseService;
import com.spring.common.entity.PageBean;
import com.spring.common.entity.TbMessage;

public interface TbMessageService extends BaseService<TbMessage, Long> {

	/**
	 * 分页查询
	 * @param params 条件参数
	 * @param pageBean 
	 * @return
	 */
	PageBean<TbMessage> findByPage(TbMessage params,PageBean<TbMessage> pageBean);
	
	HashMap<String, Object> findForJson(HashMap<String, String> params);
	/**
	 * 
	* @Title: findTbMessage 
	* @Description: 根据对象参数查询消息
	* @param @param searchParams
	* @param @return    设定文件 
	* @return Member    返回类型 
	* @throws
	 */
	TbMessage findTbMessage(TbMessage searchParams);
	/**
	 * 
	* @Title: findForJsonByTitle 
	* @Description: 根据消息标题分页消失消息详情 
	* @param @param params
	* @param @return    设定文件 
	* @return HashMap<String,Object>    返回类型 
	* @throws
	 */
	HashMap<String, Object> findForJsonByTitle(HashMap<String, String> params);

	int updateByNumber(String tmNumber);

	HashMap<String, Object> findForMember(HashMap<String, String> params);

}
