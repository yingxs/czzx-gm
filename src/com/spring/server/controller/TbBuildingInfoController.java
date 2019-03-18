package com.spring.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.spring.base.shiro.ShiroUser;
import org.apache.shiro.SecurityUtils;
import com.spring.server.common.RegExpValidatorUtils;
import com.spring.server.common.ServerResponse;
import com.spring.server.entity.ClassRoomInfoVO;
import com.spring.server.entity.TbBuildingInfo;
import com.spring.server.entity.TbClassroomInfo;
import com.spring.server.entity.TbFloorInfo;
import com.spring.server.service.TbBuildingInfoService;
import com.spring.server.service.TbClassRoomRelateService;
import com.spring.server.service.TbClassroomInfoService;
import com.spring.server.service.TbFloorInfoService;
import com.spring.base.controller.BaseController;

/**
 * 教学楼控制器
 *
 * @author wanghao
 * @Date 2019-03-08 09:49:40
 */
@Controller
@RequestMapping("/TbBuildingInfo")
public class TbBuildingInfoController extends BaseController {

    private String PREFIX = "/TbBuildingInfo";

    @Autowired
    private TbBuildingInfoService TbBuildingInfoService;
    
    @Autowired
    private TbFloorInfoService tbFloorInfoService;
    
    @Autowired
    private TbClassroomInfoService tbClassroomInfoService;
    
    @Autowired
    private TbClassRoomRelateService tbClassRoomRelateService;

    @RequestMapping(value = "/index/{paper}")
	public String view(HttpServletRequest request, @PathVariable String paper,
			 Long TbBuildingInfoId) {
		
		if ("list".equals(paper)) {
			//返回当前会员对此菜单的按钮权限操作
			request.setAttribute("btnList", saveMenuId(request));
			return PREFIX+"/TbBuildingInfo_list";
		}
		if ("add".equals(paper)) {
			return PREFIX+"/TbBuildingInfo_add";
		}
		if ("edit".equals(paper)) {

			TbBuildingInfo entity = TbBuildingInfoService.findById(TbBuildingInfoId);
			request.setAttribute("entity", entity);
			return PREFIX+"/TbBuildingInfo_edit";
		}

		return PREFIX +"/"+ paper;
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public Map<String, Object> list(String buildName) {
		try {
			RegExpValidatorUtils.isNotContainSpecialCharacters(buildName, 20, "楼宇名称", true);
			return ServerResponse.createServerResponseBySUCCESS("数据查询成功!", TbBuildingInfoService.findAllBylikeBuildName(buildName));
		} catch (Exception e) {
			e.printStackTrace();
			return ServerResponse.createServerResponseByFAIL(e.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/add")
	public Map<String,Object> add(HttpServletRequest request,TbBuildingInfo tbBuildingInfo) {
		return addOrEdit(request,tbBuildingInfo);
	}
	
	@ResponseBody
	@RequestMapping(value = "/addClassRoom")
	public Map<String,Object> addClassRoom(HttpServletRequest request) {
		try {
			String strBuildId = request.getParameter("build_id");
			String strFloorId = request.getParameter("floor_id");
			String strClassRoomName = request.getParameter("classRoom_name");
			String strClassRoomCount = request.getParameter("classRoom_count");
			RegExpValidatorUtils.isNumber(strBuildId, 10, "楼宇", false);
			RegExpValidatorUtils.isNumber(strFloorId, 10, "楼层", false);
			RegExpValidatorUtils.isNotContainSpecialCharacters(strClassRoomName, 20, "教室名称", false);
			RegExpValidatorUtils.isNumber(strClassRoomCount, 8, "教室容纳量", false);
			TbClassroomInfo tbClassroomInfo = new TbClassroomInfo();
			tbClassroomInfo.setTciBuildingId(Long.valueOf(strBuildId));
			tbClassroomInfo.setTciFloorId(Long.valueOf(strFloorId));
			tbClassroomInfo.setTciClassname(strClassRoomName);
			tbClassroomInfo.setTciCount(Integer.valueOf(strClassRoomCount));
			tbClassroomInfo.setTciStatus(1);
			tbClassroomInfo.setTciUseStatus("0");
			tbClassroomInfoService.save(tbClassroomInfo);
			return ServerResponse.createServerResponseBySUCCESS("教室添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ServerResponse.createServerResponseByFAIL(e.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/addFloor")
	public Map<String,Object> addFloor(HttpServletRequest request) {
		
		try {
			String buildId = request.getParameter("buildId");
			String floorName = request.getParameter("floorName");
			RegExpValidatorUtils.isNumber(buildId, 10, "楼宇ID", false);
			RegExpValidatorUtils.isNotContainSpecialCharacters(floorName, 20, "楼层名称", false);
			
			List<TbFloorInfo> listTbFloorInfo = tbFloorInfoService.finAllByName(floorName,buildId);
			
			if (listTbFloorInfo!=null && listTbFloorInfo.size() > 0 ){
				return ServerResponse.createServerResponseByFAIL(140, "楼层已存在");
			}else{
				TbFloorInfo tbFloorInfo = new TbFloorInfo();
				tbFloorInfo.setTfiBuildingId(Long.valueOf(buildId) );
				tbFloorInfo.setTfiName(floorName);
				tbFloorInfo.setTfiStatus(1);
				tbFloorInfoService.save(tbFloorInfo);
				return ServerResponse.createServerResponseBySUCCESS("楼层添加成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ServerResponse.createServerResponseByFAIL(e.getMessage());
		}
	}
	
	

	@ResponseBody
	@RequestMapping(value = "/edit")
	public Map<String,Object> edit(HttpServletRequest request,TbBuildingInfo tbBuildingInfo){
		return addOrEdit(request,tbBuildingInfo);
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
	public Map<String,Object> del(HttpServletRequest request,String build_id) {
		try {
			RegExpValidatorUtils.isNumber(build_id, 10, "楼宇ID", false);
			
			TbBuildingInfo BuildingInfo = new TbBuildingInfo();
			BuildingInfo.setTbiId(Long.valueOf(build_id));
			BuildingInfo.setTbiStatus(0);
			
			TbBuildingInfo tbBuildingInfo = TbBuildingInfoService.findById(Long.valueOf(build_id));
			if (tbBuildingInfo == null) {
				return ServerResponse.createServerResponseByFAIL("该楼宇不存在");
			}
			
			List<TbClassroomInfo> listTbClassroomInfo = tbClassroomInfoService.findByBuildId(Long.valueOf(build_id));
			if(listTbClassroomInfo.size() > 0 && listTbClassroomInfo != null){
				return ServerResponse.createServerResponseByFAIL("下有教室，不能删除");
			}
			TbBuildingInfoService.update(BuildingInfo);
			return ServerResponse.createServerResponseBySUCCESS("楼宇删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbBuildingInfo-del()]：错误原因:" + e.getMessage());
			return ServerResponse.createServerResponseByFAIL(e.getMessage());
		}
	}
	@ResponseBody
	@RequestMapping(value = "/delFloor")
	public Map<String,Object> delFloor(HttpServletRequest request) {
		try {
			String buildId = request.getParameter("buildId");
			String floorId = request.getParameter("floorId");
			RegExpValidatorUtils.isNumber(buildId, 10, "楼宇ID", false);
			RegExpValidatorUtils.isNumber(floorId, 10, "楼宇楼层", false);
			HashMap<String, String> mapParams = new HashMap<String,String>();
			mapParams.put("buildId", buildId);
			mapParams.put("floorId", floorId);
			TbBuildingInfo listTbBuildingInfo = TbBuildingInfoService.findById(Long.valueOf(buildId));
			TbFloorInfo listTbFloorInfo = tbFloorInfoService.findById(Long.valueOf(floorId));
			if (listTbBuildingInfo == null ) {
				ServerResponse.createServerResponseByFAIL("该楼宇ID不存在");
			}
			if (listTbFloorInfo == null) {
				ServerResponse.createServerResponseByFAIL("该楼层ID不存在");
			}
			
			List<TbClassroomInfo> listTbClassroomInfo = tbClassroomInfoService.findAllByBuildIdAndFloorId(mapParams);
			
			if (listTbClassroomInfo != null && listTbClassroomInfo.size() > 0) {
				return ServerResponse.createServerResponseByFAIL("该楼层下有教室，不能删除");
			}
			TbFloorInfo tbFloorInfo = new TbFloorInfo();
			tbFloorInfo.setTfiId(Long.valueOf(floorId) );
			tbFloorInfo.setTfiBuildingId(Long.valueOf(buildId));
			tbFloorInfo.setTfiStatus(0);
			
			tbFloorInfoService.update(tbFloorInfo);
			return ServerResponse.createServerResponseBySUCCESS("楼层删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbBuildingInfo-del()]：错误原因:" + e.getMessage());
			return ServerResponse.createServerResponseByFAIL(e.getMessage());
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/delClassRoom")
	public Map<String,Object> delClassRoom(HttpServletRequest request) {
		try {
			String[] parameterValues = request.getParameterValues("class_room[]");
			for (String str : parameterValues) {
				RegExpValidatorUtils.isNumber(str, 10, "教室ID", false);
			}
			List<ClassRoomInfoVO> listClassRoomInfoVO = tbClassroomInfoService.findByIds(parameterValues);
			if (listClassRoomInfoVO.size() > 0) {
				return ServerResponse.createServerResponse(108,"教室正在被使用,不能删除",listClassRoomInfoVO);
			}
			
			
			
			
			int iUpdateCount = tbClassroomInfoService.batchDel(parameterValues);
			if (iUpdateCount > 0){
				return ServerResponse.createServerResponseBySUCCESS("教室删除成功");
			}else {
				return ServerResponse.createServerResponseBySUCCESS("教室删除失败,未知异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbBuildingInfo-del()]：错误原因:" + e.getMessage());
			return ServerResponse.createServerResponseByFAIL(e.getMessage());
		}
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/relieveClassRoom")
	public Map<String,Object> relieveClassRoom(HttpServletRequest request) {
		try {
			String[] parameterValues = request.getParameterValues("class_room[]");
			for (String str : parameterValues) {
				RegExpValidatorUtils.isNumber(str, 10, "教室ID", false);
			}
			
			List<ClassRoomInfoVO> listClassRoomInfoVO = tbClassRoomRelateService.findNotUse(parameterValues);
			if (listClassRoomInfoVO.size() > 0) {	
				return ServerResponse.createServerResponse(108,"教室未被占用，解绑失败", listClassRoomInfoVO);
			}
			
			int delCount = tbClassRoomRelateService.relieveClassRoome(parameterValues);
			if (delCount > 0){
				return ServerResponse.createServerResponseBySUCCESS("解绑成功");
			}
			return ServerResponse.createServerResponseByFAIL("解绑失败,未知异常");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbBuildingInfo-del()]：错误原因:" + e.getMessage());
			return ServerResponse.createServerResponseByFAIL(e.getMessage());
		}
	}
	
	
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "/ajax_get_floor_list_BybuildId")
	public Map<String,Object> ajax_get_floor_list_BybuildId(HttpServletRequest request, String buildId){
		
		try {
			Map<String, String> mapParam = new HashMap<>();
			String order = request.getParameter("order");
			RegExpValidatorUtils.isNotContainSpecialCharacters(order, 10, "order", true);
			RegExpValidatorUtils.isNumber(buildId, 10, "楼宇ID", false);
			mapParam.put("order", order);
			mapParam.put("buildId",buildId);
			return ServerResponse.createServerResponseBySUCCESS("数据查询成功", tbFloorInfoService.findAllByBuildId(mapParam)) ;
		} catch (Exception e) {
			e.printStackTrace();
			return ServerResponse.createServerResponseByFAIL(e.getMessage());
		}
	}
	
	
	/**
	 * 根据楼宇ID和楼层ID获取所有教室
	 * @param request
	 * @param buildId
	 * @param floorId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_get_classRoom_list_ByBuildIdAndFloorId")
	public Map<String,Object> ajax_get_classRoom_list_ByBuildIdAndFloorId(HttpServletRequest request, String buildId,String floorId){
		try {
			Map<String, String> mapParam = new HashMap<>();
			RegExpValidatorUtils.isNumber(buildId.trim(), 10, "楼宇ID", false);
			RegExpValidatorUtils.isNumber(floorId.trim(),10, "楼层ID", false);
			mapParam.put("buildId",buildId);
			mapParam.put("floorId",floorId);
			return ServerResponse.createServerResponseBySUCCESS("数据查询成功", tbClassroomInfoService.findAllByBuildIdAndFloorId(mapParam)) ;
		} catch (Exception e) {
			e.printStackTrace();
			return ServerResponse.createServerResponseByFAIL(e.getMessage());
		}
	}
	
	
	/**
	 * 获取所有楼宇
	 * @param request
	 * @param buildId
	 * @param floorId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax_get_budiling_list")
	public Map<String,Object> ajax_get_budiling_list(){
		return ServerResponse.createServerResponseBySUCCESS("数据查询成功", TbBuildingInfoService.findAll());
	}
	
	
	
	private Map<String,Object> addOrEdit(HttpServletRequest request,TbBuildingInfo tbBuildingInfo) {
		if (tbBuildingInfo == null){
			ServerResponse.createServerResponseByINVALID_PARAM("参数非法");
		}
		try {
			String buildName = request.getParameter("build_name");
			String build_id = request.getParameter("build_id");
			if (build_id != null) {
				RegExpValidatorUtils.isNumber(build_id.trim(), 10, "楼宇ID", false);
			}
			RegExpValidatorUtils.isNotContainSpecialCharacters(buildName, 20, "楼宇名称", false);
			if (build_id != null) {
				tbBuildingInfo.setTbiId(Long.valueOf(build_id));
			}
			tbBuildingInfo.setTbiName(buildName);
			tbBuildingInfo.setTbiRegionId(1L);
			tbBuildingInfo.setTbiStatus(1);
			ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject()
					.getPrincipals().getPrimaryPrincipal();// 获取当前登录用户
			if (loginUser == null) {
				ServerResponse.createServerResponseByNEED_LOGIN("会话过期，请重新登录");
			}
			List<TbBuildingInfo> listTbBuildingInfo = TbBuildingInfoService.findAllBylikeBuildName(buildName,build_id);
			if (listTbBuildingInfo.size() > 0){
				return ServerResponse.createServerResponseByFAIL(140, "该楼宇已经存在");
			}
			if (build_id != null){
				TbBuildingInfoService.update(tbBuildingInfo);
				return ServerResponse.createServerResponseBySUCCESS("楼宇编辑成功");
			}
			TbBuildingInfoService.save(tbBuildingInfo);
			
			return ServerResponse.createServerResponseBySUCCESS("楼宇新增成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[TbBuildingInfo-add()]：错误原因:" + e.getMessage());
			return ServerResponse.createServerResponseByFAIL(e.getMessage());
		}
	}
	
	
	
	
}
