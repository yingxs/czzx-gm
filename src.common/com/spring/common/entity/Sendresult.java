package com.spring.common.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Sendresult entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sendresult")
public class Sendresult implements java.io.Serializable {

	// Fields

	private Integer id;
	private String mobile;
	private Timestamp addTime;
	private Integer status;

	// Constructors

	/** default constructor */
	public Sendresult() {
	}

	/** full constructor */
	public Sendresult(String mobile, Timestamp addTime, Integer status) {
		this.mobile = mobile;
		this.addTime = addTime;
		this.status = status;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "mobile", nullable = false, length = 20)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "addTime", nullable = false, length = 19)
	public Timestamp getAddTime() {
		return this.addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}