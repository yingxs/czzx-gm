package com.spring.base.util;

import java.util.List;
import java.util.Map;

import com.spring.base.org.json.JSONObject;
import com.spring.base.util.XGPushUtil;
import com.spring.base.xinge.ClickAction;
import com.spring.base.xinge.Message;
import com.spring.base.xinge.MessageIOS;
import com.spring.base.xinge.Style;
import com.spring.base.xinge.TimeInterval;
import com.spring.base.xinge.XingeApp;

public class XGPushUtil {
	//安卓
	public static final long ANDROID_APP_KEY = 2100138644;
	public static final String ANDROID_ACCESS_KEY = "AUE17Y57XB9M";
	public static final String ANDROID_SECRET_KEY = "b900aa55c70b83ca9195f7fe0fa20baa";
	
	//IOS
	public static final long IOS_APP_KEY = 2200132827L;
	public static final String IOS_ACCESS_KEY = "IN5BJ72G85FM";
	public static final String IOS_SECRET_KEY = "7b8b0c6479a04915014539026c532e44";
	
	static XingeApp androidXinge,iosXinge;
	static{
		androidXinge = new XingeApp(ANDROID_APP_KEY, ANDROID_SECRET_KEY);
		iosXinge = new XingeApp(IOS_APP_KEY, IOS_SECRET_KEY);
	}

	//下发IOS单个账号
	public static JSONObject pushSingleAccountIOS(Map<String, Object> custom,String account,String content) {
		MessageIOS message = new MessageIOS();
		message.setExpireTime(86400);
		message.setAlert(content);
		message.setBadge(1);
		message.setSound("beep.wav");
		TimeInterval acceptTime1 = new TimeInterval(0,0,23,59);
		message.addAcceptTime(acceptTime1);
		message.setCustom(custom);
//		XingeApp iosXinge = new XingeApp(IOS_APP_KEY, IOS_SECRET_KEY);
		JSONObject ret = iosXinge.pushSingleAccount(0, account, message, XingeApp.IOSENV_DEV);
		return (ret);
	}
	
	//下发IOS多个账号
	/*public static JSONObject pushAccountListIOS(List<String> accountList,String content) {
		MessageIOS message = new MessageIOS();
		message.setExpireTime(86400);
		message.setAlert(content);
		message.setBadge(1);
		message.setSound("beep.wav");
		JSONObject ret = iosXinge.pushAccountList(0, accountList, message, XingeApp.IOSENV_DEV);
		return (ret);
	}*/
	
	//下发安卓单个账号
	public static JSONObject pushSingleAccount(String account,String title,String content,Map<String, Object> custom) {
		
		Message message = new Message();
		message.setTitle(title);
		message.setType(Message.TYPE_NOTIFICATION);
		Style m_style = new Style(0,1,1,1,1);
		m_style.setSmallIcon("ic_launcher_s");
		m_style.setIconRes("ic_launcher_n");
		message.setStyle(m_style);
		ClickAction action = new ClickAction();
		action.setActionType(ClickAction.TYPE_ACTIVITY);
		//action.setActivity("com.huajian.xiaomianao.activity.MyMessageDetailActivity");
		message.setAction(action);
		message.setType(Message.TYPE_NOTIFICATION);
		message.setCustom(custom);
		message.setContent(content);
		
		JSONObject ret = androidXinge.pushSingleAccount(0, account, message);
		return (ret);
	}
	
	//下发多个账号
/*	public static JSONObject pushAccountList(String title,String content,List<String> accountList,Map<String, Object> custom) {
		
		Message message = new Message();
		message.setTitle(title);
		message.setType(Message.TYPE_NOTIFICATION);
		Style m_style = new Style(0,1,1,1,1);
		m_style.setSmallIcon("ic_launcher_s");
		m_style.setIconRes("ic_launcher_n");
		message.setStyle(m_style);
		ClickAction action = new ClickAction();
		action.setActionType(ClickAction.TYPE_ACTIVITY);
		//action.setActivity("com.renrun.wdzl.activity.MainActivity");
		message.setAction(action);
		message.setType(Message.TYPE_NOTIFICATION);
		message.setCustom(custom);
		message.setContent(content);
		
		JSONObject ret = androidXinge.pushAccountList(0, accountList, message);
		return (ret);
	}*/
	
	//下发所有设备
/*	public static void pushAllDevice(String content,Map<String, Object> custom)
	{
		TimeInterval acceptTime = new TimeInterval(0,0,23,59);
		Message message = new Message();
		message.setTitle("小棉袄");
		message.setType(Message.TYPE_NOTIFICATION);
		Style m_style = new Style(0,1,1,1,1);
		m_style.setSmallIcon("ic_launcher_s");
		m_style.setIconRes("ic_launcher_n");
		message.setStyle(m_style);
		ClickAction action = new ClickAction();
		action.setActionType(ClickAction.TYPE_ACTIVITY);
		action.setActivity("com.huajian.xiaomianao.activity.MyMessageDetailActivity");
		message.setAction(action);
		message.setType(Message.TYPE_NOTIFICATION);
		message.setCustom(custom);
		message.setContent(content);
		androidXinge.pushAllDevice(0, message);
		
		MessageIOS messageIos = new MessageIOS();
		messageIos.setExpireTime(86400);
		messageIos.setAlert(content);
		messageIos.setBadge(0);
		messageIos.setSound("beep.wav");
		messageIos.addAcceptTime(acceptTime);
		messageIos.setCustom(custom);
		JSONObject retIos = iosXinge.pushAllDevice(0, messageIos, XingeApp.IOSENV_DEV);
		System.out.println(retIos);
	}*/

	public static void main(String[] args) {
		//XGPushUtil.pushAllDevice("上的发生发送", null);
		
		//发单个安卓
		JSONObject obj = XGPushUtil.pushSingleAccount("15556531599", "OK", "OK",null);
//		JSONObject obj = XGPushUtil.pushSingleAccount("8F57C12F06912DC0FD51F8B150A8064A", "!!!", "!!!",null);
		System.out.println(obj);	
		
//		XGPushUtil.pushAllDevice("逗比收到了吗!!!",null);		
		
		
		//发单个IOS
//		XGPushUtil.pushSingleAccountIOS(null, "15556531599", "没有");	
//		System.out.println(XGPushUtil.pushSingleAccountIOS(null, "15556531599", "没有"));
	}
}
