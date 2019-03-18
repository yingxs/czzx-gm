package com.spring.common.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Usergroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "usergroup")
public class Usergroup extends BaseEntity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer parentId;
	private String code;
	private String name;
	private String remark;
	private Integer allowEdit;
	private Integer allowDelete;
	private Integer sortcode;
	private Integer status;
	private Long createUserId;
	private String createUserName;
	private Timestamp createTime;
	private Long modifyUserId;
	private String modifyUserName;
	private Timestamp modifyTime;

	// Constructors

	/** default constructor */
	public Usergroup() {
	}

	/** minimal constructor */
	public Usergroup(Integer parentId, String name, Integer allowEdit,
			Integer allowDelete, Integer sortcode, Integer status,
			Long createUserId, String createUserName, Timestamp createTime) {
		this.parentId = parentId;
		this.name = name;
		this.allowEdit = allowEdit;
		this.allowDelete = allowDelete;
		this.sortcode = sortcode;
		this.status = status;
		this.createUserId = createUserId;
		this.createUserName = createUserName;
		this.createTime = createTime;
	}

	/** full constructor */
	public Usergroup(Integer parentId, String code, String name, String remark,
			Integer allowEdit, Integer allowDelete, Integer sortcode,
			Integer status, Long createUserId, String createUserName,
			Timestamp createTime, Long modifyUserId, String modifyUserName,
			Timestamp modifyTime) {
		this.parentId = parentId;
		this.code = code;
		this.name = name;
		this.remark = remark;
		this.allowEdit = allowEdit;
		this.allowDelete = allowDelete;
		this.sortcode = sortcode;
		this.status = status;
		this.createUserId = createUserId;
		this.createUserName = createUserName;
		this.createTime = createTime;
		this.modifyUserId = modifyUserId;
		this.modifyUserName = modifyUserName;
		this.modifyTime = modifyTime;
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

	@Column(name = "parent_id", nullable = false)
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "code", length = 50)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "allow_edit", nullable = false)
	public Integer getAllowEdit() {
		return this.allowEdit;
	}

	public void setAllowEdit(Integer allowEdit) {
		this.allowEdit = allowEdit;
	}

	@Column(name = "allow_delete", nullable = false)
	public Integer getAllowDelete() {
		return this.allowDelete;
	}

	public void setAllowDelete(Integer allowDelete) {
		this.allowDelete = allowDelete;
	}

	@Column(name = "sortcode", nullable = false)
	public Integer getSortcode() {
		return this.sortcode;
	}

	public void setSortcode(Integer sortcode) {
		this.sortcode = sortcode;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "create_user_id", nullable = false)
	public Long getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "create_user_name", nullable = false, length = 50)
	public String getCreateUserName() {
		return this.createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")
	@Column(name = "create_time", nullable = false, length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "modify_user_id")
	public Long getModifyUserId() {
		return this.modifyUserId;
	}

	public void setModifyUserId(Long modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	@Column(name = "modify_user_name", length = 50)
	public String getModifyUserName() {
		return this.modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")
	@Column(name = "modify_time", length = 19)
	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

}