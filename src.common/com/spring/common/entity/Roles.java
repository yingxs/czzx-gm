package com.spring.common.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Roles entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "roles")
public class Roles implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer parentId;
	private String name;
	private String restriction;
	private Integer sortCode;
	private String remark;
	private Integer allowEdit;
	private Integer allowDelete;
	private Integer status;
	private Timestamp addtime;
	private Long addUserId;
	private String addUsername;
	private Timestamp modifytime;
	private Long modifyUserId;
	private String modifyUsername;

	// Constructors

	/** default constructor */
	public Roles() {
	}

	/** minimal constructor */
	public Roles(Integer parentId, String name, Integer sortCode,
			Integer allowEdit, Integer allowDelete, Integer status,
			Timestamp addtime, Long addUserId) {
		this.parentId = parentId;
		this.name = name;
		this.sortCode = sortCode;
		this.allowEdit = allowEdit;
		this.allowDelete = allowDelete;
		this.status = status;
		this.addtime = addtime;
		this.addUserId = addUserId;
	}

	/** full constructor */
	public Roles(Integer parentId, String name, String restriction,
			Integer sortCode, String remark, Integer allowEdit,
			Integer allowDelete, Integer status, Timestamp addtime,
			Long addUserId, String addUsername, Timestamp modifytime,
			Long modifyUserId, String modifyUsername) {
		this.parentId = parentId;
		this.name = name;
		this.restriction = restriction;
		this.sortCode = sortCode;
		this.remark = remark;
		this.allowEdit = allowEdit;
		this.allowDelete = allowDelete;
		this.status = status;
		this.addtime = addtime;
		this.addUserId = addUserId;
		this.addUsername = addUsername;
		this.modifytime = modifytime;
		this.modifyUserId = modifyUserId;
		this.modifyUsername = modifyUsername;
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

	@Column(name = "parentId", nullable = false)
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "restriction", length = 200)
	public String getRestriction() {
		return this.restriction;
	}

	public void setRestriction(String restriction) {
		this.restriction = restriction;
	}

	@Column(name = "sortCode", nullable = false)
	public Integer getSortCode() {
		return this.sortCode;
	}

	public void setSortCode(Integer sortCode) {
		this.sortCode = sortCode;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "allowEdit", nullable = false)
	public Integer getAllowEdit() {
		return this.allowEdit;
	}

	public void setAllowEdit(Integer allowEdit) {
		this.allowEdit = allowEdit;
	}

	@Column(name = "allowDelete", nullable = false)
	public Integer getAllowDelete() {
		return this.allowDelete;
	}

	public void setAllowDelete(Integer allowDelete) {
		this.allowDelete = allowDelete;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	@Column(name = "modifytime", length = 19)
	public Timestamp getModifytime() {
		return this.modifytime;
	}

	public void setModifytime(Timestamp modifytime) {
		this.modifytime = modifytime;
	}

	@Column(name = "modifyUserId")
	public Long getModifyUserId() {
		return this.modifyUserId;
	}

	public void setModifyUserId(Long modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	@Column(name = "modifyUsername", length = 50)
	public String getModifyUsername() {
		return this.modifyUsername;
	}

	public void setModifyUsername(String modifyUsername) {
		this.modifyUsername = modifyUsername;
	}

}