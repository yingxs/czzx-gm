package com.spring.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spring.base.service.BaseService;
import com.spring.common.entity.TbWords;


public interface TbWordsService extends BaseService<TbWords, Long> {
	
	public TbWords searchOne(TbWords t);
	
	public HashMap<String, Object> findForJson(HashMap<String, String> params);

	public List<TbWords> selectList(String code);
	public List<TbWords> selectListEx(String code);
	public List<TbWords> findAllGangWei(String code);
	public List<TbWords> findAllPro();
	
	public List<TbWords> getEngineerWords(Long id);
	
	public List<TbWords> allParentById();
	public List<TbWords> allParentByWelfare();
	
	//获取数据字典的根目录
	public List<TbWords> getRootWords();
	
	public String findSuperCodeById(Long id);
	
	public String findIdByCode(String code);

	public List<TbWords> selectWordsList(String strCode);
	
	
	
	

	//民族
	public List<TbWords> ajax_get_ttination_findall();
	
	//政治面貌
	public List<TbWords> ajaxGetTtipoliticalFindAll();

	//学历层次
	public List<TbWords> ajaxGetttittiInDegreeFindAll();

	//职务职称
	public List<TbWords> ajaxGetttiPositionFindAll();

	//教职工类别
	public List<TbWords> ajaxGetttiPositionTypeFindAll();

	//现任工作
	public List<TbWords> ajaxGetttiPostFindAll();
	
	
	/**
	 * 下拉框加载所有学生状态
	 * 
	 * @return
	 */
	List<Map<String, Object>> ajax_get_statusList();

	/**
	 * 下拉框加载所有民族
	 * 
	 * @return
	 */
	List<Map<String, Object>> ajax_get_tsNationList();

	/**
	 * 下拉框加载所有政治面貌
	 * 
	 * @return
	 */
	List<Map<String, Object>> ajax_get_tsPoliticalList();

	/**
	 * 下拉框加载所有户口性质
	 * 
	 * @return
	 */
	List<Map<String, Object>> ajax_get_tsHjPropertyList();

	/**
	 * 下拉框加载所有入学学历
	 * 
	 * @return
	 */
	List<Map<String, Object>> ajax_get_tsInDegreeList();

	/**
	 * 下拉框加载所有家庭收入来源
	 * 
	 * @return
	 */
	List<Map<String, Object>> ajax_get_tsFamilyIncomeList();
	
	/**
	 * wanghao
	 */
	List<TbWords> getMajorYear();
	
	
}
