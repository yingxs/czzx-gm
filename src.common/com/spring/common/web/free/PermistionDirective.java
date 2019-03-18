package com.spring.common.web.free;

import java.io.IOException;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.base.shiro.ShiroUser;
import com.spring.base.shiro.freemarkertag.DirectiveUtils;
import com.spring.base.utils.StringUtil;
import com.spring.common.service.UserandmenuService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;
import freemarker.template.TemplateSequenceModel;

/**
 * @author ylj
 * 2014年10月1日下午1:20:18
 * 权限控制
 * 
 */
@Component("com_auth")
public class PermistionDirective implements TemplateDirectiveModel{
	
	/**
	 * 此url必须和perm中url一致。
	 */
	public static final String PARAM_URL = "url";
	
	public static final String PERMISSION_MODEL = "_permission_key";
	
	@Autowired
	UserandmenuService authorityService;
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute(Environment arg0, Map arg1, TemplateModel[] arg2,
			TemplateDirectiveBody arg3) throws TemplateException, IOException {
		// 此处的权限判断有可能和拦截器的不一致，有没有关系？大部分应该没有关系，因为不需要判断权限的可以不加这个标签。
		// 光一个perms可能还不够，至少还有一个是否只浏览的问题。这个是否可以不管？可以！
		// 是否控制权限这个总是要的吧？perms为null代表无需控制权限。
		String url = DirectiveUtils.getString(PARAM_URL, arg1);
		boolean pass = false;
		if (StringUtil.isEmptyNull(url)) {
			// url为空，则认为有权限。
			pass = true;
		} else {
			/*TemplateSequenceModel perms = getPerms(arg0);
			if (perms == null) {
				// perms为null，则代表不需要判断权限。
				pass = true;
			} else {
				String perm;
				for (int i = 0, len = perms.size(); i < len; i++) {
					perm = ((TemplateScalarModel) perms.get(i)).getAsString();
					if (url.startsWith(perm)) {
						pass = true;
						break;
					}
				}
			}*/
			
			//登陆时查询用户所有权限，这里进行判断，减少数据库操作
			ShiroUser shiroUser = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();//获取当前登录用户
			//根据url判断该用户是否有权限
			pass = authorityService.findByParams(url, shiroUser.getId());
		}
		if (pass) {
			arg3.render(arg0.getOut());
		}
	}
	
	
	private TemplateSequenceModel getPerms(Environment env)
			throws TemplateModelException {
		TemplateModel model = env.getDataModel().get(PERMISSION_MODEL);
		if (model == null) {
			return null;
		}
		if (model instanceof TemplateSequenceModel) {
			return (TemplateSequenceModel) model;
		} else {
			throw new TemplateModelException(
					"'perms' in data model not a TemplateSequenceModel");
		}

	}
}
