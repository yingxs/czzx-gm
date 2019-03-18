package com.spring.common.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Userandgroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "userandgroup")
public class Userandgroup implements java.io.Serializable {

	// Fields

	private Long id;
	private Long userId;
	private Integer groupId;
	private Timestamp addtime;
	private Long addUserId;
	private String addUsername;

	// Constructors

	/** default constructor */
	public Userandgroup() {
	}

	/** minimal constructor */
	public Userandgroup(Long userId, Integer groupId, Timestamp addtime,
			Long addUserId) {
		this.userId = userId;
		this.groupId = groupId;
		this.addtime = addtime;
		this.addUserId = addUserId;
	}

	/** full constructor */
	public Userandgroup(Long userId, Integer groupId, Timestamp addtime,
			Long addUserId, String addUsername) {
		this.userId = userId;
		this.groupId = groupId;
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

	@Column(name = "user_id", nullable = false)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "group_id", nullable = false)
	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	@Column(name = "addtime", nullable = false, length = 19)
	public Timestamp getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	@Column(name = "add_user_id", nullable = false)
	public Long getAddUserId() {
		return this.addUserId;
	}

	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}

	@Column(name = "add_username", length = 50)
	public String getAddUsername() {
		return this.addUsername;
	}

	public void setAddUsername(String addUsername) {
		this.addUsername = addUsername;
	}

}