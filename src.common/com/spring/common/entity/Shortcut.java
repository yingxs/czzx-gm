package com.spring.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Shortcut entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "shortcut")
public class Shortcut implements java.io.Serializable {

	// Fields

	private Long id;
	private Long userId;
	private String name;
	private String url;
	private String target;
	private String icon;
	private String remark;
	private Integer sortCode;

	// Constructors

	/** default constructor */
	public Shortcut() {
	}

	/** minimal constructor */
	public Shortcut(Long userId, Integer sortCode) {
		this.userId = userId;
		this.sortCode = sortCode;
	}

	/** full constructor */
	public Shortcut(Long userId, String name, String url, String target,
			String icon, String remark, Integer sortCode) {
		this.userId = userId;
		this.name = name;
		this.url = url;
		this.target = target;
		this.icon = icon;
		this.remark = remark;
		this.sortCode = sortCode;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "userId", nullable = false)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "url", length = 200)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "target", length = 20)
	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Column(name = "icon", length = 50)
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "sortCode", nullable = false)
	public Integer getSortCode() {
		return this.sortCode;
	}

	public void setSortCode(Integer sortCode) {
		this.sortCode = sortCode;
	}

}