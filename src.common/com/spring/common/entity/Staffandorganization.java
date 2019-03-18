package com.spring.common.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Staffandorganization entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "staffandorganization")
public class Staffandorganization implements java.io.Serializable {

	// Fields

	private Long id;
	private Integer organizationId;
	private Long userId;
	private Timestamp addtime;
	private Long addUserId;
	private String addUsername;

	// Constructors

	/** default constructor */
	public Staffandorganization() {
	}

	/** minimal constructor */
	public Staffandorganization(Integer organizationId, Long userId,
			Timestamp addtime, Long addUserId) {
		this.organizationId = organizationId;
		this.userId = userId;
		this.addtime = addtime;
		this.addUserId = addUserId;
	}

	/** full constructor */
	public Staffandorganization(Integer organizationId, Long userId,
			Timestamp addtime, Long addUserId, String addUsername) {
		this.organizationId = organizationId;
		this.userId = userId;
		this.addtime = addtime;
		this.addUserId = addUserId;
		this.addUsername = addUsername;
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

	@Column(name = "organizationId", nullable = false)
	public Integer getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	@Column(name = "userId", nullable = false)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "addtime", nullable = false, length = 19)
	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	@Column(name = "addUserId", nullable = false)
	public Long getAddUserId() {
		return this.addUserId;
	}

	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}

	@Column(name = "addUsername", length = 50)
	public String getAddUsername() {
		return this.addUsername;
	}

	public void setAddUsername(String addUsername) {
		this.addUsername = addUsername;
	}

}