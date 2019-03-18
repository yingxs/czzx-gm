package com.spring.base.shiro;

import java.io.Serializable;

import com.spring.common.entity.Userinfo;

public class ShiroUser implements Serializable {

	private static final long serialVersionUID = 1L;

	private java.lang.Long id;
	private java.lang.String name;
	private java.lang.String workno;
	private java.lang.String account;
	private java.lang.String email;
	private java.lang.String sex;

	public ShiroUser() {}

	public ShiroUser(Userinfo user) {
		this.id = user.getId();
		this.account = user.getAccount();
		this.name = user.getName();
		this.email = user.getEmail();
	}

	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	@Override
	public String toString() {
		return this.account;
	}

	/**
	 * 重载equals,只计算account;
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShiroUser other = (ShiroUser) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		return true;
	}

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getWorkno() {
		return workno;
	}

	public void setWorkno(java.lang.String workno) {
		this.workno = workno;
	}

	public java.lang.String getAccount() {
		return account;
	}

	public void setAccount(java.lang.String account) {
		this.account = account;
	}

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
}