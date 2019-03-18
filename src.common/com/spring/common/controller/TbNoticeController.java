package com.spring.common.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.base.controller.BaseController;
import com.spring.base.shiro.ShiroUser;
import com.spring.base.utils.StringUtil;
import com.spring.common.entity.Notice;
import com.spring.common.entity.Teacher;
import com.spring.common.service.NoticeService;
import com.spring.common.service.TbClassService;
import com.spring.common.service.TeacherService;
@Controller
@RequestMapping(value = "/notice")
public class TbNoticeController extends BaseController{

	@Resource
	NoticeService noticeService;
	@Resource
	TbClassService tbClassService;
	@Resource
	TeacherService teacherService;
	
	@RequestMapping(value = "/index/{paper}")
	public String view(HttpServletRequest request,HttpServletResponse response, @PathVariable String paper,
			@RequestParam(value = "tniId", required = false) String tniId){
		if ("list".equals(paper)) {// 跳转到主页面
			
			return "notice/notice_list";
		}
		
		if ("add".equals(paper)) {// 跳转到添加页面
		
			return "notice/notice_add";
		}
		if ("edit".equals(paper)) {// 跳转到编辑页面
			System.out.println("跳转到编辑页面====>");
			Notice notice = noticeService.findById(Long.valueOf(tniId));
			if(notice!=null){
				request.setAttribute("notice", notice);
			}else{
				request.setAttribute("notice", new HashMap<String,Object>());
			}
			
			return "notice/notice_edit";
		}
		
		if("detail".equals(paper)){
			System.out.println("跳转到详情界面=====》");
			Notice notice = noticeService.findById(Long.valueOf(tniId));
			
			if(notice!=null){
				request.setAttribute("notice", notice);
			}else{
				request.setAttribute("notice", new HashMap<String,Object>());
			}
			
			return "notice/notice_detail";
		}

		return "notice/" + paper;
	}
	
	
	/**
	 * 
	 * @Title: list
	 * @Description: 分页显示消息
	 * @param @param request
	 * @param @return 设定文件
	 * @return HashMap<String,Object> 返回类型
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public HashMap<String, Object> list(HttpServletRequest request) {
		/**
		 * 添加分页和查询参数
		 */
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("tniTitle",request.getParameter("tniTitle"));
		params.put("tniType", request.getParameter("tniType"));
		params.put("page", request.getParameter("page"));
		params.put("pageSize", request.getParameter("rows"));
		params.put("sort", request.getParameter("sort"));
		params.put("order", request.getParameter("order"));
		params.put("tmTitle", request.getParameter("tmTitle"));
		params.put("tmAddDate", request.getParameter("tmAddDate"));
		params.put("tmAddDateEnd", request.getParameter("tmAddDateEnd"));
		
		return noticeService.findForJson(params);

	}
	@ResponseBody
	@RequestMapping(value = "/del")
	public String delNotice(HttpServletRequest request){
		System.out.println("notice/del");
		String tniId = request.getParameter("tniId");
		System.err.println("tniId:"+tniId);
		//参数验证
		if(tniId == null){
			return "101";
		}
		
		Notice notice = noticeService.findById(Long.valueOf(tniId));
		List<Notice> listNotice = noticeService.findByTitle(notice.getTniTitle());
		if(notice == null ){
			return "103";
		}
		if(listNotice.size()!=0){
			for (Notice delNotice : listNotice) {
				delNotice.setTniStatus(0);
				noticeService.update(delNotice);
			}
			return "100";
		}
		return "110";
		
	}
	
	/**
	 * 设置或取消置顶
	 * @param request
	 * @param thiId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/setTop")
	public String setTop(HttpServletRequest request){
		System.out.println("notice/top");
		String tniId = request.getParameter("tniId");
		System.err.println("tniId:"+tniId);
		if(tniId == null){
			return "101";
		}
		
		Notice notice = noticeService.findById(Long.valueOf(tniId));
		if(notice==null){
			return "103";
		}
		
		if(notice.getTniTop()==1){//置顶
			notice.setTniTop(0);
		}else if(notice.getTniTop()==0){//不置顶
			notice.setTniTop(1);
		}
		
		try {
			noticeService.update(notice);
			return "100";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "110";
		}
	}
	
	/**
	 * 设置是否头条
	 * @param request
	 * @param thiId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/setHead")
	public String setHead(HttpServletRequest request){
		System.out.println("notice/setHead");
		String tniId = request.getParameter("tniId");
		System.err.println("thiId:"+tniId);
		if(tniId == null){
			return "101";
		}
		
		Notice notice = noticeService.findById(Long.valueOf(tniId));
		if(notice==null){
			return "103";
		}
		System.err.println("head:"+notice.getTniHead());
		if(notice.getTniHead()==1){
			notice.setTniHead(0);
		}else if(notice.getTniHead()==0){
			notice.setTniHead(1);
		}
		
		try {
			noticeService.update(notice);
			return "100";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "110";
		}
	}
	
	/**
	 * 接收人列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/recvPersons")
	public HashMap<String,Object> recvPersons(HttpServletRequest request){
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("tgId",request.getParameter("tgId"));
		params.put("tsfName", request.getParameter("tsfName"));
		params.put("page", request.getParameter("page"));
		params.put("pageSize", request.getParameter("rows"));
		params.put("sort", request.getParameter("sort"));
		params.put("order", request.getParameter("order"));
		params.put("tmTitle", request.getParameter("tmTitle"));
		params.put("tmAddDate", request.getParameter("tmAddDate"));
		params.put("tmAddDateEnd", request.getParameter("tmAddDateEnd"));
		
		return noticeService.findForrecvPersons(params);
	}
	
	/**
	 * 教职工列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/recvTheacher")
	public HashMap<String,Object> recvTheacher(HttpServletRequest request){
		
		HashMap<String, String> params = new HashMap<String, String>();
//		params.put("tgId",request.getParameter("tgId"));
//		params.put("tsfName", request.getParameter("tsfName"));
		params.put("page", request.getParameter("page"));
		params.put("pageSize", request.getParameter("rows"));
		params.put("sort", request.getParameter("sort"));
		params.put("order", request.getParameter("order"));
		params.put("tmTitle", request.getParameter("tmTitle"));
		params.put("tmAddDate", request.getParameter("tmAddDate"));
		params.put("tmAddDateEnd", request.getParameter("tmAddDateEnd"));
		
		return noticeService.findForrecvThacher(params);
	}
	
	/**
	 * 添加公告
	 * @param request
	 * @param notice
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request,Notice notice){
		
		ShiroUser loginUser = (ShiroUser) SecurityUtils.getSubject()
				.getPrincipals().getPrimaryPrincipal();// 获取当前登录用户
		String addId = String.valueOf(loginUser.getId());
		
		String code = StringUtil.formatDate6(new Date());
		
		try {
			if(!"".equals(notice.getTniRecvType())){
			if(Integer.valueOf(notice.getTniRecvType()) == 2||Integer.valueOf(notice.getTniRecvType())==3){//教职工 家长
				String recvPerson = request.getParameter("ids");
				System.err.println("ids:"+recvPerson);
				String[] recvPersons = recvPerson.split(",");
				
			for(int i=0;i<recvPersons.length;i++){
				Notice tice = new Notice();
				tice.setTniTitle(notice.getTniTitle());//公告标题
				tice.setTniType(notice.getTniType());//公告类型
				tice.setTniRecvType(notice.getTniRecvType());//接收类型
				tice.setTniRecvPersons(recvPersons[i]);//接收人
				tice.setTniLogo(notice.getTniLogo());//公告图片
				tice.setTniContent(notice.getTniContent());//公告图片
				tice.setTniAddDate(new Timestamp(new Date().getTime()));//发布时间
				tice.setTniAttachment(notice.getTniAttachment());//附件
				tice.setTniStatus(1);//状态 1正常
				tice.setTniAddUser(Integer.valueOf(addId));//创建人
				tice.setTniTop(0);//置顶
				tice.setTniHead(0);//头条
				tice.setTniCode(code);//编码
				noticeService.save(tice);
				}
			}
			
			if(Integer.valueOf(notice.getTniRecvType())==0){//全部
				List<Teacher> teacher = teacherService.findAll();
				System.out.println("size:"+teacher.size());
				for(int i = 0;i<teacher.size();i++){
					teacher.get(i).getName();
				
				Notice tice = new Notice();
				tice.setTniTitle(notice.getTniTitle());//公告标题
				tice.setTniType(notice.getTniType());//公告类型
				tice.setTniRecvType("0");//接收类型
				tice.setTniRecvPersons(teacher.get(i).getName());//接收人
				tice.setTniLogo(notice.getTniLogo());//公告图片
				tice.setTniContent(notice.getTniContent());//公告图片
				tice.setTniAddDate(new Timestamp(new Date().getTime()));//发布时间
				tice.setTniAttachment(notice.getTniAttachment());//附件
				tice.setTniStatus(1);//状态 1正常
				tice.setTniAddUser(Integer.valueOf(addId));//创建人
				tice.setTniTop(0);//置顶
				tice.setTniHead(0);//头条
				tice.setTniCode(code);//编码
				noticeService.save(tice);
				}
			}
		}else{
			Notice tice = new Notice();
			tice.setTniTitle(notice.getTniTitle());//公告标题
			tice.setTniType(notice.getTniType());//公告类型
			tice.setTniRecvType(0+"");//接收类型
			tice.setTniLogo(notice.getTniLogo());//公告图片
			tice.setTniContent(notice.getTniContent());//公告内容
			tice.setTniAddDate(new Timestamp(new Date().getTime()));//发布时间
			tice.setTniAttachment(notice.getTniAttachment());//附件
			tice.setTniStatus(1);//状态 1正常
			tice.setTniAddUser(Integer.valueOf(addId));//创建人
			tice.setTniTop(0);//置顶
			tice.setTniHead(0);//头条
			tice.setTniCode(code);//编码
			noticeService.save(tice);
		}
			return "100";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "110";
		}
	}
	
	
	/**
	 * 编辑公告
	 * @param request
	 * @param notice
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,Notice notice){
		if(notice != null){
/*			List<Notice> allNotice = noticeService.findAll();
			if(allNotice.size() != 0){
				for(int i=0;i<allNotice.size();i++){
					if(notice.getTniTitle().equals(allNotice.get(i).getTniTitle())){
						return "101";
					}
				}
			}
*/			noticeService.update(notice);
			return "100";
			
		}
		return "";
	}
	
	/**
	 * 接收教职工列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/recvTeacherList")
	public HashMap<String,Object> recvTeacherList(HttpServletRequest request){
		System.out.println("notice/recvList");
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("tniCode", request.getParameter("tniCode"));
		params.put("page", request.getParameter("page"));
		params.put("pageSize", request.getParameter("rows"));
		params.put("sort", request.getParameter("sort"));
		params.put("order", request.getParameter("order"));
		params.put("tmTitle", request.getParameter("tmTitle"));
		params.put("tmAddDate", request.getParameter("tmAddDate"));
		params.put("tmAddDateEnd", request.getParameter("tmAddDateEnd"));
		
		return noticeService.RecvThacher(params);
	}
	
	
	/**
	 * 接收家长列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/recvList")
	public HashMap<String,Object> recvList(HttpServletRequest request){
		System.out.println("notice/recvList");
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("tniCode", request.getParameter("tniCode"));
		System.out.println("tniCode:"+request.getParameter("tniCode"));
		params.put("page", request.getParameter("page"));
		params.put("pageSize", request.getParameter("rows"));
		params.put("sort", request.getParameter("sort"));
		params.put("order", request.getParameter("order"));
		params.put("tmTitle", request.getParameter("tmTitle"));
		params.put("tmAddDate", request.getParameter("tmAddDate"));
		params.put("tmAddDateEnd", request.getParameter("tmAddDateEnd"));
		
		return noticeService.recvList(params);
	}

}
