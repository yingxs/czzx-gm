package com.spring.common.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.base.controller.BaseController;
import com.spring.base.shiro.ShiroUser;
import com.spring.base.utils.StringUtil;
import com.spring.common.entity.TbWords;
import com.spring.common.service.TbWordsService;

@Controller
@RequestMapping(value = "/words")
public class TbWordsController extends BaseController {

	@Resource
	TbWordsService tbWordsService;

	@RequestMapping(value = "/index/{paper}")
	public String view(HttpServletRequest request, @PathVariable String paper,
			@RequestParam(value = "twId", required = false) String twId) {
		
		if ("list".equals(paper)) {
			return "baseData/tbWords_list";
		}
		if ("add".equals(paper)) {
			return "baseData/tbWords_add";
		}
		if ("edit".equals(paper)) {

			TbWords tbWords = tbWordsService.findById(Long.valueOf(request.getParameter("twId")));
			request.setAttribute("tbWords", tbWords);
			return "baseData/tbWords_edit";
		}

		return "baseData/" + paper;
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public HashMap<String, Object> list(HttpServletRequest request) {

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("page", request.getParameter("page"));
		params.put("pageSize", request.getParameter("rows"));
		params.put("twCode", request.getParameter("twCode"));
		params.put("twName", request.getParameter("twName"));
		params.put("sort", request.getParameter("sort"));
		params.put("order", request.getParameter("order"));
		return tbWordsService.findForJson(params);
	}

	@ResponseBody
	@RequestMapping(value = "/getEngineerWords")
	public List<TbWords> getEngineerWords(HttpServletRequest request) {
		String strReferId = request.getParameter("id");
		return tbWordsService.getEngineerWords(Long.parseLong(strReferId));
	}

	@ResponseBody
	@RequestMapping(value = "/queryWordsByTwId")
	public TbWords queryWordsByTwId(HttpServletRequest request) {
		String twId = request.getParameter("twId");
		return tbWordsService.findById(Long.parseLong(twId));
	}

	/**
	 * 
	 * @Title: add
	 * @Description: 添加
	 * @param @param userinfo
	 * @param @return
	 * @return String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request,TbWords words) {
		String code = request.getParameter("code");
		
		if (StringUtils.isBlank(words.getTwName())
				|| StringUtils.isBlank(code)) {
			return "101";
		}
		// 判断编码是否已存在
		TbWords searchPars = new TbWords();
		searchPars.setTwCode(code);
		searchPars.setTwStatus(1);
		TbWords w = tbWordsService.searchOne(searchPars);
		if (w != null) {
			return "102";// 编码已存在
		}
		TbWords tbWordsOne = new TbWords();
		tbWordsOne.setTwName(words.getTwName());
		tbWordsOne.setTwStatus(1);
		tbWordsOne = tbWordsService.searchOne(tbWordsOne);
		if(tbWordsOne!=null){
			return "112";//编码名称已存在
		}
		// 验证编码是否为数字
		if (!StringUtils.isNumeric(words.getTwCode()))
			return "103";
		
		//编码长度只能为2或者4位
		if (!(words.getTwCode().length() == 2))
			return "104";

		try {
			ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject()
					.getPrincipals().getPrimaryPrincipal();// 获取当前登录用户
			words.setTwCode(code);
			words.setTwAddDate(new Timestamp(new Date().getTime()));
			words.setTwAddPerson(loginUser.getId());
			words.setTwStatus(1);
			tbWordsService.save(words);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbWordsController-add()]：错误原因:" + e.getMessage());
			return "110";
		}
	}

	/**
	 * 
	 * @Title: edit
	 * @Description: 修改
	 * @param @param userinfo
	 * @param @return
	 * @return String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,TbWords wordsInfo) {
		String code = request.getParameter("code");
		if (StringUtils.isBlank(wordsInfo.getTwName())
				|| StringUtils.isBlank(code)) {
			return "101";
		}

		// 判断编码是否已存在
		/*TbWords w = new TbWords();
		w.setTwCode(code);
		TbWords sw = tbWordsService.searchOne(w);
		if (sw != null) {
			return "102";// 编码已存在
		}*/
		
		TbWords searchPars = new TbWords();
		searchPars.setTwId(wordsInfo.getTwId());
		TbWords words = tbWordsService.searchOne(searchPars);

		// 增加对编码的验证功能
		/*if (!StringUtils.isNumeric(words.getTwCode()))
			return "103";

		if (!(words.getTwCode().length() == 2))
			return "104";*/

		try {
			//words.setTwCode(wordsInfo.getTwCode());
			words.setTwName(wordsInfo.getTwName());
			words.setTwValue(wordsInfo.getTwValue());

			tbWordsService.update(words);
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbWordsController-edit()]：错误原因:" + e.getMessage());
			return "110";
		}

	}

	/**
	 * 
	 * @Title: del
	 * @Description: 删除
	 * @param @param tcId
	 * @param @return
	 * @return String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/del")
	public String del(HttpServletRequest request) {

		String twId = request.getParameter("twId");

		if (StringUtil.isEmptyNull(twId)) {
			return "101";
		}
		try {
			TbWords words = tbWordsService.findById(Long.valueOf(twId));
			if(words.getTwCode().length()==2){
				List<TbWords> list = tbWordsService.selectList(words.getTwCode());
				if(list!=null && list.size()>0){
					return "102";
				}
			}
			
			tbWordsService.delete(Long.parseLong(twId));
			return "100";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbWordsController-del()]：错误原因:" + e.getMessage());
			return "110";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getWordsByCode")
	public List<TbWords> getWordsByCode(HttpServletRequest request) {
		String strCode = request.getParameter("code");
		return tbWordsService.selectList(strCode);
	}

	@ResponseBody
	@RequestMapping(value = "/getRootWords")
	public List<TbWords> getRootWords(HttpServletRequest request) {
		// String strCode = request.getParameter("code");
		return tbWordsService.getRootWords();
	}

	@ResponseBody
	@RequestMapping(value = "/getWordsByCodeEx")
	public List<TbWords> getWordsByCodeEx(HttpServletRequest request) {
		String strCode = request.getParameter("code");
		return tbWordsService.selectListEx(strCode);
	}

	@ResponseBody
	@RequestMapping(value = "/getProvAll")
	public List<TbWords> getProvAll(HttpServletRequest request) {
		return tbWordsService.findAll();
	}

	@ResponseBody
	@RequestMapping(value = "/findAllGangWei")
	public List<TbWords> findAllGangWei(HttpServletRequest request) {
		return tbWordsService.findAllGangWei("");
	}

	/**
	 * 
	 * @Title: findSuperCodeById
	 * @Description: 通过ID查找父项目编码
	 * @param @param request
	 * @param @return
	 * @return String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/findSuperCodeById", method = RequestMethod.POST)
	public String findSuperCodeById(HttpServletRequest request,
			String superCodeid) {
		if (superCodeid == null) {
			return "";
		}

		TbWords words = new TbWords();

		try {
			// 获取ID
			Long id = Long.valueOf(superCodeid);
			String superCode = tbWordsService.findSuperCodeById(id);// 查找跟项目code

			return superCode;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbWordsController-findSuperCodeById()]：错误原因:"
					+ e.getMessage());
			return null;
		}
	}

	/**
	 * 
	* @Title: findSuerNameByCode 
	* @Description:  根据编码查询父级名称
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/findSuerNameByCode", method = RequestMethod.POST)
	public String findSuerNameByCode(HttpServletRequest request) {
		String code = request.getParameter("code");
	
		try {
			// 获取ID
			String name = tbWordsService.findIdByCode(code);// 根据code查询id
			
			return name;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbWordsController-findSuerNameByCode()]：错误原因:"
					+ e.getMessage());
			return null;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/findWordsByCode")
	public List<TbWords> findWordsByCode(HttpServletRequest request) {
		String strCode = request.getParameter("code");
		return tbWordsService.selectWordsList(strCode);
	}
	
	
	/*
	 * 下拉框加载选择学生状态
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_get_statusList")
	public List<Map<String, Object>> ajax_get_statusList(HttpServletRequest request) {
		return tbWordsService.ajax_get_statusList();
	}

	/*
	 * 下拉框加载选择民族
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_get_tsNationList")
	public List<Map<String, Object>> ajax_get_tsNationList(HttpServletRequest request) {
		return tbWordsService.ajax_get_tsNationList();
	}
	
	/*
	 * 下拉框加载选择政治面貌
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_get_tsPoliticalList")
	public List<Map<String, Object>> ajax_get_tsPoliticalList(HttpServletRequest request) {
		return tbWordsService.ajax_get_tsPoliticalList();
	}
	
	/*
	 * 下拉框加载选择户口性质
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_get_tsHjPropertyList")
	public List<Map<String, Object>> ajax_get_tsHjPropertyList(HttpServletRequest request) {
		return tbWordsService.ajax_get_tsHjPropertyList();
	}
	
	/*
	 * 下拉框加载选择入学学历
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_get_tsInDegreeList")
	public List<Map<String, Object>> ajax_get_tsInDegreeList(HttpServletRequest request) {
		return tbWordsService.ajax_get_tsInDegreeList();
	}
	
	/*
	 * 下拉框加载选择家庭收入来源
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_get_tsFamilyIncomeList")
	public List<Map<String, Object>> ajax_get_tsFamilyIncomeList(HttpServletRequest request) {
		return tbWordsService.ajax_get_tsFamilyIncomeList();
	}
}
