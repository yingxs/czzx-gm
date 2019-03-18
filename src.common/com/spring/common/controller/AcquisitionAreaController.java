package com.spring.common.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.base.controller.BaseController;
import com.spring.common.entity.ChinaCity;
import com.spring.common.entity.ChinaCounty;
import com.spring.common.entity.ChinaProvince;
import com.spring.common.service.ChinaCityService;
import com.spring.common.service.ChinaCountyService;
import com.spring.common.service.ChinaProvinceService;

/**
 * 获取 区域列表 base公共
 * @author 
 *
 */
@Controller
@RequestMapping(value="/AcquisitionArea")
public class AcquisitionAreaController extends BaseController{

	@Resource ChinaCityService chinaCityService;
	@Resource ChinaCountyService chinaCountyService;
	@Resource ChinaProvinceService chinaProvinceService;
	
	/**ChinaProvince 所有省
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/chinaCityService")
	public List<ChinaProvince> chinaCityService(HttpServletRequest request) {
		logger.error("获取 所有省 ajax 返回数据");
		return chinaProvinceService.findAllPro();
	}
	
	/**ChinaProvince 所有省下面市
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/chinaCityServiceByCity")
	public List<ChinaCity> chinaCityServiceByCity(HttpServletRequest request) {
		logger.error("获取 所有省下面市 ajax 返回数据");
		
		if("".equals(request.getParameter("id"))){
			return chinaCityService.findAllCity();
		}
		return chinaCityService.findAllCityByPro(Long.valueOf(request.getParameter("id")));
	}
	
	
	/**ChinaProvince 所有省下面市下面区
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/chinaCityServiceByCountTy")
	public List<ChinaCounty> chinaCityServiceByCountTy(HttpServletRequest request) {
		logger.error("获取市下面区 ajax 返回数据");
		if("".equals(request.getParameter("id"))){
			return chinaCountyService.findAllCounty();
		}
		return chinaCountyService.findAllCountyByCity(Long.valueOf(request.getParameter("id")));
	}
	
	
	
}
