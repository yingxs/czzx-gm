package com.spring.server.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.base.utils.GlobalStatic;
import com.spring.base.utils.StringUtil;
import com.spring.common.entity.TbStudent;
import com.spring.common.entity.Teacher;
import com.spring.common.entity.Userinfo;
import com.spring.common.service.TbStudentService;
import com.spring.common.service.TeacherService;
import com.spring.common.service.UserinfoService;
import com.spring.server.entity.TbDepartmentTeacher;
import com.spring.server.entity.TbLeaveMessage;
import com.spring.server.entity.TbTeacherInfo;
import com.spring.server.service.TbLeaveMessageService;
import com.spring.server.service.TbTeacherInfoService;

@Controller
@RequestMapping("/tbLeaveMessage")
public class TbLeaveMessageController {

	@Resource
	TbLeaveMessageService tbLeaveMessageService;
	
	@Resource
	TbStudentService tbStudentService;
	
	@Resource
	TbTeacherInfoService tbTeacherService;
	
	@Resource
	TeacherService teacherService;
	
	@Resource
	UserinfoService userinfoService;
	
	/**
	 * 
	 * @Title: view @Description: 页面跳转 @param @param request @param @param
	 *         paper @param @param id @param @return @return String @throws
	 */
	@RequestMapping(value = "/index/{paper}")
	public String view(HttpServletRequest request, @PathVariable String paper,
			@RequestParam(value = "tlmId", required = false) Long tlmId) {
		if (StringUtils.isBlank(paper)) {
			return "";
		}

		//跳转到留言管理列表页表
		if ("list".equals(paper)) {
           
			System.out.println("进入留言管理列表页面");
			
			return "leaveMessage/tbLeaveMessage";
		}
		
		//跳转到留言管理详情页面
		if ("detail".equals(paper)) {
			
			System.out.println("进入留言管理详情页面");
			TbLeaveMessage tbLeaveMessage=tbLeaveMessageService.findById(tlmId);
			if(tbLeaveMessage!=null){
				request.setAttribute("tbLeaveMessage", tbLeaveMessage);
				System.out.println("tbLeaveMessage.getTlmStatus()...................="+tbLeaveMessage.getTlmStatus());
				Userinfo userinfo=userinfoService.findById(tbLeaveMessage.getTlmCheckPerson());
				if(userinfo!=null){
					request.setAttribute("uName", userinfo.getName());
				}else{
					request.setAttribute("uName", "");
				}
				
				
				//answerName
				//判断留言人是否是游客       TlmAskAnswerId()==0代表游客
				if(tbLeaveMessage.getTlmAskAnswerId()==0){
					request.setAttribute("answerName", "游客");
				}else{
					if(tbLeaveMessage.getTlmAskType() == null){
						tbLeaveMessage.setTlmAskType(0);
					}
					//判断   如果tlm_ask_type=1  关联学生表(tb_student表)  查询学生名称(ts_name字段)
					if( tbLeaveMessage.getTlmAskType()==1 ){
						TbStudent tbStudent=tbStudentService.findById(tbLeaveMessage.getTlmAskAnswerId());
						if(tbStudent!=null){
							request.setAttribute("answerName", tbStudent.getTsName());
						}else{
							request.setAttribute("answerName", "");
						}
						
					}else if(tbLeaveMessage.getTlmAskType()==2){
						//判断   如果tlm_ask_type=2  关联教师表(tb_teacher表)  查询教师名称(tt_name字段)
						//System.out.println(tbLeaveMessage.getTlmAskAnswerId());
						Teacher tbTeacher=teacherService.findById(Integer.valueOf(tbLeaveMessage.getTlmAskAnswerId()+""));
						///System.out.println(tbTeacher!=null);
						if(tbTeacher!=null){
							request.setAttribute("answerName", tbTeacher.getName());
						}else{
							request.setAttribute("answerName", "");
						}
					}else{
						request.setAttribute("answerName", "");
					}
				}
				
			}else{
				request.setAttribute("tbLeaveMessage", new TbLeaveMessage());
			}
			
			
			
			//判断状态    1:留言提交(待审核) 2:已审核(待回复)   3：审核失败    4：已回复    0:删除
			
			//待审核    (根据状态进入不同的详情页面)
			if(tbLeaveMessage.getTlmStatus()==1){
				return "leaveMessage/tbLeaveMessageDetailDSH";
			}
			//待回复
			if(tbLeaveMessage.getTlmStatus()==2){
				return "leaveMessage/tbLeaveMessageDetailDHF";
			}
			//审核失败
			if(tbLeaveMessage.getTlmStatus()==3){
				return "leaveMessage/tbLeaveMessageDetailSHSB";
			}
			//已回复
			if(tbLeaveMessage.getTlmStatus()==4){
				
				try {
					if(tbLeaveMessage.getTlmReply()!=null){
						//用tlm_reply关联回复表(tb_leave_message) 获取回复内容和回复时间
						TbLeaveMessage tbLeaveMessage2=tbLeaveMessageService.findById(tbLeaveMessage.getTlmReply());
						if(tbLeaveMessage2!=null){
							request.setAttribute("tbLeaveMessage2", tbLeaveMessage2);
						}else{
							request.setAttribute("tbLeaveMessage2", new TbLeaveMessage());
						}
						//用tlm_ask_answer_id关联教师表(tb_teacher) 获取回复人
						Teacher tbTeacher=teacherService.findById(Integer.valueOf(tbLeaveMessage2.getTlmAskAnswerId()+""));
						///System.out.println(tbTeacher!=null);
						if(tbTeacher!=null){
							request.setAttribute("ttName", tbTeacher.getName());
						}else{
							request.setAttribute("ttName", "");
						}
					}else{
						request.setAttribute("ttName", "");
						request.setAttribute("tbLeaveMessage2", new TbLeaveMessage());
					}
				} catch (Exception e) {
					// TODO: handle exception
					request.setAttribute("ttName", "");
					request.setAttribute("tbLeaveMessage2", new TbLeaveMessage());
				}
				
				
				return "leaveMessage/tbLeaveMessageDetailYHF";
			}
		}

		return "leaveMessage/" + paper;
	}
	
	
	/**
	 * 
	 * 留言管理列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public HashMap<String, Object> list(HttpServletRequest request) {
		/**
		 * 添加分页和查询参数
		 */
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("page", request.getParameter("page"));
		params.put("pageSize", request.getParameter("rows"));
		params.put("sort", request.getParameter("sort"));
		params.put("order", request.getParameter("order"));
		params.put("tlmId", request.getParameter("tlmId"));//留言表主键Id
		params.put("tlmAskAnswerId", request.getParameter("tlmAskAnswerId"));//留言人
		params.put("ltmName", request.getParameter("ltmName"));//联系人
		params.put("tlmPhone", request.getParameter("tlmPhone"));//联系电话
		params.put("tlmMail", request.getParameter("tlmMail")); //邮箱
		params.put("tlmType", request.getParameter("tlmType"));//留言栏目
		params.put("tlmContent", request.getParameter("tlmContent")); //留言内容
		params.put("tlmStatus", request.getParameter("tlmStatus")); //状态
		params.put("AddDate", request.getParameter("AddDate"));//创建开始时间
		params.put("AddDateEnd", request.getParameter("AddDateEnd"));//创建结束时间
		
		try {
			return tbLeaveMessageService.findForJson(params);
		} catch (Exception e) {
		//	log.error("[/tbSpecCategoryAndSpec/list]错误，错误原因："+e.getMessage());
			e.printStackTrace();
			return null;
		}

	}
	
	
	
	/**
	 * 留言管理   操作中的审核通过,删除
	 * tlm_status=0 (删除)   tlm_status=2(审核通过) 
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public String deleteTwo(HttpServletRequest request) {
		String tlmId=request.getParameter("tlmId");
		String status=request.getParameter("status");
		TbLeaveMessage tbLeaveMessage = tbLeaveMessageService.findById(Long.valueOf(tlmId));
		tbLeaveMessage.setTlmStatus(Integer.valueOf(status));
		if(Integer.valueOf(status)!=0){
			tbLeaveMessage.setTlmCheckTime(new Timestamp(new Date().getTime()));
			Userinfo userInfo = (Userinfo) request.getSession().getAttribute
					(GlobalStatic.DEFAULT_LOGIN_SESSION_NAME);//获取登录用户
			tbLeaveMessage.setTlmCheckPerson(userInfo.getId());
		}
		tbLeaveMessageService.update(tbLeaveMessage);
		return "100";
	}

	/**
	 * 留言管理   操作中的审核失败
	 *    tlm_status=3(审核失败)
	 */
	@ResponseBody
	@RequestMapping(value = "/refuse")
	public String refuse(HttpServletRequest request) {
		String tlmId=request.getParameter("tlmId");
		String status=request.getParameter("status");
		String r=request.getParameter("r");
		TbLeaveMessage tbLeaveMessage = tbLeaveMessageService.findById(Long.valueOf(tlmId));
		tbLeaveMessage.setTlmStatus(Integer.valueOf(status));
		tbLeaveMessage.setTlmBackMemo(r);
		if(Integer.valueOf(status)!=0){
			tbLeaveMessage.setTlmCheckTime(new Timestamp(new Date().getTime()));
			Userinfo userInfo = (Userinfo) request.getSession().getAttribute
					(GlobalStatic.DEFAULT_LOGIN_SESSION_NAME);//获取登录用户
			tbLeaveMessage.setTlmCheckPerson(userInfo.getId());
		}
		tbLeaveMessageService.update(tbLeaveMessage);
		return "100";
	}
	
}
