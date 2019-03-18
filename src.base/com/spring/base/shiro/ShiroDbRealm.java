package com.spring.base.shiro;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.spring.base.utils.GlobalStatic;
import com.spring.common.entity.Userinfo;
import com.spring.common.service.UserinfoService;

//认证数据库存储
@Component("shiroDbRealm")
public class ShiroDbRealm extends AuthorizingRealm {
	public Logger logger = LoggerFactory.getLogger(getClass());
	@Resource private CacheManager shiroCacheManager;
	@Resource UserinfoService userinfoService;

	public static final String HASH_ALGORITHM = "MD5";
	public static final int HASH_INTERATIONS = 1;
	private static final int SALT_SIZE = 8;

	public ShiroDbRealm() {
		// 认证
		// super.setAuthenticationCacheName(GlobalStatic.authenticationCacheName);
		super.setAuthenticationCachingEnabled(false);
		// 授权
		super.setAuthorizationCacheName(GlobalStatic.authorizationCacheName);
	}

	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principalCollection) {

		// 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()
		// (可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
		if (!SecurityUtils.getSubject().isAuthenticated()) {
			doClearCache(principalCollection);
			SecurityUtils.getSubject().logout();
			return null;
		}

		ShiroUser shiroUser = (ShiroUser) principalCollection.getPrimaryPrincipal();
		// String userId = (String)
		// principalCollection.fromRealm(getName()).iterator().next();
		Long userId = shiroUser.getId();
		if (userId==null) {
			return null;
		}
		// 添加角色及权限信息
		SimpleAuthorizationInfo sazi = new SimpleAuthorizationInfo();
		try {
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}

		return sazi;
	}

	// 认证
	@SuppressWarnings("unused")
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		// 调用业务方法
		Userinfo user = null;
		String userName = upToken.getUsername();
		try {
			Userinfo searchParams = new Userinfo();
			searchParams.setAccount(userName);
			user = userinfoService.findUserinfo(searchParams);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}

		if (user != null) {
			return new SimpleAccount(new ShiroUser(user),user.getPassword(),getName()); 
		}
		// 认证没有通过
		return null;
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
//	@PostConstruct
//	public void initCredentialsMatcher() {
//		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(
//				HASH_ALGORITHM);
//		matcher.setHashIterations(HASH_INTERATIONS);
//
//		setCredentialsMatcher(matcher);
//	}
}
