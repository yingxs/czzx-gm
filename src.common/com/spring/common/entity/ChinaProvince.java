package com.spring.common.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ChinaProvince entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "china_province")
public class ChinaProvince implements java.io.Serializable {

	// Fields

	private Integer pid;
	private String pname;
	private boolean isChecked = false;
	private List<ChinaProvince> children;

	// Constructors


	/**
	 * @return
	 */
	public boolean isChecked() {
		return isChecked;
	}

	public List<ChinaProvince> getChildren() {
		return children;
	}

	public void setChildren(List<ChinaProvince> children) {
		this.children = children;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	/** default constructor */
	public ChinaProvince() {
	}

	/** full constructor */
	public ChinaProvince(String pname) {
		this.pname = pname;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "pid", unique = true, nullable = false)
	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	@Column(name = "pname", length = 100)
	public String getPname() {
		return this.pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

}